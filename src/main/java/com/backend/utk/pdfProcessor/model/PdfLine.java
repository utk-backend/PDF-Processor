package com.backend.utk.pdfProcessor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PdfLine {
    private String text;
    private List<PdfWord> words = new ArrayList<>();
    private BoundingBox boundingBox;
}
