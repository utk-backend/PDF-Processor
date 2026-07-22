package com.backend.utk.pdfProcessor.service.processor;

import com.backend.utk.pdfProcessor.model.PdfProcessingContext;

import java.io.IOException;

public interface PdfProcessor {
    void process(PdfProcessingContext context) throws IOException;
}
