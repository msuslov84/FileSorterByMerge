package com.suslov.cft.util;

import com.suslov.cft.adapters.FileReaderAdapter;
import com.suslov.cft.exceptions.ArgsException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mikhail Suslov
 */
public class StringToIntegerConverter {

    public static Map<FileReaderAdapter, Integer> convertTo(Map<FileReaderAdapter, String> mapByString) throws ArgsException {
        HashMap<FileReaderAdapter, Integer> mapByInteger = new HashMap<>();
        for (Map.Entry<FileReaderAdapter, String> entry : mapByString.entrySet()) {
            FileReaderAdapter reader = entry.getKey();
            String element = entry.getValue();
            try {
                mapByInteger.put(reader, Integer.parseInt(element));
            } catch (NumberFormatException exp) {
                // Пропускаем некорректный элемент коллекции, алгоритм продолжает работу
                if (!reader.isEndOfFile()) {
                    mapByString.put(reader, reader.getNextElementName());
                } else {
                    mapByString.remove(reader);
                }
                throw new ArgsException(String.format("Failed to convert element '%s' of the input file '%s' to an integer, the element will be skipped",
                        element, reader.getFileName()));
            }
        }
        return mapByInteger;
    }
}
