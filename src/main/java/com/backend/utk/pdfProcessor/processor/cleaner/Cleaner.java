package com.backend.utk.pdfProcessor.processor.cleaner;

import com.backend.utk.pdfProcessor.model.PdfPage;

public interface Cleaner {
    void clean(PdfPage page);
}
