package com.d2.gap.service.parser;

import com.d2.gap.model.Student;
import com.d2.gap.service.XmlService;
import com.d2.gap.service.helper.DomTransformerHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DomParser implements XmlService {

    private static final String ELEMENT_TO_PARSE = "student";


    @Override
    public List<Student> readStudentsFromXml(String source) throws SAXException, IOException, ParserConfigurationException {
        return fillStudentList(
                getDomDocument(source).getElementsByTagName(ELEMENT_TO_PARSE)
        );
    }


    @Override
    public void writeStudentsToXml(Student student) throws ParserConfigurationException, TransformerException {
        writeStudentsToXml(Collections.singletonList(student));
    }

    @Override
    public void writeStudentsToXml(List<Student> student) throws ParserConfigurationException, TransformerException {
        Document doc = getDocumentBuilder().newDocument();
        Element root = createRootElement(doc);
        student.forEach(s -> prepareDocumentToWrite(s, doc, root));
        DomTransformerHelper.getTransformer()
                .transform(new DOMSource(doc),
                        new StreamResult(new File("src/main/resources/users.xml"))
                );
    }

    private void prepareDocumentToWrite(Student student, Document doc, Element root) {
        root.appendChild(createStudent(doc, student));
    }

    private Element createRootElement(Document doc) {
        Element root = doc.createElementNS("students", "students");
        doc.appendChild(root);
        return root;
    }

    private Document getDomDocument(String source) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder builder = getDocumentBuilder();
        return builder.parse(new File(source));
    }

    private DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        return factory.newDocumentBuilder();
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

    private Node createStudent(Document document, Student student) {
        Element studentElement = document.createElement("student");
        addStudentFieldFromObjToDoc(document, student, studentElement);
        return studentElement;
    }

    private void addStudentFieldFromObjToDoc(Document document, Student student, Element studentElement) {
        studentElement.appendChild(createStudentElement(document, "name", student.getName()));
        studentElement.appendChild(createStudentElement(document, "birthday", student.getBirthday().toString()));
        studentElement.appendChild(createStudentElement(document, "education", student.getEducation()));
    }

    private Node createStudentElement(Document document, String name, String value) {
        Element node = document.createElement(name);
        node.appendChild(document.createTextNode(value));
        return node;
    }


}
