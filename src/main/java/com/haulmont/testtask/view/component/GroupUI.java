package com.haulmont.testtask.view.component;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.haulmont.testtask.controller.GroupController;
import com.haulmont.testtask.controller.exception.ControllerCriticalException;
import com.haulmont.testtask.controller.exception.ControllerException;
import com.haulmont.testtask.controller.view.GroupView;
import com.haulmont.testtask.view.component.modal.window.GroupModalWindow;
import com.vaadin.ui.*;

import java.util.List;

/**
 * Created by Leon on 13.06.2016.
 */
public class GroupUI {
    private static Table groupTable = new Table();
    private static VerticalLayout groupTab = new VerticalLayout();
    private static HorizontalLayout buttonBlock = new HorizontalLayout();
    //private static ButtonBlock buttonBlock = new ButtonBlock();

    //Первичная отрисовка элементов на табе
    public static void prepareGroupPage(){
        groupTable.setSizeFull();
        groupTable.setPageLength(0);
        groupTable.setHeight("100%");
        Button addGroupButton = new Button("Добавить");
        Button editGroupButton = new Button("Редактировать");
        //Button deleteGroupButton = new Button("Удалить");

        editGroupButton.addClickListener( e -> {
            UI.getCurrent().addWindow(addEditModal());
        });
//
        addGroupButton.addClickListener( e -> {
            UI.getCurrent().addWindow(addAddModal());
        });
//
//        deleteGroupButton.addClickListener(e -> {
//            deleteGroup();
//        });
//
        buttonBlock.setMargin(true);
//        //buttonBlock.setSpacing(true);
        buttonBlock.addComponent(addGroupButton);
        buttonBlock.addComponent(editGroupButton);
//        buttonBlock.addComponent(deleteGroupButton);
//
        editGroupButton.setVisible(false);
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
                editGroupButton.setVisible(false);
//                buttonBlock.visibleDeleteButton(false);
//                buttonBlock.visibleEditButton(false);
            }else{
                editGroupButton.setVisible(true);
//                buttonBlock.visibleDeleteButton(true);
//                buttonBlock.visibleEditButton(true);
            }
        });

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

    //Добавление группы
    private static long addGroup(String facultyName, Integer groupNumber){
        try {
            long id = GroupController.insert(new GroupView(groupNumber, facultyName));
            Notification.show("Запись добавлена");
            return id;
        } catch (ControllerException e) {
            e.printStackTrace();
            return -1L;
        } catch (ControllerCriticalException e) {
            e.printStackTrace();
            return -1L;
        }
//        } catch (ClassNotFoundException e) {
//            Notification.show("Class not found");
//            return -1;
//        } catch (SQLException e) {
//            Notification.show("SQL Error");
//            return -1;
//        } catch (DatabaseException e) {
//            Notification.show("Critical error");
//            return -1;
//        }
    }

    //Апдейт группы
    private static boolean editGroup(String facultyName, Integer groupNumber, Long groupId){
        try {
            GroupView group = new GroupView(groupId, groupNumber, facultyName);
            boolean ret = GroupController.update(group);
            Notification.show("Запись изменена");
            return ret;
        } catch (ControllerException e) {
            e.printStackTrace();
            return false;
        } catch (ControllerCriticalException e) {
            e.printStackTrace();
            return false;
        }
//        } catch (ClassNotFoundException e) {
//            Notification.show("Class not found");
//            return null;
//        } catch (SQLException e) {
//            Notification.show("SQL Error");
//            return null;
//        } catch (DatabaseException e) {
//            Notification.show("Critical error");
//            return null;
//        }
    }

    //Возвращает имя таба
    public static String getTabCaption(){
        return groupTab.getCaption();
    }

    //Добавление модального окна для добавления группы
    private static Window addAddModal(){

        Window modalNewGroup = new Window("Добавление группы");
//        VerticalLayout modalNewGroupContent = new VerticalLayout();
//        modalNewGroupContent.setMargin(true);
//        modalNewGroup.setContent(modalNewGroupContent);
//
//        TextField inputNewGroupFaculty = new TextField();
//        inputNewGroupFaculty.setCaption("Факультет");
//        inputNewGroupFaculty.addValidator(new StringLengthValidator(
//                "Название факультета должно быть больше 1 символа и меньше 40",
//                1, 40, true));
//
//        inputNewGroupFaculty.setImmediate(true);
//        inputNewGroupFaculty.setValidationVisible(false);
//        inputNewGroupFaculty.setBuffered(true);
//
//        TextField inputNewGroupNumber = new TextField();
//        inputNewGroupNumber.setCaption("Номер группы");
//        inputNewGroupNumber.addValidator(new StringLengthValidator(
//                "Номер группы должен быть больше 1 символа и меньше 10",
//                1, 10, true));
//
//        inputNewGroupNumber.setImmediate(true);
//        inputNewGroupNumber.setValidationVisible(false);
//        inputNewGroupNumber.setBuffered(true);
//
//        Button submitNewGroup = new Button();
//        submitNewGroup.setCaption("Добавить");
//        Button closeNewGroup = new Button();
//        closeNewGroup.setCaption("Отменить");
//        closeNewGroup.addClickListener(e -> {
//            modalNewGroup.close();
//        });
//
//        submitNewGroup.addClickListener(e -> {
//            if(inputNewGroupNumber.getValue().matches("[-+]?\\d+")) {
//                try {
//                    inputNewGroupFaculty.setValidationVisible(true);
//                    inputNewGroupFaculty.validate();
//                    inputNewGroupFaculty.commit();
//
//                    inputNewGroupNumber.setValidationVisible(true);
//                    inputNewGroupNumber.validate();
//                    inputNewGroupNumber.commit();
//
//                    long res = addGroup(inputNewGroupFaculty.getValue(), Integer.parseInt(inputNewGroupNumber.getValue()));
//                    //Добавляем в таблицу то, что отдала база данных, а не то, что ввел пользователь на случай если
//                    //запись в БД будет некорректной (например, неверная кодировка)
//                    //if (res.size() > 0) {
//                        //for (UniverGroup result : res) {
//                            groupTable.addItem(new Object[]{inputNewGroupFaculty.getValue(), Integer.parseInt(inputNewGroupNumber.getValue())}, res);
//                        //}
//                    //}
//                    inputNewGroupNumber.setValidationVisible(false);
//                    inputNewGroupFaculty.setValidationVisible(false);
//                    modalNewGroup.close();
//                } catch (Validator.InvalidValueException ex) {
//                    Notification.show(ex.getMessage());
//                }
//            }else{
//                inputNewGroupNumber.setValidationVisible(true);
//                Notification.show("Номер группы должен быть числовым");
//            }
//        });
//
//        FormLayout formAddGroup = new FormLayout();
//        formAddGroup.setWidth("335px");
//        formAddGroup.addComponent(inputNewGroupFaculty);
//        formAddGroup.addComponent(inputNewGroupNumber);
//        HorizontalLayout buttonPlace = new HorizontalLayout();
//        buttonPlace.addComponent(submitNewGroup);
//        buttonPlace.addComponent(closeNewGroup);
//        formAddGroup.addComponent(buttonPlace);
//        modalNewGroupContent.addComponent(formAddGroup);
//
//        modalNewGroup.center();
        GroupModalWindow window = new GroupModalWindow("Добавление группы", groupTable);

        return window;
        //return modalNewGroup;
    }

    //Добавление модального окна на редактирование группы
    private static Window addEditModal(){
//        Window modalEditGroup = new Window("Редактирование группы");
//        VerticalLayout modalEditGroupContent = new VerticalLayout();
//        modalEditGroupContent.setMargin(true);
//        modalEditGroup.setContent(modalEditGroupContent);
//        Object rowId = groupTable.getValue();
//
//        TextField inputEditGroupFaculty = new TextField();
//        inputEditGroupFaculty.setCaption("Факультет");
//        inputEditGroupFaculty.addValidator(new StringLengthValidator(
//                "Название факультета должно быть больше 1 символа и меньше 40",
//                1, 40, true));
//        inputEditGroupFaculty.setImmediate(true);
//        inputEditGroupFaculty.setValidationVisible(false);
//        inputEditGroupFaculty.setBuffered(true);
//        String facultyName = groupTable.getContainerProperty(rowId, "Факультет").getValue().toString();
//        inputEditGroupFaculty.setValue(facultyName);
//
//        TextField inputEditGroupNumber = new TextField();
//        inputEditGroupNumber.setCaption("Номер группы");
//        inputEditGroupNumber.addValidator(new StringLengthValidator(
//                "Номер группы должен быть больше 1 символа и меньше 10",
//                1, 10, true));
//        inputEditGroupNumber.setImmediate(true);
//        inputEditGroupNumber.setValidationVisible(false);
//        inputEditGroupNumber.setBuffered(true);
//        String groupNumber = groupTable.getContainerProperty(rowId, "Группа").getValue().toString();
//        inputEditGroupNumber.setValue(groupNumber);
//
//        Button submitEditGroup = new Button();
//        submitEditGroup.setCaption("Изменить");
//        Button closeEditGroup = new Button();
//        closeEditGroup.setCaption("Отменить");
//        closeEditGroup.addClickListener(e -> {
//            modalEditGroup.close();
//        });
//
//        submitEditGroup.addClickListener(e -> {
//            if(inputEditGroupNumber.getValue().matches("[-+]?\\d+")) {
//                try {
//                    inputEditGroupFaculty.setValidationVisible(true);
//                    inputEditGroupFaculty.validate();
//                    inputEditGroupFaculty.commit();
//
//                    inputEditGroupNumber.setValidationVisible(true);
//                    inputEditGroupNumber.validate();
//                    inputEditGroupNumber.commit();
//
//                    Object group = inputEditGroupNumber.getValue();
//                    Object faculty = inputEditGroupFaculty.getValue();
//
//                    editGroup(faculty.toString(), Integer.parseInt(group.toString()), Long.parseLong(rowId.toString()));
//
//                    groupTable.getContainerProperty(rowId, "Факультет").setValue(faculty.toString());
//                    groupTable.getContainerProperty(rowId, "Группа").setValue(Integer.parseInt(group.toString()));
//
//                    inputEditGroupNumber.setValidationVisible(false);
//                    inputEditGroupFaculty.setValidationVisible(false);
//                    modalEditGroup.close();
//                } catch (Validator.InvalidValueException ex) {
//                    Notification.show(ex.getMessage());
//                }
//            }else{
//                inputEditGroupNumber.setValidationVisible(true);
//                Notification.show("Номер группы должен быть числовым");
//            }
//
//        });
//
//        FormLayout formAddGroup = new FormLayout();
//        formAddGroup.setWidth("335px");
//        formAddGroup.addComponent(inputEditGroupFaculty);
//        formAddGroup.addComponent(inputEditGroupNumber);
//        HorizontalLayout buttonPlace = new HorizontalLayout();
//        buttonPlace.addComponent(submitEditGroup);
//        buttonPlace.addComponent(closeEditGroup);
//        formAddGroup.addComponent(buttonPlace);
//        modalEditGroupContent.addComponent(formAddGroup);
//
//        modalEditGroup.center();
        GroupModalWindow groupModalWindow = new GroupModalWindow("Редактирование группы", groupTable, new GroupView());

        return groupModalWindow;
        //return modalEditGroup;
    }
}
