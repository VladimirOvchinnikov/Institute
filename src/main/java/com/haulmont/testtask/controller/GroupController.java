package com.haulmont.testtask.controller;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.haulmont.testtask.controller.adapter.StubGroupToView;
import com.haulmont.testtask.controller.view.GroupView;
import com.haulmont.testtask.model.dao.GroupDAO;
import com.haulmont.testtask.model.entity.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ovchinnikov on 10.06.2016.
 */
public class GroupController {

    private static GroupDAO groupDAO = new GroupDAO();

    public static int delete(List<GroupView> views){
        List<Group> groups = new ArrayList<>(views.size());
        for(GroupView view: views){
            groups.add(StubGroupToView.conv(view));
        }
         return groupDAO.delete(groups);
    }

    public static List<GroupView> select(List<GroupView> views){
        List<Group> groups = new ArrayList<>(views.size());
        for(GroupView view: views){
            groups.add(StubGroupToView.conv(view));
        }
        List<Group> list = groupDAO.select(groups);
        List<GroupView> result = Lists.newArrayList();
        for(Group group: list){
            result.add(StubGroupToView.conv1(group));
        }
        return result;
    }

    public static Long insert(GroupView view){
        Long id = groupDAO.insert(StubGroupToView.conv(view));
        return id;
    }

    public static boolean update(GroupView view){
        boolean isUpdate = groupDAO.update(StubGroupToView.conv(view));
        return isUpdate;
    }
}
