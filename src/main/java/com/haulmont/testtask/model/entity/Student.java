package com.haulmont.testtask.model.entity;

import com.haulmont.testtask.controller.view.StudentView;

import java.util.Date;

/**
 * Created by Leon on 12.06.2016.
 */
public class Student implements Entity {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date birthDay;
    private Long groupId;


    public Student(){
    }

    public Student(Long id){
        this.id = id;
    }

    public Student(Long id, String firstName, String middleName, String lastName, Date birthDay, Long groupId){
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.groupId = groupId;
    }

    public Student( String firstName, String middleName, String lastName, Date birthDay, Long groupId){
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.groupId = groupId;
    }

    public Student(StudentView view){
        this.id = view.getId();
        this.firstName = view.getFirstName();
        this.middleName = view.getMiddleName();
        this.lastName = view.getLastName();
        this.birthDay = view.getBirthDay();
        this.groupId = view.getGroupId();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDay=" + birthDay +
                ", groupId=" + groupId +
                '}';
    }
}
