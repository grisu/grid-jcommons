package grisu.jcommons.model.info;

import grisu.jcommons.constants.JobSubmissionProperty;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;

public class Filters {

	private static class AcceptsJobFilter implements Predicate<Queue> {

		private final Map<JobSubmissionProperty, String> jobProps;
		private final Group group;

		public AcceptsJobFilter(Map<JobSubmissionProperty, String> p, Group g) {
			this.jobProps = p;
			this.group = g;
		}

		public boolean apply(Queue q) {

			if (!q.getGroups().contains(group)) {
				return false;
			}

			return q.acceptsJob(jobProps);
		}

	}

	private static class ResourceFilter implements Predicate<AbstractResource> {

		private final AbstractResource[] filters;

		public ResourceFilter(AbstractResource[] f) {
			this.filters = f;
		}

		public boolean apply(AbstractResource resource) {

			Collection<AbstractResource> r = resource.getConnections();

			for (AbstractResource ar : this.filters) {
				if (!r.contains(ar)) {
					return false;
				}
			}

			return true;
		}

	}

	private static Logger myLogger = LoggerFactory.getLogger(Filters.class);

	public static Predicate<Queue> acceptsJob(
			Map<JobSubmissionProperty, String> jobProps, Group group) {
		return new AcceptsJobFilter(jobProps, group);
	}

	public static Predicate<AbstractResource> filterResource(
			AbstractResource... o) {
		return new ResourceFilter(o);
	}
}
