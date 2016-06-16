package com.haulmont.testtask.controller;

import com.haulmont.testtask.controller.adapter.StudentConverter;
import com.haulmont.testtask.controller.exception.ControllerException;
import com.haulmont.testtask.controller.view.StudentView;
import com.haulmont.testtask.model.dao.HandlerDAO;
import com.haulmont.testtask.model.dao.StudentDAO;
import com.haulmont.testtask.model.dao.exception.DAOException;
import com.haulmont.testtask.model.entity.Student;

import java.util.List;
import java.util.Map;

/**
 * Created by Leon on 12.06.2016.
 */
public class StudentController {

    public static int delete(List<StudentView> views) throws ControllerException {
        List<Student> students = StudentConverter.viewToStudent(views, StudentConverter::newStudent);
        try {
            return HandlerDAO.delete(students, Student.class);
        } catch (DAOException e) {
            throw new ControllerException(e);
        }

    }

    public static List<StudentView> select(List<StudentView> views) throws ControllerException {
        try {
            List<Student> ids = StudentConverter.viewToStudent(views, StudentConverter::newStudent);
            Map<Long, Integer> map = ((StudentDAO) HandlerDAO.getDAO(Student.class)).selectNumberGroupStudents(ids);
            List students = HandlerDAO.select(ids, Student.class);
            List<StudentView> result = StudentConverter.studentToView(students, StudentConverter::newStudentView);
            result.stream().forEach(r -> r.setNumberGroup(map.get(r.getId())));
            return result;
        } catch (DAOException e) {
            throw new ControllerException(e);
        }
    }

    public static boolean update(StudentView view) throws ControllerException {
        try {
            return HandlerDAO.update(StudentConverter.viewToStudent(view, StudentConverter::newStudent));
        } catch (DAOException e) {
            throw new ControllerException(e);
        }
    }

    public static Long insert(StudentView view) throws ControllerException {
        try {
            return HandlerDAO.insert(StudentConverter.viewToStudent(view, StudentConverter::newStudent));
        } catch (DAOException e) {
            throw new ControllerException(e);
        }
    }

    public static Map<Long, Integer> getNumberGroups() throws ControllerException {
        try {
            return ((StudentDAO) HandlerDAO.getDAO(Student.class)).selectDistinctNumberGroup();
        } catch (DAOException e) {
            throw new ControllerException(e);
        }
    }

    public static List<StudentView> filter(StudentView filter) throws ControllerException {
        try {
            List<Student> students = ((StudentDAO) HandlerDAO.getDAO(Student.class)).filter(filter.getLastName(), filter.getNumberGroup());
            Map<Long, Integer> map = ((StudentDAO) HandlerDAO.getDAO(Student.class)).selectNumberGroupStudents(students);
            List<StudentView> views = StudentConverter.studentToView(students, StudentConverter::newStudentView);
            views.stream().forEach(r -> r.setNumberGroup(map.get(r.getId())));
            return views;
        } catch (DAOException e) {
            throw new ControllerException(e);
        }
    }

}
