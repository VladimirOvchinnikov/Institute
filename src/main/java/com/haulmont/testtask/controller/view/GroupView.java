package com.haulmont.testtask.controller.view;

import com.haulmont.testtask.model.entity.Group;

/**
 * Created by ovchinnikov on 10.06.2016.
 */
public class GroupView implements ViewEntity {


    private long id;
    private int number;
    private String faculty;

    public GroupView(Long id, Integer number, String faculty){
        this.id = id;
        this.number = number;
        this.faculty = faculty;
    }

    public GroupView(Group group){
        id = group.getId();
        number = group.getNumber();
        faculty = group.getFaculty();
    }

    public GroupView(){

    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

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
