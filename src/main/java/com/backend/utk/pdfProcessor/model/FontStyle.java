package com.backend.utk.pdfProcessor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FontStyle {
    private String fontName;
    private float fontSize;
    private boolean isBold;
    private boolean isItalic;
    private float spaceWidth;
}
