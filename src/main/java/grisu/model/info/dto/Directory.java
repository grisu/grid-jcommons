package grisu.model.info.dto;

import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "directory")
public class Directory {

	private FileSystem filesystem;
	private Set<Group> groups;
	private String host;

	private String path;
	private Site site;

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
	public void setSite(Site site) {
		this.site = site;
	}

}
