package com.backend.utk.pdfProcessor.model;

import java.util.ArrayList;
import java.util.List;

public class PdfPage {
    private int pageNumber;
    private float width;
    private float height;
    private List<PdfParagraph> paragraphs = new ArrayList<>();
}
