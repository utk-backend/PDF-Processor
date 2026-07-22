package com.backend.utk.pdfProcessor.service.renderer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

@Getter
@Setter
@AllArgsConstructor
public class RenderContext {

    private PDDocument document;
    private PDPage page;
    private PDPageContentStream contentStream;
    private float cursorY;
    private PDFont font;
    private RenderPageLayout renderPageLayout;
}