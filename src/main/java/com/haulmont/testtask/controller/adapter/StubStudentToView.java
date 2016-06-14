package com.haulmont.testtask.controller.adapter;

import com.haulmont.testtask.controller.view.StudentView;
import com.haulmont.testtask.model.entity.Student;

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
}
