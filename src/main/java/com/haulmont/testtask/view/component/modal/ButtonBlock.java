package com.haulmont.testtask.view.component.modal;

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
        editButton.addClickListener( e -> {
//            UI.getCurrent().addWindow(addEditModal());
        });

        addButton.addClickListener( e -> {
//            UI.getCurrent().addWindow(addAddModal());
        });

        deleteButton.addClickListener(e -> {
//            deleteGroup();
        });
        this.setMargin(true);
        this.addComponent(addButton);
        this.addComponent(editButton);
        this.addComponent(deleteButton);

        editButton.setVisible(false);
        deleteButton.setVisible(false);
    }

//    public void addListenerAddButton(Button.ClickListener l){
//        addButton.addClickListener();
//    }

    public Button getAddButton() {
        return addButton;
    }


    public Button getEditButton() {
        return editButton;
    }


    public Button getDeleteButton() {
        return deleteButton;
    }

    public void visibleEditButton(boolean isVisible){
        editButton.setVisible(isVisible);
    }

    public void visibleDeleteButton(boolean isVisible){
        deleteButton.setVisible(isVisible);
    }
}
