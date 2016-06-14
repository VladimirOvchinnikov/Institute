package com.haulmont.testtask.controller;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.haulmont.testtask.controller.adapter.StubGroupToView;
import com.haulmont.testtask.controller.adapter.ViewToEntity;
import com.haulmont.testtask.controller.view.GroupView;
import com.haulmont.testtask.model.dao.GroupDAO;
import com.haulmont.testtask.model.dao.HandlerDAO;
import com.haulmont.testtask.model.entity.Entity;
import com.haulmont.testtask.model.entity.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ovchinnikov on 10.06.2016.
 */
public class GroupController {

    public static int delete(List<GroupView> views) {
        List<Group> groups = new ArrayList<>(views.size());
        for (GroupView view : views) {
            groups.add(StubGroupToView.conv(view));
        }
        //views.stream().map(new ViewToEntity<v,e>())
        return HandlerDAO.delete(groups, Group.class);
    }

    public static List<GroupView> select(List<GroupView> views) {
        List<Group> groups = new ArrayList<>(views.size());
        for (GroupView view : views) {
            groups.add(StubGroupToView.conv(view));
        }
        List<Entity> list = HandlerDAO.select(groups, Group.class);
        List<GroupView> result = Lists.newArrayList();
        for (Entity group : list) {
            result.add(StubGroupToView.conv1((Group) group));
        }
        return result;
    }

    public static Long insert(GroupView view) {
        Long id = HandlerDAO.insert(StubGroupToView.conv(view));
        return id;
    }

    public static boolean update(GroupView view) {
        boolean isUpdate = HandlerDAO.update(StubGroupToView.conv(view));
        return isUpdate;
    }
}
