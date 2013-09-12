package grisu.jcommons.processes;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * Project: grisu
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 12/06/13
 * Time: 4:53 PM
 */
abstract class ExternalCommand  {

    public static final Logger myLogger = LoggerFactory
			.getLogger(ExternalCommand.class);

    private static String OS = System.getProperty("os.name").toLowerCase();
    private boolean failed = false;

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

    protected List<String> getCommand() {
        return null;
    }

    abstract protected String getWorkingDirectory();

    private void addMessage(String detail) {
        System.out.println(detail);
    }

    private void addErrorMessage(String error) {
        System.err.println(error);
    }

    protected void execute() {


        Process proc = null;
        ProcessBuilder pb = new ProcessBuilder(getCommand());

        if (StringUtils.isNotBlank(getWorkingDirectory())) {
            pb.directory(new File(getWorkingDirectory()));
        }

        addMessage("Executing command: " + StringUtils.join(getCommand(), " "));
        try {
            proc = pb.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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

            // wait in case stdout/err buffers are not empty yet
            Thread.sleep(500);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String cmd = StringUtils.join(getCommand(), " ");

        if ( proc.exitValue() == 0 ) {
            addMessage("Command " + cmd + " finished");
        } else {
            addMessage("Command " + cmd + " failed");
            setFailed();
        }
    }

    public void setFailed() {
        this.failed = true;
    }

}
