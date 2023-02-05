package com.suslov.cft;

import com.suslov.cft.exceptions.ArgsException;
import com.suslov.cft.services.ArgsParser;
import com.suslov.cft.services.FileSorter;

/**
 * @author Mikhail Suslov
 */
public class FileSorterByMerge {

    public static void main(String[] args) {
        // TODO: добавить логи в файл вместо вывода сообщения в консоль
        System.out.println("Старт выполнения программы сортировки...");
        try {
            ArgsParser argsParser = new ArgsParser(args);
            FileSorter fileSorter = new FileSorter(argsParser.getWriter(), argsParser.getReaders(),
                    argsParser.getSortType(), argsParser.getElementType());
            fileSorter.sort();
        } catch (ArgsException exp) {
            // TODO: добавить логи в файл вместо вывода сообщения в консоль
            System.out.println(exp.getMessage());
            return;
        }
        // TODO: добавить логи в файл вместо вывода сообщения в консоль
        System.out.println("Выполнение программы сортировки завершено!");
    }
}
