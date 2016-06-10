package com.haulmont.testtask.controller.adapter;

import com.haulmont.testtask.controller.view.GroupView;
import com.haulmont.testtask.model.entity.Group;

/**
 * Created by ovchinnikov on 10.06.2016.
 */
public class StubGroupToView {

    public static GroupView conv1(Group group){
        return new GroupView(group);
    }

    public static Group conv(GroupView view){
        return new Group(view);
    }
}
