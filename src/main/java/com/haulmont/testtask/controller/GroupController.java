package com.haulmont.testtask.controller;

import com.haulmont.testtask.controller.adapter.StubGroupToView;
import com.haulmont.testtask.controller.view.GroupView;
import com.haulmont.testtask.model.dao.GroupDAO;

/**
 * Created by ovchinnikov on 10.06.2016.
 */
public class GroupController {

    private static GroupDAO groupDAO = new GroupDAO();

    public static boolean deleteGroup(GroupView view){
         return groupDAO.delete(StubGroupToView.conv(view));
    }
}
