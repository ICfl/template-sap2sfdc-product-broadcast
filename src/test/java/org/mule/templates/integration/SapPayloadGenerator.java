/**
 * Mule Anypoint Template
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 */

package org.mule.templates.integration;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SapPayloadGenerator {
	private static final String DEFAULT_TEMPLATE_NAME = "SAP_TEMPLATE";
	private static final String MATERIAL_ID_XPATH = "//E1MARAM/MATNR";

	private XPath xpath;
	private Document doc;
	private String templateName;

	private List<String> uniqueIdList = new ArrayList<String>();

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
		String xml = loadFile("./src/test/resources/mat_master_new.xml");
		SapPayloadGenerator generator = new SapPayloadGenerator(xml);
		System.out.println(generator.generateXML());

		System.out.println(generator.getUniqueIdList());
	}

	private static String loadFile(String filePath) throws IOException {
		InputStream in = new FileInputStream(filePath);
		return IOUtils.toString(in);
	}

	private static Document buildDocument(String xml) throws ParserConfigurationException, SAXException, IOException {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true); // never forget this!
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));

		return doc;
	}

	public SapPayloadGenerator(String xmlFile) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
		doc = buildDocument(xmlFile);
		xpath = XPathFactory.newInstance().newXPath();
	}

	/**
	 * Generates an SAP XML based on an example XML provided. The returned XML
	 * has its respective IDs created in a unique way for this particular run.
	 * 
	 * @return
	 */
	public String generateXML() {
		uniqueIdList.clear();

		try {
			return generateUniqueIds();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemlateName() {
		if (StringUtils.isEmpty(templateName)) {
			return DEFAULT_TEMPLATE_NAME;
		}
		return templateName;
	}

	public List<String> getUniqueIdList() {
		return uniqueIdList;
	}

	private String generateUniqueIds() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException,
			TransformerException {

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		NodeList nodeList = (NodeList) xpath.compile(MATERIAL_ID_XPATH).evaluate(doc, javax.xml.xpath.XPathConstants.NODESET);
		makeIdsUnique(nodeList);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.transform(new DOMSource(doc), new StreamResult(os));

		return new String(os.toByteArray());

	}

	private void makeIdsUnique(NodeList nodeList) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

		String timeStamp = Long.valueOf(new Date().getTime()).toString();
		String waterMark = "_" + getTemlateName() + timeStamp;

		int index = 0;
		while (index < nodeList.getLength()) {
			Node node = nodeList.item(index);
			String uniqueId = node.getTextContent() + waterMark;
			node.setTextContent(uniqueId);
			index++;

			uniqueIdList.add(uniqueId);
		}
	}
}
