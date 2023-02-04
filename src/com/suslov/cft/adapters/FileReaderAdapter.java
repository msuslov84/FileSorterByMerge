package com.suslov.cft.adapters;

import com.suslov.cft.exceptions.ArgsException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Mikhail Suslov
 */
public class FileReaderAdapter {
    private Scanner scanner;
    private String nextElementName;
    private boolean endOfFile;

    public FileReaderAdapter(String fileName) throws ArgsException {
        try {
            scanner = new Scanner(new File(fileName));
            if (scanner.hasNext()) {
                nextElementName = scanner.next();
            } else {
                endOfFile = true;
                throw new ArgsException(String.format("ОШИБКА: в файле '%s' отсутствуют данные\n", fileName));
            }
        } catch (FileNotFoundException exp) {
            throw new ArgsException(String.format("ОШИБКА ЧТЕНИЯ ФАЙЛА '%s': %s\n", fileName, exp.getMessage()));
        }
    }

    public String getNextElementName() {
        if (endOfFile) {
            return "";
        }

        String currentElementName = nextElementName;

        if (scanner.hasNext()) {
            nextElementName = scanner.next();
        } else {
            nextElementName = "";
            endOfFile = true;
            scanner.close();
        }

        return currentElementName;
    }

    public boolean isEndOfFile() {
        return endOfFile;
    }
}
