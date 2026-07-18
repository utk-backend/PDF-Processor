package com.backend.utk.pdfProcessor.model;

import lombok.Data;
import org.apache.pdfbox.pdmodel.PDDocument;

@Data
public class PdfProcessingContext {
    private String inputFile;
    private PDDocument sourceDocument;
    private PdfDocument pdfDocument;
}
