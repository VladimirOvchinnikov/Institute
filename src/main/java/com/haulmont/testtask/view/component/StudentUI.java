package com.haulmont.testtask.view.component;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
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
import java.util.List;

/**
 * Created by Leon on 13.06.2016.
 */
public class StudentUI {
    private static Table studentTable = new Table("Студенты");
    private static VerticalLayout studentTab = new VerticalLayout();
    private static HorizontalLayout buttonBlock = new HorizontalLayout();

    public static void prepareStudentPage(){
        studentTable.setSizeFull();
        studentTable.setPageLength(0);
        studentTable.setHeight("100%");
        Button addStudentButton = new Button("Добавить");
        Button editStudentButton = new Button("Редактировать");
        Button deleteStudentButton = new Button("Удалить");
        Button filterStudentButton = new Button("Фильтр");


        editStudentButton.addClickListener( e -> {
            UI.getCurrent().addWindow(addEditModal());
        });

        addStudentButton.addClickListener( e -> {
            UI.getCurrent().addWindow(addAddModal());
        });

        deleteStudentButton.addClickListener(e -> {
            deleteStudent();
        });

        filterStudentButton.addClickListener(e -> {
            UI.getCurrent().addWindow(addFilter());
            //();
        });


        buttonBlock.setMargin(true);
        buttonBlock.addComponent(addStudentButton);
        buttonBlock.addComponent(filterStudentButton);
        buttonBlock.addComponent(editStudentButton);
        buttonBlock.addComponent(deleteStudentButton);


        editStudentButton.setVisible(false);
        deleteStudentButton.setVisible(false);

        studentTable.setSelectable(true);

        studentTable.addContainerProperty("Имя", String.class, null);
        studentTable.addContainerProperty("Фамилия",  String.class, null);
        studentTable.addContainerProperty("Отчество",  String.class, null);
        studentTable.addContainerProperty("Дата рождения",  Date.class, null);
        studentTable.addContainerProperty("Группа",  Integer.class, null);

        //Грузим элементы из БД

        List<StudentView> students =  Lists.newArrayList();
        try {
            students = StudentController.select(students);
        } catch (ControllerException e) {
            e.printStackTrace();
        } catch (ControllerCriticalException e) {
            e.printStackTrace();
        }

        for (StudentView student: students){
            studentTable.addItem(new Object[]{student.getFirstName(), student.getLastName(), student.getMiddleName(),
                    student.getBirthDay(), student.getNumberGroup()}, student.getId());
        }

        studentTable.setPageLength(10);

        studentTable.addValueChangeListener(e ->{
            if(studentTable.getValue()==null){
                editStudentButton.setVisible(false);
                deleteStudentButton.setVisible(false);
            }else{
                editStudentButton.setVisible(true);
                deleteStudentButton.setVisible(true);
            }
        });

        System.out.println();
        studentTab.addComponent(buttonBlock);
        studentTab.addComponent(studentTable);

        studentTab.setCaption("Студенты");
    }

    private static Window addFilter(){
        Window filterBlock = new Window();
        filterBlock.setCaption("Фильтр студентов");
        VerticalLayout filterContent = new VerticalLayout();
        filterContent.setMargin(true);
        filterBlock.setContent(filterContent);

        TextField inputFilterStudentFirstName = new TextField();
        inputFilterStudentFirstName.setCaption("Имя");

        TextField inputFilterStudentMiddleName = new TextField();
        inputFilterStudentMiddleName.setCaption("Фамилия");

        TextField inputFilterStudentLastName = new TextField();
        inputFilterStudentLastName.setCaption("Отчество");

        NativeSelect selectGroup = new NativeSelect();
        //selectGroup = StudentController.fillCombo(selectGroup);//AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
        selectGroup.setNullSelectionAllowed(false);
        selectGroup.setCaption("Группа");

        Button submitFilterStudent = new Button();
        submitFilterStudent.setCaption("OK");

        Button closeFilter = new Button();
        closeFilter.setCaption("Отмена");

        closeFilter.addClickListener(e -> {
            try {
                studentTable.removeAllItems();
                //studentTable = StudentController.loadAllElems(studentTable);

                List<StudentView> students = Lists.newArrayList();
                students = StudentController.select(students);
                for (StudentView student : students) {
                    studentTable.addItem(new Object[]{student.getFirstName(), student.getLastName(), student.getMiddleName(),
                            student.getBirthDay(), student.getNumberGroup()}, student.getId());
                }
            } catch (ControllerException e1) {
                e1.printStackTrace();
            } catch (ControllerCriticalException e1) {
                e1.printStackTrace();
            }

            filterBlock.close();
        });

        final NativeSelect finalSelectGroup = selectGroup;
        submitFilterStudent.addClickListener(e -> {
            try {
                inputFilterStudentFirstName.setValidationVisible(true);
                inputFilterStudentFirstName.validate();
                inputFilterStudentFirstName.commit();

                inputFilterStudentMiddleName.setValidationVisible(true);
                inputFilterStudentMiddleName.validate();
                inputFilterStudentMiddleName.commit();

                inputFilterStudentLastName.setValidationVisible(true);
                inputFilterStudentLastName.validate();
                inputFilterStudentLastName.commit();

                studentTable.removeAllItems();
                studentTable = filterStudents(
                        studentTable,
                        inputFilterStudentFirstName.getValue(),
                        inputFilterStudentMiddleName.getValue(),
                        inputFilterStudentLastName.getValue(),
                        Integer.parseInt(finalSelectGroup.getValue().toString())
                );

                inputFilterStudentFirstName.setValidationVisible(false);
                inputFilterStudentMiddleName.setValidationVisible(false);
                inputFilterStudentLastName.setValidationVisible(false);
            } catch (Validator.InvalidValueException ex) {
                Notification.show(ex.getMessage());
            }
        });

        FormLayout formFilterStudent = new FormLayout();
        formFilterStudent.setWidth("315px");
        formFilterStudent.addComponent(inputFilterStudentFirstName);
        formFilterStudent.addComponent(inputFilterStudentMiddleName);
        formFilterStudent.addComponent(inputFilterStudentLastName);
        formFilterStudent.addComponent(selectGroup);
        HorizontalLayout buttonPlace = new HorizontalLayout();
        buttonPlace.addComponent(submitFilterStudent);
        buttonPlace.addComponent(closeFilter);
        formFilterStudent.addComponent(buttonPlace);
        filterContent.addComponent(formFilterStudent);

        filterBlock.center();

        return filterBlock;
    }

    private static Table filterStudents(
            Table studentTable,
            String firstName,
            String middleName,
            String lastName,
            Integer groupNumber){

        Table ret = null;
//        try {
//            ret = StudentController.loadFilteredElems(
//                    studentTable,
//                    firstName,
//                    middleName,
//                    lastName,
//                    groupNumber);//тут фильтры
            ret = studentTable;
//        } catch (ClassNotFoundException e) {
//            Notification.show("Class not found");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            Notification.show("SQL Error");
//        } catch (DatabaseException e) {
//            Notification.show("Critical Error");
//        }
        return ret;
    }

    public static Component studentPage(){
        if(studentTab.getCaption() == null){
            prepareStudentPage();
            return studentTab;
        }else{
            return studentTab;
        }
    }

    private static void deleteStudent(){
        Long studentID = (Long) studentTable.getValue();
        try {
            StudentView student = new StudentView(studentID);
            List<StudentView> students = Lists.newArrayList();
            students.add(student);
            StudentController.delete(students);
            studentTable.removeItem(studentTable.getValue());
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

    private static Long addStudent(
            String firstName,
            String middleName,
            String lastName,
            Date birth,
            Long groupId,
            Integer groupNumber){
        try {
            StudentView student = new StudentView(firstName, middleName, lastName, birth, groupId, groupNumber);
            Long id = StudentController.insert(student);
            Notification.show("Запись добавлена");
            return id;
        } catch (ControllerException e) {
            e.printStackTrace();
            return -1L;
        } catch (ControllerCriticalException e) {
            e.printStackTrace();
            return -1L;
        }
//        } catch (ClassNotFoundException e) {
//            Notification.show("Class not found");
//            return null;
//        } catch (SQLException e) {
//            Notification.show("SQL Error");
//            return null;
//        } catch (DatabaseException e) {
//            Notification.show("Critical error");
//            return null;
//        }
    }

    private static boolean editStudent(
            String firstName,
            String middleName,
            String lastName,
            Date birth,
            Integer groupNumber,
            Long groupId,
            Long id){
        try {
            StudentView student = new StudentView(id, firstName, middleName, lastName, birth, groupId, groupNumber);
            boolean isUpdate = StudentController.update(student);
            Notification.show("Запись изменена");
            return isUpdate;
        } catch (ControllerException e) {
            e.printStackTrace();
            return false;
        } catch (ControllerCriticalException e) {
            e.printStackTrace();
            return false;
        }
//        } catch (ClassNotFoundException e) {
//            Notification.show("Class not found");
//            return null;
//        } catch (SQLException e) {
//            Notification.show("SQL Error");
//            return null;
//        } catch (DatabaseException e) {
//            Notification.show("Critical error");
//            return null;
//        }
    }

    public static String getTabCaption(){
        return studentTab.getCaption();
    }

    private static Window addAddModal(){
        Window modalNewStudent = new Window("Добавление студента");
        VerticalLayout modalNewStudentContent = new VerticalLayout();
        modalNewStudentContent.setMargin(true);
        modalNewStudent.setContent(modalNewStudentContent);

        TextField inputNewStudentFirstName = new TextField();
        inputNewStudentFirstName.setCaption("Имя");
        inputNewStudentFirstName.addValidator(new StringLengthValidator(
                "Имя должно быть больше 1 символа и меньше 40",
                1, 40, true));

        inputNewStudentFirstName.setImmediate(true);
        inputNewStudentFirstName.setValidationVisible(false);
        inputNewStudentFirstName.setBuffered(true);

        TextField inputNewStudentMiddleName = new TextField();
        inputNewStudentMiddleName.setCaption("Фамилия");
        inputNewStudentMiddleName.addValidator(new StringLengthValidator(
                "Фамилия должна быть больше 1 символа и меньше 40",
                1, 40, true));

        inputNewStudentMiddleName.setImmediate(true);
        inputNewStudentMiddleName.setValidationVisible(false);
        inputNewStudentMiddleName.setBuffered(true);

        TextField inputNewStudentLastName = new TextField();
        inputNewStudentLastName.setCaption("Отчество");
        inputNewStudentLastName.addValidator(new StringLengthValidator(
                "Фамилия должна быть больше 1 символа и меньше 40",
                1, 40, true));

        inputNewStudentLastName.setImmediate(true);
        inputNewStudentLastName.setValidationVisible(false);
        inputNewStudentLastName.setBuffered(true);

        DateField inputNewBirth = new DateField();
        inputNewBirth.setValue(new Date());
        inputNewBirth.setCaption("Дата рождения");
        inputNewBirth.addValidator(new DateRangeValidator("Ввдедите дату рождения", new Date(0), new Date(), Resolution.DAY));

        inputNewBirth.setImmediate(true);
        inputNewBirth.setValidationVisible(false);
        inputNewBirth.setBuffered(true);

        NativeSelect selectGroup = new NativeSelect();
        //selectGroup = StudentController.fillCombo(selectGroup);//AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaaa
        selectGroup.setNullSelectionAllowed(false);
        selectGroup.setCaption("Группа");
        selectGroup.addValidator(new IntegerRangeValidator("Введите группу", 1, 99999));

        selectGroup.setImmediate(true);
        selectGroup.setValidationVisible(false);
        selectGroup.setBuffered(true);

        Button submitNewStudent = new Button();
        submitNewStudent.setCaption("ОК");
        Button closeNewStudent = new Button();
        closeNewStudent.setCaption("Отменить");
        closeNewStudent.addClickListener(e -> {
            modalNewStudent.close();
        });

        final NativeSelect finalSelectGroup = selectGroup;
        submitNewStudent.addClickListener(e -> {
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

                Long res = addStudent(
                        inputNewStudentFirstName.getValue(),
                        inputNewStudentMiddleName.getValue(),
                        inputNewStudentLastName.getValue(),
                        inputNewBirth.getValue(),
                        -1L,
                        Integer.parseInt(finalSelectGroup.getValue().toString())
                );
                if (res != -1) {
                    //for (StudentView result : res) {
                        studentTable.addItem(new Object[]{
                                        inputNewStudentFirstName.getValue(),
                                        inputNewStudentMiddleName.getValue(),
                                        inputNewStudentLastName.getValue(),
                                        inputNewBirth.getValue(),
                                        Integer.parseInt(finalSelectGroup.getValue().toString())},
                                        res);
                    //}
                }
                inputNewStudentFirstName.setValidationVisible(false);
                inputNewStudentMiddleName.setValidationVisible(false);
                inputNewStudentLastName.setValidationVisible(false);
                modalNewStudent.close();
            } catch (Validator.InvalidValueException ex) {
                Notification.show(ex.getMessage());
            }
        });

        FormLayout formAddStudent = new FormLayout();
        formAddStudent.setWidth("315px");
        formAddStudent.addComponent(inputNewStudentFirstName);
        formAddStudent.addComponent(inputNewStudentMiddleName);
        formAddStudent.addComponent(inputNewStudentLastName);
        formAddStudent.addComponent(inputNewBirth);
        formAddStudent.addComponent(selectGroup);
        HorizontalLayout buttonPlace = new HorizontalLayout();
        buttonPlace.addComponent(submitNewStudent);
        buttonPlace.addComponent(closeNewStudent);
        formAddStudent.addComponent(buttonPlace);
        modalNewStudentContent.addComponent(formAddStudent);

        modalNewStudent.center();

        return modalNewStudent;
    }

    private static Window addEditModal(){
        Window modalEditStudent = new Window("Редактирование студента");
        VerticalLayout modalEditStudentContent = new VerticalLayout();
        modalEditStudentContent.setMargin(true);
        modalEditStudent.setContent(modalEditStudentContent);
        Object rowId = studentTable.getValue();

        TextField inputEditStudentFirstName = new TextField();
        inputEditStudentFirstName.setCaption("Имя");
        String firstName = studentTable.getContainerProperty(rowId, "Имя").getValue().toString();
        inputEditStudentFirstName.setValue(firstName);
        inputEditStudentFirstName.addValidator(new StringLengthValidator(
                "Имя должно быть больше 1 символа и меньше 40",
                1, 40, true));

        inputEditStudentFirstName.setImmediate(true);
        inputEditStudentFirstName.setValidationVisible(false);
        inputEditStudentFirstName.setBuffered(true);

        TextField inputEditStudentMiddleName = new TextField();
        inputEditStudentMiddleName.setCaption("Отчество");
        String middleName = studentTable.getContainerProperty(rowId, "Отчество").getValue().toString();
        inputEditStudentMiddleName.setValue(middleName);
        inputEditStudentMiddleName.addValidator(new StringLengthValidator(
                "Фамилия должна быть больше 1 символа и меньше 40",
                1, 40, true));

        inputEditStudentMiddleName.setImmediate(true);
        inputEditStudentMiddleName.setValidationVisible(false);
        inputEditStudentMiddleName.setBuffered(true);

        TextField inputEditStudentLastName = new TextField();
        inputEditStudentLastName.setCaption("Фамилия");
        String lastName = studentTable.getContainerProperty(rowId, "Фамилия").getValue().toString();
        inputEditStudentLastName.setValue(lastName);
        inputEditStudentLastName.addValidator(new StringLengthValidator(
                "Фамилия должна быть больше 1 символа и меньше 40",
                1, 40, true));

        inputEditStudentLastName.setImmediate(true);
        inputEditStudentLastName.setValidationVisible(false);
        inputEditStudentLastName.setBuffered(true);

        DateField inputEditBirth = new DateField();
        inputEditBirth.setCaption("Дата рождения");
        inputEditBirth.setValue(new Date());
        inputEditBirth.addValidator(new DateRangeValidator("Ввдедите дату рождения",
                new Date(0), new Date(), Resolution.DAY));

        inputEditBirth.setImmediate(true);
        inputEditBirth.setValidationVisible(false);
        inputEditBirth.setBuffered(true);

        NativeSelect selectGroup = new NativeSelect();
        //selectGroup = StudentController.fillCombo(selectGroup);
        selectGroup.setNullSelectionAllowed(false);
        selectGroup.setCaption("Группа");
        selectGroup.addValidator(new IntegerRangeValidator("Введите группу", 1, 99999));

        selectGroup.setImmediate(true);
        selectGroup.setValidationVisible(false);
        selectGroup.setBuffered(true);


        Button submitEditStudent = new Button();
        submitEditStudent.setCaption("OK");
        Button closeEditStudent = new Button();
        closeEditStudent.setCaption("Отменить");
        closeEditStudent.addClickListener(e -> {
            modalEditStudent.close();
        });

        final NativeSelect finalSelectGroup = selectGroup;
        submitEditStudent.addClickListener(e -> {

            try {
                inputEditStudentFirstName.setValidationVisible(true);
                inputEditStudentFirstName.validate();
                inputEditStudentFirstName.commit();

                inputEditStudentMiddleName.setValidationVisible(true);
                inputEditStudentMiddleName.validate();
                inputEditStudentMiddleName.commit();

                inputEditStudentLastName.setValidationVisible(true);
                inputEditStudentLastName.validate();
                inputEditStudentLastName.commit();

                Object reFirstName = inputEditStudentFirstName.getValue();
                Object reMiddleName = inputEditStudentMiddleName.getValue();
                Object reLastName = inputEditStudentLastName.getValue();
                Object birth = inputEditBirth.getValue();
                Object group = finalSelectGroup.getValue();

                boolean res = editStudent(
                        reFirstName.toString(),
                        reMiddleName.toString(),
                        reLastName.toString(),
                        inputEditBirth.getValue(),
                        Integer.parseInt(finalSelectGroup.getValue().toString()),
                        -1L,
                        Long.parseLong(rowId.toString())
                );



                studentTable.getContainerProperty(rowId, "Имя").setValue(reFirstName.toString());
                studentTable.getContainerProperty(rowId, "Отчество").setValue(reMiddleName.toString());
                studentTable.getContainerProperty(rowId, "Фамилия").setValue(reLastName.toString());
                studentTable.getContainerProperty(rowId, "Дата рождения").setValue(birth);
                studentTable.getContainerProperty(rowId, "Группа").setValue(Integer.parseInt(group.toString()));

                inputEditStudentFirstName.setValidationVisible(false);
                inputEditStudentMiddleName.setValidationVisible(false);
                inputEditStudentLastName.setValidationVisible(false);
                modalEditStudent.close();
            } catch (Validator.InvalidValueException ex) {
                Notification.show(ex.getMessage());
            }

        });

        FormLayout formAddStudent = new FormLayout();
        formAddStudent.setWidth("315px");
        formAddStudent.addComponent(inputEditStudentFirstName);
        formAddStudent.addComponent(inputEditStudentMiddleName);
        formAddStudent.addComponent(inputEditStudentLastName);
        formAddStudent.addComponent(inputEditBirth);
        formAddStudent.addComponent(selectGroup);
        HorizontalLayout buttonPlace = new HorizontalLayout();
        buttonPlace.addComponent(submitEditStudent);
        buttonPlace.addComponent(closeEditStudent);
        formAddStudent.addComponent(buttonPlace);
        modalEditStudentContent.addComponent(formAddStudent);

        modalEditStudent.center();

        return modalEditStudent;
    }
}
