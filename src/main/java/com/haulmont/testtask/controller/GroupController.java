package com.haulmont.testtask.controller;

import com.haulmont.testtask.controller.adapter.GroupConverter;
import com.haulmont.testtask.controller.exception.ControllerException;
import com.haulmont.testtask.controller.view.GroupView;
import com.haulmont.testtask.model.dao.HandlerDAO;
import com.haulmont.testtask.model.dao.exception.DAOException;
import com.haulmont.testtask.model.entity.Group;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ovchinnikov on 10.06.2016.
 */
public class GroupController {

    public static int delete(List<GroupView> views) throws ControllerException {
        try {
            List<Group> groups = views.stream().map(view -> GroupConverter.viewToGroup(view, GroupConverter::newGrpoup)).collect(Collectors.toList());
            return HandlerDAO.delete(groups, Group.class);
        } catch (DAOException e) {
            throw new ControllerException(e);
        }
    }

    public static List<GroupView> select(List<GroupView> views) throws ControllerException {
        try {
            List<Group> groups = GroupConverter.viewToGroup(views, GroupConverter::newGrpoup);
            List list = HandlerDAO.select(groups, Group.class);
            List<GroupView> result = GroupConverter.groupToView(list, GroupConverter::newGroupView);
            return result;
        } catch (DAOException e) {
            throw new ControllerException(e);
        }
    }

    public static Long insert(GroupView view) throws ControllerException {
        try {
            Long id = HandlerDAO.insert(GroupConverter.viewToGroup(view, GroupConverter::newGrpoup));
            return id;
        } catch (DAOException e) {
            throw new ControllerException(e);
        }

    }

    public static boolean update(GroupView view) throws ControllerException {
        try {
            return HandlerDAO.update(GroupConverter.viewToGroup(view, GroupConverter::newGrpoup));
        } catch (DAOException e) {
            throw new ControllerException(e);
        }
    }
}
