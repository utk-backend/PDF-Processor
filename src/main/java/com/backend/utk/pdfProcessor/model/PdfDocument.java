package com.backend.utk.pdfProcessor.model;

import java.util.ArrayList;
import java.util.List;

public class PdfDocument {
    private String filename;
    private int totalPages;
    private List<PdfPage> pages = new ArrayList<>();
}
