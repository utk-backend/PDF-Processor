package com.backend.utk.pdfProcessor.model;

import lombok.Data;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.nio.file.Path;

@Data
public class PdfProcessingContext {
    private Path inputFile;
    private PDDocument sourceDocument;
    private PdfDocument pdfDocument;
}
