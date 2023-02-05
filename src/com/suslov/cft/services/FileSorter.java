package com.suslov.cft.services;

import com.sun.xml.internal.ws.api.SOAPVersion;
import com.suslov.cft.adapters.FileReaderAdapter;
import com.suslov.cft.adapters.FileWriterAdapter;
import com.suslov.cft.exceptions.ArgsException;
import com.suslov.cft.models.Sort;
import com.suslov.cft.models.Type;
import com.suslov.cft.util.StringToIntegerConverter;

import java.util.*;

/**
 * @author Mikhail Suslov
 */
public class FileSorter {
    private final FileWriterAdapter writer;
    private final Sort sortType;
    private final Type elementType;
    private final Map<FileReaderAdapter, String> nextElementsToSort;

    public FileSorter(FileWriterAdapter writer, List<FileReaderAdapter> readers, Sort sortType, Type elementType) {
        this.writer = writer;
        this.sortType = sortType;
        this.elementType = elementType;
        this.nextElementsToSort = new HashMap<>();

        for (FileReaderAdapter reader : readers) {
            nextElementsToSort.put(reader, reader.getNextElementName());
        }
    }

    public void sort() {
        while (!nextElementsToSort.isEmpty()) {
            if (elementType == Type.INTEGER) {
                Map.Entry<FileReaderAdapter, Integer> result;
                try {
                    if (sortType == Sort.ASC) {
                        result = Collections.min(StringToIntegerConverter.convertTo(nextElementsToSort).entrySet(), Map.Entry.comparingByValue());
                    } else {
                        result = Collections.max(StringToIntegerConverter.convertTo(nextElementsToSort).entrySet(), Map.Entry.comparingByValue());
                    }
                    processComparisonInteger(result);
                } catch (ArgsException exp) {
                    // TODO: добавить логи в файл вместо вывода сообщения в консоль
                    System.out.println(exp.getMessage());
                }
            } else {
                Map.Entry<FileReaderAdapter, String> result;
                if (sortType == Sort.DESC) {
                    result = Collections.min(nextElementsToSort.entrySet(), Map.Entry.comparingByValue());
                } else {
                    result = Collections.max(nextElementsToSort.entrySet(), Map.Entry.comparingByValue());
                }
                processComparisonString(result);
            }
        }
    }

    private void processComparisonInteger(Map.Entry<FileReaderAdapter, Integer> result) {
        int currentValue = result.getValue();
        int lastValue = Integer.parseInt(writer.getLastElement());

        if ((sortType == Sort.ASC && currentValue < lastValue) || (sortType == Sort.DESC && currentValue > lastValue)) {
            throw new ArgsException(String.format("ОШИБКА: в файле %s сбита сортировка элементов, элемент %d будет пропущен",
                    result.getKey().getFileName(), currentValue));
        }

        writer.write(currentValue);
        FileReaderAdapter reader = result.getKey();
        scrollToNextElement(reader);
    }

    private void processComparisonString(Map.Entry<FileReaderAdapter, String> result) {
        String currentValue = result.getValue();
        String lastValue = writer.getLastElement();

        if ((sortType == Sort.ASC && currentValue.compareTo(lastValue) < 0)
                || (sortType == Sort.DESC && currentValue.compareTo(lastValue) > 0)) {
            throw new ArgsException(String.format("ОШИБКА: в файле %s сбита сортировка элементов, элемент %s будет пропущен",
                    result.getKey().getFileName(), currentValue));
        }

        writer.write(currentValue);
        FileReaderAdapter reader = result.getKey();
        scrollToNextElement(reader);
    }

    private void scrollToNextElement(FileReaderAdapter reader) {
        if (reader.isEndOfFile()) {
            nextElementsToSort.remove(reader);
        } else {
            nextElementsToSort.put(reader, reader.getNextElementName());
        }
    }
}
