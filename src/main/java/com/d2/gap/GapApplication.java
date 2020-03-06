package com.d2.gap;

import com.d2.gap.service.XmlService;


public class GapApplication {

//	public static void main(String[] args) {
//		SpringApplication.run(GapApplication.class, args);
//	}


	public static void main(String[] args) {
		XmlService service = new XmlService();

		service.xmlToObj().ifPresent(group -> group.getStudents().forEach(System.out::println));

	}

}
