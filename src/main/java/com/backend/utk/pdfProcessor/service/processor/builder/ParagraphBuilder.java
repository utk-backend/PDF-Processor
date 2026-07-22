package com.backend.utk.pdfProcessor.service.processor.builder;

import com.backend.utk.pdfProcessor.model.*;
import com.backend.utk.pdfProcessor.service.processor.PdfProcessor;
import com.backend.utk.pdfProcessor.util.ParagraphBuilderUtil;

import java.io.IOException;

public class ParagraphBuilder implements PdfProcessor {

    @Override
    public void process(PdfProcessingContext context) throws IOException {
        ParagraphBuilderUtil paragraphBuilderUtil = new ParagraphBuilderUtil();
        for(PdfPage page : context.getPdfDocument().getPages()){
            paragraphBuilderUtil.build(page);
        }
    }
}
