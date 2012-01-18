package grisu.jcommons.model.info;

public class Gateway {

	private Site site;
	private String host;

	private Gateway() {
	}

	public Gateway(Site site, String host) {
		this.site = site;
		this.host = host;
	}

	public String getHost() {
		return host;
	}

	public Site getSite() {
		return site;
	}

	@Override
	public String toString() {
		return host;
	}

}
