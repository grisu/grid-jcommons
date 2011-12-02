package grisu.jcommons.view.cli;

public interface ProgressDisplay {

	public void setIndeterminateProgress(boolean start);

	public void setIndeterminateProgress(final String message, boolean start);

	public void setProgress(int completed, int total);

}
