package com.backend.utk.pdfProcessor.model;

import java.util.ArrayList;
import java.util.List;

public class PdfWord {
    private String text;
    private List<PdfCharacter> characters = new ArrayList<>();
    private BoundingBox boundingBox;
}
