package com.suslov.cft.adapters;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Mikhail Suslov
 */
public class FileWriterAdapter {
    private final File file;

    public FileWriterAdapter(String fileName) {
        this.file = new File(fileName);
    }

    public void write(String element) {
        if (element == null) {
            System.out.println("ОШИБКА: На запись передан несуществующий элемент!");
            return;
        }

        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(element + "\n");
            writer.flush();
        } catch (IOException e) {
            System.out.printf("ОШИБКА ЗАПИСИ ФАЙЛА '%s': %s", file.getName(), e.getMessage());
        }
    }
}
