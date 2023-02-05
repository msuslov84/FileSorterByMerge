package com.suslov.cft.adapters;

import com.suslov.cft.exceptions.ArgsException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Mikhail Suslov
 */
public class FileReaderAdapter {
    private final Scanner scanner;
    private String nextElementName;
    private boolean endOfFile;
    private final String fileName;

    public FileReaderAdapter(String fileName) throws ArgsException {
        this.fileName = fileName;
        try {
            scanner = new Scanner(new File(fileName));
            if (scanner.hasNext()) {
                nextElementName = scanner.next();
            } else {
                endOfFile = true;
                throw new ArgsException(String.format("File '%s' is missing data\n", fileName));
            }
        } catch (FileNotFoundException exp) {
            throw new ArgsException(String.format("File read error in '%s': %s\n", fileName, exp.getMessage()));
        }
    }

    public String getNextElementName() {
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

    public String getFileName() {
        return fileName;
    }
}
