package com.haulmont.testtask.view.component;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

/**
 * Created by Leon on 15.06.2016.
 */
public class ButtonBlock extends HorizontalLayout {
    private Button addButton ;
    private Button editButton ;
    private Button deleteButton;

    public ButtonBlock(){
        super();
        addButton = new Button("Добавить");
        editButton = new Button("Редактировать");
        deleteButton = new Button("Удалить");

        setMargin(true);
        addComponent(addButton);
        addComponent(editButton);
        addComponent(deleteButton);

        editButton.setVisible(false);
        deleteButton.setVisible(false);
    }


    public Button getAddButton() {
        return addButton;
    }


    public Button getEditButton() {
        return editButton;
    }


    public Button getDeleteButton() {
        return deleteButton;
    }

}
