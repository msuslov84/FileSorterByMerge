package com.suslov.cft.services;

import com.suslov.cft.adapters.FileReaderAdapter;
import com.suslov.cft.adapters.FileWriterAdapter;
import com.suslov.cft.exceptions.ArgsException;
import com.suslov.cft.models.Sort;
import com.suslov.cft.models.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Mikhail Suslov
 */
public class ArgsParser {
    public static final Logger LOG = Logger.getLogger(ArgsParser.class.getName());

    private Sort sortType = Sort.ASC;
    private Type elementType;
    private final FileWriterAdapter writer;
    private final List<FileReaderAdapter> readers;

    public ArgsParser(String[] args) throws ArgsException {
        if (args.length < 3) {
            throw new ArgsException("Not enough input parameters passed!");
        }

        int index = 0;
        for (int i = 0; i < 3; i++) {
            switch (args[i]) {
                case "-a":
                    sortType = Sort.ASC;
                    break;
                case "-d":
                    sortType = Sort.DESC;
                    break;
                case "-s":
                    elementType = Type.STRING;
                    break;
                case "-i":
                    elementType = Type.INTEGER;
                    break;
                default:
                    index = i;
                    break;
            }
            if (index > 0) {
                break;
            }
        }

        writer = new FileWriterAdapter(args[index++], sortType);

        readers = new ArrayList<>();
        for (int i = index; i < args.length; i++) {
            try {
                readers.add(new FileReaderAdapter(args[i]));
            } catch (ArgsException exp) {
                // Проблемные файлы просто пропускаем без заверешения программы
                LOG.warning(exp.getMessage());
            }
        }
        if (readers.isEmpty()) {
            throw new ArgsException("File names with correct data were not passed in the parameter list!");
        }
    }

    public Sort getSortType() {
        return sortType;
    }

    public Type getElementType() {
        return elementType;
    }

    public FileWriterAdapter getWriter() {
        return writer;
    }

    public List<FileReaderAdapter> getReaders() {
        return readers;
    }
}
