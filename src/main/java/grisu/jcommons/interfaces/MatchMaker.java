package grisu.jcommons.interfaces;

import grisu.jcommons.constants.JobSubmissionProperty;

import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;


public interface MatchMaker {

	public abstract List<GridResource> findAllResources(
			Map<JobSubmissionProperty, String> jobProperties, String fqan);

	public abstract List<GridResource> findAllResources(Document jsdl,
			String fqan);

	public abstract List<GridResource> findAvailableResources(
			Map<JobSubmissionProperty, String> jobProperties, String fqan);

	public abstract List<GridResource> findAvailableResources(Document jsdl,
			String fqan);

	public abstract void setRankingAlgorithm(RankingAlgorithm rankingAlgorithm);

}
