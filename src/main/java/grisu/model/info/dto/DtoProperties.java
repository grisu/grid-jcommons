package grisu.model.info.dto;

import grisu.jcommons.constants.JobSubmissionProperty;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This one holds information about a job that was created (and maybe already
 * submitted to the endpoint resource).
 * 
 * You can use this to query information like job-directory and status of the
 * job. Have a look in the Constants class in the GlueInterface module of the
 * Infosystems project for values of keys of possible job properties.
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

	public Map<String, String> propertiesAsMap() {

		final Map<String, String> map = new HashMap<String, String>();

		for (final DtoProperty prop : getProperties()) {
			map.put(prop.getKey(), prop.getValue());
		}

		return map;
	}

	public String readProperty(String key) {
		return propertiesAsMap().get(key);
	}

	public void setProperties(List<DtoProperty> properties) {
		this.properties = properties;
	}

}
