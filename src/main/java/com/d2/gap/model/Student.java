package com.d2.gap.model;


import com.d2.gap.adapter.LocalDateAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;


public class Student {
    private String name;
    private LocalDate birthday;
    private String education;

    public Student() {
    }

    public Student(String name, LocalDate birthday, String education) {
        this.name = name;
        this.birthday = birthday;
        this.education = education;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public LocalDate getBirthday() {
        return birthday;
    }
//    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }
}
