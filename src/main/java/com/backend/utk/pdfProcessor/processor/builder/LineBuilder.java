package com.backend.utk.pdfProcessor.processor.builder;

import com.backend.utk.pdfProcessor.model.PdfPage;
import com.backend.utk.pdfProcessor.model.PdfProcessingContext;
import com.backend.utk.pdfProcessor.processor.PdfProcessor;
import com.backend.utk.pdfProcessor.util.LineBuilderUtil;

public class LineBuilder implements PdfProcessor {

    @Override
    public void process(PdfProcessingContext context) {
        LineBuilderUtil builder = new LineBuilderUtil();
        for (PdfPage page : context.getPdfDocument().getPages()) {
            builder.build(page);
        }
    }
}