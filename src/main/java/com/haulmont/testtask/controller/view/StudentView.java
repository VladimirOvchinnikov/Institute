package com.haulmont.testtask.controller.view;

import com.haulmont.testtask.exception.CriticalException;
import com.haulmont.testtask.model.db.ConnectDB;
import com.haulmont.testtask.model.entity.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Leon on 12.06.2016.
 */
public class StudentView implements ViewEntity {

    private long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date birthDay;
    private int numberGroup;
    private long groupId;

    public StudentView(){
    }

    public StudentView(long id){
        this.id = id;
    }

    public StudentView(long id, String firstName, String middleName, String  lastName, Date birthDay,  long groupId, int numberGroup){
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.numberGroup = numberGroup;
        this.groupId = groupId;
    }

    public StudentView(String firstName, String middleName, String  lastName, Date birthDay, long groupId, int numberGroup){
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.numberGroup = numberGroup;
        this.groupId = groupId;
    }

    public StudentView(Student student){
        if (student.getId()!=null) {
            this.id = student.getId();
        }
        this.firstName = student.getFirstName();
        this.middleName = student.getMiddleName();
        this.lastName = student.getLastName();
        this.birthDay = student.getBirthDay();

        //stub
//        try(PreparedStatement preparedStatement = ConnectDB.getInstance().getConnection().prepareStatement("SELECT NUMBER FROM GROUPS WHERE ID = ?;")){
//            preparedStatement.setLong(1, student.getGroupId());
//            try(ResultSet rs = preparedStatement.executeQuery()){
//                rs.next();
//                this.numberGroup = rs.getInt("number");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (CriticalException e) {
//            e.printStackTrace();
//        }
        this.groupId = student.getGroupId();
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
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

    public int getNumberGroup() {
        return numberGroup;
    }

    public void setNumberGroup(int numberGroup) {
        this.numberGroup = numberGroup;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
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
