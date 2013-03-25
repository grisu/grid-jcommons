package grisu.model.info.dto;

import grisu.jcommons.constants.JobSubmissionProperty;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

/**
 * This is mostly a wrapper class that makes it possible to send Maps over the
 * wire.
 * 
 * JAXB does not seem to support Maps natively. This implementation is not
 * particularly fast, not sure whether it's necessary to optimize.
 * 
 * @author Markus Binsteiner
 * 
 */
@XmlRootElement(name = "properties")
public class DtoProperties {

	public static DtoProperties createProperties(
			Map<String, String> userProperties) {

		final DtoProperties result = new DtoProperties();

		final List<DtoProperty> list = new LinkedList<DtoProperty>();
		for (final String key : userProperties.keySet()) {
			final DtoProperty temp = new DtoProperty();
			temp.setKey(key);
			temp.setValue(userProperties.get(key));
			list.add(temp);
		}
		result.setProperties(list);

		return result;
	}

	public static DtoProperties createPropertiesFromJobSubmissionProperties(
			Map<JobSubmissionProperty, String> jobProps) {
		final DtoProperties result = new DtoProperties();

		final List<DtoProperty> list = new LinkedList<DtoProperty>();
		for (final JobSubmissionProperty key : jobProps.keySet()) {
			final DtoProperty temp = new DtoProperty();
			temp.setKey(key.toString());
			temp.setValue(jobProps.get(key));
			list.add(temp);
		}
		result.setProperties(list);

		return result;
	}

	public static DtoProperties createUserPropertiesIntegerValue(
			Map<String, Integer> userProperties) {

		if (userProperties == null) {
			return null;
		}

		final DtoProperties result = new DtoProperties();

		final List<DtoProperty> list = new LinkedList<DtoProperty>();
		for (final String key : userProperties.keySet()) {
			final DtoProperty temp = new DtoProperty();
			temp.setKey(key);
			temp.setValue(userProperties.get(key).toString());
			list.add(temp);
		}
		result.setProperties(list);

		return result;
	}

	public static DtoProperties fromString(String dummy) {
		return new DtoProperties();
	}

	/**
	 * The list of user properties.
	 */
	private List<DtoProperty> properties = new LinkedList<DtoProperty>();

	final private Set<DtoProperty> tempPropSet = Sets.newLinkedHashSet();
	private final Set<String> tempKeySet = Sets.newLinkedHashSet();
	final private Set<String> tempValueSet = Sets.newLinkedHashSet();

	public void addProperty(String key, String value) {
		properties.add(new DtoProperty(key, value));
	}

	public Map<JobSubmissionProperty, String> asJobPropertiesMap() {

		final Map<JobSubmissionProperty, String> converterMap = new HashMap<JobSubmissionProperty, String>();
		for (final DtoProperty jp : getProperties()) {
			converterMap.put(JobSubmissionProperty.fromString(jp.getKey()),
					jp.getValue());
		}

		return converterMap;
	}

	@XmlElement(name = "userproperty")
	public List<DtoProperty> getProperties() {
		return properties;
	}

	public static Map<String, String> asMap(DtoProperties props) {

		if (props == null) {
			return null;
		}

		final Map<String, String> map = new HashMap<String, String>();

		for (final DtoProperty prop : props.getProperties()) {
			map.put(prop.getKey(), prop.getValue());
		}

		return map;
	}

	public String readProperty(String key) {
		DtoProperty prop = readDtoProperty(key);
		if (prop == null) {
			return null;
		} else {
			return prop.getValue();
		}
	}

	public DtoProperty readDtoProperty(String key) {
		for (DtoProperty prop : getProperties()) {
			if (prop.getKey().equals(key)) {
				return prop;
			}
		}
		return null;
	}

	public void setProperties(List<DtoProperty> properties) {
		this.tempKeySet.clear();
		this.tempValueSet.clear();
		this.tempPropSet.clear();
		if (properties == null) {
			this.properties = new LinkedList<DtoProperty>();
		} else {
			this.properties = properties;
			for (DtoProperty prop : this.properties) {
				tempPropSet.add(prop);
				tempKeySet.add(prop.getKey());
				tempValueSet.add(prop.getValue());
			}
		}
	}

	public void clear() {
		getProperties().clear();
	}

	public boolean containsKey(Object key) {

		if (!(key instanceof String)) {
			return false;
		}

		for (final DtoProperty prop : getProperties()) {
			if (prop.getKey().equals(key)) {
				return true;
			}
		}
		return false;
	}

	public boolean containsValue(Object value) {
		if (!(value instanceof String)) {
			return false;
		}
		for (final DtoProperty prop : getProperties()) {
			if (prop.getValue().equals(value)) {
				return true;
			}
		}
		return false;
	}

	public Set entrySet() {
		return tempPropSet;
	}

	public Object get(Object key) {
		if (!(key instanceof String)) {
			return null;
		}
		return readProperty((String) key);
	}

	public boolean isEmpty() {
		if (getProperties().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public Set keySet() {

		return tempKeySet;
	}

	private synchronized void recreateValueSet() {
		tempValueSet.clear();
		for (DtoProperty p : getProperties()) {
			tempValueSet.add(p.getValue());
		}
	}

	public synchronized Object put(Object key, Object value) {
		if (!(key instanceof String)) {
			throw new RuntimeException("Key is not a string");
		}
		if (!(value instanceof String)) {
			throw new RuntimeException("Value is not a string");
		}
		String old = null;
		DtoProperty prop = readDtoProperty((String) key);
		if (prop == null) {
			prop = new DtoProperty((String) key, (String) value);
			getProperties().add(prop);
			tempPropSet.add(prop);
			tempKeySet.add(prop.getKey());
			tempValueSet.add(prop.getValue());
		} else {
			old = prop.getValue();
			prop.setValue((String) value);
			recreateValueSet();
		}

		return old;
	}

	public void putAll(Map m) {

		for (Object o : m.keySet()) {
			this.put(o, m.get(o));
		}

	}

	public Object remove(Object key) {
		if (!(key instanceof String)) {
			throw new RuntimeException("Key not instance of String: "
					+ key.toString());
		}
		DtoProperty prop = readDtoProperty((String) key);
		if (prop == null) {
			return null;
		} else {
			getProperties().remove(prop);
			return prop.getValue();
		}
	}

	public int size() {
		return getProperties().size();
	}

	public Collection values() {
		return tempValueSet;
	}

}
