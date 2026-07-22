package com.backend.utk.pdfProcessor.model;

import com.backend.utk.pdfProcessor.enums.ParagraphType;

import java.util.Map;

public class ParagraphAnnotation {
    private ParagraphType paragraphType;
    private float confidence;
    private String annotator;
    private Map<String, String> attributes;
}

