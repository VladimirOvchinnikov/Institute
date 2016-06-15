package com.haulmont.testtask.view.component.modal.window;

import com.haulmont.testtask.controller.StudentController;
import com.haulmont.testtask.controller.exception.ControllerCriticalException;
import com.haulmont.testtask.controller.exception.ControllerException;
import com.haulmont.testtask.controller.view.StudentView;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;

import java.util.Date;
import java.util.Map;

/**
 * Created by ovchinnikov on 15.06.2016.
 */
public class StudentModalWindow extends BasicModalWindow {
    private Table table;
    private VerticalLayout modalNewStudentContent;
    private TextField inputNewStudentFirstName;
    private TextField inputNewStudentMiddleName;
    private TextField inputNewStudentLastName;
    private DateField inputNewBirth;
    private NativeSelect selectGroup;

    private void init(){

        inputNewStudentFirstName = new TextField();
        inputNewStudentFirstName.setCaption("Имя");
        inputNewStudentFirstName.addValidator(new StringLengthValidator(
                "Имя должно быть больше 1 символа и меньше 40",
                1, 40, true));

        inputNewStudentFirstName.setImmediate(true);
        inputNewStudentFirstName.setValidationVisible(false);
        inputNewStudentFirstName.setBuffered(true);

        inputNewStudentMiddleName = new TextField();
        inputNewStudentMiddleName.setCaption("Фамилия");
        inputNewStudentMiddleName.addValidator(new StringLengthValidator(
                "Фамилия должна быть больше 1 символа и меньше 40",
                1, 40, true));

        inputNewStudentMiddleName.setImmediate(true);
        inputNewStudentMiddleName.setValidationVisible(false);
        inputNewStudentMiddleName.setBuffered(true);

        inputNewStudentLastName = new TextField();
        inputNewStudentLastName.setCaption("Отчество");
        inputNewStudentLastName.addValidator(new StringLengthValidator(
                "Фамилия должна быть больше 1 символа и меньше 40",
                1, 40, true));

        inputNewStudentLastName.setImmediate(true);
        inputNewStudentLastName.setValidationVisible(false);
        inputNewStudentLastName.setBuffered(true);

        inputNewBirth = new DateField();
        inputNewBirth.setValue(new Date());
        inputNewBirth.setCaption("Дата рождения");
        inputNewBirth.addValidator(new DateRangeValidator("Ввдедите дату рождения", new Date(0), new Date(), Resolution.DAY));

        inputNewBirth.setImmediate(true);
        inputNewBirth.setValidationVisible(false);
        inputNewBirth.setBuffered(true);

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

        selectGroup.setImmediate(true);
        selectGroup.setValidationVisible(false);
        selectGroup.setBuffered(true);

        FormLayout formAddStudent = new FormLayout();
        formAddStudent.setWidth("315px");

        formAddStudent.addComponent(inputNewStudentFirstName);
        formAddStudent.addComponent(inputNewStudentMiddleName);
        formAddStudent.addComponent(inputNewStudentLastName);
        formAddStudent.addComponent(inputNewBirth);
        formAddStudent.addComponent(selectGroup);

        HorizontalLayout buttonPlace = new HorizontalLayout();
        buttonPlace.addComponent(getOkButton());
        buttonPlace.addComponent(getCancelButton());
        formAddStudent.addComponent(buttonPlace);
        getModalContent().addComponent(formAddStudent);
    }

    public StudentModalWindow(String name, boolean isUpdate, Table table) {
        super(name,isUpdate);
        this.table = table;

        init();
        if (isUpdate) {
            String firstName = table.getContainerProperty(table.getValue(), "Имя").getValue().toString();
            inputNewStudentFirstName.setValue(firstName);

            String middleName = table.getContainerProperty(table.getValue(), "Отчество").getValue().toString();
            inputNewStudentMiddleName.setValue(middleName);

            String lastName = table.getContainerProperty(table.getValue(), "Фамилия").getValue().toString();
            inputNewStudentLastName.setValue(lastName);

            inputNewBirth.setValue((Date) table.getContainerProperty(table.getValue(), "Дата рождения").getValue());
        }
    }

    @Override
    protected void enableValidation(){
        inputNewStudentFirstName.setValidationVisible(true);
        inputNewStudentFirstName.validate();
        inputNewStudentFirstName.commit();

        inputNewStudentMiddleName.setValidationVisible(true);
        inputNewStudentMiddleName.validate();
        inputNewStudentMiddleName.commit();

        inputNewStudentLastName.setValidationVisible(true);
        inputNewStudentLastName.validate();
        inputNewStudentLastName.commit();
    }

    @Override
    protected void disableValidation(){
        inputNewStudentFirstName.setValidationVisible(false);
        inputNewStudentMiddleName.setValidationVisible(false);
        inputNewStudentLastName.setValidationVisible(false);
    }

    @Override
    public void add() {
        try {
            enableValidation();
            StudentView student = new StudentView(inputNewStudentFirstName.getValue(), inputNewStudentMiddleName.getValue(),
                    inputNewStudentLastName.getValue(), inputNewBirth.getValue(),
                    Long.parseLong(String.valueOf(selectGroup.getValue().toString())),
                    Integer.parseInt(selectGroup.getItemCaption(selectGroup.getValue())));

            Long id = StudentController.insert(student);
            Notification.show("Запись добавлена");
            if (id != -1) {
                table.addItem(new Object[]{
                                inputNewStudentFirstName.getValue(),
                                inputNewStudentMiddleName.getValue(),
                                inputNewStudentLastName.getValue(),
                                inputNewBirth.getValue(),
                                Integer.parseInt(selectGroup.getItemCaption(selectGroup.getValue()))},
                        id);
            }
            disableValidation();
            close();
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
    public boolean update() {
        try {
            enableValidation();
            Object reFirstName = inputNewStudentFirstName.getValue();
            Object reMiddleName = inputNewStudentMiddleName.getValue();
            Object reLastName = inputNewStudentLastName.getValue();
            Object birth = inputNewBirth.getValue();
            Object group = selectGroup.getItemCaption(selectGroup.getValue());

            StudentView student = new StudentView(Long.parseLong(table.getValue().toString()),
                    reFirstName.toString(),
                    reMiddleName.toString(),
                    reLastName.toString(),
                    inputNewBirth.getValue(),
                    Long.parseLong(String.valueOf(selectGroup.getValue().toString())),
                    Integer.parseInt(group.toString()));
            boolean isUpdate = StudentController.update(student);
            Notification.show("Запись изменена");

            table.getContainerProperty(table.getValue(), "Имя").setValue(reFirstName.toString());
            table.getContainerProperty(table.getValue(), "Отчество").setValue(reMiddleName.toString());
            table.getContainerProperty(table.getValue(), "Фамилия").setValue(reLastName.toString());
            table.getContainerProperty(table.getValue(), "Дата рождения").setValue(birth);
            table.getContainerProperty(table.getValue(), "Группа").setValue(Integer.parseInt(group.toString()));

            disableValidation();
            close();
            return isUpdate;
        } catch (NullPointerException npe) {
            return false;
        } catch (Validator.InvalidValueException ex) {
            Notification.show(ex.getMessage());
            return false;
        } catch (ControllerException e) {
            e.printStackTrace();
            return false;
        } catch (ControllerCriticalException e) {
            e.printStackTrace();
            return false;
        }
    }
}
