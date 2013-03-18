package grisu.model.info.dto;

import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "directory")
public class Directory {

	public static boolean isShared(Directory d) {
		return true;
	}

	public static boolean isVolatileDirectory(Directory d) {
		return true;
	}

	private FileSystem filesystem;
	private Set<Group> groups;
	private String host;

	private String path;
	private Site site;

	private boolean isShared;
	private boolean isVolatileDirectory;

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

	@XmlElement(name = "path")
	public String getPath() {
		return path;
	}

	@XmlElement(name = "site")
	public Site getSite() {
		return site;
	}

	@XmlElement(name = "shared")
	public boolean isShared() {
		return isShared;
	}

	@XmlElement(name = "volatileDirectory")
	public boolean isVolatileDirectory() {
		return isVolatileDirectory;
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

	public void setPath(String path) {
		this.path = path;
	}

	public void setShared(boolean isShared) {
		this.isShared = isShared;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public void setVolatileDirectory(boolean isVolatileDirectory) {
		this.isVolatileDirectory = isVolatileDirectory;
	}

	@Override
	public String toString() {
		return toUrl();
	}

	public String toUrl() {
		return filesystem.toString() + path;
	}

}
