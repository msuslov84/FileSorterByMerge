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
                    reader.getNextElementName();
                }
                throw new ArgsException(String.format("ОШИБКА: не удалось преобразовать в целое число элемент %s входного файла %s.",
                        element, reader.getFileName()));
            }
        }
        return mapByInteger;
    }
}
