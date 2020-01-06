package com.d2.gap.service;

import com.d2.gap.model.StudentGroup;
import org.xml.sax.SAXException;
import java.util.Optional;
import org.w3c.dom.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.*;
import java.io.*;

public class XmlService {

    private static final String SOURCE = "src/main/resources/static/student.xml";

    public Optional<StudentGroup> xmlToObj() {
        File file = new File(SOURCE);
        return unpackXmlToObj(file);
    }

    public void xmlToObjWithDom() throws IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = builder.parse(new File(SOURCE));
        document.getDocumentElement().normalize();

        Element root = document.getDocumentElement();
        NodeList child = root.getChildNodes();
        System.out.println(child.getLength());


    }

    public String ObjToXml(StudentGroup studentGroup) {
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


    private Optional<StudentGroup> unpackXmlToObj(File file) {
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
