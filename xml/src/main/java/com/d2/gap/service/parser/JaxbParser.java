package com.d2.gap.service.parser;

import com.d2.gap.model.Student;
import com.d2.gap.model.StudentGroup;
import com.d2.gap.service.XmlService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.List;
import java.util.Optional;

public class JaxbParser implements XmlService {

    private static final String OUTPUT = "output/student";

    @Override
    public List<Student> readStudentsFromXml(String source) {
        return unpackXmlToObjWithJaxb(new File(source)).get().getStudents();
    }

    @Override
    public void writeStudentsToXml(Student student) {

    }


    @Override
    public void writeStudentsToXml(List<Student> students) throws IOException {
        Integer counter = 0;
        for (Student student : students) {
            writeObjToFile(counter++, student);
        }
    }

    private void writeObjToFile(Integer counter, Student student) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(format(counter))));
        out.write(writeStudentsToString(student));
        out.close();
    }

    private String format(Integer number) {
        return OUTPUT + number + ".xml";
    }

    private String writeStudentsToString(Student student) {
        return packObjToXml(student).get();
    }


    private Optional<String> packObjToXml(Student student) {
        try {
            StringWriter writer = new StringWriter();
            getJaxbMarshaller(Student.class).marshal(student, writer);
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
