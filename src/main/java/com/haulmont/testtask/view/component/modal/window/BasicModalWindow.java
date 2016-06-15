package com.haulmont.testtask.view.component.modal.window;

import com.haulmont.testtask.controller.view.ViewEntity;
import com.vaadin.ui.*;

/**
 * Created by Leon on 15.06.2016.
 */
public abstract class BasicModalWindow extends Window {

    private Button okButton;
    private Button cancelButton;

    private VerticalLayout modalContent;

    public VerticalLayout getModalContent(){
        return modalContent;
    }

    public BasicModalWindow(String name) {
        super(name);
        init();
        okButton.addClickListener(e -> {
            add();
        });
    }

    public BasicModalWindow(String name,  ViewEntity view) {
        super(name);
        init();
        okButton.addClickListener(e -> {
            if (!update(view)){
                close();
                Notification.show("Запись не обновилась");
            }

        });
    }

    private void init() {
        modalContent = new VerticalLayout();
        modalContent.setMargin(true);
        setContent(modalContent);

        okButton = new Button();
        okButton.setCaption("Добавить");
        cancelButton = new Button();
        cancelButton.setCaption("Отменить");
        cancelButton.addClickListener(e -> {
            close();
        });
    }

    public Button getOkButton() {
        return okButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    abstract public void add();

    abstract public boolean update(ViewEntity view);
}
