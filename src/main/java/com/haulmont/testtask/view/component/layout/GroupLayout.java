package com.haulmont.testtask.view.component.layout;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.haulmont.testtask.controller.GroupController;
import com.haulmont.testtask.controller.exception.ControllerException;
import com.haulmont.testtask.controller.view.GroupView;
import com.haulmont.testtask.view.component.modal.window.add.AddGroupModalWindow;
import com.haulmont.testtask.view.component.modal.window.edit.EditGroupModalWindow;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;

import java.util.List;

/**
 * Created by Leon on 16.06.2016.
 */
public class GroupLayout extends BasicLayout {
    public GroupLayout(String name) {
        super(name);
        table.setColumnWidth("Факультет", 300);
        table.addContainerProperty("Факультет", String.class, null);
        table.addContainerProperty("Группа", Integer.class, null);
        refresh();
    }

    @Override
    protected void delete() {
        try {
            List<GroupView> listDelete = Lists.newArrayList();
            listDelete.add(new GroupView((Long) table.getValue()));
            if (GroupController.delete(listDelete) == 0) {
                Notification.show("Не удалось удалить группу. В группе еще есть студенты!");
            } else {
                for (GroupView item : listDelete) {
                    table.removeItem(item.getId());
                }
            }
        } catch (ControllerException e) {
            Notification.show(e.getMessage());
        }
    }

    @Override
    protected Window openEditWindow() {
        EditGroupModalWindow window = new EditGroupModalWindow("Добавление группы", table);
        return window;
    }

    @Override
    protected Window openAddWindow() {
        AddGroupModalWindow window = new AddGroupModalWindow("Редактирование группы", table);
        return window;
    }

    @Override
    public void refresh() {
        try {
            table.removeAllItems();
            List<GroupView> groups = GroupController.select(Lists.newArrayList());
            for (GroupView group : groups) {
                table.addItem(new Object[]{
                        group.getFaculty(),
                        group.getNumber()
                }, group.getId());
            }
        } catch (ControllerException e) {
            Notification.show(e.getMessage());
        }
    }
}
