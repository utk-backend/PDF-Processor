package com.backend.utk.pdfProcessor.service.processor.cleaner;

import com.backend.utk.pdfProcessor.model.PdfPage;
import com.backend.utk.pdfProcessor.model.PdfParagraph;

import java.text.Normalizer;

public class UnicodeCleaner extends AbstractCleaner {

    @Override
    public void clean(PdfPage page) {
        for (PdfParagraph paragraph : page.getParagraphs()) {
            String normalizedText = Normalizer.normalize(
                    paragraph.getText(),
                    Normalizer.Form.NFKC
            );
            paragraph.setText(normalizedText);
        }
    }
}