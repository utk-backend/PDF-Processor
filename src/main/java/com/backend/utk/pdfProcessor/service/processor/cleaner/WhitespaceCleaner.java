package com.backend.utk.pdfProcessor.service.processor.cleaner;

import com.backend.utk.pdfProcessor.model.PdfPage;
import com.backend.utk.pdfProcessor.model.PdfParagraph;

public class WhitespaceCleaner extends AbstractCleaner {

    private static final String MULTIPLE_SPACES = "[ \\t]+";
    private static final String SPACE_BEFORE_PUNCTUATION = "\\s+([.,!?;:])";
    private static final String NEW_LINE = "\\R+";

    @Override
    public void clean(PdfPage page) {

        for (PdfParagraph paragraph : page.getParagraphs()) {

            String text = paragraph.getText();

            text = text.replaceAll(NEW_LINE, " ");
            text = text.replaceAll(MULTIPLE_SPACES, " ");
            text = text.replaceAll(SPACE_BEFORE_PUNCTUATION, "$1");
            text = text.trim();

            paragraph.setText(text);
        }
    }
}