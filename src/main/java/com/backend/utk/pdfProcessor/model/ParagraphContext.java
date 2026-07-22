package com.backend.utk.pdfProcessor.model;

import com.backend.utk.pdfProcessor.enums.TextAlignment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ParagraphContext {
    private TextAlignment textAlignment; // LEFT, RIGHT, CENTER
    private FontStyling fontStyling; // BOLD, ITALIC, UNDERLINE
    private float fontSize;
    private int lineCount;
    private int pageNumber;
}
