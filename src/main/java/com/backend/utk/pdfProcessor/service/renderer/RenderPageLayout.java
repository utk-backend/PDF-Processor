package com.backend.utk.pdfProcessor.service.renderer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

@Getter
@RequiredArgsConstructor
public enum RenderPageLayout {
    PAPERBACK(
            430f,
            650f,
            50f,
            50f,
            60f,
            60f
    ),

    LETTER(
            PDRectangle.LETTER.getWidth(),
            PDRectangle.LETTER.getHeight(),
            72f,
            72f,
            72f,
            72f
    ),

    A4(
            PDRectangle.A4.getWidth(),
            PDRectangle.A4.getHeight(),
            72f,
            72f,
            72f,
            72f
    );

    private final float pageWidth;
    private final float pageHeight;
    private final float leftMargin;
    private final float rightMargin;
    private final float topMargin;
    private final float bottomMargin;

    public float getContentWidth() {
        return pageWidth - leftMargin - rightMargin;
    }

    public float getContentHeight() {
        return pageHeight - topMargin - bottomMargin;
    }
}
