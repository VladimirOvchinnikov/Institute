package com.haulmont.testtask.view.component.modal.window;

import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Created by Leon on 16.06.2016.
 */
public abstract class BasicModalWindow extends Window {

    protected Button okButton;
    protected Button cancelButton;

    protected VerticalLayout modalContent;

    public BasicModalWindow(String name){
        super(name);
        modalContent = new VerticalLayout();
        modalContent.setMargin(true);
        setContent(modalContent);

        okButton = new Button("Добавить");
        okButton.addClickListener(e -> {
            action();
        });
        cancelButton = new Button("Отменить");
        cancelButton.addClickListener(e -> {
            close();
        });
        center();
    }

    abstract public void action();
}
