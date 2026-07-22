package com.backend.utk.pdfProcessor.service.processor.extractor;

import com.backend.utk.pdfProcessor.model.PdfProcessingContext;
import com.backend.utk.pdfProcessor.service.processor.PdfProcessor;
import com.backend.utk.pdfProcessor.util.PdfCharacterStripper;

import java.io.IOException;

public class CharacterExtractor implements PdfProcessor {
    @Override
    public void process(PdfProcessingContext context) throws IOException {
        PdfCharacterStripper stripper = new PdfCharacterStripper(context);
        stripper.getText(context.getSourceDocument());
    }
}
