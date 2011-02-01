package grisu.jcommons.utils;

import grisu.jcommons.interfaces.GridResource;

import java.util.SortedSet;
import java.util.TreeSet;


public class RankedSite implements Comparable<RankedSite> {

	private int highestRank = -1;

	private final String sitename;

	private SortedSet<GridResource> resources = new TreeSet<GridResource>();

	public RankedSite(String sitename) {
		this.sitename = sitename;
	}

	public void addGridResource(GridResource resource) {
		resources.add(resource);
		if (resource.getRank() > highestRank) {
			highestRank = resource.getRank();
		}
	}

	public int compareTo(RankedSite arg0) {
		return new Integer(arg0.getHighestRank()).compareTo(new Integer(
				getHighestRank()));
	}

	@Override
	public boolean equals(Object o) {

		if (!(o instanceof RankedSite)) {
			return false;
		}

		return getSitename().equals(((RankedSite) o).getSitename());
	}

	public int getHighestRank() {
		return highestRank;
	}

	public SortedSet<GridResource> getResources() {
		return resources;
	}

	public String getSitename() {
		return sitename;
	}

	@Override
	public int hashCode() {
		return 29 * getSitename().hashCode();
	}

	public void setGridResources(SortedSet<GridResource> resources) {
		this.resources = resources;

		for (GridResource resource : resources) {
			if (resource.getRank() > highestRank) {
				highestRank = resource.getRank();
			}
		}
	}

	@Override
	public String toString() {
		return sitename + " (Ranking: " + getHighestRank() + ")";
	}
}
