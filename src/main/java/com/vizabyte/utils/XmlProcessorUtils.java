package com.vizabyte.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Utility class to aid XML processing. Provides methods to retrieve element
 * values via XPath expressions.
 * 
 * @author Drona
 *
 */
public class XmlProcessorUtils {

	private static DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	private static XPathFactory xpathFactory = XPathFactory.newInstance();

	/**
	 * Returns the xml element value as per the given xpath expression.
	 * 
	 * @param doc
	 *            - xml DOM
	 * @param xpathExpr
	 *            - xpath expression object
	 * @param returnType
	 * @return Java type that maps to the input QName
	 * @throws XPathExpressionException
	 */
	public static Object getElementValue(Document doc, XPathExpression xpathExpr, QName returnType)
			throws XPathExpressionException {
		return xpathExpr.evaluate(doc, returnType);
	}

	/**
	 * Returns the xml element value as per the given xpath expression.
	 * 
	 * @param xmlDocumentString
	 *            - xml string
	 * @param xpathExpr
	 *            - xpath expression object
	 * @param returnType
	 *            - QName of the return type as defined in XPathConstants.class.
	 *            One of the following:
	 *            <ul>
	 *            <li>{@link javax.xml.xpath.XPathConstants.STRING}</li>
	 *            <li>{@link javax.xml.xpath.XPathConstants.BOOLEAN}</li>
	 *            <li>{@link javax.xml.xpath.XPathConstants.NUMBER}</li>
	 *            <li>{@link javax.xml.xpath.XPathConstants.NODE}</li>
	 *            <li>{@link javax.xml.xpath.XPathConstants.NODESET}</li>
	 *            </ul>
	 *            The return type should be cast to the appropriate Java type
	 *            that maps to the QName.
	 * @return Java type that maps to the input QName
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws XPathExpressionException
	 */
	public static Object getElementValue(String xmlDocumentString, XPathExpression xpathExpr, QName returnType)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		Document domDocument = getXmlDocument(xmlDocumentString);
		return getElementValue(domDocument, xpathExpr, returnType);
	}

	/**
	 * Returns the xml element value as per the given xpath expression.
	 * 
	 * @param xmlInputStream
	 *            - xml input stream
	 * @param xpathExpr
	 *            - xpath expression object
	 * @param returnType
	 *            - QName of the return type as defined in XPathConstants.class.
	 *            One of the following:
	 *            <ul>
	 *            <li>{@link javax.xml.xpath.XPathConstants.STRING}</li>
	 *            <li>{@link javax.xml.xpath.XPathConstants.BOOLEAN}</li>
	 *            <li>{@link javax.xml.xpath.XPathConstants.NUMBER}</li>
	 *            <li>{@link javax.xml.xpath.XPathConstants.NODE}</li>
	 *            <li>{@link javax.xml.xpath.XPathConstants.NODESET}</li>
	 *            </ul>
	 *            The return type should be cast to the appropriate Java type
	 *            that maps to the QName.
	 * @return Java type that maps to the input QName
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws XPathExpressionException
	 */
	public static Object getElementValue(InputStream xmlInputStream, XPathExpression xpathExpr, QName returnType)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		Document domDocument = getXmlDocument(xmlInputStream);
		return getElementValue(domDocument, xpathExpr, returnType);
	}

	/**
	 * Returns the xml element value as per the given xpath expression.
	 * 
	 * @param xmlDocumentString
	 *            - xml string
	 * @param xpathExpr
	 *            - xpath string
	 * @param returnType
	 *            - QName of the return type as defined in XPathConstants.class.
	 *            One of the following:
	 *            <ul>
	 *            <li>{@link javax.xml.xpath.XPathConstants.STRING}</li>
	 *            <li>{@link javax.xml.xpath.XPathConstants.BOOLEAN}</li>
	 *            <li>{@link javax.xml.xpath.XPathConstants.NUMBER}</li>
	 *            <li>{@link javax.xml.xpath.XPathConstants.NODE}</li>
	 *            <li>{@link javax.xml.xpath.XPathConstants.NODESET}</li>
	 *            </ul>
	 *            The return type should be cast to the appropriate Java type
	 *            that maps to the QName.
	 * @return Java type that maps to the input QName
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws XPathExpressionException
	 */
	public static Object getElementValue(String xmlDocumentString, String xpathExpr, QName returnType)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		Document domDocument = getXmlDocument(xmlDocumentString);

		XPath xPath = xpathFactory.newXPath();
		XPathExpression expr = xPath.compile(xpathExpr);

		return getElementValue(domDocument, expr, returnType);
	}

	/**
	 * Returns the xml element value as per the given xpath expression.
	 * 
	 * @param xmlInputStream
	 *            - xml input stream
	 * @param xpathExpr
	 *            - xpath expression as string
	 * @param returnType
	 *            - QName of the return type as defined in XPathConstants.class.
	 *            One of the following:
	 *            <ul>
	 *            <li>{@link javax.xml.xpath.XPathConstants.STRING}</li>
	 *            <li>{@link javax.xml.xpath.XPathConstants.BOOLEAN}</li>
	 *            <li>{@link javax.xml.xpath.XPathConstants.NUMBER}</li>
	 *            <li>{@link javax.xml.xpath.XPathConstants.NODE}</li>
	 *            <li>{@link javax.xml.xpath.XPathConstants.NODESET}</li>
	 *            </ul>
	 *            The return type should be cast to the appropriate Java type
	 *            that maps to the QName.
	 * @return Java type that maps to the input QName
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws XPathExpressionException
	 */
	public static Object getElementValue(InputStream xmlInputStream, String xpathExpr, QName returnType)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		Document domDocument = getXmlDocument(xmlInputStream);

		XPath xPath = xpathFactory.newXPath();
		XPathExpression expr = xPath.compile(xpathExpr);

		return getElementValue(domDocument, expr, returnType);
	}

	/**
	 * Returns xml DOM from a given xml string
	 * 
	 * @param xmlDocumentString
	 *            - xml string
	 * @return xml DOM object
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private static Document getXmlDocument(String xmlDocumentString)
			throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilder domParser = docBuilderFactory.newDocumentBuilder();
		return domParser.parse(xmlDocumentString);
	}

	/**
	 * Returns an xml DOM from a given xml file.
	 * 
	 * @param xmlFile
	 *            - xml file
	 * @return xml DOM object
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	private static Document getXmlDocument(File xmlFile)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder domParser = docBuilderFactory.newDocumentBuilder();
		return domParser.parse(xmlFile);
	}

	/**
	 * Returns an xml DOM from a given input stream.
	 * 
	 * @param inputStream
	 *            - xml input stream
	 * @return xml DOM object
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private static Document getXmlDocument(InputStream inputStream)
			throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilder domParser = docBuilderFactory.newDocumentBuilder();
		return domParser.parse(inputStream);
	}

	public static void main(String[] args) {
		String xmlPath = "/Users/vikas/work/workspaces/eclipse/indus-ws-utilities/src/inventory.xml";
		String xpathNum = "count(//computer)";
		String xpathString = "/inventory/vendor[1]/computer[1]/model";
		String xpathBoolean = "contains(/inventory/vendor[2], 'Apple')";

		try {
			Double count = (Double) XmlProcessorUtils.getElementValue(xmlPath, xpathNum, XPathConstants.NUMBER);
			System.out.println(count);

			String model = (String) XmlProcessorUtils.getElementValue(xmlPath, xpathString, XPathConstants.STRING);
			System.out.println(model);

			Boolean test = (Boolean) XmlProcessorUtils.getElementValue(xmlPath, xpathBoolean, XPathConstants.BOOLEAN);
			System.out.println(test);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
