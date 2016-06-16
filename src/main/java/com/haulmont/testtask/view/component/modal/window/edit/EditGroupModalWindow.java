package com.haulmont.testtask.view.component.modal.window.edit;

import com.haulmont.testtask.controller.GroupController;
import com.haulmont.testtask.controller.exception.ControllerCriticalException;
import com.haulmont.testtask.controller.exception.ControllerException;
import com.haulmont.testtask.controller.view.GroupView;
import com.vaadin.data.Validator;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;

/**
 * Created by Leon on 16.06.2016.
 */
public class EditGroupModalWindow extends com.haulmont.testtask.view.component.modal.window.add.AddGroupModalWindow {

//    private Table table;
//
//    private TextField newFaculty;
//    private TextField newNumber;

    public EditGroupModalWindow(String name, Table table) {
        super(name, table);
        okButton.setCaption("Изменить");
        String facultyName = table.getContainerProperty(table.getValue(), "Факультет").getValue().toString();
        newFaculty.setValue(facultyName);
        String groupNumber = table.getContainerProperty(table.getValue(), "Группа").getValue().toString();
        newNumber.setValue(groupNumber);
    }

    @Override
    public void add() {
        if (newNumber.getValue().matches("[-+]?\\d+")) {
            try {
                enableValidation();

                Object group = newNumber.getValue();
                Object faculty = newFaculty.getValue();

                boolean isUpdate = GroupController.update(new GroupView(Long.parseLong(table.getValue().toString()), Integer.parseInt(group.toString()), faculty.toString()));

                table.getContainerProperty(table.getValue(), "Факультет").setValue(faculty.toString());
                table.getContainerProperty(table.getValue(), "Группа").setValue(Integer.parseInt(group.toString()));

                close();
            } catch (Validator.InvalidValueException ex) {
                Notification.show(ex.getMessage());

            } catch (ControllerException e) {
                //e.printStackTrace();

            } catch (ControllerCriticalException e) {
                //e.printStackTrace();
            }
        } else {
            newNumber.setValidationVisible(true);
            Notification.show("Номер группы должен быть числовым");

        }

    }
}
