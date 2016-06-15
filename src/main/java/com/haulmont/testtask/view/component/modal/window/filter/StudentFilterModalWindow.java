package com.haulmont.testtask.view.component.modal.window.filter;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.haulmont.testtask.controller.StudentController;
import com.haulmont.testtask.controller.exception.ControllerCriticalException;
import com.haulmont.testtask.controller.exception.ControllerException;
import com.haulmont.testtask.controller.view.StudentView;
import com.haulmont.testtask.view.component.modal.window.BasicModalWindow;
import com.vaadin.ui.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Leon on 16.06.2016.
 */
public class StudentFilterModalWindow extends BasicModalWindow {
    private Table table;
    private TextField inputFilterStudentLastName;
    private NativeSelect selectGroup;

    private void init() {
        setCaption("Фильтр студентов");
        VerticalLayout filterContent = new VerticalLayout();
        filterContent.setMargin(true);
        setContent(filterContent);

//        TextField inputFilterStudentFirstName = new TextField();
//        inputFilterStudentFirstName.setCaption("Имя");

//        TextField inputFilterStudentMiddleName = new TextField();
//        inputFilterStudentMiddleName.setCaption("Фамилия");

        inputFilterStudentLastName = new TextField();
        inputFilterStudentLastName.setCaption("Фамилия");

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

        selectGroup.setNullSelectionAllowed(false);
        selectGroup.setCaption("Группа");

        getOkButton().setCaption("Ok");

        getCancelButton().addClickListener(e -> {
            table.removeAllItems();
            //studentTable = StudentController.loadAllElems(studentTable);

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

        FormLayout formFilterStudent = new FormLayout();
        formFilterStudent.setWidth("315px");
        //formFilterStudent.addComponent(inputFilterStudentFirstName);
//        formFilterStudent.addComponent(inputFilterStudentMiddleName);
        formFilterStudent.addComponent(inputFilterStudentLastName);
        formFilterStudent.addComponent(selectGroup);
        HorizontalLayout buttonPlace = new HorizontalLayout();
        buttonPlace.addComponent(getOkButton());
        buttonPlace.addComponent(getCancelButton());
        formFilterStudent.addComponent(buttonPlace);
        filterContent.addComponent(formFilterStudent);
    }

    public StudentFilterModalWindow(String name, Table table) {
        super(name, false);
        this.table = table;
        init();
    }

    @Override
    public void add() {

        StudentView filter;
        if (selectGroup.getValue() == null) {
            filter = new StudentView(null, null, null, inputFilterStudentLastName.getValue(), null, null, null);
        } else {
            filter = new StudentView(null, null, null, inputFilterStudentLastName.getValue(), null, null, Integer.parseInt(selectGroup.getItemCaption(selectGroup.getValue())));
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

    @Override
    public boolean update() {
        return true;
    }

    @Override
    protected void enableValidation() {
        inputFilterStudentLastName.setValidationVisible(true);
        inputFilterStudentLastName.validate();
        inputFilterStudentLastName.commit();
    }

    @Override
    protected void disableValidation() {
        inputFilterStudentLastName.setValidationVisible(false);
    }
}
