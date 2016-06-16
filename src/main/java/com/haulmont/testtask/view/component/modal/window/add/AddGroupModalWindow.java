package com.haulmont.testtask.view.component.modal.window.add;

import com.haulmont.testtask.controller.GroupController;
import com.haulmont.testtask.controller.exception.ControllerCriticalException;
import com.haulmont.testtask.controller.exception.ControllerException;
import com.haulmont.testtask.controller.view.GroupView;
import com.haulmont.testtask.view.component.modal.window.BasicModalWindow;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

/**
 * Created by Leon on 16.06.2016.
 */
public class AddGroupModalWindow extends BasicModalWindow implements Validation {

    protected Table table;

    protected TextField newFaculty;
    protected TextField newNumber;

    public AddGroupModalWindow(String name, Table table) {
        super(name);
        this.table = table;
        newFaculty = new TextField();
        newFaculty.setCaption("Факультет");
        newFaculty.addValidator(new StringLengthValidator(
                "Название факультета должно быть больше 1 символа и меньше 40",
                1, 40, true));

        newFaculty.setImmediate(true);
        newFaculty.setValidationVisible(false);
        newFaculty.setBuffered(true);

        newNumber = new TextField();
        newNumber.setCaption("Номер группы");
        newNumber.addValidator(new StringLengthValidator(
                "Номер группы должен быть больше 1 символа и меньше 10",
                1, 10, true));

        newNumber.setImmediate(true);
        newNumber.setValidationVisible(false);
        newNumber.setBuffered(true);

        FormLayout formAddGroup = new FormLayout();
        formAddGroup.setWidth("335px");
        formAddGroup.addComponent(newFaculty);
        formAddGroup.addComponent(newNumber);
        HorizontalLayout buttonPlace = new HorizontalLayout();
        buttonPlace.addComponent(okButton);
        buttonPlace.addComponent(cancelButton);
        formAddGroup.addComponent(buttonPlace);
        modalContent.addComponent(formAddGroup);
    }

    @Override
    public void add() {
        if (newNumber.getValue().matches("[-+]?\\d+")) {
            try {
                enableValidation();
                GroupView view = new GroupView(Integer.parseInt(newNumber.getValue()), newFaculty.getValue());
                Long id = GroupController.insert(view);
                table.addItem(new Object[]{newFaculty.getValue(), Integer.parseInt(newNumber.getValue())}, id);
                close();
            } catch (Validator.InvalidValueException ex) {
                Notification.show(ex.getMessage());
            } catch (ControllerException e) {
                //e.printStackTrace();
                Notification.show("Добавить запись не удалось");
                //return -1L;
            } catch (ControllerCriticalException e) {
                Notification.show("Ошибка подключения бд");
            }
        } else {
            newNumber.setValidationVisible(true);
            Notification.show("Номер группы должен быть числовым");
        }

    }

    @Override
    public void enableValidation() {
        newFaculty.setValidationVisible(true);
        newFaculty.validate();
        newFaculty.commit();

        newNumber.setValidationVisible(true);
        newNumber.validate();
        newNumber.commit();
    }
}
