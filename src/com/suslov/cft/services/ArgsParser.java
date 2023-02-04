package com.suslov.cft.services;

import com.suslov.cft.exceptions.ArgsException;
import com.suslov.cft.adapters.FileReaderAdapter;
import com.suslov.cft.adapters.FileWriterAdapter;
import com.suslov.cft.models.Sort;
import com.suslov.cft.models.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mikhail Suslov
 */
public class ArgsParser {
    private Sort sortType;
    private Type elementType;
    private FileWriterAdapter writer;
    private List<FileReaderAdapter> readers;

    public ArgsParser(String[] args) throws ArgsException {
        if (args.length < 3) {
            throw new ArgsException("ОШИБКА: Передано недостаточно входных параметров!");
        }

        int index = 0;
        for (int i = 0; i < 2; i++) {
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
            }
        }

        writer = new FileWriterAdapter(args[index++]);

        readers = new ArrayList<>();
        for (int i = index; i < args.length; i++) {
            try {
                readers.add(new FileReaderAdapter(args[i]));
            } catch (ArgsException exp) {
                // Проблемные файлы просто пропускаем без заверешения программы
                System.out.println(exp.getMessage());
            }
        }
        if (readers.isEmpty()) {
            throw new ArgsException("ОШИБКА: в списке параметров не переданы имена файлов с корректными данными!");
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
