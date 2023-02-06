package com.suslov.cft.exceptions;

/**
 * @author Mikhail Suslov
 */
public class ArgsException extends RuntimeException {

    public ArgsException(ErrorCode errorCode, String errFileName, String errValue) {
        this(errorCode.getName(errFileName, errValue));
    }

    public ArgsException(ErrorCode errorCode, String errFileName) {
        this(errorCode.getName(errFileName));
    }

    public ArgsException(ErrorCode errorCode) {
        this(errorCode.getName());
    }

    public ArgsException(String message) {
        super(message);
    }

    public enum ErrorCode {
        MISSING_PARAMS("Not enough input parameters passed!\n"),
        EMPTY_FILES("File names with correct data were not passed in the parameter list!\n"),
        EMPTY_FILE("File '%s' is missing data\n"),
        INVALID_SORT("In the file '%1$s' the sorting of the elements is broken, the element '%2$s' will be skipped\n"),
        INVALID_CONVERT("Failed to convert element '%2$s' of the input file '%1$s' to an integer, the element will be skipped\n"),
        INVALID_WRITE("File write error in '%1$s': %2$s\n"),
        INVALID_READ("File read error in '%1$s': %2$s\n");

        private final String name;

        ErrorCode(String name) {
            this.name = name;
        }

        public String getName(String... params) {
            return String.format(name, params);
        }
    }
}
