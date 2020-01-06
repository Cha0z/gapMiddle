package com.d2.gap.service;

import com.d2.gap.model.Student;
import com.d2.gap.model.StudentGroup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class XmlServiceTest {

    private XmlService service = new XmlService();
    private StudentGroup expectedGroup;
    private List<Student> expectedStudents;
    private static final String SOURCE = "src/main/resources/static/student.xml";


    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    public void testXmlToOBj(Integer number) {
        expectedGroup = new StudentGroup();
        expectedGroup.setGroupName("TK");
        expectedStudents = Arrays.asList(new Student("Alex", LocalDate.parse("1997-08-16"), "master"),
                new Student("John", LocalDate.parse("1789-01-09"), "bachelor"));
        expectedGroup.setStudents(expectedStudents);


        StudentGroup actual = service.xmlToObj().get(); //будет время - поправить, в тесте не критично
        assertEquals(expectedGroup.getGroupName(), actual.getGroupName());
        assertEquals(expectedStudents.get(number).getBirthday(), actual.getStudents().get(number).getBirthday());
        assertEquals(expectedStudents.get(number).getName(), actual.getStudents().get(number).getName());
        assertEquals(expectedStudents.get(number).getEducation(), actual.getStudents().get(number).getEducation());
    }

    @Test
    public void testObjToXml() {
        expectedGroup = new StudentGroup();
        expectedGroup.setGroupName("TK");
        expectedStudents = Arrays.asList(new Student("Alex", LocalDate.parse("1997-08-16"), "master"),
                new Student("John", LocalDate.parse("1789-01-09"), "bachelor"));
        expectedGroup.setStudents(expectedStudents);
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


    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    public void testXmlToObjWithDom(Integer number) throws ParserConfigurationException {

        expectedStudents = Arrays.asList(new Student("Alex", LocalDate.parse("1997-08-16"), "master"),
                new Student("John", LocalDate.parse("1789-01-09"), "bachelor"));


        List<Student> actual = fillListWtihDom();
        assertEquals(expectedStudents.get(number), actual.get(number));


    }
    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    public void testXmlToObjWithSax(Integer number) throws ParserConfigurationException {

        expectedStudents = Arrays.asList(new Student("Alex", LocalDate.parse("1997-08-16"), "master"),
                new Student("John", LocalDate.parse("1789-01-09"), "bachelor"));


        List<Student> actual = fillListWtihSax();
        assertEquals(expectedStudents.get(number), actual.get(number));


    }

    private List<Student> fillListWtihDom() throws ParserConfigurationException {

        try {
            return service.xmlToObjWithDom();
        } catch (IOException | SAXException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Student> fillListWtihSax() throws ParserConfigurationException {

        try {
            return service.xmlToObjWithDom();
        } catch (IOException | SAXException e) {
            e.printStackTrace();
            return null;
        }
    }
}
