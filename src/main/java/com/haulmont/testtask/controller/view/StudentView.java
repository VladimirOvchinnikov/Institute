package com.haulmont.testtask.controller.view;

import java.util.Date;

/**
 * Created by Leon on 12.06.2016.
 */
public class StudentView implements ViewEntity {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date birthDay;
    private Integer numberGroup;
    private Long groupId;

    public StudentView() {
    }

    public StudentView(Long id) {
        this.id = id;
    }

    public StudentView(Long id, String firstName, String middleName, String lastName, Date birthDay, Long groupId, Integer numberGroup) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.numberGroup = numberGroup;
        this.groupId = groupId;
    }

    public StudentView(Long id, String firstName, String middleName, String lastName, Date birthDay, Long groupId) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.groupId = groupId;
    }

    public StudentView(String firstName, String middleName, String lastName, Date birthDay, Long groupId, Integer numberGroup) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.numberGroup = numberGroup;
        this.groupId = groupId;
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

    public Integer getNumberGroup() {
        return numberGroup;
    }

    public void setNumberGroup(Integer numberGroup) {
        this.numberGroup = numberGroup;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "StudentView{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDay=" + birthDay +
                ", numberGroup=" + numberGroup +
                ", groupId=" + groupId +
                '}';
    }
}
