package com.suslov.cft.services;

import com.suslov.cft.adapters.FileReaderAdapter;
import com.suslov.cft.adapters.FileWriterAdapter;
import com.suslov.cft.models.Sort;
import com.suslov.cft.models.Type;

import java.util.List;

/**
 * @author Mikhail Suslov
 */
public class FileSorter {
    private final FileWriterAdapter writer;
    private final List<FileReaderAdapter> readers;
    private final Sort sortType;
    private final Type elementType;

    public FileSorter(FileWriterAdapter writer, List<FileReaderAdapter> readers, Sort sortType, Type elementType) {
        this.writer = writer;
        this.readers = readers;
        this.sortType = sortType;
        this.elementType = elementType;
    }
}
