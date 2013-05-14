package grisu.jcommons.git;

import grisu.jcommons.constants.GridEnvironment;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GitRepoUpdater {

	public static void main(String[] args) {

		String url = "git://github.com/nesi/nesi-grid-info.git/nesi_info.groovy";

		System.out.println(getRepoPart(url));
		System.out.println("x" + getPathPart(url) + "x");

		File file = ensureUpdated(url);

		System.out.println(file.getAbsolutePath());

		System.out.println(getLocalBaseDir(url));
	}

	private static String getRepoPart(String gitUrl) {
		int index = gitUrl.indexOf(".git") + 4;
		return gitUrl.substring(0, index);
	}

	private static String getPathPart(String gitUrl) {
		int index = gitUrl.indexOf(".git") + 4;
		return gitUrl.substring(index);

	}

	/**
	 * Replaces all special charactes in a url with "_" in order to be able to
	 * store it in the local cache (in .grisu/cache).
	 * 
	 * @param url
	 *            the url
	 * @return the "clean" string
	 */
	private static String get_url_string_path(final String url) {
		return url.replace("=", "_").replace(",", "_").replace(" ", "_")
				.replace(":", "").replace("//", File.separator)
				.replace("/", File.separator);
	}

	private final static Logger myLogger = LoggerFactory
			.getLogger(GitRepoUpdater.class);

	public static File ensureUpdated(String remotePath) {

		File localPath = null;
		Repository localRepo = null;
		Git git = null;

		String remoteRepo = getRepoPart(remotePath);

		localPath = new File(GridEnvironment.getGridCommonCacheDirectory(),
				get_url_string_path(remoteRepo));

		if (!localPath.exists()) {
			try {
				Git.cloneRepository().setURI(remoteRepo)
						.setDirectory(localPath).call();
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new RuntimeException(e1);
			}
		}

		try {
			localRepo = new FileRepository(localPath + "/.git");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		git = new Git(localRepo);

		PullCommand pullCmd = git.pull();
		try {
			pullCmd.call();
		} catch (GitAPIException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return new File(localPath, getPathPart(remotePath));
	}

	public static String getLocalBaseDir(String giturl) {
		return GridEnvironment.getGridCommonCacheDirectory() + File.separator
				+ get_url_string_path(getRepoPart(giturl));
	}
}
