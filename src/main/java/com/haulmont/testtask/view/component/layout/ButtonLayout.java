package com.haulmont.testtask.view.component.layout;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

/**
 * Created by Leon on 15.06.2016.
 */
public class ButtonLayout extends HorizontalLayout {
    private Button addButton ;
    private Button editButton ;
    private Button deleteButton;

    public ButtonLayout(){
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

    public void addButton(Button button){
        addComponent(button);
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
