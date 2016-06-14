package com.haulmont.testtask.model.dao.helper;

import com.haulmont.testtask.model.entity.Entity;
import com.haulmont.testtask.model.entity.Group;
import com.haulmont.testtask.model.entity.Student;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Leon on 14.06.2016.
 */
public class Helper {

    private static void setParametersInsertGroup(PreparedStatement ps, Group group) throws SQLException {
        ps.setInt(1, group.getNumber());
        ps.setString(2, group.getFaculty());
    }
    private static void setParametersInsertStudent(PreparedStatement ps, Student student) throws SQLException {
        ps.setString(1, student.getFirstName());
        ps.setString(2, student.getMiddleName());
        ps.setString(3, student.getLastName());
        ps.setDate(4, new Date(student.getBirthDay().getTime()));
        ps.setLong(5, student.getGroupId());
    }

    public static void setParametersInsert(PreparedStatement ps, Entity entity) throws SQLException {
        if (entity.getClass().equals(Student.class)){
            setParametersInsertStudent( ps, (Student) entity);
        } else if (entity.getClass().equals(Group.class)){
            setParametersInsertGroup( ps, (Group) entity);
        }else {
            //throw
        }
    }


    public static Entity getEntity(Class clazz, ResultSet rs) throws SQLException {
        if (clazz.equals(Student.class)){
            return getEntityStudent(rs);
        }else if (clazz.equals(Group.class)){
            return getEntityGroup(rs);
        }else {
            return null;//throw ClassNotFounded
        }

    }

    private static Student getEntityStudent(ResultSet rs) throws SQLException {
        Student student =  new Student();
        student.setId(rs.getLong("id"));
        student.setFirstName(rs.getString("first_name"));
        student.setMiddleName(rs.getString("middle_name"));
        student.setLastName(rs.getString("last_name"));
        student.setBirthDay(rs.getDate("birth_day"));
        student.setGroupId(rs.getLong("group_id"));
        return student;
    }

    private static Group getEntityGroup(ResultSet rs) throws SQLException {
        Group group = new Group();
        group.setId(rs.getLong("id"));
        group.setNumber(rs.getInt("number"));
        group.setFaculty(rs.getString("faculty"));
        return group;
    }
}
