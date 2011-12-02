package grisu.jcommons.view.cli;

public class LineByLineProgressDisplay implements ProgressDisplay {

	public void setIndeterminateProgress(boolean start) {
		// doing nothing
	}

	public void setIndeterminateProgress(String message, boolean start) {
		System.out.println(message);
	}

	public void setProgress(int completed, int total) {
		System.out.println("Progress: " + completed + " / " + total);
	}

}
