package grisu.jcommons.view.html;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class VelocityUtils {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static String render(String templateName,
			Map<String, Object> properties) {

		try {

			VelocityEngine ve = new VelocityEngine();
			ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			ve.setProperty("classpath.resource.loader.class",
					ClasspathResourceLoader.class.getName());

			ve.init();

			final String templatePath = templateName + ".vm";
			InputStream input = VelocityUtils.class.getClassLoader()
					.getResourceAsStream(templatePath);
			if (input == null) {
				throw new IOException("Template file doesn't exist");
			}

			VelocityContext context = new VelocityContext();

			if (properties != null) {
				// stringfyNulls(properties);
				for (Map.Entry<String, Object> property : properties.entrySet()) {
					context.put(property.getKey(), property.getValue());
				}
			}

			Template template = ve.getTemplate(templatePath, "UTF-8");
			StringWriter writer = new StringWriter();

			template.merge(context, writer);

			return writer.toString();

		} catch (Exception e) {
			throw new RuntimeException("Could not create template string.", e);
		}

	}

}
