package com.haulmont.testtask.controller.adapter;

import com.haulmont.testtask.controller.view.StudentView;
import com.haulmont.testtask.model.entity.Student;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Leon on 12.06.2016.
 */
public class StudentConverter {

    public static StudentView studentToView(Student student, Converter<Student, StudentView> predicate) {
        return predicate.apply(student);
    }

    public static List<StudentView> studentToView(List<Student> student, Converter<Student, StudentView> predicate) {
        return student.stream().map(predicate::apply).collect(Collectors.toList());
    }

    public static Student viewToStudent(StudentView studentView, Converter<StudentView, Student> predicate) {
        return predicate.apply(studentView);
    }

    public static List<Student> viewToStudent(List<StudentView> studentView, Converter<StudentView, Student> predicate) {
        return studentView.stream().map(predicate::apply).collect(Collectors.toList());
    }

    public static Student newStudent(StudentView view){
        return new Student(view.getId(), view.getFirstName(), view.getMiddleName(), view.getLastName(), view.getBirthDay(), view.getGroupId());
    }

    public static StudentView newStudentView(Student student){
        return new StudentView(student.getId(), student.getFirstName(), student.getMiddleName(), student.getLastName(), student.getBirthDay(), student.getGroupId());
    }
}
