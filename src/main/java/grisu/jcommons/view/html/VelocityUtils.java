package grisu.jcommons.view.html;


import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class VelocityUtils {

    public static String render(File templateFile, Map<String, Object> properties) {
        return render(templateFile, null, properties);
    }

    public static String render(File templateFile, Set<File> additionalTemplateRootPaths, Map<String, Object> properties) {

        try {

            if (templateFile == null || !templateFile.exists() || !templateFile.isFile()) {
                return null;
            }

            StringBuffer loaderPath = new StringBuffer(templateFile.getAbsoluteFile().getParentFile().getAbsolutePath());

            Properties velocityProperties = new Properties();

            velocityProperties.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            velocityProperties.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");

            StringBuffer paths = new StringBuffer(loaderPath.toString());
            paths.append(","+new File("/").getAbsolutePath());
            if (additionalTemplateRootPaths != null) {
                for (File path : additionalTemplateRootPaths) {
                    paths.append(","+path.getAbsolutePath());
                }
            }

            velocityProperties.setProperty("file.resource.loader.path", paths.toString());

            VelocityEngine ve = new VelocityEngine();

            ve.init(velocityProperties);
            VelocityContext context = new VelocityContext();

            if (properties != null) {
                // stringfyNulls(properties);
                for (Map.Entry<String, Object> property : properties.entrySet()) {
                    context.put(property.getKey(), property.getValue());
                }
            }

//            context.put("context", context);

            Template template = ve.getTemplate(templateFile.getName(), "UTF-8");
            StringWriter writer = new StringWriter();

            template.merge(context, writer);

            return writer.toString();

        } catch (Exception e) {
            throw new RuntimeException("Could not create template string.", e);
        }

    }

    public static String render(String templateName,
                                Map<String, Object> properties) {
        return render(templateName, null, properties);
    }


    public static String render(String templateName, Set<File> templateRootPaths,
                                Map<String, Object> properties) {

        try {

            File templateFile = new File(templateName);
            if (templateFile.exists() && templateFile.isFile()) {
                return render(templateFile, templateRootPaths, properties);
            }


            Properties velocityProperties = new Properties();

            StringBuffer paths = new StringBuffer(new File("/").getAbsolutePath());
            if (templateRootPaths != null) {
                for (File path : templateRootPaths) {
                    paths.append(","+path.getAbsolutePath());
                }
            }

            velocityProperties.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            velocityProperties.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
            velocityProperties.setProperty("file.resource.loader.path", paths.toString());

            velocityProperties.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath, file");

            VelocityEngine ve = new VelocityEngine();

            ve.init(velocityProperties);


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
