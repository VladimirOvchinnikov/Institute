package com.haulmont.testtask.view;

import com.haulmont.testtask.view.component.layout.GroupLayout;
import com.haulmont.testtask.view.component.layout.StudentLayout;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Widgetset("com.haulmont.MyAppWidgetset")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        final VerticalLayout layout = new VerticalLayout();
        layout.setHeight(null);
        layout.setWidth("100%");

        TabSheet tabsheet = new TabSheet();
        layout.addComponent(tabsheet);

        Component studentTab = new StudentLayout("Студенты");
        Component groupTab = new GroupLayout("Группы");

        studentTab.setHeight("100%");
        groupTab.setHeight("100%");

        tabsheet.addTab(groupTab, groupTab.getCaption());
        tabsheet.addTab(studentTab, studentTab.getCaption());

        tabsheet.addSelectedTabChangeListener(e -> {
            TabSheet tabSheet = e.getTabSheet();

            Layout tab = (Layout) tabSheet.getSelectedTab();

            String caption = tabSheet.getTab(tab).getCaption();
            if(caption.equals("Группы")){

            }else if(caption.equals("Студенты")){
                ((StudentLayout)tabSheet.getSelectedTab()).refresh();
            }
        });

        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
