package com.d2.gap.service;

import com.d2.gap.model.Student;
import com.d2.gap.service.factory.XmlFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class XmlServiceTest {


    private XmlService service = null;
    private List<Student> expectedStudents;
    private static final String SOURCE = "src/main/resources/static/student.xml";


    @BeforeEach
    public void initialize() {
        prepareStudentsForTest();
    }


    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    public void testXmlToOBjWithJaxb(Integer number) throws ParserConfigurationException, SAXException, IOException {
        initXmlService(ParserType.JAXB);
        List<Student> actual = service.readStudentsFromXml(SOURCE);

        assertEquals(expectedStudents.get(number).getBirthday(), actual.get(number).getBirthday());
        assertEquals(expectedStudents.get(number).getName(), actual.get(number).getName());
        assertEquals(expectedStudents.get(number).getEducation(), actual.get(number).getEducation());
    }

    @Test
    public void testObjToXmlWithJaxb() throws IOException, TransformerException, ParserConfigurationException {
        initXmlService(ParserType.JAXB);
        service.writeStudentsToXml(expectedStudents);

    }


    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    public void testXmlToObjWithDom(Integer number) throws ParserConfigurationException {
        initXmlService(ParserType.DOM);
        List<Student> actual = fillListWithDom();
        assertEquals(expectedStudents.get(number), actual.get(number));
    }


    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    public void testXmlToObjWithSax(Integer number) throws ParserConfigurationException {
        initXmlService(ParserType.SAX);

        List<Student> actual = fillListWithSax();
        assertEquals(expectedStudents.get(number), actual.get(number));
    }

    private void prepareStudentsForTest() {
        expectedStudents = Arrays.asList(new Student("Alex", LocalDate.parse("1997-08-16"), "master"),
                new Student("John", LocalDate.parse("1789-01-09"), "bachelor"));
    }

    private List<Student> fillListWithDom() throws ParserConfigurationException {
        try {
            return service.readStudentsFromXml(SOURCE);
        } catch (IOException | SAXException e) {
            e.printStackTrace();
            return null;
        }
    }


    private List<Student> fillListWithSax() throws ParserConfigurationException {
        try {
            return service.readStudentsFromXml(SOURCE);
        } catch (IOException | SAXException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void initXmlService(ParserType dom) {
        service = XmlFactory.getXmlParser(dom);
    }
}
