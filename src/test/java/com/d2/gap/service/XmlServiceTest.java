package com.d2.gap.service;

import com.d2.gap.model.Student;
import com.d2.gap.model.StudentGroup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class XmlServiceTest {

    private XmlService service = new XmlService();
    private StudentGroup expected;
    private List<Student> students;
    private static final String SOURCE = "src/main/resources/static/student.xml";


    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    public void testXmlToOBj(Integer number) {
        expected = new StudentGroup();
        expected.setGroupName("TK");
        students = Arrays.asList(new Student("Alex", LocalDate.parse("1997-08-16"), "master"),
                new Student("John", LocalDate.parse("1789-01-09"), "bachelor"));
        expected.setStudents(students);


        StudentGroup actual = service.xmlToObj().get(); //будет время - поправить, в тесте не критично
        assertEquals(expected.getGroupName(), actual.getGroupName());
        assertEquals(students.get(number).getBirthday(), actual.getStudents().get(number).getBirthday());
        assertEquals(students.get(number).getName(), actual.getStudents().get(number).getName());
        assertEquals(students.get(number).getEducation(), actual.getStudents().get(number).getEducation());
    }

    @Test
    public void testObjToXml() {
        expected = new StudentGroup();
        expected.setGroupName("TK");
        students = Arrays.asList(new Student("Alex", LocalDate.parse("1997-08-16"), "master"),
                new Student("John", LocalDate.parse("1789-01-09"), "bachelor"));
        expected.setStudents(students);
        StringBuilder textFromFile = new StringBuilder("");
        try (BufferedReader reader = new BufferedReader(new FileReader(SOURCE))) {
            textFromFile.append(reader.readLine());
            String line;
            while ((line = reader.readLine()) != null) {
                textFromFile.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println();
    }


    @Test
    public void testXmlToObjWithDom() {
        expected = new StudentGroup();
        expected.setGroupName("TK");
        students = Arrays.asList(new Student("Alex", LocalDate.parse("1997-08-16"), "master"),
                new Student("John", LocalDate.parse("1789-01-09"), "bachelor"));
        expected.setStudents(students);


        try {
            service.xmlToObjWithDom();
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }
    }
}
