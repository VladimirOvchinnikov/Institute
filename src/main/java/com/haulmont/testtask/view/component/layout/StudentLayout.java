package com.haulmont.testtask.view.component.layout;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.haulmont.testtask.controller.StudentController;
import com.haulmont.testtask.controller.exception.ControllerCriticalException;
import com.haulmont.testtask.controller.exception.ControllerException;
import com.haulmont.testtask.controller.view.StudentView;
import com.haulmont.testtask.view.component.modal.window.add.AddStudentModalWindow;
import com.haulmont.testtask.view.component.modal.window.edit.EditStudentModalWindow;
import com.haulmont.testtask.view.component.modal.window.filter.StudentFilterModalWindow;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import java.util.Date;
import java.util.List;

/**
 * Created by Leon on 16.06.2016.
 */
public class StudentLayout extends BasicLayout {
    public StudentLayout(String name) {
        super(name);

        Button filterStudentButton = new Button("Фильтр");
        filterStudentButton.addClickListener(e -> {
            UI.getCurrent().addWindow(addFilter());
        });
        buttonLayout.addButton(filterStudentButton);

        table.addContainerProperty("Имя", String.class, null);
        table.addContainerProperty("Фамилия", String.class, null);
        table.addContainerProperty("Отчество", String.class, null);
        table.addContainerProperty("Дата рождения", Date.class, null);
        table.addContainerProperty("Группа", Integer.class, null);

       refresh();
    }

    @Override
    protected void delete() {
        Long studentID = (Long) table.getValue();
        try {
            StudentView student = new StudentView(studentID);
            List<StudentView> students = Lists.newArrayList();
            students.add(student);
            if (StudentController.delete(students) == 0) {
                Notification.show("Не удалось удалить студента");
            } else {
                table.removeItem(table.getValue());
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

    @Override
    protected Window newEditWindow() {
        EditStudentModalWindow window = new EditStudentModalWindow("Редактирование студента", table);
        return window;

    }

    @Override
    protected Window newAddWindow() {
        AddStudentModalWindow window = new AddStudentModalWindow("Добавление студента", table);
        return window;
    }

    @Override
    public void refresh() {
        table.removeAllItems();
        List<StudentView> students = Lists.newArrayList();
        try {
            students = StudentController.select(students);
        } catch (ControllerException e) {
            e.printStackTrace();
        } catch (ControllerCriticalException e) {
            e.printStackTrace();
        }

        for (StudentView student : students) {
            table.addItem(new Object[]{student.getFirstName(), student.getLastName(), student.getMiddleName(),
                    student.getBirthDay(), student.getNumberGroup()}, student.getId());
        }
    }

    private Window addFilter(){
        StudentFilterModalWindow window = new StudentFilterModalWindow("Фильтр студентов", table);
        return window;
    }
}
