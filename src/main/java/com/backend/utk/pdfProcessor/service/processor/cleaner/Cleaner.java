package com.backend.utk.pdfProcessor.service.processor.cleaner;

import com.backend.utk.pdfProcessor.model.PdfPage;

public interface Cleaner {
    void clean(PdfPage page);
}
