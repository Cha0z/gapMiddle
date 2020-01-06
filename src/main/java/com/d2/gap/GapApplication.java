package com.d2.gap;

import com.d2.gap.service.XmlService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.bind.annotation.XmlElement;

@SpringBootApplication
public class GapApplication {

//	public static void main(String[] args) {
//		SpringApplication.run(GapApplication.class, args);
//	}


	public static void main(String[] args) {
		XmlService service = new XmlService();
		service.xmlToObj();
	}

}
