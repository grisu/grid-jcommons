package grisu.jcommons.model.info;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

/**
 * Wrapper class to access and get information about remote files.
 * 
 * Nicer than having to work with the JSON object GlobusOnline returns
 * directly...
 * 
 * @author Markus Binsteiner
 * 
 */
public class GFile implements Comparable<GFile> {

	enum Type {
		FILE,
		DIRECTORY
	}

	DateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssz");

	private final String name;
	private final String group;
	private final String user;
	private final long size;
	private final long last_modified;

	private final String permissions;

	private final Type type;

	public GFile(JSONObject o) throws Exception {
		this.name = o.getString("name");
		String typeS = o.getString("type");
		if ( "file".equals(typeS) ) {
			this.type = Type.FILE;
		} else {
			this.type = Type.DIRECTORY;
		}
		user = o.getString("user");
		group = o.getString("group");

		String sizeS = o.getString("size");
		size = Long.parseLong(sizeS);

		String last_modifiedS = o.getString("last_modified");
		last_modifiedS = new StringBuffer(last_modifiedS).delete(
				last_modifiedS.length() - 3, last_modifiedS.length() - 2)
				.toString();
		Date date = DATE_FORMATTER.parse(last_modifiedS);
		last_modified = date.getTime();

		permissions = o.getString("permissions");

	}

	public GFile(String name, Type type, String user, String group, long size,
			long last_modified, String permissions) {
		this.name = name;
		this.type = type;
		this.user = user;
		this.group = group;
		this.size = size;
		this.last_modified = last_modified;
		this.permissions = permissions;
	}

	public int compareTo(GFile o) {

		int i = getType().compareTo(o.getType());
		if ( i == 0 ) {
			return getName().compareTo(o.getName());
		} else {
			return i;
		}

	}

	@Override
	public boolean equals(Object other) {

		if (other instanceof GFile) {
			GFile o = (GFile) other;
			if (StringUtils.equals(name, o.getName())
					&& StringUtils.equals(group, o.getGroup())
					&& StringUtils.equals(user, o.getUser())
					&& (size == o.getSize())
					&& (last_modified == o.getSize())
					&& (type == o.getType())) {
				return true;
			}
		}
		return false;

	}

	public String getGroup() {
		return group;
	}

	public long getLast_modified() {
		return last_modified;
	}

	public String getName() {
		return name;
	}

	public String getPermissions() {
		return permissions;
	}
	public long getSize() {
		return size;
	}
	public Type getType() {
		return type;
	}

	public String getUser() {
		return user;
	}

	@Override
	public int hashCode() {
		return 44 * (name.hashCode() + (group != null ? group.hashCode() : -99)
				+ (user != null ? user.hashCode() : -77) + type.hashCode()
				+ (permissions != null ? permissions.hashCode() : 32)
				+ new Long(last_modified).intValue() + new Long(size)
		.intValue());

	}

}
