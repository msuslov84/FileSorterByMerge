package com.suslov.cft;

import com.suslov.cft.exceptions.ArgsException;
import com.suslov.cft.services.ArgsParser;
import com.suslov.cft.services.FileSorter;

import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * @author Mikhail Suslov
 */
public class FileSorterByMerge {
    public static final Logger LOG = Logger.getLogger(FileSorterByMerge.class.getName());

    public static void main(String[] args) {
        try {
            initializeLogger();
            startApplication(args);
        } catch (IOException exp) {
            System.err.println("Could not setup logger configuration: " + exp);
        }
    }

    public static void initializeLogger() throws IOException {
        LogManager.getLogManager().readConfiguration(FileSorterByMerge.class.getResourceAsStream("/logging.properties"));
    }

    public static void startApplication(String[] args) {
        LOG.info("Start the sorting application...\n");
        try {
            ArgsParser argsParser = new ArgsParser(args);
            FileSorter fileSorter = new FileSorter(argsParser.getWriter(), argsParser.getReaders(),
                    argsParser.getSortType(), argsParser.getElementType());
            fileSorter.sort();
        } catch (ArgsException exp) {
            LOG.warning(exp.getMessage());
            return;
        }
        LOG.info("The sorting application is completed!\n");
    }
}
