package com.suslov.cft.exceptions;

import com.suslov.cft.adapters.FileReaderAdapter;

/**
 * @author Mikhail Suslov
 */
public class ArgsException extends RuntimeException {
    private FileReaderAdapter expLocation;

    public ArgsException(String message) {
        super(message);
    }
}
