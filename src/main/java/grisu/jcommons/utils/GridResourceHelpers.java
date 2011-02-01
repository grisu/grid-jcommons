package grisu.jcommons.utils;

import grisu.jcommons.interfaces.GridResource;

import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;


public class GridResourceHelpers {

	public static SortedSet<RankedSite> asSetOfRankedSites(
			Collection<GridResource> resources) {

		SortedSet<RankedSite> result = new TreeSet<RankedSite>();

		Map<String, SortedSet<GridResource>> map = resourcesPerSite(resources);

		for (String siteName : map.keySet()) {
			RankedSite temp = new RankedSite(siteName);
			temp.setGridResources(map.get(siteName));
			result.add(temp);
		}

		return result;

	}

	public static Map<String, SortedSet<GridResource>> resourcesPerSite(
			Collection<GridResource> resources) {

		Map<String, SortedSet<GridResource>> result = new TreeMap<String, SortedSet<GridResource>>();

		for (GridResource resource : resources) {
			SortedSet<GridResource> temp = result.get(resource.getSiteName());
			if (temp == null) {
				temp = new TreeSet<GridResource>();
				result.put(resource.getSiteName(), temp);
			}
			temp.add(resource);
		}

		return result;
	}

}
