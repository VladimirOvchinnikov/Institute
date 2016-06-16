package com.haulmont.testtask.view.component.modal.window.edit;

import com.haulmont.testtask.controller.StudentController;
import com.haulmont.testtask.controller.exception.ControllerException;
import com.haulmont.testtask.controller.view.StudentView;
import com.haulmont.testtask.view.component.modal.window.add.AddStudentModalWindow;
import com.vaadin.data.Validator;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;

import java.util.Date;

/**
 * Created by Leon on 16.06.2016.
 */
public class EditStudentModalWindow extends AddStudentModalWindow {

    public EditStudentModalWindow(String name, Table table) {
        super(name, table);
        okButton.setCaption("Изменить");
        String firstName = table.getContainerProperty(table.getValue(), "Имя").getValue().toString();
        firstNameTextField.setValue(firstName);

        String middleName = table.getContainerProperty(table.getValue(), "Отчество").getValue().toString();
        middleNameTextField.setValue(middleName);

        String lastName = table.getContainerProperty(table.getValue(), "Фамилия").getValue().toString();
        lastNameTextField.setValue(lastName);

        birthDayTextField.setValue((Date) table.getContainerProperty(table.getValue(), "Дата рождения").getValue());
    }

    @Override
    public void action() {
        try {
            enableValidation();
            Object reFirstName = firstNameTextField.getValue();
            Object reMiddleName = middleNameTextField.getValue();
            Object reLastName = lastNameTextField.getValue();
            Object birth = birthDayTextField.getValue();
            Object group = selectGroupNativeSelect.getItemCaption(selectGroupNativeSelect.getValue());

            StudentView student = new StudentView(Long.parseLong(table.getValue().toString()),
                    reFirstName.toString(),
                    reMiddleName.toString(),
                    reLastName.toString(),
                    birthDayTextField.getValue(),
                    Long.parseLong(String.valueOf(selectGroupNativeSelect.getValue().toString())),
                    Integer.parseInt(group.toString()));
            if (StudentController.update(student)) {

                table.getContainerProperty(table.getValue(), "Имя").setValue(reFirstName.toString());
                table.getContainerProperty(table.getValue(), "Отчество").setValue(reMiddleName.toString());
                table.getContainerProperty(table.getValue(), "Фамилия").setValue(reLastName.toString());
                table.getContainerProperty(table.getValue(), "Дата рождения").setValue(birth);
                table.getContainerProperty(table.getValue(), "Группа").setValue(Integer.parseInt(group.toString()));
                Notification.show("Запись изменена");
            }else {
                Notification.show("Запись не изменена");
            }
            close();

        } catch (NullPointerException npe) {
            Notification.show("Не удалось изменить запись");
        } catch (Validator.InvalidValueException ex) {
            Notification.show(ex.getMessage());
        } catch (ControllerException e) {
            Notification.show(e.getMessage());
        }

    }
}
