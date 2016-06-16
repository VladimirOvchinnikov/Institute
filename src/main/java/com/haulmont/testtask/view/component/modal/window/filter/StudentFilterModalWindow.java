package com.haulmont.testtask.view.component.modal.window.filter;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.haulmont.testtask.controller.StudentController;
import com.haulmont.testtask.controller.exception.ControllerCriticalException;
import com.haulmont.testtask.controller.exception.ControllerException;
import com.haulmont.testtask.controller.view.StudentView;
import com.haulmont.testtask.view.component.modal.window.BasicModalWindow;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.ui.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Leon on 16.06.2016.
 */
public class StudentFilterModalWindow extends BasicModalWindow {

    protected Table table;

    protected TextField lastName;
    protected NativeSelect selectGroup;

    public StudentFilterModalWindow(String name, Table table) {
        super(name);
        this.table = table;

        lastName = new TextField();
        lastName.setCaption("Фамилия");

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

        selectGroup.setCaption("Группа");
        selectGroup.addValidator(new IntegerRangeValidator("Введите группу", 1, 99999));

        selectGroup.setImmediate(true);
        selectGroup.setValidationVisible(false);
        selectGroup.setBuffered(true);

        okButton.setCaption("Ok");

        cancelButton.addClickListener(e -> {
            table.removeAllItems();

            List<StudentView> students = Lists.newArrayList();
            try {
                students = StudentController.select(students);
            } catch (ControllerCriticalException e1) {
                e1.printStackTrace();
            } catch (ControllerException e1) {
                e1.printStackTrace();
            }
            for (StudentView student : students) {
                table.addItem(new Object[]{student.getFirstName(), student.getLastName(), student.getMiddleName(),
                        student.getBirthDay(), student.getNumberGroup()}, student.getId());
            }
            close();
        });

        FormLayout formAddStudent = new FormLayout();
        formAddStudent.setWidth("315px");


        formAddStudent.addComponent(lastName);
        formAddStudent.addComponent(selectGroup);

        HorizontalLayout buttonPlace = new HorizontalLayout();
        buttonPlace.addComponent(okButton);
        buttonPlace.addComponent(cancelButton);
        formAddStudent.addComponent(buttonPlace);
        modalContent.addComponent(formAddStudent);
    }

    @Override
    public void add() {
        StudentView filter;
        if (selectGroup.getValue() == null) {
            filter = new StudentView(null, null, null, lastName.getValue(), null, null, null);
        } else {
            filter = new StudentView(null, null, null, lastName.getValue(), null, null, Integer.parseInt(selectGroup.getItemCaption(selectGroup.getValue())));
        }
        List<StudentView> students = Lists.newArrayList();
        try {
            students = StudentController.filter(filter);
        } catch (ControllerCriticalException e) {
            e.printStackTrace();
        } catch (ControllerException e) {
            e.printStackTrace();
        }
        if (students.size() > 0) {
            table.removeAllItems();
            for (StudentView student : students) {
                table.addItem(new Object[]{student.getFirstName(), student.getLastName(), student.getMiddleName(),
                        student.getBirthDay(), student.getNumberGroup()}, student.getId());
            }
        } else {
            Notification.show("Результат фильтра ничего не вернул!");
        }

    }
}
