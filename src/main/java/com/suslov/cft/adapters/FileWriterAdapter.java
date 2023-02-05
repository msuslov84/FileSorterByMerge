package com.suslov.cft.adapters;

import com.suslov.cft.models.Sort;
import com.suslov.cft.models.Type;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author Mikhail Suslov
 */
public class FileWriterAdapter {
    public static final Logger LOG = Logger.getLogger(FileWriterAdapter.class.getName());

    private final File file;
    private String lastElement;

    public FileWriterAdapter(String fileName, Sort sortType, Type elementType) {
        this.file = new File(fileName);
        try {
            new FileWriter(file, false).close();
        } catch (IOException e) {
            LOG.warning(String.format("File write error in '%s': %s", file.getName(), e.getMessage()));
        }
        if (elementType == Type.INTEGER) {
            lastElement = String.valueOf(sortType == Sort.ASC ? Integer.MIN_VALUE : Integer.MAX_VALUE);
        }
    }

    public void write(Integer element) {
        write(String.valueOf(element));
    }

    public void write(String element) {
        if (element == null) {
            LOG.warning("A non-existent element was passed for writing!");
            return;
        }

        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(element + "\n");
            writer.flush();
            lastElement = element;
        } catch (IOException e) {
            LOG.warning(String.format("File write error in '%s': %s", file.getName(), e.getMessage()));
        }
    }

    public String getLastElement() {
        return lastElement;
    }
}
