package com.haulmont.testtask.view.component.modal.window;

import com.haulmont.testtask.controller.GroupController;
import com.haulmont.testtask.controller.exception.ControllerCriticalException;
import com.haulmont.testtask.controller.exception.ControllerException;
import com.haulmont.testtask.controller.view.GroupView;
import com.haulmont.testtask.controller.view.ViewEntity;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

/**
 * Created by Leon on 15.06.2016.
 */
public class GroupModalWindow extends BasicModalWindow {
    private TextField inputNewGroupFaculty;
    private TextField inputNewGroupNumber;
    private Table table;

    public GroupModalWindow(String name, Table table) {
        super(name);
        this.table = table;
        inputNewGroupFaculty = new TextField();
        inputNewGroupFaculty.setCaption("Факультет");
        inputNewGroupFaculty.addValidator(new StringLengthValidator(
                "Название факультета должно быть больше 1 символа и меньше 40",
                1, 40, true));

        inputNewGroupFaculty.setImmediate(true);
        inputNewGroupFaculty.setValidationVisible(false);
        inputNewGroupFaculty.setBuffered(true);

        inputNewGroupNumber = new TextField();
        inputNewGroupNumber.setCaption("Номер группы");
        inputNewGroupNumber.addValidator(new StringLengthValidator(
                "Номер группы должен быть больше 1 символа и меньше 10",
                1, 10, true));

        inputNewGroupNumber.setImmediate(true);
        inputNewGroupNumber.setValidationVisible(false);
        inputNewGroupNumber.setBuffered(true);

        FormLayout formAddGroup = new FormLayout();
        formAddGroup.setWidth("335px");
        formAddGroup.addComponent(inputNewGroupFaculty);
        formAddGroup.addComponent(inputNewGroupNumber);
        HorizontalLayout buttonPlace = new HorizontalLayout();
        buttonPlace.addComponent(getOkButton());
        buttonPlace.addComponent(getCancelButton());
        formAddGroup.addComponent(buttonPlace);
        getModalContent().addComponent(formAddGroup);

        center();
    }

    public GroupModalWindow(String name, Table table, GroupView view) {
        super(name, view);
        this.table = table;

//        Object rowId = table.getValue();

        inputNewGroupFaculty = new TextField();
        inputNewGroupFaculty.setCaption("Факультет");
        inputNewGroupFaculty.addValidator(new StringLengthValidator(
                "Название факультета должно быть больше 1 символа и меньше 40",
                1, 40, true));
        inputNewGroupFaculty.setImmediate(true);
        inputNewGroupFaculty.setValidationVisible(false);
        inputNewGroupFaculty.setBuffered(true);
        String facultyName = table.getContainerProperty(table.getValue(), "Факультет").getValue().toString();
        inputNewGroupFaculty.setValue(facultyName);

        inputNewGroupNumber = new TextField();
        inputNewGroupNumber.setCaption("Номер группы");
        inputNewGroupNumber.addValidator(new StringLengthValidator(
                "Номер группы должен быть больше 1 символа и меньше 10",
                1, 10, true));
        inputNewGroupNumber.setImmediate(true);
        inputNewGroupNumber.setValidationVisible(false);
        inputNewGroupNumber.setBuffered(true);
        String groupNumber = table.getContainerProperty(table.getValue(), "Группа").getValue().toString();
        inputNewGroupNumber.setValue(groupNumber);

        FormLayout formAddGroup = new FormLayout();
        formAddGroup.setWidth("335px");
        formAddGroup.addComponent(inputNewGroupFaculty);
        formAddGroup.addComponent(inputNewGroupNumber);
        HorizontalLayout buttonPlace = new HorizontalLayout();
        buttonPlace.addComponent(getOkButton());
        buttonPlace.addComponent(getCancelButton());
        formAddGroup.addComponent(buttonPlace);
        getModalContent().addComponent(formAddGroup);

        center();
    }

    @Override
    public void add() {
        if (inputNewGroupNumber.getValue().matches("[-+]?\\d+")) {
            try {
                inputNewGroupFaculty.setValidationVisible(true);
                inputNewGroupFaculty.validate();
                inputNewGroupFaculty.commit();

                inputNewGroupNumber.setValidationVisible(true);
                inputNewGroupNumber.validate();
                inputNewGroupNumber.commit();

                GroupView view = new GroupView(Integer.parseInt(inputNewGroupNumber.getValue()), inputNewGroupFaculty.getValue());
                Long id = GroupController.insert(view); //addGroup(inputNewGroupFaculty.getValue(), Integer.parseInt(inputNewGroupNumber.getValue()));
                //Добавляем в таблицу то, что отдала база данных, а не то, что ввел пользователь на случай если
                //запись в БД будет некорректной (например, неверная кодировка)
                //if (res.size() > 0) {
                //for (UniverGroup result : res) {
                table.addItem(new Object[]{inputNewGroupFaculty.getValue(), Integer.parseInt(inputNewGroupNumber.getValue())}, id);
                //}
                //}
                inputNewGroupNumber.setValidationVisible(false);
                inputNewGroupFaculty.setValidationVisible(false);
                close();
            } catch (Validator.InvalidValueException ex) {
                Notification.show(ex.getMessage());
            } catch (ControllerException e) {
                //e.printStackTrace();
                Notification.show("Добавить запись не удалось");
                //return -1L;
            } catch (ControllerCriticalException e) {
                Notification.show("Ошибка подключения бд");
            }
        } else {
            inputNewGroupNumber.setValidationVisible(true);
            Notification.show("Номер группы должен быть числовым");
        }
    }

    @Override
    public boolean update(ViewEntity view) {
        if (inputNewGroupNumber.getValue().matches("[-+]?\\d+")) {
            try {
                inputNewGroupFaculty.setValidationVisible(true);
                inputNewGroupFaculty.validate();
                inputNewGroupFaculty.commit();

                inputNewGroupNumber.setValidationVisible(true);
                inputNewGroupNumber.validate();
                inputNewGroupNumber.commit();

                Object group = inputNewGroupNumber.getValue();
                Object faculty = inputNewGroupFaculty.getValue();

                boolean isUpdate = GroupController.update(new GroupView(Long.parseLong(table.getValue().toString()), Integer.parseInt(group.toString()), faculty.toString()));
                //editGroup(faculty.toString(), Integer.parseInt(group.toString()), Long.parseLong(table.getValue().toString()));

                table.getContainerProperty(table.getValue(), "Факультет").setValue(faculty.toString());
                table.getContainerProperty(table.getValue(), "Группа").setValue(Integer.parseInt(group.toString()));

                inputNewGroupNumber.setValidationVisible(false);
                inputNewGroupFaculty.setValidationVisible(false);
                close();
                return isUpdate;
            } catch (Validator.InvalidValueException ex) {
                Notification.show(ex.getMessage());
                return false;
            } catch (ControllerException e) {
                //e.printStackTrace();
                return false;
            } catch (ControllerCriticalException e) {
                //e.printStackTrace();
                return false;
            }
        } else {
            inputNewGroupNumber.setValidationVisible(true);
            Notification.show("Номер группы должен быть числовым");
            return false;
        }
//        return false;
    }
}
