package com.haulmont.testtask.db.init;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Leon on 08.06.2016.
 */
public class InitializtionDB {

    public static void createFile() {
        File file = new File("create_table.script");
        StringBuilder script = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                script.append(scanner.next());
                script.append(" ");
            }
        } catch (IOException ioe) {

        }


    }
}
