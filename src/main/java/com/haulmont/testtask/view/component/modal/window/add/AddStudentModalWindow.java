package com.haulmont.testtask.view.component.modal.window.add;

import com.haulmont.testtask.controller.StudentController;
import com.haulmont.testtask.controller.exception.ControllerException;
import com.haulmont.testtask.controller.view.StudentView;
import com.haulmont.testtask.view.component.modal.window.BasicModalWindow;
import com.haulmont.testtask.view.component.modal.window.Validation;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;

import java.util.Date;
import java.util.Map;

/**
 * Created by Leon on 16.06.2016.
 */
public class AddStudentModalWindow extends BasicModalWindow implements Validation {
    protected Table table;

    protected TextField firstNameTextField;
    protected TextField middleNameTextField;
    protected TextField lastNameTextField;
    protected DateField birthDayTextField;
    protected NativeSelect selectGroupNativeSelect;

    public AddStudentModalWindow(String name, Table table) {
        super(name);
        this.table = table;

        firstNameTextField = new TextField();
        firstNameTextField.setCaption("Имя");
        firstNameTextField.addValidator(new StringLengthValidator(
                "Имя должно быть больше 1 символа и меньше 40",
                1, 40, true));

        firstNameTextField.setImmediate(true);
        firstNameTextField.setValidationVisible(false);
        firstNameTextField.setBuffered(true);

        middleNameTextField = new TextField();
        middleNameTextField.setCaption("Фамилия");
        middleNameTextField.addValidator(new StringLengthValidator(
                "Фамилия должна быть больше 1 символа и меньше 40",
                1, 40, true));

        middleNameTextField.setImmediate(true);
        middleNameTextField.setValidationVisible(false);
        middleNameTextField.setBuffered(true);

        lastNameTextField = new TextField();
        lastNameTextField.setCaption("Отчество");
        lastNameTextField.addValidator(new StringLengthValidator(
                "Фамилия должна быть больше 1 символа и меньше 40",
                1, 40, true));

        lastNameTextField.setImmediate(true);
        lastNameTextField.setValidationVisible(false);
        lastNameTextField.setBuffered(true);

        birthDayTextField = new DateField();
        birthDayTextField.setValue(new Date());
        birthDayTextField.setCaption("Дата рождения");
        birthDayTextField.addValidator(new DateRangeValidator("Ввдедите дату рождения", new Date(0), new Date(), Resolution.DAY));

        birthDayTextField.setImmediate(true);
        birthDayTextField.setValidationVisible(false);
        birthDayTextField.setBuffered(true);

        selectGroupNativeSelect = new NativeSelect();
        try {
            Map<Long, Integer> map = StudentController.getNumberGroups();
            for (Map.Entry<Long, Integer> entry : map.entrySet()) {
                selectGroupNativeSelect.addItem(entry.getKey());
                selectGroupNativeSelect.setItemCaption(entry.getKey(), String.valueOf(entry.getValue()));
            }
        } catch (ControllerException e) {
            Notification.show(e.getMessage());
        }

        selectGroupNativeSelect.setNullSelectionItemId(-1L);
        selectGroupNativeSelect.setItemCaption(-1L, "--Select");

        selectGroupNativeSelect.setCaption("Группа");
        selectGroupNativeSelect.addValidator(new IntegerRangeValidator("Введите группу", 1, 99999));
        selectGroupNativeSelect.validate();
        selectGroupNativeSelect.commit();

        selectGroupNativeSelect.setImmediate(true);
        selectGroupNativeSelect.setValidationVisible(false);
        selectGroupNativeSelect.setBuffered(true);

        FormLayout formAddStudent = new FormLayout();
        formAddStudent.setWidth("315px");

        formAddStudent.addComponent(firstNameTextField);
        formAddStudent.addComponent(middleNameTextField);
        formAddStudent.addComponent(lastNameTextField);
        formAddStudent.addComponent(birthDayTextField);
        formAddStudent.addComponent(selectGroupNativeSelect);

        HorizontalLayout buttonPlace = new HorizontalLayout();
        buttonPlace.addComponent(okButton);
        buttonPlace.addComponent(cancelButton);
        formAddStudent.addComponent(buttonPlace);
        modalContent.addComponent(formAddStudent);
    }

    @Override
    public void action() {
        try {
            enableValidation();
            StudentView student = new StudentView(firstNameTextField.getValue(), middleNameTextField.getValue(),
                    lastNameTextField.getValue(), birthDayTextField.getValue(),
                    Long.parseLong(String.valueOf(selectGroupNativeSelect.getValue().toString())),
                    Integer.parseInt(selectGroupNativeSelect.getItemCaption(selectGroupNativeSelect.getValue())));

            Long id = StudentController.insert(student);
            if (id != -1) {
                table.addItem(new Object[]{
                                firstNameTextField.getValue(),
                                middleNameTextField.getValue(),
                                lastNameTextField.getValue(),
                                birthDayTextField.getValue(),
                                Integer.parseInt(selectGroupNativeSelect.getItemCaption(selectGroupNativeSelect.getValue()))},
                        id);
                Notification.show("Запись добавлена");
            } else {
                Notification.show("Запись не добавлена");
            }
            close();
        } catch (NullPointerException npe) {
            Notification.show("Запись не добавлена");
        } catch (Validator.InvalidValueException | ControllerException ex) {
            Notification.show(ex.getMessage());
        }

    }

    @Override
    public void enableValidation() {
        firstNameTextField.setValidationVisible(true);
        firstNameTextField.validate();
        firstNameTextField.commit();

        middleNameTextField.setValidationVisible(true);
        middleNameTextField.validate();
        middleNameTextField.commit();

        lastNameTextField.setValidationVisible(true);
        lastNameTextField.validate();
        lastNameTextField.commit();
    }
}
