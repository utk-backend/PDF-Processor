package com.backend.utk.pdfProcessor.processor.cleaner;

import com.backend.utk.pdfProcessor.model.PdfPage;
import com.backend.utk.pdfProcessor.model.PdfProcessingContext;
import com.backend.utk.pdfProcessor.processor.PdfProcessor;

import java.io.IOException;

public abstract class AbstractCleaner implements Cleaner, PdfProcessor {
    @Override
    public void process(PdfProcessingContext context) throws IOException {
        for (PdfPage page : context.getPdfDocument().getPages()) {
            clean(page);
        }
    }
}
