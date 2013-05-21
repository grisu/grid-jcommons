package grisu.model.info.dto;

import grisu.jcommons.constants.Constants;
import grisu.jcommons.utils.EndpointHelpers;

import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Objects;

@XmlRootElement(name = "directory")
public class Directory implements Comparable<Directory> {

	public static boolean isShared(Directory d) {
		String shared = d.getOptions().readProperty(
				Constants.INFO_DIRECTORY_SHARED_KEY);

		return Boolean.parseBoolean(shared);
	}

	public static boolean isVolatileDirectory(Directory d) {
		String vol = d.getOptions()
				.readProperty(Constants.INFO_IS_VOLATILE_KEY);

		return Boolean.parseBoolean(vol);
	}

	public static String getOption(Directory d, String key) {
		String val = d.getOptions().readProperty(key);
		return val;
	}

	public static String getRelativePath(Directory d, String url) {
		if (EndpointHelpers.isGlobusOnlineUrl(url)) {
			String username = EndpointHelpers.extractUsername(url);
			String epName = EndpointHelpers.extractEndpointName(url);

			if (!epName
					.equals(EndpointHelpers.extractEndpointName(d.getAlias()))) {
				throw new IllegalStateException(
						"Url not in this directory filespace.");
			}

			String otherPath = EndpointHelpers.extractPathPart(url);

			if (!otherPath.startsWith(d.getPath())) {
				throw new IllegalStateException(
						"Url not in this directory filespace.");
			}

			return otherPath.substring(d.getPath().length());

		} else {

			String thisUrl = d.toUrl();

			if (!url.startsWith(thisUrl)) {
				throw new IllegalStateException(
						"Url not in this directory filespace.");
			}

			return url.substring(thisUrl.length());

		}
	}

	private FileSystem filesystem;
	private Set<Group> groups;
	private String host;

	private String path;
	private Site site;

	private String alias;

	private DtoProperties options;

	@Override
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

	@XmlElement(name = "alias")
	public String getAlias() {
		return alias;
	}

	@XmlElement(name = "filesystem")
	public FileSystem getFilesystem() {
		return filesystem;
	}

	@XmlElement(name = "groups")
	public Set<Group> getGroups() {
		return groups;
	}

	@XmlElement(name = "host")
	public String getHost() {
		return host;
	}

	@XmlElement(name = "options")
	public DtoProperties getOptions() {
		return options;
	}

	@XmlElement(name = "path")
	public String getPath() {
		return path;
	}

	@XmlElement(name = "site")
	public Site getSite() {
		return site;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(filesystem, path, groups);
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setFilesystem(FileSystem filesystem) {
		this.filesystem = filesystem;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setOptions(DtoProperties options) {
		this.options = options;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public String toUrl() {
		return filesystem.toString() + path;
	}

	@Override
	public String toString() {
		return toUrl();
	}

}
