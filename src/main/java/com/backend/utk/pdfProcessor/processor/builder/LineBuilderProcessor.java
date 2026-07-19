package com.backend.utk.pdfProcessor.processor.builder;

import com.backend.utk.pdfProcessor.model.PdfPage;
import com.backend.utk.pdfProcessor.model.PdfProcessingContext;
import com.backend.utk.pdfProcessor.processor.PdfProcessor;
import com.backend.utk.pdfProcessor.util.LineBuilder;

public class LineBuilderProcessor implements PdfProcessor {

    @Override
    public void process(PdfProcessingContext context) {
        LineBuilder builder = new LineBuilder();
        for (PdfPage page : context.getPdfDocument().getPages()) {
            builder.build(page);
        }
    }
}