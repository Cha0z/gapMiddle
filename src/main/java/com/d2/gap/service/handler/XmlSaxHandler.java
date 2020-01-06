package com.d2.gap.service.handler;


import com.d2.gap.model.Student;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDate;
import java.util.List;


public class XmlSaxHandler extends DefaultHandler {

    private String currentElementName;
    private String name = "";
    private String birthday = "";
    private String education = "";


    private List<Student> studentList;


    public XmlSaxHandler(List<Student> studentList) {
        this.studentList = studentList;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentElementName = qName;
        System.out.println("/" + qName);
        super.startElement(uri, localName, qName, attributes);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (!name.isEmpty() && !birthday.isEmpty() && !education.isEmpty()) {
            studentList.add(new Student(name, LocalDate.parse(birthday), education));
            name = "";
            birthday = "";
            education = "";
        }
        super.endElement(uri, localName, qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        String information = new String(ch, start, length)
                .replace("/n", "")
                .trim();

        if (!information.isEmpty()) {

            if (currentElementName.equals("name")) {
                name = information;
            }
            if (currentElementName.equals("birthday")) {
                birthday = information;
            }
            if (currentElementName.equals("education")) {
                education = information;
            }

        }
        super.characters(ch, start, length);
    }
    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        super.ignorableWhitespace(ch, start, length);
    }
}
