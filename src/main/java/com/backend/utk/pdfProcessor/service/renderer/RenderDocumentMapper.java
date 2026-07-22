package com.backend.utk.pdfProcessor.service.renderer;

import com.backend.utk.pdfProcessor.model.*;

import java.util.ArrayList;
import java.util.List;

public class RenderDocumentMapper {

    public RenderDocument map(PdfDocument document) {
        List<RenderParagraph> renderParagraphs = new ArrayList<>();
        for (PdfPage page : document.getPages()) {
            for (PdfParagraph paragraph : page.getParagraphs()) {
                renderParagraphs.add(
                        new RenderParagraph(
                                paragraph.getText(),
                                defaultStyle()
                        )
                );
            }
        }
        return new RenderDocument(renderParagraphs);
    }

    private ParagraphStyle defaultStyle() {
        return new ParagraphStyle(
                11f,
                16f,
                10f
        );
    }
}