package com.haulmont.testtask.controller;

import com.haulmont.testtask.controller.view.GroupView;
import com.haulmont.testtask.exception.CriticalException;
import com.haulmont.testtask.model.db.ConnectDB;

/**
 * Created by ovchinnikov on 10.06.2016.
 */
public class MainController {

    public static void main(String[] args) throws CriticalException {
        GroupView view= new GroupView(2L, 1, "");
        System.out.println(GroupController.deleteGroup(view));
        ConnectDB.getInstance().close();
    }
}
