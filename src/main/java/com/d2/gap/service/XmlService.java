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
        File file = new File(SOURCE);
        return unpackXmlToObjWithJaxb(file);
    }


    public List<Student> xmlToObjWithDom() throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();


        Document document = builder.parse(new File(SOURCE));


        Element root = document.getDocumentElement();


        NodeList nList = document.getElementsByTagName("student");

        return fillStudentList(nList);


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

    public List<Student> xmlToObjWithSax() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        List<Student> studentList = new ArrayList<>();

        XmlSaxHandler handler = new XmlSaxHandler(studentList);
        parser.parse(new File(SOURCE), handler);

        return studentList;
    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        XmlService service = new XmlService();
        service.xmlToObjWithSax();
    }


    public String ObjToXmlWithJaxb(StudentGroup studentGroup) {
        return packObjToXml(studentGroup).get();
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
