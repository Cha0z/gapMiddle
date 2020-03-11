package com.d2.gap.service.parser;

import com.d2.gap.model.Student;
import com.d2.gap.service.XmlService;
import com.d2.gap.service.handler.XmlSaxHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaxParser implements XmlService {
    @Override
    public List<Student> readStudentsFromXml(String source) throws SAXException, IOException, ParserConfigurationException {
        return parseXmlToList(getSaxParser(), source);
    }

    @Override
    public void writeStudentsToXml(Student element) {

    }

    @Override
    public void writeStudentsToXml(List<Student> element) {

    }

    private SAXParser getSaxParser() throws ParserConfigurationException, SAXException {
        return SAXParserFactory.newInstance().newSAXParser();
    }

    private List<Student> parseXmlToList(SAXParser parser, String source) throws SAXException, IOException {
        List<Student> studentList = new ArrayList<>();
        parser.parse(new File(source), new XmlSaxHandler(studentList));
        return studentList;
    }
}
