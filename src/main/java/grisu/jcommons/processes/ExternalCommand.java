package grisu.jcommons.processes;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Project: grisu
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 12/06/13
 * Time: 4:53 PM
 */
public class ExternalCommand  {

    public static final Logger myLogger = LoggerFactory
			.getLogger(ExternalCommand.class);

    private static String OS = System.getProperty("os.name").toLowerCase();
    private boolean failed = false;
    private String workingDirectory;

    private boolean started = false;
    private boolean finished = false;

    public boolean isStarted() {
        return started;
    }

    public boolean isFinished() {
        return finished;
    }

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private final List<String> stdout = Collections.synchronizedList(new ArrayList<String>());
    private String lastStdoutMessage = null;
    private final List<String> stderr = Collections.synchronizedList(new ArrayList<String>());
    private String lastStderrMessage = null;
    private final List<String> log = Collections.synchronizedList(new ArrayList<String>());
    private String lastLogMessage = null;

    public static void main(String[] args) {

        List<String> command = Lists.newArrayList();
        command.add("sh");
        command.add("test_script.sh");



        ExternalCommand ec = new ExternalCommand(command);
        ec.setWorkingDirectory("/home/markus/local/bin");
        ec.execute();

        System.out.println(StringUtils.join(ec.getStdout(), "\n"));

    }

    public List<String> getStdout() {
        return stdout;
    }

    public List<String> getStderr() {
        return stderr;
    }

    public static boolean isWindows() {

        return (OS.indexOf("win") >= 0);

    }

    public static boolean isMac() {

        return (OS.indexOf("mac") >= 0);

    }

    public static boolean isUnix() {

        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );

    }

    public static boolean isSolaris() {

        return (OS.indexOf("sunos") >= 0);

    }

    private final List<String> command;

    public ExternalCommand(List<String> command) {
        this.command = command;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }

    protected List<String> getCommand() {
        return command;
    }

    protected String getWorkingDirectory() {
        return workingDirectory;
    }

    private void addMessage(String detail) {
        String old = lastStdoutMessage;
        stdout.add(detail);
        pcs.firePropertyChange("lastMessage", old, detail);
    }

    private void addLogMessage(String detail) {
        String old = lastLogMessage;
        log.add(detail);
        pcs.firePropertyChange("lastLogMessage", old, detail);
    }

    private void addErrorMessage(String error) {
        String old = lastStderrMessage;
        stderr.add(error);
        pcs.firePropertyChange("lastErrorMessage", old, error);
    }

    public String getLastStdoutMessage() {
        return lastStdoutMessage;
    }

    public String getLastStderrMessage() {
        return lastStderrMessage;
    }

    public String getLastLogMessage() {
        return lastLogMessage;
    }

    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    public void execute() {

        Process proc = null;
        ProcessBuilder pb = new ProcessBuilder(getCommand());

        if (StringUtils.isNotBlank(getWorkingDirectory())) {
            pb.directory(new File(getWorkingDirectory()));
        }

        addLogMessage("Executing command: " + StringUtils.join(getCommand(), " "));
        try {
            proc = pb.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        started = true;
        pcs.firePropertyChange("started", false, true);

        //proc = getCommand().execute();

        InputStream is = proc.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        final BufferedReader br = new BufferedReader(isr);
        new Thread() {
            public void run() {
                String line;
                try {
                    while ((line = br.readLine()) != null) {
                        addMessage(line);
                    }
                } catch (IOException e) {
                    myLogger.debug(e.getLocalizedMessage());
                }
            }
        }.start();
        InputStream es = proc.getErrorStream();
        InputStreamReader esr = new InputStreamReader(es);
        final BufferedReader ebr = new BufferedReader(esr);
        new Thread() {
            public void run() {
                String line;
                try {
                    while ((line = ebr.readLine()) != null) {
                        addErrorMessage(line);
                    }
                } catch (IOException e) {
                    myLogger.debug(e.getLocalizedMessage());
                }
            }
        }.start();

        try {
            proc.waitFor();
            finished = true;
            pcs.firePropertyChange("finished", false, true);
            // wait in case stdout/err buffers are not empty yet
            Thread.sleep(500);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String cmd = StringUtils.join(getCommand(), " ");

        if ( proc.exitValue() == 0 ) {
            addLogMessage("Command " + cmd + " finished");
        } else {
            addLogMessage("Command " + cmd + " failed");
            setFailed();
        }
    }

    public void setFailed() {
        this.failed = true;
        pcs.firePropertyChange("failed", false, true);
    }

    public boolean isFailed() {
        return failed;
    }
}
