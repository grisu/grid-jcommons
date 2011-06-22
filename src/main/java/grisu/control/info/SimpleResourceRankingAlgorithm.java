package grisu.control.info;

import grisu.jcommons.interfaces.GridResource;
import grisu.jcommons.interfaces.RankingAlgorithm;

/**
 * This is the simplest ranking algorithm that I thought of...
 * 
 * it considers only the freejobslots and
 * 
 * @author gerson
 * 
 */
public class SimpleResourceRankingAlgorithm implements RankingAlgorithm {

	public int getRank(GridResource gridResource) {
		int rank = 0;
		rank += gridResource.getFreeJobSlots(); // TODO: multiply this by
												// ranking multiplier;
		rank += gridResource.isDesiredSoftwareVersionInstalled() ? 1 : 0; // TODO:
																			// use
																			// the
																			// multiplier
																			// for
																			// this
																			// one
																			// as
																			// well

		return rank;
	}

}
