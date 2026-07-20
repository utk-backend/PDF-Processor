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
public class PdfPage {
    private int pageNumber;
    private float width;
    private float height;
    private List<PdfCharacter> characters = new ArrayList<>();
    private List<PdfWord> words = new ArrayList<>();
    private List<PdfLine> lines = new ArrayList<>();
    private List<PdfParagraph> paragraphs = new ArrayList<>();
    private PageLayout pageLayout;
}
