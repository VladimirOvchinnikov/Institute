package com.haulmont.testtask.controller;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.haulmont.testtask.controller.adapter.StubStudentToView;
import com.haulmont.testtask.controller.view.StudentView;
import com.haulmont.testtask.model.dao.HandlerDAO;
import com.haulmont.testtask.model.dao.StudentDAO;
import com.haulmont.testtask.model.entity.Entity;
import com.haulmont.testtask.model.entity.Student;

import java.util.List;
import java.util.Map;

/**
 * Created by Leon on 12.06.2016.
 */
public class StudentController {

    private static StudentDAO studentDAO = new StudentDAO();

    public static int delete(List<StudentView> views){
        List<Student> students = Lists.newArrayList();
        for(StudentView view: views){
            students.add(StubStudentToView.conv(view));
        }
        return HandlerDAO.delete(students, Student.class);
    }

    public static List<StudentView> select(List<StudentView> views){
        List<Student> ids = Lists.newArrayList();
        for(StudentView view: views){
            ids.add(StubStudentToView.conv(view));
        }
        Map<Long,Integer> map = ((StudentDAO)HandlerDAO.getDAO(Student.class)).selectNumberGroupStudents(ids);
        List<Entity> students = HandlerDAO.select(ids, Student.class);
        List<StudentView> result = Lists.newArrayList();
        for(Entity student: students){
            StudentView sv =StubStudentToView.conv1((Student) student);
            sv.setNumberGroup(map.get(sv.getId()));
            result.add(sv);
        }
        return result;
    }

    public static boolean update(StudentView view){
        boolean isUpdate = HandlerDAO.update(StubStudentToView.conv(view));
        return isUpdate;
    }

    public static Long insert(StudentView view){
        Long id = HandlerDAO.insert(StubStudentToView.conv(view));
        return id;
    }

    public static Map<Long, Integer> getNumberGroups(){
        return ((StudentDAO)HandlerDAO.getDAO(Student.class)).selectDistinctNumberGroup();
    }

}
