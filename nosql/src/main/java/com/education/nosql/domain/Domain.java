package com.education.nosql.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "domain")
@Data
@AllArgsConstructor
public class Domain {

    @Id
    private long id;

    //    @Indexed(unique = true)
    private String domain;

    private Integer age;
    private boolean displayAds;

    //getters and setters
}
