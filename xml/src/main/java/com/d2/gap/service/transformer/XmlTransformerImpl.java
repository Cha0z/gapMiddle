package com.d2.gap.service.transformer;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class XmlTransformerImpl implements XmlTransformer {

    private static final String XSLT_SOURCE = "src/main/resources/static/Xslt2Html.xsl";
    private static final String OUTPUT_FILE_NAME = "output/students.html";

    public void transformXmlToHtml(String source) {
        try {
            Transformer transformer = getTransformerFactory().newTransformer(new StreamSource(source));
            transformer.transform(new StreamSource(XSLT_SOURCE), new StreamResult(new FileOutputStream(OUTPUT_FILE_NAME)));
        } catch (TransformerException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private TransformerFactory getTransformerFactory() {
        return TransformerFactory.newInstance();
    }

    public static void main(String[] args) {
        XmlTransformer transformer = new XmlTransformerImpl();
        transformer.transformXmlToHtml("student.xml");
    }


}
