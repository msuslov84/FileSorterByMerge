package com.suslov.cft.adapters;

import com.suslov.cft.exceptions.ArgsException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * @author Mikhail Suslov
 */
public class FileReaderAdapter {
    public static final Logger LOG = Logger.getLogger(FileReaderAdapter.class.getName());

    private final Scanner scanner;
    private String nextElementName;
    private boolean endOfFile;
    private final String fileName;

    public FileReaderAdapter(String fileName) throws ArgsException {
        this.fileName = fileName;
        try {
            scanner = new Scanner(new File(fileName));
            if (scanner.hasNextLine()) {
                nextElementName = scanner.nextLine();
                if (nextElementName.contains(" ")) {
                    LOG.warning(String.format("The line '%s' in the file %s contains spaces, it will be skipped.",
                            nextElementName, fileName));
                    getNextElementName();
                }
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

        if (scanner.hasNextLine()) {
            nextElementName = scanner.nextLine();
            if (nextElementName.contains(" ")) {
                LOG.warning(String.format("The line '%s' in the file %s contains spaces, it will be skipped.",
                        nextElementName, fileName));
                getNextElementName();
            }
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
