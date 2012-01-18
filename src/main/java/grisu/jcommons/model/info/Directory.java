package grisu.jcommons.model.info;

import java.util.Collection;
import java.util.Set;

import org.bestgrid.goji.utils.EndpointHelpers;

import com.google.common.collect.Sets;

/**
 * A Directory is an object that points to a url in grid-space (consisting of a
 * {@link FileSystem} and a path.
 * 
 * It also contains information on which VO is needed to access the url.
 * 
 * @author Markus Binsteiner
 * 
 */
public class Directory implements Comparable<Directory> {

	private static String fixMdsLegacies(String path) {

		path = path.replace("${GLOBUS_USER_HOME}", "~");

		int i = path.indexOf("[");
		if (i > 0) {
			path = path.substring(0, i);
		}

		return path;
	}

	private static String slash(String path) {

		if (!path.startsWith("/")) {
			path = "/" + path;
		}

		if (!path.endsWith("/")) {
			return path + "/";
		} else {
			return path;
		}
	}

	private FileSystem filesystem;
	private String path;
	private Collection<Group> groups;
	private String alias;

	public Directory() {

	}

	public Directory(FileSystem fs, String path, Set<Group> fqans, String alias) {
		this.filesystem = fs;
		if (path.endsWith("/")) {
			this.path = slash(fixMdsLegacies(path.substring(0,
					path.length() - 1)));
		} else {
			this.path = slash(fixMdsLegacies(path));
		}

		this.groups = Sets.newTreeSet(fqans);
		// if (StringUtils.isBlank(alias)) {
		// alias = EndpointHelpers.translateIntoEndpointName(fs.getHost(),
		// fqan.getFqan());
		// }
		this.alias = alias;
	}

	public int compareTo(Directory o) {

		int r = filesystem.compareTo(o.getFilesystem());
		if (r == 0) {
			r = path.compareTo(o.getPath());
			if (r == 0) {

				// TODO compare both groups
				// r = groups.compareTo(o.getGroups());
			}
		}
		return r;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Directory) {
			Directory other = (Directory) o;

			if (getFilesystem().equals(other.getFilesystem())
					&& path.equals(other.getPath())) {
				// TODO equals for groups
				// && groups.equals(other.getGroup())) {
				return true;
			}
		}
		return false;
	}

	public String getAlias() {
		return alias;
	}

	public FileSystem getFilesystem() {
		return filesystem;
	}

	public Collection<Group> getGroups() {
		return groups;
	}

	public String getPath() {
		return path;
	}

	public String getRelativePath(String url) {
		if (EndpointHelpers.isGlobusOnlineUrl(url)) {
			String username = EndpointHelpers.extractUsername(url);
			String epName = EndpointHelpers.extractEndpointName(url);

			if (!epName.equals(EndpointHelpers.extractEndpointName(alias))) {
				throw new IllegalStateException(
						"Url not in this directory filespace.");
			}

			String otherPath = EndpointHelpers.extractPathPart(url);

			if (!otherPath.startsWith(path)) {
				throw new IllegalStateException(
						"Url not in this directory filespace.");
			}

			return otherPath.substring(path.length());

		} else {

			String thisUrl = getUrl();

			if (!url.startsWith(thisUrl)) {
				throw new IllegalStateException(
						"Url not in this directory filespace.");
			}

			return url.substring(thisUrl.length());

		}
	}

	public String getUrl() {
		return filesystem.toString() + path;
	}

	@Override
	public int hashCode() {
		return filesystem.hashCode() + path.hashCode() + groups.hashCode();
	}

	@Override
	public String toString() {
		return getUrl();
	}

}
