package com.haulmont.testtask.view.component.modal.window.add;

import com.haulmont.testtask.controller.StudentController;
import com.haulmont.testtask.controller.exception.ControllerCriticalException;
import com.haulmont.testtask.controller.exception.ControllerException;
import com.haulmont.testtask.controller.view.StudentView;
import com.haulmont.testtask.view.component.modal.window.BasicModalWindow;
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
public class AddStudentModalWindow  extends BasicModalWindow implements Validation {
    protected Table table;

    protected TextField newFirstName;
    protected TextField newMiddleName;
    protected TextField newLastName;
    protected DateField newBirthDay;
    protected NativeSelect selectGroup;

    public AddStudentModalWindow(String name, Table table) {
        super(name);
        this.table = table;

        newFirstName = new TextField();
        newFirstName.setCaption("Имя");
        newFirstName.addValidator(new StringLengthValidator(
                "Имя должно быть больше 1 символа и меньше 40",
                1, 40, true));

        newFirstName.setImmediate(true);
        newFirstName.setValidationVisible(false);
        newFirstName.setBuffered(true);

        newMiddleName = new TextField();
        newMiddleName.setCaption("Фамилия");
        newMiddleName.addValidator(new StringLengthValidator(
                "Фамилия должна быть больше 1 символа и меньше 40",
                1, 40, true));

        newMiddleName.setImmediate(true);
        newMiddleName.setValidationVisible(false);
        newMiddleName.setBuffered(true);

        newLastName = new TextField();
        newLastName.setCaption("Отчество");
        newLastName.addValidator(new StringLengthValidator(
                "Фамилия должна быть больше 1 символа и меньше 40",
                1, 40, true));

        newLastName.setImmediate(true);
        newLastName.setValidationVisible(false);
        newLastName.setBuffered(true);

        newBirthDay = new DateField();
        newBirthDay.setValue(new Date());
        newBirthDay.setCaption("Дата рождения");
        newBirthDay.addValidator(new DateRangeValidator("Ввдедите дату рождения", new Date(0), new Date(), Resolution.DAY));

        newBirthDay.setImmediate(true);
        newBirthDay.setValidationVisible(false);
        newBirthDay.setBuffered(true);

        selectGroup = new NativeSelect();
        try {
            Map<Long, Integer> map = StudentController.getNumberGroups();
            for (Map.Entry<Long, Integer> entry : map.entrySet()) {
                selectGroup.addItem(entry.getKey());
                selectGroup.setItemCaption(entry.getKey(), String.valueOf(entry.getValue()));
            }
        } catch (ControllerCriticalException e) {
            e.printStackTrace();
        } catch (ControllerException e) {
            e.printStackTrace();
        }
        //selectGroup.setNullSelectionAllowed(false);

        selectGroup.setNullSelectionItemId(0L);
        selectGroup.setItemCaption(0L, "--Select");

        selectGroup.setCaption("Группа");
        selectGroup.addValidator(new IntegerRangeValidator("Введите группу", 1, 99999));
        selectGroup.validate();
        selectGroup.commit();

        selectGroup.setImmediate(true);
        selectGroup.setValidationVisible(false);
        selectGroup.setBuffered(true);

        FormLayout formAddStudent = new FormLayout();
        formAddStudent.setWidth("315px");

        formAddStudent.addComponent(newFirstName);
        formAddStudent.addComponent(newMiddleName);
        formAddStudent.addComponent(newLastName);
        formAddStudent.addComponent(newBirthDay);
        formAddStudent.addComponent(selectGroup);

        HorizontalLayout buttonPlace = new HorizontalLayout();
        buttonPlace.addComponent(okButton);
        buttonPlace.addComponent(cancelButton);
        formAddStudent.addComponent(buttonPlace);
        modalContent.addComponent(formAddStudent);
    }

    @Override
    public void add() {
        try {
            enableValidation();
            StudentView student = new StudentView(newFirstName.getValue(), newMiddleName.getValue(),
                    newLastName.getValue(), newBirthDay.getValue(),
                    Long.parseLong(String.valueOf(selectGroup.getValue().toString())),
                    Integer.parseInt(selectGroup.getItemCaption(selectGroup.getValue())));

            Long id = StudentController.insert(student);
            if (id != -1) {
                table.addItem(new Object[]{
                                newFirstName.getValue(),
                                newMiddleName.getValue(),
                                newLastName.getValue(),
                                newBirthDay.getValue(),
                                Integer.parseInt(selectGroup.getItemCaption(selectGroup.getValue()))},
                        id);
            }
            close();
            Notification.show("Запись добавлена");
        }catch (NullPointerException npe){
            Notification.show("Запись не добавлена");
        }
        catch (Validator.InvalidValueException ex) {
            Notification.show(ex.getMessage());
        } catch (ControllerException e) {
            e.printStackTrace();
        } catch (ControllerCriticalException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void enableValidation() {
        newFirstName.setValidationVisible(true);
        newFirstName.validate();
        newFirstName.commit();

        newMiddleName.setValidationVisible(true);
        newMiddleName.validate();
        newMiddleName.commit();

        newLastName.setValidationVisible(true);
        newLastName.validate();
        newLastName.commit();
    }
}
