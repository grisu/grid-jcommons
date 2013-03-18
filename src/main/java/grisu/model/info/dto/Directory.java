package grisu.model.info.dto;

import grisu.jcommons.constants.Constants;

import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "directory")
public class Directory {

	public static boolean isShared(Directory d) {
		String shared = d.getOptions().get(Constants.INFO_DIRECTORY_SHARED_KEY);

		return Boolean.parseBoolean(shared);
	}

	public static boolean isVolatileDirectory(Directory d) {
		String vol = d.getOptions().get(Constants.INFO_IS_VOLATILE_KEY);

		return Boolean.parseBoolean(vol);
	}

	private FileSystem filesystem;
	private Set<Group> groups;
	private String host;

	private String path;
	private Site site;

	private Map<String, String> options;

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
	public Map<String, String> getOptions() {
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

	public void setFilesystem(FileSystem filesystem) {
		this.filesystem = filesystem;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setOptions(Map<String, String> options) {
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

}
