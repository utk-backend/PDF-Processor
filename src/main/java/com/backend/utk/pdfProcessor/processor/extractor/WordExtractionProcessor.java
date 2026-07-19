package com.backend.utk.pdfProcessor.processor.extractor;

import com.backend.utk.pdfProcessor.model.PdfPage;
import com.backend.utk.pdfProcessor.model.PdfProcessingContext;
import com.backend.utk.pdfProcessor.processor.PdfProcessor;
import com.backend.utk.pdfProcessor.util.WordBuilder;

import java.io.IOException;

public class WordExtractionProcessor implements PdfProcessor {
    private final WordBuilder wordBuilder;

    public WordExtractionProcessor(WordBuilder wordBuilder) {
        this.wordBuilder = wordBuilder;
    }

    @Override
    public void process(PdfProcessingContext context) throws IOException {
        for(PdfPage page: context.getPdfDocument().getPages()){
            wordBuilder.build(page);
        }
    }
}
