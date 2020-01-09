package com.d2.gap.service;

import com.d2.gap.model.Student;
import com.d2.gap.model.StudentGroup;
import com.d2.gap.service.handler.XmlSaxHandler;
import com.d2.gap.service.service.XpathXmlService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.*;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPathExpressionException;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class XmlService {

    private static final String SOURCE = "src/main/resources/static/student.xml";
    private static final String XSLT_SOURCE = "src/main/resources/static/Xslt2Html.xsl";

    private XpathXmlService service;

    public XmlService() {
        service = new XpathXmlService();
    }

    public Optional<StudentGroup> xmlToObj() {
        File file = new File(SOURCE);
        return unpackXmlToObjWithJaxb(file);
    }


    public List<Student> readStudentFromXmlWithDom() throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(SOURCE));
        Element root = document.getDocumentElement();
        NodeList nList = document.getElementsByTagName("student");
        return fillStudentList(nList);
    }


    public List<Student> readStudentFromXmlWithSAX() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        List<Student> studentList = new ArrayList<>();
        XmlSaxHandler handler = new XmlSaxHandler(studentList);
        parser.parse(new File(SOURCE), handler);
        return studentList;
    }

    public Object readXmlWithXpath(String expression, QName xPathConstantsRealization) throws ParserConfigurationException, SAXException, XPathExpressionException {
        return service.readXml(SOURCE, expression, xPathConstantsRealization);

    }

    public String ObjToXmlWithJaxb(StudentGroup studentGroup) {
        return packObjToXml(studentGroup).get();
    }

    public void transformXmlToHtml() throws ParserConfigurationException, IOException, SAXException, TransformerException {

        TransformerFactory tFactory = TransformerFactory.newInstance();

        Source xslDoc = new StreamSource(XSLT_SOURCE);

        Source xmlDoc = new StreamSource(SOURCE);

        String outputFileName = "output/report.html";


        OutputStream htmlFile = new FileOutputStream(outputFileName);

        Transformer trasform = tFactory.newTransformer(xslDoc);

        trasform.transform(xmlDoc, new StreamResult(htmlFile));
    }


    public static void main(String[] args) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        XmlService service = new XmlService();
        service.transformXmlToHtml();
    }

    private List<Student> fillStudentList(NodeList nList) {
        List<Student> studentList = new ArrayList<>();
        for (int i = 0; i < nList.getLength(); i++) {
            Element student = (Element) nList.item(i);

            studentList.add(new Student(
                    student.getElementsByTagName("name").item(0).getTextContent(),
                    LocalDate.parse(student.getElementsByTagName("birthday").item(0).getTextContent()),
                    student.getElementsByTagName("education").item(0).getTextContent()
            ));

        }
        return studentList;
    }

    private Optional<String> packObjToXml(StudentGroup studentGroup) {
        try {
            StringWriter writer = new StringWriter();
            JAXBContext jaxbContext = JAXBContext.newInstance(StudentGroup.class);
            Marshaller jaxbMarshller = jaxbContext.createMarshaller();
            jaxbMarshller.marshal(studentGroup, writer);
            return Optional.ofNullable(writer.toString());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    private Optional<StudentGroup> unpackXmlToObjWithJaxb(File file) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(StudentGroup.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return Optional.ofNullable((StudentGroup) jaxbUnmarshaller.unmarshal(file));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


}
