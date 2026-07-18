package com.backend.utk.pdfProcessor.processor.extractor;

import com.backend.utk.pdfProcessor.model.PdfProcessingContext;
import com.backend.utk.pdfProcessor.processor.PdfProcessor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBufferedFile;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;

public class LoadDocumentProcessor implements PdfProcessor {

    @Override
    public void process(PdfProcessingContext context) throws IOException {
        PDDocument document = Loader.loadPDF(new RandomAccessReadBufferedFile(context.getInputFile()));
        context.setSourceDocument(document);
    }
}
