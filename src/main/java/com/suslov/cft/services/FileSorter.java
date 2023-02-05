package com.suslov.cft.services;

import com.suslov.cft.adapters.FileReaderAdapter;
import com.suslov.cft.adapters.FileWriterAdapter;
import com.suslov.cft.exceptions.ArgsException;
import com.suslov.cft.models.Sort;
import com.suslov.cft.models.Type;
import com.suslov.cft.util.StringToIntegerConverter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Mikhail Suslov
 */
public class FileSorter {
    public static final Logger LOG = Logger.getLogger(FileSorter.class.getName());

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
                    LOG.warning(exp.getMessage());
                }
            } else {
                Map.Entry<FileReaderAdapter, String> result;
                if (sortType == Sort.ASC) {
                    result = Collections.min(nextElementsToSort.entrySet(), Map.Entry.comparingByValue());
                } else {
                    result = Collections.max(nextElementsToSort.entrySet(), Map.Entry.comparingByValue());
                }
                try {
                    processComparisonString(result);
                } catch (ArgsException exp) {
                    LOG.warning(exp.getMessage());
                }
            }
        }
    }

    private void processComparisonInteger(Map.Entry<FileReaderAdapter, Integer> result) {
        int currentValue = result.getValue();
        int lastValue = Integer.parseInt(writer.getLastElement());

        FileReaderAdapter reader = result.getKey();
        if ((sortType == Sort.ASC && currentValue < lastValue) || (sortType == Sort.DESC && currentValue > lastValue)) {
            scrollToNextElement(reader);
            throw new ArgsException(String.format("In the file '%s' the sorting of the elements is broken, the element '%d' will be skipped",
                    result.getKey().getFileName(), currentValue));
        }

        writer.write(currentValue);
        scrollToNextElement(reader);
    }

    private void processComparisonString(Map.Entry<FileReaderAdapter, String> result) {
        String currentValue = result.getValue();
        String lastValue = writer.getLastElement();

        FileReaderAdapter reader = result.getKey();
        if ((sortType == Sort.ASC && lastValue != null && currentValue.compareTo(lastValue) < 0)
                || (sortType == Sort.DESC && currentValue.compareTo(lastValue) > 0)) {
            scrollToNextElement(reader);
            throw new ArgsException(String.format("In the file '%s' the sorting of the elements is broken, the element '%s' will be skipped",
                    result.getKey().getFileName(), currentValue));
        }

        writer.write(currentValue);
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
