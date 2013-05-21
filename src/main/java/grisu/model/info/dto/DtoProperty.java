package grisu.model.info.dto;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.google.common.base.Objects;

/**
 * A wrapper class that holds a job property key and value.
 * 
 * @author Markus Binsteiner
 * 
 */
@XmlRootElement(name = "property")
public class DtoProperty {

	public static List<DtoProperty> dtoPropertiesFromMap(Map<String, String> map) {

		final List<DtoProperty> result = new LinkedList<DtoProperty>();

		for (final String key : map.keySet()) {
			result.add(new DtoProperty(key, map.get(key)));
		}

		return result;

	}

	public static Map<String, String> mapFromDtoPropertiesList(
			List<DtoProperty> props) {

		final Map<String, String> result = new LinkedHashMap<String, String>();
		for (final DtoProperty p : props) {
			result.put(p.getKey(), p.getValue());
		}
		return result;
	}

	/**
	 * The key of the job property (see a list of possible values in the
	 * Constants class in the Infosystems GlueInterface module.
	 */
	private String key;
	/**
	 * The value for this job property.
	 */
	private String value;

	public DtoProperty() {
	}

	public DtoProperty(String key, String value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof DtoProperty) {
			return Objects.equal(this.getKey(), ((DtoProperty) o).getKey())
					&& Objects.equal(this.getValue(),
							((DtoProperty) o).getValue());
		}
		return false;
	}

	@XmlAttribute
	public String getKey() {
		return key;
	}

	@XmlValue
	public String getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		return com.google.common.base.Objects.hashCode(getKey(), getValue());
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
