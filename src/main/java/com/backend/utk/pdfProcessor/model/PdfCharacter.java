package com.backend.utk.pdfProcessor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PdfCharacter {
    private char value;
    private BoundingBox boundingBox;
    private FontStyle fontStyle;
}
