package com.haulmont.testtask.controller;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.haulmont.testtask.controller.adapter.StudentConverter;
import com.haulmont.testtask.controller.view.StudentView;
import com.haulmont.testtask.model.db.exception.DatabaseException;
import com.haulmont.testtask.model.entity.Student;

import java.util.Date;
import java.util.List;

/**
 * Created by ovchinnikov on 10.06.2016.
 */
public class MainController {

    public static void main(String[] args) throws DatabaseException {

//        GroupView view0= new GroupView(2L);
//        GroupView view1= new GroupView(1L);
//        List<GroupView> views = new ArrayList<>();
//        //views.add(view0);
//        //views.add(view1);
//        System.out.println(GroupController.delete(views));
//        ConnectDB.getInstance().close();

//        GroupView view = new GroupView(0L);
//        GroupView view1 = new GroupView(1L);
//        List<GroupView> views = Lists.newArrayList();
//        //views.add(view);
//        //views.add(view1);
//        List<GroupView> res =  GroupController.select(views);
//        for(GroupView group: res){
//            System.out.println(group);
//        }
//        ConnectDB.getInstance().close();

//        GroupView view = new GroupView(666, "Hell");
//        long id = GroupController.insert(view);
//        view.setId(id);
//        System.out.println(view);
//        ConnectDB.getInstance().close();


//        GroupView view1 = new GroupView(0L, 2, "Information");
//        GroupView view2 = new GroupView(1L, 2, "Science");
//        GroupView view3 = new GroupView(2L, 4, "Performance");
//        boolean bview1 = GroupController.update(view1);
//        boolean bview2 = GroupController.update(view2);
//        boolean bview3 = GroupController.update(view3);
//        System.out.println(bview1);
//        System.out.println(bview2);
//        System.out.println(bview3);
//        ConnectDB.getInstance().close();

//        StudentView view0 = new StudentView(0L);
//        StudentView view1 = new StudentView(1L);
//        List<StudentView> views = Lists.newArrayList();
//        views.add(view0);
//        views.add(view1);
//        System.out.println(StudentController.delete(views));
//        ConnectDB.getInstance().close();

//        List<StudentView> views = Lists.newArrayList();
//        List<StudentView> views1 = StudentController.select(views);
//        for(StudentView view: views1){
//            System.out.println(view);
//        }
//        ConnectDB.getInstance().close();

//        StudentView view = new StudentView("Katia", "Katerinovna", "Katenka", new Date(), 1L, 1);
//        System.out.println(StudentController.insert(view));
//        ConnectDB.getInstance().close();

//        StudentView view = new StudentView(0L, "Katia", "Katerinovna", "Katenka", new Date(), 1L, 1);
//        System.out.println(StudentController.update(view));
//        ConnectDB.getInstance().close();

//        Map<Long, Integer> map = StudentController.getNumberGroups();
//        for(Map.Entry<Long, Integer> entry: map.entrySet()){
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        }
//        ConnectDB.getInstance().close();

//        List<GroupView> views = Lists.newArrayList();
//        List<GroupView> res =  GroupController.select(views);
//        for(GroupView group: res){
//            System.out.println(group);
//        }
//
//        List<StudentView> views1 = Lists.newArrayList();
//        views1 = StudentController.select(views1);
//        for(StudentView view: views1){
//            System.out.println(view);
//        }
//        ConnectDB.getInstance().close();

        Student student = new Student(-1L, "king", "King", "KinG", new Date(), -2L);
        StudentView sv = StudentConverter.studentToView(student, (s) -> new StudentView(s.getId(),
                                                                                        s.getFirstName(),
                                                                                        s.getMiddleName(),
                                                                                        s.getLastName(),
                                                                                        s.getBirthDay(),
                                                                                        s.getGroupId(),
                                                                                        -3));
        System.out.println(sv);

        List<Student> list = Lists.newArrayList();
        list.add(student);
        Student student1 = new Student(-2L, "cat", "Cat", "caT", new Date(), -10L);
        list.add(student1);
        List<StudentView> views= StudentConverter.studentToView(list, (s) -> new StudentView(student.getId(),
                                                                                             student.getFirstName(),
                                                                                             student.getMiddleName(),
                                                                                             student.getLastName(),
                                                                                             student.getBirthDay(),
                                                                                             student.getGroupId(),
                                                                                             -4));
        for(StudentView view: views){
            System.out.println(view);
        }

        System.out.println();
        Student student2 = StudentConverter.viewToStudent(sv, (view) -> new Student(view.getId(),
                                                                                 view.getFirstName(),
                                                                                 view.getMiddleName(),
                                                                                 view.getLastName(),
                                                                                 view.getBirthDay(),
                                                                                 view.getGroupId()));
        System.out.println(student2);
        List<Student> sudents = StudentConverter.viewToStudent(views, (view) -> new Student(view.getId(),
                view.getFirstName(),
                view.getMiddleName(),
                view.getLastName(),
                view.getBirthDay(),
                view.getGroupId()));

        for(Student view: sudents){
            System.out.println(view);
        }

    }
}
