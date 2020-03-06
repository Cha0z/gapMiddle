package com.d2.gap.service;

import com.d2.gap.model.Student;
import com.d2.gap.model.StudentGroup;
import com.d2.gap.service.handler.XmlSaxHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class XmlService {

    private static final String SOURCE = "src/main/resources/static/student.xml";

    public Optional<StudentGroup> xmlToObj() {
        return unpackXmlToObjWithJaxb(new File(SOURCE));
    }


    public List<Student> xmlToObjWithDom() throws IOException, SAXException, ParserConfigurationException {
        return fillStudentList(
                getDomDocument(SOURCE).getElementsByTagName("student")
        );
    }

    private Document getDomDocument(String source) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new File(source));
    }

    private List<Student> fillStudentList(NodeList nList) {
        List<Student> studentList = new ArrayList<>();
        for (int i = 0; i < nList.getLength(); i++) {
            addStudentToList(studentList, (Element) nList.item(i));
        }
        return studentList;
    }

    private void addStudentToList(List<Student> studentList, Element studentElement) {
        studentList.add(createNewStudent(studentElement));
    }

    private Student createNewStudent(Element studentElement) {
        return new Student(
                studentElement.getElementsByTagName("name").item(0).getTextContent(),
                LocalDate.parse(studentElement.getElementsByTagName("birthday").item(0).getTextContent()),
                studentElement.getElementsByTagName("education").item(0).getTextContent()
        );
    }

    public List<Student> xmlToObjWithSax() throws ParserConfigurationException, SAXException, IOException {
        return parseXmlToList(getSaxParser());
    }

    private SAXParser getSaxParser() throws ParserConfigurationException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        return factory.newSAXParser();
    }

    private List<Student> parseXmlToList(SAXParser parser) throws SAXException, IOException {
        List<Student> studentList = new ArrayList<>();
        parser.parse(new File(SOURCE), new XmlSaxHandler(studentList));
        return studentList;
    }
//    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
//        XmlService service = new XmlService();
//        service.xmlToObjWithSax();
//    }

    public String ObjToXmlWithJaxb(StudentGroup studentGroup) {
        return packObjToXml(studentGroup).get();
    }

    private Optional<String> packObjToXml(StudentGroup studentGroup) {
        try {
            StringWriter writer = new StringWriter();
            getJaxbMarshaller(studentGroup.getClass()).marshal(studentGroup, writer);
            return Optional.ofNullable(writer.toString());
        } catch (JAXBException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private Marshaller getJaxbMarshaller(Class aClass) throws JAXBException {
        return getJaxbContext(aClass).createMarshaller();
    }


    private Optional<StudentGroup> unpackXmlToObjWithJaxb(File file) {
        try {
            return Optional.ofNullable((StudentGroup) getJaxbUnmarshaller().unmarshal(file));
        } catch (JAXBException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private Unmarshaller getJaxbUnmarshaller() throws JAXBException {
        return getJaxbContext(StudentGroup.class).createUnmarshaller();
    }

    private JAXBContext getJaxbContext(Class<StudentGroup> studentGroupClass) throws JAXBException {
        return JAXBContext.newInstance(studentGroupClass);
    }

}
