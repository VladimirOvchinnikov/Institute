package com.haulmont.testtask.model.entity;

/**
 * Created by ovchinnikov on 10.06.2016.
 */
public class Group implements Entity {

    private Long id;
    private Integer number;
    private String faculty;

    public Group() {

    }

    public Group(Long id, Integer number, String faculty) {
        this.id = id;
        this.number = number;
        this.faculty = faculty;
    }

    public Group(Integer number, String faculty) {
        this.number = number;
        this.faculty = faculty;
    }

    public Group(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}
