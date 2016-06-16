package com.haulmont.testtask.view.component.modal.window.add;

import com.haulmont.testtask.controller.GroupController;
import com.haulmont.testtask.controller.exception.ControllerException;
import com.haulmont.testtask.controller.view.GroupView;
import com.haulmont.testtask.view.component.modal.window.BasicModalWindow;
import com.haulmont.testtask.view.component.modal.window.Validation;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

/**
 * Created by Leon on 16.06.2016.
 */
public class AddGroupModalWindow extends BasicModalWindow implements Validation {

    protected Table table;

    protected TextField facultyTextField;
    protected TextField numberTextField;

    public AddGroupModalWindow(String name, Table table) {
        super(name);
        this.table = table;
        facultyTextField = new TextField();
        facultyTextField.setCaption("Факультет");
        facultyTextField.addValidator(new StringLengthValidator(
                "Название факультета должно быть больше 1 символа и меньше 40",
                1, 40, true));

        facultyTextField.setImmediate(true);
        facultyTextField.setValidationVisible(false);
        facultyTextField.setBuffered(true);

        numberTextField = new TextField();
        numberTextField.setCaption("Номер группы");
        numberTextField.addValidator(new StringLengthValidator(
                "Номер группы должен быть больше 1 символа и меньше 10",
                1, 10, true));

        numberTextField.setImmediate(true);
        numberTextField.setValidationVisible(false);
        numberTextField.setBuffered(true);

        FormLayout formAddGroup = new FormLayout();
        formAddGroup.setWidth("335px");
        formAddGroup.addComponent(facultyTextField);
        formAddGroup.addComponent(numberTextField);
        HorizontalLayout buttonPlace = new HorizontalLayout();
        buttonPlace.addComponent(okButton);
        buttonPlace.addComponent(cancelButton);
        formAddGroup.addComponent(buttonPlace);
        modalContent.addComponent(formAddGroup);
    }

    @Override
    public void action() {
        if (numberTextField.getValue().matches("[-+]?\\d+")) {
            try {
                enableValidation();
                GroupView view = new GroupView(Integer.parseInt(numberTextField.getValue()), facultyTextField.getValue());
                Long id = GroupController.insert(view);
                table.addItem(new Object[]{facultyTextField.getValue(), Integer.parseInt(numberTextField.getValue())}, id);
                close();
            } catch (Validator.InvalidValueException ex) {
                Notification.show(ex.getMessage());
            } catch (ControllerException e) {
                Notification.show(e.getMessage());
            }
        } else {
            numberTextField.setValidationVisible(true);
            Notification.show("Номер группы должен быть числовым");
        }

    }

    @Override
    public void enableValidation() {
        facultyTextField.setValidationVisible(true);
        facultyTextField.validate();
        facultyTextField.commit();

        numberTextField.setValidationVisible(true);
        numberTextField.validate();
        numberTextField.commit();
    }
}
