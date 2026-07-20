package com.backend.utk.pdfProcessor.processor;

import com.backend.utk.pdfProcessor.model.PdfProcessingContext;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBufferedFile;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;

public class DocumentLoader implements PdfProcessor {

    @Override
    public void process(PdfProcessingContext context) throws IOException {
        PDDocument document = Loader.loadPDF(new RandomAccessReadBufferedFile(context.getInputFile()));
        context.setSourceDocument(document);
    }
}
