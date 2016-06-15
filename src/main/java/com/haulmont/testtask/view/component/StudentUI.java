package com.haulmont.testtask.view.component;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.haulmont.testtask.controller.StudentController;
import com.haulmont.testtask.controller.exception.ControllerCriticalException;
import com.haulmont.testtask.controller.exception.ControllerException;
import com.haulmont.testtask.controller.view.StudentView;
import com.haulmont.testtask.view.component.modal.window.StudentModalWindow;
import com.haulmont.testtask.view.component.modal.window.filter.StudentFilterModalWindow;
import com.vaadin.ui.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Leon on 13.06.2016.
 */
public class StudentUI {
    private static Table studentTable = new Table("Студенты");
    private static VerticalLayout studentTab = new VerticalLayout();
    //private static HorizontalLayout buttonBlock = new HorizontalLayout();
    private static ButtonBlock buttonBlock = new ButtonBlock();

    public static void prepareStudentPage() {
        studentTable.setSizeFull();
        studentTable.setPageLength(0);
        studentTable.setHeight("100%");

        Button filterStudentButton = new Button("Фильтр");
        filterStudentButton.addClickListener(e -> {
            UI.getCurrent().addWindow(addFilter());
        });
        buttonBlock.addButton(filterStudentButton);


        buttonBlock.getEditButton().addClickListener(e -> {
            UI.getCurrent().addWindow(addEditModal());
        });

        buttonBlock.getAddButton().addClickListener(e -> {
            UI.getCurrent().addWindow(addAddModal());
        });

        buttonBlock.getDeleteButton().addClickListener(e -> {
            deleteStudent();
        });


        buttonBlock.getEditButton().setVisible(false);
        buttonBlock.getDeleteButton().setVisible(false);

        studentTable.setSelectable(true);

        studentTable.addContainerProperty("Имя", String.class, null);
        studentTable.addContainerProperty("Фамилия", String.class, null);
        studentTable.addContainerProperty("Отчество", String.class, null);
        studentTable.addContainerProperty("Дата рождения", Date.class, null);
        studentTable.addContainerProperty("Группа", Integer.class, null);

        //Грузим элементы из БД

        List<StudentView> students = Lists.newArrayList();
        try {
            students = StudentController.select(students);
        } catch (ControllerException e) {
            e.printStackTrace();
        } catch (ControllerCriticalException e) {
            e.printStackTrace();
        }

        for (StudentView student : students) {
            studentTable.addItem(new Object[]{student.getFirstName(), student.getLastName(), student.getMiddleName(),
                    student.getBirthDay(), student.getNumberGroup()}, student.getId());
        }

        studentTable.setPageLength(10);

        studentTable.addValueChangeListener(e -> {
            if (studentTable.getValue() == null) {
                buttonBlock.getEditButton().setVisible(false);
                buttonBlock.getDeleteButton().setVisible(false);
            } else {
                buttonBlock.getEditButton().setVisible(true);
                buttonBlock.getDeleteButton().setVisible(true);
            }
        });

        System.out.println();
        studentTab.addComponent(buttonBlock);
        studentTab.addComponent(studentTable);

        studentTab.setCaption("Студенты");
    }

    private static Window addFilter() {
        StudentFilterModalWindow studentFilterModalWindow = new StudentFilterModalWindow("Фильтр студентов", studentTable);
        return studentFilterModalWindow;

    }

    public static Component studentPage() {
        if (studentTab.getCaption() == null) {
            prepareStudentPage();
            return studentTab;
        } else {
            return studentTab;
        }
    }

    private static void deleteStudent() {
        Long studentID = (Long) studentTable.getValue();
        try {
            StudentView student = new StudentView(studentID);
            List<StudentView> students = Lists.newArrayList();
            students.add(student);
            if (StudentController.delete(students) == 0) {
                Notification.show("Не удалось удалить студента");
            } else {
                studentTable.removeItem(studentTable.getValue());
            }
        } catch (ControllerCriticalException e) {
            e.printStackTrace();
        } catch (ControllerException e) {
            e.printStackTrace();
        }
//        } catch (SQLException e) {
//            Notification.show("SQL Error");
//        } catch (DatabaseException e) {
//            Notification.show("Critical error");
//        }

    }


    public static String getTabCaption() {
        return studentTab.getCaption();
    }

    private static Window addAddModal() {
        StudentModalWindow studentModalWindow = new StudentModalWindow("Добавление студента", false, studentTable);
        return studentModalWindow;
    }

    private static Window addEditModal() {
        StudentModalWindow studentModalWindow = new StudentModalWindow("Редактирование студента", true, studentTable);
        return studentModalWindow;
    }
}
