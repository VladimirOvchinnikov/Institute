package com.haulmont.testtask.view.component;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.haulmont.testtask.controller.GroupController;
import com.haulmont.testtask.controller.exception.ControllerCriticalException;
import com.haulmont.testtask.controller.exception.ControllerException;
import com.haulmont.testtask.controller.view.GroupView;
import com.haulmont.testtask.view.component.modal.ButtonBlock;
import com.haulmont.testtask.view.component.modal.window.GroupModalWindow;
import com.vaadin.ui.*;

import java.util.List;

/**
 * Created by Leon on 13.06.2016.
 */
public class GroupUI {
    private static Table groupTable = new Table();
    private static VerticalLayout groupTab = new VerticalLayout();
    //private static HorizontalLayout buttonBlock = new HorizontalLayout();
    private static ButtonBlock buttonBlock = new ButtonBlock();

    //Первичная отрисовка элементов на табе
    public static void prepareGroupPage(){
        groupTable.setSizeFull();
        groupTable.setPageLength(0);
        groupTable.setHeight("100%");
        //Button addGroupButton = new Button("Добавить");
        //Button editGroupButton = new Button("Редактировать");
        //Button deleteGroupButton = new Button("Удалить");

        buttonBlock.getAddButton().addClickListener(e -> {
            UI.getCurrent().addWindow(addAddModal());
        });

        buttonBlock.getEditButton().addClickListener(e -> {
            UI.getCurrent().addWindow(addEditModal());
        });
        buttonBlock.getDeleteButton().addClickListener(e -> {
            deleteGroup();
        });

//        editGroupButton.addClickListener( e -> {
//            UI.getCurrent().addWindow(addEditModal());
//        });
//
//        addGroupButton.addClickListener( e -> {
//            UI.getCurrent().addWindow(addAddModal());
//        });
//
//        deleteGroupButton.addClickListener(e -> {
//            deleteGroup();
//        });
//
        //buttonBlock.setMargin(true);
//        //buttonBlock.setSpacing(true);
        //buttonBlock.addComponent(addGroupButton);
        //buttonBlock.addComponent(editGroupButton);
//        buttonBlock.addComponent(deleteGroupButton);
//
        //editGroupButton.setVisible(false);
        buttonBlock.getEditButton().setVisible(false);
        buttonBlock.getDeleteButton().setVisible(false);
//        deleteGroupButton.setVisible(false);

        groupTable.setSelectable(true);
        groupTable.setColumnWidth("Факультет", 300);
        groupTable.addContainerProperty("Факультет", String.class, null);
        groupTable.addContainerProperty("Группа",  Integer.class, null);

        //Грузим элементы из БД
        try {
            //groupTable = GroupController.loadElems(groupTable);
            List<GroupView> groups = GroupController.select(Lists.newArrayList());
            for (GroupView group : groups) {
                groupTable.addItem(new Object[]{
                        group.getFaculty(),
                        group.getNumber()
                }, group.getId());
            }
        } catch (ControllerException e) {
            e.printStackTrace();
        } catch (ControllerCriticalException e) {
            e.printStackTrace();
        }

        groupTable.setPageLength(10);

        groupTable.addValueChangeListener(e ->{
            if(groupTable.getValue()==null){
                buttonBlock.getEditButton().setVisible(false);
                buttonBlock.getDeleteButton().setVisible(false);
            }else{
                buttonBlock.getEditButton().setVisible(true);
                buttonBlock.getDeleteButton().setVisible(true);
            }
        });

        //groupTab.addComponent(buttonBlock);
        groupTab.addComponent(buttonBlock);
        groupTab.addComponent(groupTable);

        groupTab.setCaption("Группы");
    }

    //Возвращает таб
    public static Component groupPage(){
        if(groupTab.getCaption() == null){
            prepareGroupPage();
            return groupTab;
        }else{
            return groupTab;
        }
    }

    //Удаление группы
    private static void deleteGroup(){
        //Long groupID = (Long) groupTable.getValue();
        List<GroupView> listDelete = Lists.newArrayList();
        listDelete.add(new GroupView((Long)groupTable.getValue()));
        try {
            GroupController.delete(listDelete);//GroupController.delete(groupTable.getValue());
            groupTable.removeAllItems();
            List<GroupView> groups = GroupController.select(Lists.newArrayList());
            for (GroupView group : groups) {
                groupTable.addItem(group);
            }
        } catch (ControllerException e) {
            e.printStackTrace();
        } catch (ControllerCriticalException e) {
            e.printStackTrace();
        }
        /*} catch (SQLException e) {
            e.printStackTrace();
            Notification.show("SQL Error");
        } catch (DatabaseException e) {
            Notification.show("Critical error");
        }*/

    }

    //Возвращает имя таба
    public static String getTabCaption(){
        return groupTab.getCaption();
    }

    private static Window addAddModal(){
        GroupModalWindow window = new GroupModalWindow("Добавление группы", groupTable);
        return window;
    }

    private static Window addEditModal(){
        GroupModalWindow groupModalWindow = new GroupModalWindow("Редактирование группы", groupTable, new GroupView());
        return groupModalWindow;
    }
}
