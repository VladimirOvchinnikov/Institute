package com.haulmont.testtask.controller.adapter;

import com.haulmont.testtask.controller.view.StudentView;
import com.haulmont.testtask.model.entity.Student;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Leon on 12.06.2016.
 */
public class StubStudentToView {

    public static StudentView conv1(Student student){
        return new StudentView(student);
    }

    public static Student conv(StudentView view){
        return new Student(view);
    }

    public static StudentView convert(Student student, EntityToView<Student, StudentView> predicate){
        return predicate.apply(student);
    }

    public static List<StudentView> convert1(List<Student> student, EntityToView<Student, StudentView> predicate){
        return student.stream().map(e->predicate.apply(e)).collect(Collectors.toList());
        //return predicate.apply(student);
    }
}
