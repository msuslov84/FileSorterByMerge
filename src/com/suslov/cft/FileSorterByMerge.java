package com.suslov.cft;

import com.suslov.cft.exceptions.ArgsException;
import com.suslov.cft.services.ArgsParser;
import com.suslov.cft.services.FileSorter;

/**
 * @author Mikhail Suslov
 */
public class FileSorterByMerge {

    public static void main(String[] args) {
        try {
            ArgsParser argsParser = new ArgsParser(args);
            FileSorter fileSorter = new FileSorter(argsParser.getWriter(), argsParser.getReaders(),
                    argsParser.getSortType(), argsParser.getElementType());
            // Выполняем сортировку
        } catch (ArgsException exp) {
            System.out.println(exp.getMessage());
            return;
        }
        // Завершаем выполнение программы
    }
}
