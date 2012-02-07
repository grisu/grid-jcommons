package grisu.jcommons.model.info;




abstract public class AbstractPhysicalResource extends AbstractResource {

	abstract public String getContactString();

	abstract public Site getSite();

	public boolean isAccessibleVia(String url) {

		if (getContactString().contains(url)) {
			return true;
		} else {
			return false;
		}

	}

}
