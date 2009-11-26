package au.org.arcs.jcommons.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.XMLConstants;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import au.org.arcs.jcommons.constants.Constants;
import au.org.arcs.jcommons.constants.JSDLNamespaceContext;

//import au.org.arcs.jcommons.constants.Constants;
//import au.org.arcs.jcommons.constants.JSDLNamespaceContext;

/*
 * This is a pretty important helper class as it has got all the helper methods
 * to access/alter a jsdl document.
 * 
 * @author Markus Binsteiner
 */

public final class JsdlHelpers {

	private JsdlHelpers() {
	}
	
	static final Logger myLogger = Logger
			.getLogger(JsdlHelpers.class.getName());

	public static final String USER_EXECUTION_HOST_FILESYSTEM = "userExecutionHostFs";
	public static final String LOCAL_EXECUTION_HOST_FILESYSTEM = "localExecutionHostFs";

	// TODO check whether access to this has to be synchronized
	private static final XPath xpath = getXPath();

	private static XPath getXPath() {
		XPath xpath = XPathFactory.newInstance().newXPath();
		xpath.setNamespaceContext(new JSDLNamespaceContext());
		return xpath;
	}

	/**
	 * Checks whether the jsdl jobDescription against jsdl.xsd to see whether
	 * it's valid xml.
	 * 
	 * @param jobDescription
	 *            the jsdl xml document
	 * @return true if valid - false if not
	 */
	public static boolean validateJSDL(final Document jobDescription) {

		// TODO use static Schema for better performance
		// create a SchemaFactory capable of understanding WXS schemas
		SchemaFactory factory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		// load a WXS schema, represented by a Schema instance
		Source schemaFile = new StreamSource(new File("jsdl.xsd"));
		Schema schema = null;
		try {
			schema = factory.newSchema(schemaFile);
		} catch (SAXException e1) {
			// this should not happen
			e1.printStackTrace(System.err);
		}

		// create a Validator instance, which can be used to validate an
		// instance document
		Validator validator = schema.newValidator();

		// validate the DOM tree
		try {
			validator.validate(new DOMSource(jobDescription));
		} catch (SAXException e) {
			// instance document is invalid!
			return false;
		} catch (IOException e) {
			// this should not happen
			e.printStackTrace(System.err);
		}
		return true;

	}

	/**
	 * Parses the jsdl document and returns the value of the JobName element.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @return the name of the job
	 */
	public static String getJobname(final Document jsdl) {

		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl:JobIdentification/jsdl:JobName";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger.warn("No jobname in jsdl file.");
			return null;
		}

		if (resultNodes.getLength() != 1) {
			return null;
		}

		return resultNodes.item(0).getTextContent();
	}

	/**
	 * Parses the jsdl document and returns the value of the Description element.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @return the description of the type of job
	 */
	public static String getDescription(final Document jsdl) {

		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl:Application/jsdl:Description";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger.warn("No description in jsdl file.");
			return null;
		}

		if (resultNodes.getLength() != 1) {
			return null;
		}

		return resultNodes.item(0).getTextContent();
	}

	/**
	 * Returns the content of the walltime element in the jsdl file. The
	 * walltime specified in the jsdl document should be
	 * "walltime in seconds * no cpus". This is not what this method gives back.
	 * 
	 * This is not what this method gives back. The walltime this method gives
	 * back is the total walltime in seconds.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @return the walltime in seconds or -1 if there is no (or no valid) entry
	 */
	public static int getWalltime(final Document jsdl) {

		int cpus = getProcessorCount(jsdl);

		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl:Application/jsdl:TotalCPUTime";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger.warn("No jobname in jsdl file.");
			return -1;
		}

		if (resultNodes.getLength() != 1) {
			return -1;
		}

		int walltimeInSecs;
		try {
			walltimeInSecs = new Integer(resultNodes.item(0).getTextContent());
		} catch (NumberFormatException e) {
			myLogger.error("No valid number entry in the walltime element.");
			return -1;
		}

		if (cpus > 1) {
			return walltimeInSecs / cpus;
		} else {
			return walltimeInSecs;
		}
	}
	
	/**
	 * Sets the totalCPUTime of the job in seconds. If there is already a walltime specified it will be overwritten.
	 * 
	 * Be aware that you need to multiply the walltime in seconds times the number of cpus for this job to get the proper totalcputime.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @param totalcputimeinseconds
	 *            the new totalcputime
	 * @throws XPathExpressionException
	 *             if the JobName element could not be found
	 */
	public static void setTotalCPUTimeInSeconds(final Document jsdl, final int totalcputimeinseconds) {
		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl:Application/jsdl:TotalCPUTime";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger.warn("No Walltime node in jsdl file.");
			// that's ok if we want to set the jobname
		}

		Node walltime = null;

		// TODO what to do if that happens? Shouldn't though because the jsdl is
		// validated against jsdl.xsd beforehand
		if (resultNodes.getLength() > 1) {
			return;
		} else if (resultNodes.getLength() == 0) {
			expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl:Application";
			try {
				resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
						XPathConstants.NODESET);
			} catch (XPathExpressionException e) {
				myLogger.warn("No JobIdentification node in jsdl file.");
				throw new RuntimeException(e);
			}

			if (resultNodes.getLength() != 1) {
				throw new RuntimeException(
						"No or more than one JobIdentification nodes in jsdl document.");
			}

			walltime = jsdl.createElement("jsdl:TotalCPUTime");
			walltime.setTextContent(new Integer(totalcputimeinseconds).toString());
			resultNodes.item(0).appendChild(walltime);
		} else {
			// replace the text content of the already existing JobName element
			resultNodes.item(0).setTextContent(new Integer(totalcputimeinseconds).toString());
		}

	}

	/**
	 * Parses the jsdl document and returns the value of the TotalCPUCount
	 * element.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @return the number of cpus used in this job
	 */
	public static int getProcessorCount(final Document jsdl) {

		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl:Application/jsdl:TotalCPUCount/jsdl:exact";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger.warn("No jobname in jsdl file.");
			return -1;
		}

		if (resultNodes.getLength() != 1) {
			myLogger
					.warn("This template doesn't specify a (correct) TotalCPUCount element. Please have a look at your template and replace a possible <TotalCPUCount>2</TotalCPUCount> elemnt with something like: <TotalCPUCount><exact>2</exact></TotalCPUCount> Trying old, incorrect implementation...");
			// this is just for backwards compatibility because I got the
			// TotalCPUCount element wrong before...
			return getProcessorCount_OLD(jsdl);
		}

		int processorCount;
		try {
			processorCount = new Integer(resultNodes.item(0).getTextContent());
		} catch (NumberFormatException e) {
			myLogger.error("No valid number entry in the walltime element.");
			return -1;
		}

		return processorCount;

	}
	
	/**
	 * Sets the totalCPUcount of the job. If there is already a cpucount specified it will be overwritten.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @param totalcputimeinseconds
	 *            the new totalcputime
	 * @throws XPathExpressionException
	 *             if the JobName element could not be found
	 */
	public static void setProcessorCount(final Document jsdl, final int processorcount)
			 {
		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl:Application/jsdl:TotalCPUCount/jsdl:exact";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger.warn("No Walltime node in jsdl file.");
			// that's ok if we want to set the jobname
		}

		Node cpucount = null;

		// TODO what to do if that happens? Shouldn't though because the jsdl is
		// validated against jsdl.xsd beforehand
		if (resultNodes.getLength() > 1) {
			return;
		} else if (resultNodes.getLength() == 0) {
			expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl:Application/jsdl:TotalCPUCount";
			try {
				resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
						XPathConstants.NODESET);
			} catch (XPathExpressionException e) {
				myLogger.warn("No JobIdentification node in jsdl file.");
				throw new RuntimeException(e);
			}

			if (resultNodes.getLength() != 1) {
				throw new RuntimeException(
						"No or more than one JobIdentification nodes in jsdl document.");
			}

			cpucount = jsdl.createElement("jsdl:exact");
			cpucount.setTextContent(new Integer(processorcount).toString());
			resultNodes.item(0).appendChild(cpucount);
		} else {
			// replace the text content of the already existing JobName element
			resultNodes.item(0).setTextContent(new Integer(processorcount).toString());
		}

	}

	// don't use that anymore -- this will be deleted soon.
	private static int getProcessorCount_OLD(final Document jsdl) {
		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl:Application/jsdl:TotalCPUCount";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger.warn("No jobname in jsdl file.");
			return -1;
		}

		if (resultNodes.getLength() != 1) {
			return -1;
		} else {
			myLogger
					.error("Template uses incorrect specification of TotalCPUCount element. Please replace <TotalCPUCount>2</TotalCPUCount> elemnt with something like: <TotalCPUCount><exact>2</exact></TotalCPUCount>.");
		}

		int processorCount;
		try {
			processorCount = new Integer(resultNodes.item(0).getTextContent());
		} catch (NumberFormatException e) {
			myLogger.error("No valid number entry in the walltime element.");
			return -1;
		}

		return processorCount;
	}

	/**
	 * Returns the total memory requirement for this job. This is the memory
	 * that is required for each cpu multiplied with the number of cpus.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @return the total memory requirment
	 */
	public static long getTotalMemoryRequirement(final Document jsdl) {

		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl:Application/jsdl:TotalPhysicalMemory/jsdl:LowerBoundedRange";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger.warn("No jobname in jsdl file.");
			return -1;
		}

		if (resultNodes.getLength() != 1) {
			return -1;
		}

		Long minMem;

		try {
			minMem = new Long(resultNodes.item(0).getTextContent());
		} catch (NumberFormatException e) {
			myLogger.error("No valid entry in the MinTotalMemory element.", e);
			return -1;
		}

		return minMem;

	}

	private static Node getApplicationNode(final Document jsdl) {
		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl:Application/jsdl:ApplicationName";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger.warn("No application in jsdl file.");
			return null;
		}

		if (resultNodes.getLength() != 1) {
			return null;
		}
		return resultNodes.item(0);
	}

	/**
	 * Parses the jsdl document and returns the value of the ApplicationName
	 * element. Be aware that this is not the name or path of the executable.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @return the name of the applications
	 */
	public static String getApplicationName(final Document jsdl) {

		return getApplicationNode(jsdl).getTextContent();

	}

	/**
	 * Sets the name of the application to use for this job.
	 * 
	 * @param xmlTemplateDoc
	 *            the jsdl document
	 * @param application
	 *            the name of the application
	 */
	public static void setApplicationName(final Document xmlTemplateDoc,
			final String application) {

		getApplicationNode(xmlTemplateDoc).setTextContent(application);

	}

	private static Node getApplicationVersionNode(final Document jsdl) {

		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl:Application/jsdl:ApplicationVersion";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger.warn("No application in jsdl file.");
			return null;
		}

		if (resultNodes.getLength() != 1) {
			return null;
		}
		return resultNodes.item(0);

	}

	/**
	 * Returns the version of the application for this job.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @return the version
	 */
	public static String getApplicationVersion(final Document jsdl) {

		Node appNode = getApplicationVersionNode(jsdl);

		if (appNode != null) {
			return appNode.getTextContent();
		}

		return null;
	}

	/**
	 * Parses the jsdl document and returns the value of the ApplicationVersion
	 * element.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @param version the version of the application
	 * @return the version of the application to run
	 */
	public static void setApplicationVersion(final Document jsdl, final String version) {
		getApplicationVersionNode(jsdl).setTextContent(version);
	}

	/**
	 * Parses the jsdl document and returns the value of the
	 * jsdl-posix:Executable element. This is the name (and path) of the
	 * executable.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @return the (path to the) application
	 */
	public static String getPosixApplicationExecutable(final Document jsdl) {

		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl:Application/jsdl-posix:POSIXApplication/jsdl-posix:Executable";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger.warn("No application in jsdl file.");
			return null;
		}

		if (resultNodes.getLength() != 1) {
			return null;
		}

		return resultNodes.item(0).getTextContent();

	}

	/**
	 * Parses the jsdl document and returns the value of the
	 * jsdl-posix:WorkingDirectory element. That information is of not much use
	 * unless you know as well to which filesyste it's relative.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @return the working directory (relative to $GLOBUS_HOME in our case)
	 */
	public static String getWorkingDirectory(final Document jsdl) {

		return getWorkingDirectoryElement(jsdl).getTextContent();

	}

	/**
	 * Sets the falue of the workingdirectory element.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @param fileSystemName
	 *            the name of the filesystem where the workingdirectory sits
	 * @param path
	 *            the path to the workingdirectory
	 */
	public static void setWorkingDirectory(final Document jsdl,
			final String fileSystemName, final String path) {

		Element workingDirectoryElement = getWorkingDirectoryElement(jsdl);

		workingDirectoryElement.setAttribute("filesystemName", fileSystemName);
		workingDirectoryElement.setTextContent(path);

	}

	/**
	 * Returns an absolute url to the working directory of this job.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @return the absolute url
	 */
	public static String getAbsoluteWorkingDirectoryUrl(final Document jsdl) {

		Element wd = getWorkingDirectoryElement(jsdl);
		String fsName = wd.getAttribute("filesystemName");
		String fsUrl = getFileSystemRootUrl(jsdl, fsName);

		if (fsUrl.endsWith("/") || wd.getTextContent().startsWith("/")) {
			return fsUrl + wd.getTextContent();
		} else {
			return fsUrl + "/" + wd.getTextContent();
		}

	}

	private static Element getWorkingDirectoryElement(final Document jsdl) {
		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl:Application/jsdl-posix:POSIXApplication/jsdl-posix:WorkingDirectory";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger.warn("No application in jsdl file.");
			return null;
		}

		if (resultNodes.getLength() != 1) {
			return null;
		}

		return (Element) resultNodes.item(0);
	}

	/**
	 * Parses the jsdl document and returns the value of the jsdl-posix:Output
	 * element.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @return the path to the stdout file (relative to the working directory)
	 */
	public static String getPosixStandardOutput(final Document jsdl) {

		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl:Application/jsdl-posix:POSIXApplication/jsdl-posix:Output";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger.warn("No output in jsdl file.");
			return null;
		}

		if (resultNodes.getLength() != 1) {
			return null;
		}

		return resultNodes.item(0).getTextContent();

	}

	/**
	 * Parses the jsdl document and returns the value of the jsdl-posix:Output
	 * element.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @return the path to the stdout file (relative to the working directory)
	 */
	public static String getPosixStandardInput(final Document jsdl) {

		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl:Application/jsdl-posix:POSIXApplication/jsdl-posix:Input";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger.warn("No input in jsdl file.");
			return null;
		}

		if (resultNodes.getLength() != 1) {
			return null;
		}

		return resultNodes.item(0).getTextContent();

	}

	/**
	 * Parses the jsdl document and returns the value of the jsdl-posix:Error
	 * element.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @return the path to the stderr file (relative to the working directory)
	 */
	public static String getPosixStandardError(final Document jsdl) {

		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl:Application/jsdl-posix:POSIXApplication/jsdl-posix:Error";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger.warn("No output in jsdl file.");
			return null;
		}

		if (resultNodes.getLength() != 1) {
			return null;
		}

		return resultNodes.item(0).getTextContent();

	}

	/**
	 * Parses the jsdl document and returns an array of all the arguments that
	 * are used on the jsdl-posix:Executable.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @return a list of all arguments
	 */
	public static String[] getPosixApplicationArguments(final Document jsdl) {

		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl:Application/jsdl-posix:POSIXApplication/jsdl-posix:Argument";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger.warn("No arguments in jsdl file.");
			return null;
		}

		String[] arguments = new String[resultNodes.getLength()];

		for (int i = 0; i < resultNodes.getLength(); i++) {
			arguments[i] = resultNodes.item(i).getTextContent();
		}
		return arguments;
	}

	/**
	 * Converts the jsdl xml document into string format.
	 * 
	 * @param jsdl
	 *            the jsdl document as a {@link Document}
	 * @return the jsdl document as String
	 * @throws TransformerFactoryConfigurationError
	 *             if the {@link TransformerFactory} can't be created
	 * @throws TransformerException
	 *             if the {@link Transformer} throws an error while transforming
	 *             the xml document
	 */
	public static String getJsdl(final Document jsdl)
			throws TransformerException {

		// TODO use static transformer to reduce overhead?
		Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		// initialize StreamResult with File object to save to file
		StreamResult result = new StreamResult(new StringWriter());
		DOMSource source = new DOMSource(jsdl);
		transformer.transform(source, result);

		String jsdl_string = result.getWriter().toString();

		return jsdl_string;
	}

	/**
	 * Sets the name of the job (by changing the value of the jsdl:JobName
	 * element). If there is already a name specified it will be overwritten.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @param new_jobname
	 *            the new name of the job.
	 * @throws XPathExpressionException
	 *             if the JobName element could not be found
	 */
	public static void setJobname(final Document jsdl, final String new_jobname)
			throws XPathExpressionException {
		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl:JobIdentification/jsdl:JobName";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger.warn("No JobName node in jsdl file.");
			// that's ok if we want to set the jobname
		}

		Node jobName = null;

		// TODO what to do if that happens? Shouldn't though because the jsdl is
		// validated against jsdl.xsd beforehand
		if (resultNodes.getLength() > 1) {
			return;
		} else if (resultNodes.getLength() == 0) {
			expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl:JobIdentification";
			try {
				resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
						XPathConstants.NODESET);
			} catch (XPathExpressionException e) {
				myLogger.warn("No JobIdentification node in jsdl file.");
				throw e;
			}

			if (resultNodes.getLength() != 1) {
				throw new XPathExpressionException(
						"No or more than one JobIdentification nodes in jsdl document.");
			}

			jobName = jsdl.createElement("jsdl:JobName");
			jobName.setTextContent(new_jobname);
			resultNodes.item(0).appendChild(jobName);
		} else {
			// replace the text content of the already existing JobName element
			resultNodes.item(0).setTextContent(new_jobname);
		}

	}

//	/**
//	 * Adds submission locations to the job.
//	 * 
//	 * @param jsdl
//	 *            the jsdl document
//	 * @param subLocs
//	 *            the submission locations
//	 * @throws XPathExpressionException
//	 *             if the candidatehosts element could not be found
//	 */
//	public static void addCandidateHosts(final Document jsdl, final String[] subLocs)
//			throws XPathExpressionException {
//		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl:Resources/jsdl:CandidateHosts";
//		NodeList resultNodes = null;
//		try {
//			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
//					XPathConstants.NODESET);
//		} catch (XPathExpressionException e) {
//			myLogger.warn("No CandidateHosts node in jsdl file.");
//			throw new XPathExpressionException(
//					"Problem parsing candidateHost element: "
//							+ e.getLocalizedMessage());
//		}
//
//		Node hostName = null;
//
//		if (resultNodes.getLength() > 1) {
//			throw new XPathExpressionException(
//					"More than one CandidateHosts elements found");
//		}
//
//		if (resultNodes.getLength() != 1) {
//			throw new XPathExpressionException(
//					"No or more than one JobIdentification nodes in jsdl document.");
//		}
//
//		String nsURL = new JSDLNamespaceContext().getNamespaceURI("jsdl");
//		for (String subLoc : subLocs) {
//			hostName = jsdl.createElementNS(nsURL, "HostName");
//			hostName.setTextContent(subLoc);
//			resultNodes.item(0).appendChild(hostName);
//		}
//	}
	
	/**
	 * Sets the submission location of the job.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @param subLocs
	 *            the submission locations
	 * @throws XPathExpressionException
	 *             if the candidatehosts element could not be found
	 */
	public static void setCandidateHosts(final Document jsdl, final String[] subLocs)
			throws XPathExpressionException {
		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl:Resources/jsdl:CandidateHosts";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger.warn("No CandidateHosts node in jsdl file.");
			throw new XPathExpressionException(
					"Problem parsing candidateHost element: "
							+ e.getLocalizedMessage());
		}

		Node hostName = null;

		if (resultNodes.getLength() > 1) {
			throw new XPathExpressionException(
					"More than one CandidateHosts elements found");
		}

		if (resultNodes.getLength() != 1) {
			throw new XPathExpressionException(
					"No or more than one JobIdentification nodes in jsdl document.");
		}

		String nsURL = new JSDLNamespaceContext().getNamespaceURI("jsdl");
//		for ( int i=0; i<resultNodes.item(0).getChildNodes().getLength(); i++ ) {
//			resultNodes.item(0).removeChild(resultNodes.item(0).getChildNodes().item(i));
//			
//		}
		for (String subLoc : subLocs) {
			hostName = jsdl.createElementNS(nsURL, "HostName");
			hostName.setTextContent(subLoc);
			resultNodes.item(0).replaceChild(hostName, resultNodes.item(0).getFirstChild());
		}
	}

	/**
	 * Parses the jsdl document and gets a list of all HostName elements (of
	 * which we currently only use the one at index 0). This is not really used
	 * in the proper way because Grisu allows the queue to be included in the
	 * value as well (sque@brecca:ng2.vpac.org).
	 * 
	 * @param jsdl
	 *            the jsdl docuemnt the jsdl Document
	 * @return a list of hostnames
	 */
	public static String[] getCandidateHosts(final Document jsdl) {

		String[] hosts = null;

		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl:Resources/jsdl:CandidateHosts/jsdl:HostName";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger.warn("No HostNames in jsdl file.");
			return null;
		}

		int number_hosts = resultNodes.getLength();
		if (number_hosts == 0) {
			return null;
		}
		hosts = new String[number_hosts];
		for (int i = 0; i < number_hosts; i++) {
			String host_line = resultNodes.item(i).getTextContent();
			hosts[i] = host_line;
		}

		return hosts;
	}

	/**
	 * Extracts the target filesystem url from a given StageIn element.
	 * 
	 * @param stageIn
	 *            a DataStaging element from a jsdl file
	 * @return the url of the file to stage in
	 */
	public static String extractTargetFromStageInElement(final Element stageIn) {
		String fileSystemName = ((Element) stageIn.getElementsByTagName(
				"FileSystemName").item(0)).getTextContent();
		String fileSystemUrl = getFileSystemRootUrl(stageIn.getOwnerDocument(),
				fileSystemName);
		String target = fileSystemUrl
				+ File.separator
				+ ((Element) stageIn.getElementsByTagName("FileName").item(0))
						.getTextContent();

		return target;
	}

	/**
	 * Sets the name of the source file that gets staged into the job directory.
	 * 
	 * @param stageIn
	 *            the DataStaging element
	 * @param source_url
	 *            the url of the source file
	 */
	public static void setSourceForStageInElement(final Element stageIn,
			final String source_url) {

		Element source_uri = ((Element) ((Element) stageIn
				.getElementsByTagName("Source").item(0)).getElementsByTagName(
				"URI").item(0));
		source_uri.setTextContent(source_url);

	}

	/**
	 * Returns a list of all stage-in elements for this job.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @return the stage-in elements
	 */
	public static List<Element> getStageInElements(final Document jsdl) {

		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl:DataStaging";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger.warn("No StageIn elements in jsdl file.");
			return null;
		}

		List<Element> stageIns = new ArrayList<Element>();
		// for every file staging element
		for (int i = 0; i < resultNodes.getLength(); i++) {
			stageIns.add((Element) resultNodes.item(i));

		}
		return stageIns;
	}

	/**
	 * Returns a list of all inputfile urls for this job.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @return the input files
	 */
	public static String[] getInputFileUrls(final Document jsdl) {

		List<Element> stageInElements = getStageInElements(jsdl);

		if (stageInElements == null || stageInElements.size() == 0) {
			return null;
		}

		Set<String> result = new HashSet<String>();
		for (Element stageInElement : stageInElements) {
			String temp = getStageInSource(stageInElement);
			result.add(temp);
		}

		return result.toArray(new String[] {});
	}

	/**
	 * Returns the source-file-url for this stage-in-element.
	 * 
	 * @param stageInElement
	 *            the stage-in element
	 * @return the source-file-url
	 */
	public static String getStageInSource(final Element stageInElement) {

		NodeList sources = stageInElement.getElementsByTagName("Source");
		if (sources.getLength() != 1) {
			// not implemented/possible?
			myLogger
					.error("More than one/no source element in stageIn element.");
			throw new RuntimeException(
					"More than one/no source element in stageIn element.");
		}

		Element source = (Element) sources.item(0);
		sources = source.getElementsByTagName("URI");
		if (sources.getLength() != 1) {
			// not implemented/possible?
			myLogger.error("More than one/no URI element in sources element.");
			throw new RuntimeException(
					"More than one/no URI element in source element.");
		}
		Element uri = (Element) sources.item(0);

		return uri.getTextContent();
	}

	/**
	 * Returns the relative part of the target url for this stage-in element.
	 * 
	 * @param stageInElement
	 *            the stage-in element
	 * @return the relative part of the target url.
	 */
	public static Element getStageInTarget_relativePart(final Element stageInElement) {

		NodeList filenames = stageInElement.getElementsByTagName("FileName");
		if (filenames.getLength() != 1) {
			// not implemented/possible?
			myLogger
					.error("More than one/no FileName element in stageIn element.");
			throw new RuntimeException(
					"More than one/no FileName element in stageIn element.");
		}

		Element filename = (Element) filenames.item(0);
		return filename;
	}

	/**
	 * Returns the filesystem part of the target url for this stage-in element.
	 * 
	 * @param stageInElement
	 *            the stage-in element
	 * @return the filesystem part of the target url.
	 */
	public static Element getStageInTarget_filesystemPart(final Element stageInElement) {

		NodeList filesystems = stageInElement
				.getElementsByTagName("FileSystemName");

		if (filesystems.getLength() != 1) {
			// not implemented/possible?
			myLogger
					.error("More than one/no FileSystemName element in target element.");
			throw new RuntimeException(
					"More than one/no FileSystemName element in target element.");
		}

		Element filesystem = (Element) filesystems.item(0);
		return filesystem;
	}

	/**
	 * Calculates the full url of this stage-in-elements' target file.
	 * 
	 * @param stageInElement
	 *            the stage-in element
	 * @return the url of the target file
	 */
	public static String getStageInTarget(final Element stageInElement) {

		String fsNamePart = getStageInTarget_filesystemPart(stageInElement)
				.getTextContent();
		String relPart = getStageInTarget_relativePart(stageInElement)
				.getTextContent();

		String fsRoot = getFileSystemRootUrl(stageInElement.getOwnerDocument(),
				fsNamePart);

		if (fsRoot.endsWith("/")) {
			return fsRoot + relPart;
		} else {
			if (relPart.startsWith("/")) {
				return fsRoot + relPart;
			} else {
				return fsRoot + "/" + relPart;
			}
		}

	}

	/**
	 * This one does not really follow the jsdl syntax. But we use something
	 * called "modules" within the APACGrid so here it is. I suppose I should
	 * write my own extension to the jsdl standard, somthing like jsdl-apac.
	 * Problem is, I don't know how...
	 * 
	 * Anyway. This parses a jsdl template document and extracts all
	 * jsdl-posix:Module elements.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @return all modules that have to be loaded for running this job
	 */
	public static String[] getModules(final Document jsdl) {

		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl-arcs:Module";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger.warn("No module in jsdl file.");
			return null;
		}

		if (resultNodes.getLength() > 0) {
			String[] modules = new String[resultNodes.getLength()];
			for (int i = 0; i < resultNodes.getLength(); i++) {
				modules[i] = resultNodes.item(i).getTextContent();
			}

			return modules;

		} else {
			// try old module path. This will be removed soonish.
			myLogger.debug("No module found. Trying old module location...");

			expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl:Application/jsdl-posix:POSIXApplication/jsdl-posix:Module";
			resultNodes = null;
			try {
				resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
						XPathConstants.NODESET);
			} catch (XPathExpressionException e) {
				myLogger.debug("No module in old location in jsdl file.");
				return null;
			}

			if (resultNodes.getLength() < 1) {
				return null;
			}

			String[] modules = new String[resultNodes.getLength()];
			for (int i = 0; i < resultNodes.getLength(); i++) {
				modules[i] = resultNodes.item(i).getTextContent();
			}
			myLogger
					.debug("Found modules but they are specified in the wrong (old) location within the jsdl document. Please change your template/jsdl file according to https://projects.arcs.org.au/trac/grisu/wiki/GlobusToolkitSubmitter");
			return modules;
		}

	}

	/**
	 * Returns whether pbs-debugging should be enabled for this job or not.
	 * 
	 * This is an ARCS-specific extension to jsdl/rsl.
	 * 
	 * @param jsdl
	 *            the jsdl file
	 * @return whether to enable pbs-debugging or not
	 */
	public static String getPbsDebugElement(final Document jsdl) {
		
		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl-arcs:PbsDebug";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger.warn("No application in jsdl file.");
			return null;
		}

		if (resultNodes.getLength() == 0) {
			// try old version
			myLogger.debug("Couldn't find pbs debug element.");
			return null;
		}
		if (resultNodes.getLength() != 1) {
			myLogger
					.debug("Found more than one pbs debug element. Not returning anything.");
			return null;
		}
		String value = ((Element) resultNodes.item(0)).getTextContent();

		return value;

	}

	private static Element getEmailElement(final Document jsdl) {
		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl-arcs:GrisuTemplate/jsdl-arcs:Email";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger.warn("No application in jsdl file.");
			return null;
		}

		if (resultNodes.getLength() == 0) {
			// try old version
			myLogger
					.debug("Couldn't find email element. Trying old email element...");
			return getEmailElement_OLD(jsdl);
		}
		if (resultNodes.getLength() != 1) {
			return null;
		}
		return (Element) resultNodes.item(0);
	}

	// don't use that anymore, this is wrong!
	private static Element getEmailElement_OLD(final Document jsdl) {
		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl:Application/jsdl-posix:POSIXApplication/jsdl-posix:Email";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger.warn("No application in jsdl file.");
			return null;
		}

		if (resultNodes.getLength() != 1) {
			return null;
		} else {
			myLogger
					.error("Found email, but it is in the wrong spot. Please change your template so the \"Email\" element is now under: \"/JobDescription/jsdl-arcs:GrisuTemplate/\"");
		}
		return (Element) resultNodes.item(0);
	}

	/**
	 * Whether to send an email when this job starts or not.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @return whether to send an email or not
	 */
	public static boolean getSendEmailOnJobStart(final Document jsdl) {

		Element emailElement = getEmailElement(jsdl);

		if (emailElement == null) {
			return false;
		}

		String value = emailElement
				.getAttribute(Constants.SEND_EMAIL_ON_JOB_START_ATTRIBUTE_KEY);

		if ("true".equals(value)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Whether to send an email when this job finishes or not.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @return whether to send an email or not
	 */
	public static boolean getSendEmailOnJobFinish(final Document jsdl) {

		Element emailElement = getEmailElement(jsdl);

		if (emailElement == null) {
			return false;
		}

		String value = emailElement
				.getAttribute(Constants.SEND_EMAIL_ON_JOB_END_ATTRIBUTE_KEY);

		if ("true".equals(value)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * The email address to send emails regarding this job.
	 * 
	 * @param jsdl
	 *            the jsld document
	 * @return the email address of the user
	 */
	public static String getEmail(final Document jsdl) {

		Element emailElement = getEmailElement(jsdl);
		if (emailElement == null) {
			return null;
		}

		String email = emailElement.getTextContent();
		return email;

	}

	/**
	 * Parses the jsdl document and returns the url of filesystem on which the
	 * files are staged in using the EXECUTION_HOST_FILESYSTEM variable to
	 * determine what the name is for that ("executionHostFs").
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @return the url of filesystem where all the files are staged in
	 */
	public static String getUserExecutionHostFs(final Document jsdl) {

		return getFileSystemRootUrl(jsdl, USER_EXECUTION_HOST_FILESYSTEM);

	}

	public static Element getUserExecutionHostFSElement(final Document jsdl) {
		return (Element) getMountSourceElement(jsdl,
				USER_EXECUTION_HOST_FILESYSTEM).getParentNode();
	}

	/**
	 * Parses a jsdl document and returns the urn of the filesystem that has
	 * been requested.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @param fileSystemName
	 *            the name of the filesystem
	 * @return the url of the filesystem
	 */
	public static String getFileSystemRootUrl(final Document jsdl,
			final String fileSystemName) {

		myLogger.debug("Getting root url for filesystem: " + fileSystemName);
		Element ms = getMountSourceElement(jsdl, fileSystemName);
		return ms.getTextContent();
	}

	/**
	 * Returns a list of all elements that use the filesystem with the specified
	 * alias.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @param fileSystemName
	 *            the name of the filesystem
	 * @return a list of all elements
	 */
	public static List<Element> getElementsWithFileSystemNameAttribute(
			final Document jsdl, final String fileSystemName) {
		String expression = "//@filesystemName";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger.warn("No application in jsdl file.");
			return null;
		}

		ArrayList<Element> result = new ArrayList<Element>();
		for (int i = 0; i < resultNodes.getLength(); i++) {

			Element el = ((Attr) resultNodes.item(i)).getOwnerElement();
			if (fileSystemName.equals(el.getAttribute("filesystemName"))) {
				result.add(el);
			}
		}

		return result;
	}

	/**
	 * Returns the element that is connected to this filesystem alias.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @param fileSystemName
	 *            the name of the filesystem
	 * @return the MountSourceElement
	 */
	public static Element getMountSourceElement(final Document jsdl,
			final String fileSystemName) {

		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl:Resources/jsdl:FileSystem[@name='"
				+ fileSystemName + "']/jsdl:MountSource";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger
					.warn("No mountsource element with that name in jsdl file.");
			return null;
		}

		if (resultNodes.getLength() == 0) {
			myLogger.error("No matching file system found in jsdl:");
			return null;
		}
		if (resultNodes.getLength() != 1) {
			myLogger
					.error("More than one or no matching filesystems found. That is not possible.");
			for (int i = 0; i < resultNodes.getLength(); i++) {
				myLogger.error(resultNodes.item(i).getNodeName() + ": "
						+ resultNodes.item(i).getTextContent());
			}
			return null;
		}
		Element result = (Element) resultNodes.item(0);

		return result;
	}

	/**
	 * Adds a FileSystem element to the specified jsdl document. If a filesystem
	 * with that name already exists, the new root url is that and it is
	 * returned.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @param fileSystemName
	 *            the name of the (possibly new) filesystem
	 * @param fileSystemRoot the root url for the filesystem
	 * @return the filesystem element
	 */
	public static Element addOrRetrieveExistingFileSystemElement(final Document jsdl,
			final String fileSystemName, final String fileSystemRoot) {
		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl:Resources/jsdl:FileSystem[@name='"
				+ fileSystemName + "']";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger.warn("No application in jsdl file. Good.");
		}

		if (resultNodes != null && resultNodes.getLength() == 1) {
			myLogger
					.info("There's already a filesystem with that name. Returning that one.");
			Element fs = (Element) resultNodes.item(0);
			NodeList childs = fs.getElementsByTagName("MountSource");
			if (childs.getLength() != 1) {
				myLogger
						.error("Filesystem element has got more or less than one MountSource elements. That shouldn't be possible.");
				return null;
			}
			((Element) childs.item(0)).setTextContent(fileSystemRoot);
			return fs;
		}
		if (resultNodes != null && resultNodes.getLength() > 1) {
			myLogger
					.error("More than one filesystems found. That is not possible.");
			for (int i = 0; i < resultNodes.getLength(); i++) {
				myLogger.error(resultNodes.item(i).getNodeName() + ": "
						+ resultNodes.item(i).getTextContent());
			}
			return null;
		}

		// Element old = getUserExecutionHostFSElement(jsdl);
		// System.out.println(old.getNamespaceURI());

		String nsURL = new JSDLNamespaceContext().getNamespaceURI("jsdl");
		// creating new one
		Element filesystem = jsdl.createElementNS(nsURL, "FileSystem");
		// Element filesystem = jsdl.createDElement("jsdl:FileSystem");
		filesystem.setAttribute("name", fileSystemName);
		Element mountSource = jsdl.createElementNS(nsURL, "MountSource");
		Element filesystemtype = jsdl.createElementNS(nsURL, "FileSystemType");
		filesystemtype.setTextContent("normal");
		mountSource.setTextContent(fileSystemRoot);

		filesystem.appendChild(mountSource);
		filesystem.appendChild(filesystemtype);

		// getting resources element
		Element resources = getResourcesElement(jsdl);
		resources.appendChild(filesystem);
		return filesystem;
	}

	/**
	 * Get the resources element.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @return the resources element
	 */
	public static Element getResourcesElement(final Document jsdl) {

		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl:Resources";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger.warn("No application in jsdl file. Good.");
		}

		if (resultNodes == null || resultNodes.getLength() == 0
				|| resultNodes.getLength() > 1) {
			myLogger
					.info("No or more than one Resource elements found. That's not possible");
			return null;
		}

		Element resources = (Element) resultNodes.item(0);
		return resources;
	}

	/**
	 * Gets the (ARCS-specific) templateTag info element which is used to
	 * display information about the application/executable that is used for
	 * this job.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @param templateTagName
	 *            the name of the template tag
	 * @return the element
	 */
	public static Element getTemplateTagInfoElement(final Document jsdl,
			final String templateTagName) {

		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl-arcs:GrisuTemplate/jsdl-arcs:Info/jsdl-arcs:TemplateTag[@name='"
				+ templateTagName + "']";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger.warn("No application in jsdl file. Good.");
		}

		if (resultNodes == null || resultNodes.getLength() == 0
				|| resultNodes.getLength() > 1) {
			myLogger
					.info("No or more than one Resource elements found. That's not possible");
			return null;
		}

		Element resources = (Element) resultNodes.item(0);
		return resources;

	}

	/**
	 * Whether this is a forced-single or forced-mpi job.
	 * 
	 * This is an ARCS-extension to jsdl and is used for usecases where users
	 * want to run 1 cpu mpi jobs or multiple cpu single jobs.
	 * 
	 * @param jsdl
	 *            the jsdl document
	 * @return the job type (single/mpi)
	 */
	public static String getArcsJobType(final Document jsdl) {

		String expression = "/jsdl:JobDefinition/jsdl:JobDescription/jsdl-arcs:JobType";
		NodeList resultNodes = null;
		try {
			resultNodes = (NodeList) xpath.evaluate(expression, jsdl,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			myLogger.warn("No output in jsdl file.");
			return null;
		}

		if (resultNodes.getLength() != 1) {
			return null;
		}

		return resultNodes.item(0).getTextContent();
	}

	/**
	 * Returns a map of all infoitems for this template or null, if no infoItem
	 * exists.
	 * 
	 * @param jsdl
	 *            the jsdl
	 * @param templateTagName the name of the template tag
	 * @return the map or null
	 */
	public static Map<String, String> getTemplateTagInfoItems(final Document jsdl,
			final String templateTagName) {

		Element info = getTemplateTagInfoElement(jsdl, templateTagName);
		if (info == null) {
			return null;
		}

		NodeList infoItems = info.getElementsByTagName("InfoItem");

		int l = infoItems.getLength();
		if (infoItems == null || infoItems.getLength() == 0) {
			return null;
		}

		Map<String, String> result = new TreeMap<String, String>();
		for (int i = 0; i < infoItems.getLength(); i++) {
			String key = ((Element) (infoItems.item(i))).getAttribute("id");
			if (key != null && !"".equals(key)) {
				String value = ((Element) (infoItems.item(i))).getTextContent();
				result.put(key, value);
			}
		}
		if (result.size() == 0) {
			return null;
		}

		return result;

	}

}
