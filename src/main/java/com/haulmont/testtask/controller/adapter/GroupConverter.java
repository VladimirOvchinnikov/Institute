package com.haulmont.testtask.controller.adapter;

import com.haulmont.testtask.controller.view.GroupView;
import com.haulmont.testtask.model.entity.Group;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ovchinnikov on 10.06.2016.
 */
public class GroupConverter {

    public static GroupView groupToView(Group student, Converter<Group, GroupView> predicate) {
        return predicate.apply(student);
    }

    public static List<GroupView> groupToView(List<Group> student, Converter<Group, GroupView> predicate) {
        return student.stream().map(predicate::apply).collect(Collectors.toList());
    }

    public static Group viewToGroup(GroupView studentView, Converter<GroupView, Group> predicate) {
        return predicate.apply(studentView);
    }

    public static List<Group> viewToGroup(List<GroupView> studentView, Converter<GroupView, Group> predicate) {
        return studentView.stream().map(predicate::apply).collect(Collectors.toList());
    }

    public static Group newGrpoup(GroupView view) {
        return new Group(view.getId(), view.getNumber(), view.getFaculty());
    }

    public static GroupView newGroupView(Group group) {
        return new GroupView(group.getId(), group.getNumber(), group.getFaculty());
    }
}
