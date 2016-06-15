package com.haulmont.testtask.view.component.modal.window;

import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

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

    public BasicModalWindow(String name,  boolean isUpdate) {
        super(name);
        init();
        if (isUpdate) {
            getOkButton().setCaption("Изменить");
            okButton.addClickListener(e -> {
                if (!update()) {
                    close();
                    Notification.show("Запись не обновилась");
                }

            });
        }else {
            okButton.addClickListener(e -> {
                add();
            });
        }
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
        center();
    }

    public Button getOkButton() {
        return okButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    abstract public void add();

    abstract public boolean update();

    abstract protected void enableValidation();
    abstract protected void disableValidation();
}
