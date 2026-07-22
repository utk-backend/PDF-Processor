package com.backend.utk.pdfProcessor.model;

import com.backend.utk.pdfProcessor.enums.ParagraphType;

public record AnnotationResult(
        int id,
        ParagraphType type,
        float confidence
) { }
