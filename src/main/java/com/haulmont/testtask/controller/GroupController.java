package com.haulmont.testtask.controller;

import com.haulmont.testtask.controller.adapter.GroupConverter;
import com.haulmont.testtask.controller.exception.ControllerCriticalException;
import com.haulmont.testtask.controller.exception.ControllerException;
import com.haulmont.testtask.controller.view.GroupView;
import com.haulmont.testtask.model.dao.HandlerDAO;
import com.haulmont.testtask.model.dao.exception.DAOCriticalException;
import com.haulmont.testtask.model.dao.exception.DAOException;
import com.haulmont.testtask.model.entity.Group;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ovchinnikov on 10.06.2016.
 */
public class GroupController {

    public static int delete(List<GroupView> views) throws ControllerCriticalException, ControllerException {
        List<Group> groups = views.stream().map(view-> GroupConverter.viewToGroup(view, GroupConverter::newGrpoup)).collect(Collectors.toList());
        try {
            return HandlerDAO.delete(groups, Group.class);
        }  catch (DAOCriticalException e) {
            //e.printStackTrace();
            throw new ControllerCriticalException("", e);
        } catch (DAOException e) {
            //e.printStackTrace();
            throw new ControllerException("", e);
        }
    }

    public static List<GroupView> select(List<GroupView> views) throws ControllerCriticalException, ControllerException {
        try {
            List<Group> groups = GroupConverter.viewToGroup(views, GroupConverter::newGrpoup);
            List list = HandlerDAO.select(groups, Group.class);
            List<GroupView> result = GroupConverter.groupToView(list, GroupConverter::newGroupView);
            return result;
        } catch (DAOCriticalException e) {
            //e.printStackTrace();
            throw new ControllerCriticalException("", e);
        } catch (DAOException e) {
            //e.printStackTrace();
            throw new ControllerException("", e);
        }
    }

    public static Long insert(GroupView view) throws ControllerCriticalException, ControllerException {
        try {
            Long id = HandlerDAO.insert(GroupConverter.viewToGroup(view, GroupConverter::newGrpoup));
            return id;
        } catch (DAOCriticalException e) {
            //e.printStackTrace();
            throw new ControllerCriticalException("", e);
        } catch (DAOException e) {
            //e.printStackTrace();
            throw new ControllerException("", e);
        }

    }

    public static boolean update(GroupView view) throws ControllerCriticalException, ControllerException {
        try {
            boolean isUpdate = HandlerDAO.update(GroupConverter.viewToGroup(view, GroupConverter::newGrpoup));
            return isUpdate;
        } catch (DAOCriticalException e) {
            //e.printStackTrace();
            throw new ControllerCriticalException("", e);
        } catch (DAOException e) {
            //e.printStackTrace();
            throw new ControllerException("", e);
        }
    }
}
