package grisu.model.info.dto;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

@XmlRootElement(name = "list")
public class DtoStringList {

	public static DtoStringList fromSingleString(String string) {

		if (StringUtils.isBlank(string)) {
			return new DtoStringList();
		}

		final DtoStringList result = new DtoStringList();
		final List<String> list = new LinkedList<String>();
		list.add(string);
		result.setStringList(list);
		return result;
	}

	public static DtoStringList fromString(String string) {
		return fromSingleString(string);
	}

	public static DtoStringList fromStringArray(String[] array) {

		final DtoStringList result = new DtoStringList();
		result.setStringList(Arrays.asList(array));

		return result;

	}

	public static DtoStringList fromStringColletion(Collection<String> list) {

		final DtoStringList result = new DtoStringList();
		result.setStringList(new LinkedList(list));

		return result;

	}

	public static DtoStringList fromStringList(List<String> list) {

		final DtoStringList result = new DtoStringList();
		result.setStringList(list);

		return result;

	}

	private List<String> stringList = new LinkedList<String>();

	public String[] asArray() {
		if (this.stringList != null) {
			return this.stringList.toArray(new String[] {});
		} else {
			return null;
		}
	}

	public SortedSet<String> asSortedSet() {
		if (this.stringList != null) {
			return new TreeSet<String>(this.stringList);
		} else {
			return null;
		}
	}

	@XmlElement(name = "listElement")
	public List<String> getStringList() {
		return stringList;
	}

	public void setStringList(List<String> stringList) {
		this.stringList = stringList;
	}

}
