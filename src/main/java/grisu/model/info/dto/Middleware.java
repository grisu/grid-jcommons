package grisu.model.info.dto;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "middleware")
public class Middleware implements Comparable<Middleware> {

    public static String PROLOG_EPILOG_AVAILABLE = "prologEpilogAvailable";

    private String name;
    private String version;

    private DtoProperties options;

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    @XmlElement(name = "version")
    public String getVersion() {
        return version;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @XmlElement(name = "options")
    public DtoProperties getOptions() {
        return options;
    }

    public void setOptions(DtoProperties options) {
		this.options = options;
	}

    @Override
    public int compareTo(Middleware o) {
        int result = ComparisonChain.start()
				.compare(getName(), o.getName())
				.compare(getVersion(), o.getVersion())
				.compare(getOptions(), o.getOptions()).result();
        return result;
    }

    @Override
    public boolean equals(Object o){
        if (! (o instanceof Middleware)) {
            return false;
        }
        Middleware other = (Middleware)o;
        return Objects.equal(getName(), other.getName())
                && Objects.equal(getVersion(), other.getVersion())
                && Objects.equal(getOptions(), other.getOptions());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName(), getVersion(), getOptions());
    }

    public String toString() {
        return getName() + " / " + getVersion();
    }
}
