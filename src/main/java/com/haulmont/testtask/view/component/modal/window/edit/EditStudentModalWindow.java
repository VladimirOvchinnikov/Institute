package com.haulmont.testtask.view.component.modal.window.edit;

import com.haulmont.testtask.controller.StudentController;
import com.haulmont.testtask.controller.exception.ControllerCriticalException;
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
//    private Table table;
//
//    private TextField newFirstName;
//    private TextField newMiddleName;
//    private TextField newLastName;
//    private DateField newBirthDay;
//    private NativeSelect selectGroup;

    public EditStudentModalWindow(String name, Table table) {
        super(name, table);
        okButton.setCaption("Изменить");
        String firstName = table.getContainerProperty(table.getValue(), "Имя").getValue().toString();
        newFirstName.setValue(firstName);

        String middleName = table.getContainerProperty(table.getValue(), "Отчество").getValue().toString();
        newMiddleName.setValue(middleName);

        String lastName = table.getContainerProperty(table.getValue(), "Фамилия").getValue().toString();
        newLastName.setValue(lastName);

        newBirthDay.setValue((Date) table.getContainerProperty(table.getValue(), "Дата рождения").getValue());
    }

    @Override
    public void add() {
        try {
            enableValidation();
            Object reFirstName = newFirstName.getValue();
            Object reMiddleName = newMiddleName.getValue();
            Object reLastName = newLastName.getValue();
            Object birth = newBirthDay.getValue();
            Object group = selectGroup.getItemCaption(selectGroup.getValue());

            StudentView student = new StudentView(Long.parseLong(table.getValue().toString()),
                    reFirstName.toString(),
                    reMiddleName.toString(),
                    reLastName.toString(),
                    newBirthDay.getValue(),
                    Long.parseLong(String.valueOf(selectGroup.getValue().toString())),
                    Integer.parseInt(group.toString()));
            boolean isUpdate = StudentController.update(student);
            Notification.show("Запись изменена");

            table.getContainerProperty(table.getValue(), "Имя").setValue(reFirstName.toString());
            table.getContainerProperty(table.getValue(), "Отчество").setValue(reMiddleName.toString());
            table.getContainerProperty(table.getValue(), "Фамилия").setValue(reLastName.toString());
            table.getContainerProperty(table.getValue(), "Дата рождения").setValue(birth);
            table.getContainerProperty(table.getValue(), "Группа").setValue(Integer.parseInt(group.toString()));

            close();
        } catch (NullPointerException npe) {
            Notification.show("null");
        } catch (Validator.InvalidValueException ex) {
            Notification.show(ex.getMessage());

        } catch (ControllerException e) {
            e.printStackTrace();

        } catch (ControllerCriticalException e) {
            e.printStackTrace();

        }

    }
}
