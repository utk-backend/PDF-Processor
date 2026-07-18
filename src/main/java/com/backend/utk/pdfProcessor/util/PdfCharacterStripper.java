package com.backend.utk.pdfProcessor.util;

import com.backend.utk.pdfProcessor.model.*;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.List;

public class PdfCharacterStripper extends PDFTextStripper {
    private final PdfProcessingContext context;
    private PdfPage currentPage;
    private PdfDocument pdfDocument;
    private int currentPageNumber;


    public PdfCharacterStripper(PdfProcessingContext context) throws IOException {
        this.context = context;
        this.pdfDocument = new PdfDocument();
        context.setPdfDocument(pdfDocument);
    }

    @Override
    protected void startPage(PDPage page) throws IOException {
        super.startPage(page);
        currentPageNumber++;
        currentPage = new PdfPage();
        currentPage.setPageNumber(currentPageNumber);
        currentPage.setWidth(page.getMediaBox().getWidth());
        currentPage.setHeight(page.getMediaBox().getHeight());
        pdfDocument.getPages().add(currentPage);
    }

    @Override
    protected void writeString(String text, List<TextPosition> positions) throws IOException {
        for (TextPosition position : positions) {
            PdfCharacter character = new PdfCharacter();
            String unicode = position.getUnicode();
            if (unicode == null || unicode.isEmpty()) {
                return;
            }
            character.setValue(position.getUnicode().charAt(0));

            BoundingBox boundingBox = new BoundingBox();
            boundingBox.setX(position.getXDirAdj());
            boundingBox.setY(position.getYDirAdj());
            boundingBox.setWidth(position.getWidthDirAdj());
            boundingBox.setHeight(position.getHeightDir());
            character.setBoundingBox(boundingBox);

            FontStyle fontStyle = new FontStyle();
            fontStyle.setFontName(position.getFont().toString());
            fontStyle.setFontSize(position.getFontSize());
            fontStyle.setSpaceWidth(position.getWidthOfSpace());
            character.setFontStyle(fontStyle);

            currentPage.getCharacters().add(character);
        }
    }

    @Override
    protected void endPage(PDPage page) throws IOException {
        super.endPage(page);
    }
}
