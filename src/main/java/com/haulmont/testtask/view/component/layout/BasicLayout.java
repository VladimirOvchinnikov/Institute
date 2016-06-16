package com.haulmont.testtask.view.component.layout;

import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Created by Leon on 16.06.2016.
 */
public abstract class BasicLayout extends VerticalLayout {
    protected Table table;
    protected ButtonLayout buttonLayout;

    public BasicLayout(String name) {
        setCaption(name);
        table = new Table(name);
        buttonLayout = new ButtonLayout();
        preparePage();
    }

    private void preparePage() {
        table.setSizeFull();
        table.setPageLength(0);
        table.setHeight("100%");

        buttonLayout.getEditButton().addClickListener(e -> {
            UI.getCurrent().addWindow(openEditWindow());
        });

        buttonLayout.getAddButton().addClickListener(e -> {
            UI.getCurrent().addWindow(openAddWindow());
        });

        buttonLayout.getDeleteButton().addClickListener(e -> {
            delete();
        });

        buttonLayout.getEditButton().setVisible(false);
        buttonLayout.getDeleteButton().setVisible(false);

        table.setSelectable(true);

        table.setPageLength(10);

        table.addValueChangeListener(e -> {
            if (table.getValue() == null) {
                buttonLayout.getEditButton().setVisible(false);
                buttonLayout.getDeleteButton().setVisible(false);
            } else {
                buttonLayout.getEditButton().setVisible(true);
                buttonLayout.getDeleteButton().setVisible(true);
            }
        });

        addComponent(buttonLayout);
        addComponent(table);
    }

    abstract protected void delete();

    abstract protected Window openEditWindow();

    abstract protected Window openAddWindow();

    abstract public void refresh();
}
