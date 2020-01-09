package com.d2.gap.service.service;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class XpathXmlService{


    public Object readXml(String source, String expression, QName type) throws ParserConfigurationException, SAXException, XPathExpressionException {
        try (FileInputStream fileIS = new FileInputStream(new File(source))) {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDocument = builder.parse(fileIS);
            XPath xPath = XPathFactory.newInstance().newXPath();

            return xPath.compile(expression).evaluate(xmlDocument, type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
