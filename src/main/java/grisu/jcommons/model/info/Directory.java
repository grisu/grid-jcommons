package grisu.jcommons.model.info;

import org.apache.commons.lang.StringUtils;
import org.bestgrid.goji.utils.EndpointHelpers;


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

	private final FileSystem filesystem;
	private final String path;
	private final String fqan;
	private final String alias;

	public Directory(FileSystem fs, String path, String fqan, String alias) {
		this.filesystem = fs;
		this.path = path;
		this.fqan = fqan;
		if (StringUtils.isBlank(alias)) {
			alias = EndpointHelpers.translateIntoEndpointName(fs.getHost(),
					fqan);
		}
		this.alias = alias;
	}

	public int compareTo(Directory o) {

		int r = filesystem.compareTo(o.getFilesystem());
		if (r == 0) {
			r = path.compareTo(o.getPath());
			if (r == 0) {
				r = fqan.compareTo(o.getFqan());
			}
		}
		return r;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Directory) {
			Directory other = (Directory) o;

			if (getFilesystem().equals(other.getFilesystem())
					&& path.equals(other.getPath())
					&& fqan.equals(other.getFqan())) {
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

	public String getFqan() {
		return fqan;
	}

	public String getPath() {
		return path;
	}

	@Override
	public int hashCode() {
		return filesystem.hashCode() + path.hashCode() + fqan.hashCode();
	}

	@Override
	public String toString() {
		return filesystem.toString() + "/" + path;
	}

}
