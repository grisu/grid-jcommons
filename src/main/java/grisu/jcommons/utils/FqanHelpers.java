package grisu.jcommons.utils;

import java.util.Collection;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Sets;

/**
 * Helps with the handling of FQANs. Basically just String manipulation.
 * 
 * @author Markus Binsteiner
 * 
 */
public final class FqanHelpers {

	public static String getFullFqan(Set<String> keySet, String uniqueGroupname) {
		return getFullFqan(keySet.toArray(new String[keySet.size()]),
				uniqueGroupname);
	}

	/**
	 * Translate back the unique String from {@link #getUniqueGroupname(String)}
	 * .
	 * 
	 * @param uniqueGroupname
	 *            the unique groupname
	 * @return the full fqan or null if no fqan could be found
	 */
	public static String getFullFqan(String[] allFqans, String groupname) {
		for (final String fqan : allFqans) {
			if (fqan.endsWith(groupname.replace(".", "/"))) {
				return fqan;
			}
		}
		return null;
	}

	public static String getGroupList(final Collection<String> fqans) {

		Set<String> result = Sets.newTreeSet();
		for (String fqan : fqans) {
			result.add(getLastGroupPart(fqan));
		}

		return StringUtils.join(result, "_");

	}

	/**
	 * Parses a fqan for the group.
	 * 
	 * @param fqan
	 *            the fqan
	 * @return the group (something like /APACGrid/NGAdmin)
	 */
	public static String getGroupPart(final String fqan) {
		String groupPart = null;
		try {
			final int index = fqan.indexOf("/Role=");
			if (index == -1) {
				groupPart = fqan;
			} else {
				groupPart = fqan.substring(0, fqan.indexOf("/Role="));
			}
		} catch (final Exception e) {
			return null;
		}
		return groupPart;
	}

	/**
	 * Parses a fqan for the last part of the group (/APACGrid/NGAdmin would
	 * return NGAdmin).
	 * 
	 * @param fqan
	 *            the fqan
	 * @return the name of the last subgroup
	 */
	public static String getLastGroupPart(final String fqan) {
		final String group = getGroupPart(fqan);
		return group.substring(group.lastIndexOf("/") + 1);
	}

	/**
	 * Parses the fqan for the role part.
	 * 
	 * @param fqan
	 *            the fqan
	 * @return the role (something like Member)
	 */
	public static String getRolePart(final String fqan) {

		String role = null;
		try {
			role = fqan.substring(fqan.indexOf("/Role=") + 6,
					fqan.indexOf("/Capability="));
		} catch (final Exception e) {
			return null;
		}
		return role;
	}

	public static String getUniqueGroupname(Set<String> keySet, String fqan) {
		return getUniqueGroupname(keySet.toArray(new String[keySet.size()]),
				fqan);
	}

	/**
	 * This method translates the provided fqan into the shortest possible
	 * unique groupname, starting from the last token.
	 * 
	 * For example, if a user is a member of the following VOs:<br>
	 * :/ARCS<br>
	 * :/ARCS/BeSTGRID<br>
	 * :/ARCS/BeSTGRID/Bio/<br>
	 * :/ARCS/BeSTGRID/Bio/Project<br>
	 * :/ARCS/BeSTGRID/Bio/Project2<br>
	 * :/ARCS/BeSTGRID/Project<br>
	 * :/ARCS/BeSTGRID/Drugs/Project<br>
	 * <br>
	 * this would be the result:<br>
	 * :/ARCS/BeSTRID -> BeSTGRID :/ARCS/BeSTGRID/Bio/Project2 -> Project2
	 * :/ARCS/BeSTGRID/Bio/Project -> Bio/Project :/ARCS/BeSTGRID/Project ->
	 * BeSTGRID/Project :/ARCS/BeSTGRID/Drugs/Project -> Drugs/Project
	 * 
	 * @param fqan
	 *            the fqan to shorten
	 * @return the short, unique group name
	 */
	public static String getUniqueGroupname(String[] allFqans, String fqan) {

		final String[] tokens = fqan.split("/");

		boolean unique = true;
		String token = "";

		for (int i = tokens.length - 1; i >= 0; i--) {

			unique = true;
			if (StringUtils.isBlank(token)) {
				token = tokens[i];
			} else {
				token = tokens[i] + "." + token;
			}

			for (final String f : allFqans) {
				if (f.equals(fqan)) {
					continue;
				}
				if (f.endsWith(token)) {
					unique = false;
					break;
				}
			}

			if (unique) {
				return token;
			}

		}

		return null;

	}

	public static void main(String[] args) {

		final String[] VOS = new String[] { "/ARCS", "/ARCS/BeSTGRID",
				"/ARCS/BeSTGRID/Project", "/ARCS/BeSTGRID/Bio",
				"/ARCS/BeSTGRID/Bio/Project", "/ARCS/BeSTGRID/Bio/Project2",
				"/ARCS/BeSTGRID/Drugs", "/ARCS/BeSTGRID/Drugs/Project",
				"/ARCS/BeSTGRID/Drugs/Project3", "/ARCS/BeSTGRID/Other" };

		String unique = getUniqueGroupname(VOS, "/ARCS");
		System.out.println("/ARCS -> " + unique);
		System.out.println("...and back: " + getFullFqan(VOS, unique));

		System.out.println();

		unique = getUniqueGroupname(VOS, "/ARCS/BeSTGRID");
		System.out.println("/ARCS/BeSTGRID -> " + unique);
		System.out.println("...and back: " + getFullFqan(VOS, unique));

		System.out.println();

		unique = getUniqueGroupname(VOS, "/ARCS/BeSTGRID/Project");
		System.out.println("/ARCS/BeSTGRID/Project -> " + unique);
		System.out.println("...and back: " + getFullFqan(VOS, unique));

		System.out.println();

		unique = getUniqueGroupname(VOS, "/ARCS/BeSTGRID/Bio/Project");
		System.out.println("/ARCS/BeSTGRID/Bio/Project -> " + unique);
		System.out.println("...and back: " + getFullFqan(VOS, unique));

		System.out.println();

		unique = getUniqueGroupname(VOS, "/ARCS/BeSTGRID/Bio/Project2");
		System.out.println("/ARCS/BeSTGRID/Bio/Project2 -> " + unique);
		System.out.println("...and back: " + getFullFqan(VOS, unique));

		System.out.println();

		unique = getUniqueGroupname(VOS, "/ARCS/BeSTGRID/Drugs/Project");
		System.out.println("/ARCS/BeSTGRID/Drugs/Project -> " + unique);
		System.out.println("...and back: " + getFullFqan(VOS, unique));

		System.out.println();

		unique = getUniqueGroupname(VOS, "/ARCS/BeSTGRID/Drugs/Project3");
		System.out.println("/ARCS/BeSTGRID/Drugs/Project3 -> " + unique);
		System.out.println("...and back: " + getFullFqan(VOS, unique));

		System.out.println();

		unique = getUniqueGroupname(VOS, "/ARCS/BeSTGRID/Other");
		System.out.println("/ARCS/BeSTGRID/Other -> " + unique);
		System.out.println("...and back: " + getFullFqan(VOS, unique));

	}

}
