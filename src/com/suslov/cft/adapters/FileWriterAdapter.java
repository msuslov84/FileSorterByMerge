package com.suslov.cft.adapters;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Mikhail Suslov
 */
public class FileWriterAdapter {
    private final File file;
    private String lastElement;

    public FileWriterAdapter(String fileName) {
        this.file = new File(fileName);
    }

    public void write(Integer element) {
        write(String.valueOf(element));
    }

    public void write(String element) {
        if (element == null) {
            // TODO: добавить логи в файл вместо вывода сообщения в консоль
            System.out.println("ОШИБКА: На запись передан несуществующий элемент!");
            return;
        }

        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(element + "\n");
            writer.flush();
            lastElement = element;
        } catch (IOException e) {
            // TODO: добавить логи в файл вместо вывода сообщения в консоль
            System.out.printf("ОШИБКА ЗАПИСИ ФАЙЛА '%s': %s", file.getName(), e.getMessage());
        }
    }

    public String getLastElement() {
        return lastElement;
    }

}
