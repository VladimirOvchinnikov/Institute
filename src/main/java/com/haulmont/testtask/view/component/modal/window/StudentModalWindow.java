package com.haulmont.testtask.view.component.modal.window;

import com.haulmont.testtask.controller.StudentController;
import com.haulmont.testtask.controller.exception.ControllerCriticalException;
import com.haulmont.testtask.controller.exception.ControllerException;
import com.haulmont.testtask.controller.view.StudentView;
import com.haulmont.testtask.controller.view.ViewEntity;
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

    public StudentModalWindow(String name, Table table) {
        super(name);
        this.table = table;

        //Window modalNewStudent = new Window("Добавление студента");
        modalNewStudentContent = new VerticalLayout();
        modalNewStudentContent.setMargin(true);
        setContent(modalNewStudentContent);

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
        //selectGroup = StudentController.fillCombo(selectGroup);//AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaaa
        try {
            Map<Long, Integer> map = StudentController.getNumberGroups();
            for(Map.Entry<Long, Integer> entry: map.entrySet()) {
                selectGroup.addItem(entry.getValue());
                //selectGroup.setValue(entry.getKey());//entry.getKey()
                //selectGroup.setId(entry.getKey().toString());
            }
        } catch (ControllerCriticalException e) {
            e.printStackTrace();
        } catch (ControllerException e) {
            e.printStackTrace();
        }
        selectGroup.setNullSelectionAllowed(false);
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
        modalNewStudentContent.addComponent(formAddStudent);

        center();
    }

    public StudentModalWindow(String name, ViewEntity view, Table table) {
        super(name, view);
        this.table = table;

        modalNewStudentContent = new VerticalLayout();
        modalNewStudentContent.setMargin(true);
        setContent(modalNewStudentContent);
        //Object rowId = table.getValue();

        inputNewStudentFirstName = new TextField();
        inputNewStudentFirstName.setCaption("Имя");
        String firstName = table.getContainerProperty(table.getValue(), "Имя").getValue().toString();
        inputNewStudentFirstName.setValue(firstName);
        inputNewStudentFirstName.addValidator(new StringLengthValidator(
                "Имя должно быть больше 1 символа и меньше 40",
                1, 40, true));

        inputNewStudentFirstName.setImmediate(true);
        inputNewStudentFirstName.setValidationVisible(false);
        inputNewStudentFirstName.setBuffered(true);

        inputNewStudentMiddleName = new TextField();
        inputNewStudentMiddleName.setCaption("Отчество");
        String middleName = table.getContainerProperty(table.getValue(), "Отчество").getValue().toString();
        inputNewStudentMiddleName.setValue(middleName);
        inputNewStudentMiddleName.addValidator(new StringLengthValidator(
                "Фамилия должна быть больше 1 символа и меньше 40",
                1, 40, true));

        inputNewStudentMiddleName.setImmediate(true);
        inputNewStudentMiddleName.setValidationVisible(false);
        inputNewStudentMiddleName.setBuffered(true);

        inputNewStudentLastName = new TextField();
        inputNewStudentLastName.setCaption("Фамилия");
        String lastName = table.getContainerProperty(table.getValue(), "Фамилия").getValue().toString();
        inputNewStudentLastName.setValue(lastName);
        inputNewStudentLastName.addValidator(new StringLengthValidator(
                "Фамилия должна быть больше 1 символа и меньше 40",
                1, 40, true));

        inputNewStudentLastName.setImmediate(true);
        inputNewStudentLastName.setValidationVisible(false);
        inputNewStudentLastName.setBuffered(true);

        inputNewBirth = new DateField();
        inputNewBirth.setCaption("Дата рождения");
        inputNewBirth.setValue(new Date());
        inputNewBirth.addValidator(new DateRangeValidator("Ввдедите дату рождения",
                new Date(0), new Date(), Resolution.DAY));

        inputNewBirth.setImmediate(true);
        inputNewBirth.setValidationVisible(false);
        inputNewBirth.setBuffered(true);

         selectGroup = new NativeSelect();
        //selectGroup = StudentController.fillCombo(selectGroup);
        try {
            Map<Long, Integer> map = StudentController.getNumberGroups();
            for(Map.Entry<Long, Integer> entry: map.entrySet()) {
                //selectGroup.addItem(map);
                //selectGroup.setValue(map);
                //selectGroup.setValue(entry.getKey());//entry.getKey()
                //selectGroup.setId(entry.getKey().toString());
            }
        } catch (ControllerCriticalException e) {
            e.printStackTrace();
        } catch (ControllerException e) {
            e.printStackTrace();
        }
        selectGroup.setNullSelectionAllowed(false);
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
        modalNewStudentContent.addComponent(formAddStudent);

        center();
    }

    @Override
    public void add() {
        try {
            inputNewStudentFirstName.setValidationVisible(true);
            inputNewStudentFirstName.validate();
            inputNewStudentFirstName.commit();

            inputNewStudentMiddleName.setValidationVisible(true);
            inputNewStudentMiddleName.validate();
            inputNewStudentMiddleName.commit();

            inputNewStudentLastName.setValidationVisible(true);
            inputNewStudentLastName.validate();
            inputNewStudentLastName.commit();

//            Long res = addStudent(
//                    inputNewStudentFirstName.getValue(),
//                    inputNewStudentMiddleName.getValue(),
//                    inputNewStudentLastName.getValue(),
//                    inputNewBirth.getValue(),
//                    -1L,
//                    Integer.parseInt(finalSelectGroup.getValue().toString())
//            );
            StudentView student = new StudentView(inputNewStudentFirstName.getValue(), inputNewStudentMiddleName.getValue(),
                    inputNewStudentLastName.getValue(), inputNewBirth.getValue(),
                    Long.parseLong(String.valueOf(selectGroup.getId())),
                    Integer.parseInt(selectGroup.getValue().toString()));
            Long id = StudentController.insert(student);
            Notification.show("Запись добавлена");
            if (id != -1) {
                table.addItem(new Object[]{
                                inputNewStudentFirstName.getValue(),
                                inputNewStudentMiddleName.getValue(),
                                inputNewStudentLastName.getValue(),
                                inputNewBirth.getValue(),
                                Integer.parseInt(selectGroup.getValue().toString())},
                        id);

            }
            inputNewStudentFirstName.setValidationVisible(false);
            inputNewStudentMiddleName.setValidationVisible(false);
            inputNewStudentLastName.setValidationVisible(false);
            close();
        } catch (Validator.InvalidValueException ex) {
            Notification.show(ex.getMessage());
        } catch (ControllerException e) {
            e.printStackTrace();
        } catch (ControllerCriticalException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean update(ViewEntity view) {
        try {
            inputNewStudentFirstName.setValidationVisible(true);
            inputNewStudentFirstName.validate();
            inputNewStudentFirstName.commit();

            inputNewStudentMiddleName.setValidationVisible(true);
            inputNewStudentMiddleName.validate();
            inputNewStudentMiddleName.commit();

            inputNewStudentLastName.setValidationVisible(true);
            inputNewStudentLastName.validate();
            inputNewStudentLastName.commit();

            Object reFirstName = inputNewStudentFirstName.getValue();
            Object reMiddleName = inputNewStudentMiddleName.getValue();
            Object reLastName = inputNewStudentLastName.getValue();
            Object birth = inputNewBirth.getValue();
            Object group = selectGroup.getValue();

//            boolean res = editStudent(
//                    reFirstName.toString(),
//                    reMiddleName.toString(),
//                    reLastName.toString(),
//                    inputNewBirth.getValue(),
//                    Integer.parseInt(finalSelectGroup.getValue().toString()),
//                    -1L,
//                    Long.parseLong(rowId.toString())
//            );
            StudentView student = new StudentView(reFirstName.toString(), reMiddleName.toString(), reLastName.toString(),
                    inputNewBirth.getValue(),
                    Long.parseLong(String.valueOf(selectGroup.getId())),
                    Integer.parseInt(group.toString()));
            boolean isUpdate = StudentController.update(student);
            Notification.show("Запись изменена");



            table.getContainerProperty(table.getValue(), "Имя").setValue(reFirstName.toString());
            table.getContainerProperty(table.getValue(), "Отчество").setValue(reMiddleName.toString());
            table.getContainerProperty(table.getValue(), "Фамилия").setValue(reLastName.toString());
            table.getContainerProperty(table.getValue(), "Дата рождения").setValue(birth);
            table.getContainerProperty(table.getValue(), "Группа").setValue(Integer.parseInt(group.toString()));

            inputNewStudentFirstName.setValidationVisible(false);
            inputNewStudentMiddleName.setValidationVisible(false);
            inputNewStudentLastName.setValidationVisible(false);
            close();
            return isUpdate;
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
