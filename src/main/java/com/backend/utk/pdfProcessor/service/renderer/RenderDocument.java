package com.backend.utk.pdfProcessor.service.renderer;

import com.backend.utk.pdfProcessor.model.RenderParagraph;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RenderDocument {
    private List<RenderParagraph> renderParagraphs;

    public RenderDocument(List<RenderParagraph> renderParagraphs) {
        this.renderParagraphs = renderParagraphs;
    }
}
