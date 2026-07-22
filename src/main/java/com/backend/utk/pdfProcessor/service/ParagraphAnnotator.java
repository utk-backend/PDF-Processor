package com.backend.utk.pdfProcessor.service;

import com.backend.utk.pdfProcessor.model.PdfDocument;

public interface ParagraphAnnotator {
    void annotate(PdfDocument document);
}
