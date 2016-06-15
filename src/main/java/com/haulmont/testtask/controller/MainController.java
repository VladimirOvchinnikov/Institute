package com.haulmont.testtask.controller;

import com.haulmont.testtask.controller.exception.ControllerCriticalException;
import com.haulmont.testtask.controller.exception.ControllerException;
import com.haulmont.testtask.controller.view.StudentView;
import com.haulmont.testtask.model.db.exception.DatabaseException;

import java.util.List;

/**
 * Created by ovchinnikov on 10.06.2016.
 */
public class MainController {

    public static void main(String[] args) throws DatabaseException, ControllerException, ControllerCriticalException {

        StudentView view = new StudentView(null, null, null, "asd", null, null, 6668);
        List<StudentView> views = StudentController.filter(view);
        for(StudentView view1: views){
            System.out.println(view1);
        }


    }
}
