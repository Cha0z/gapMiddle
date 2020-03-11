package com.d2.gap.service;

import com.d2.gap.model.Student;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

public interface XmlService {

    List<Student> readStudentsFromXml(String source) throws SAXException, IOException, ParserConfigurationException;

    void writeStudentsToXml(Student element) throws ParserConfigurationException, TransformerException;

    void writeStudentsToXml(List<Student> element) throws IOException, ParserConfigurationException, TransformerException;


}
