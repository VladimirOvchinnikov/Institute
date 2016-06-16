package com.haulmont.testtask.view.component.modal.window.edit;

import com.haulmont.testtask.controller.GroupController;
import com.haulmont.testtask.controller.exception.ControllerException;
import com.haulmont.testtask.controller.view.GroupView;
import com.vaadin.data.Validator;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;

/**
 * Created by Leon on 16.06.2016.
 */
public class EditGroupModalWindow extends com.haulmont.testtask.view.component.modal.window.add.AddGroupModalWindow {

    public EditGroupModalWindow(String name, Table table) {
        super(name, table);
        okButton.setCaption("Изменить");
        String facultyName = table.getContainerProperty(table.getValue(), "Факультет").getValue().toString();
        facultyTextField.setValue(facultyName);
        String groupNumber = table.getContainerProperty(table.getValue(), "Группа").getValue().toString();
        numberTextField.setValue(groupNumber);
    }

    @Override
    public void action() {
        if (numberTextField.getValue().matches("[-+]?\\d+")) {
            try {
                enableValidation();

                Object group = numberTextField.getValue();
                Object faculty = this.facultyTextField.getValue();

                if (GroupController.update(new GroupView(Long.parseLong(table.getValue().toString()), Integer.parseInt(group.toString()), faculty.toString()))) {
                    table.getContainerProperty(table.getValue(), "Факультет").setValue(faculty.toString());
                    table.getContainerProperty(table.getValue(), "Группа").setValue(Integer.parseInt(group.toString()));
                    Notification.show("Запись изменена");
                }else {
                    Notification.show("Запись не изменилась");
                }
                close();

            } catch (Validator.InvalidValueException ex) {
                Notification.show(ex.getMessage());

            } catch (ControllerException e) {
                Notification.show(e.getMessage());
            }
        } else {
            numberTextField.setValidationVisible(true);
            Notification.show("Номер группы должен быть числовым");

        }

    }
}
