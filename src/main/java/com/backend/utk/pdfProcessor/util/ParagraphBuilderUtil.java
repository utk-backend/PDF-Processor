package com.backend.utk.pdfProcessor.util;

import com.backend.utk.pdfProcessor.model.*;

import java.util.ArrayList;
import java.util.List;

public class ParagraphBuilderUtil {
    private static final float PARAGRAPH_MULTIPLIER = 1.5f;
    private static final float INDENT_THRESHOLD = 10.0f;

    public void build(PdfPage page) {
        PageLayout pageLayout = PageLayoutAnalyzer.analyze(page);
        page.setPageLayout(pageLayout);
        List<PdfLine> currentParagraph = new ArrayList<>();
        PdfLine previous = null;
        for (PdfLine current : page.getLines()) {
            if (previous != null &&
                    startsNewParagraph(previous,
                            current,
                            pageLayout.getNormalLineSpacing(),
                            pageLayout.getCommonLeftMargin())) {

                finishCurrentParagraph(page, currentParagraph);
            }

            currentParagraph.add(current);

            previous = current;
        }

        finishCurrentParagraph(page, currentParagraph);
    }


    private boolean startsNewParagraph(
            PdfLine previous,
            PdfLine current,
            float normalLineSpacing,
            float leftMargin) {

        boolean hasLargeVerticalGap = false;
        if (normalLineSpacing > 0) {
            float gap = Math.abs(
                    current.getBoundingBox().getY()
                            - previous.getBoundingBox().getY());
            hasLargeVerticalGap =
                    gap > normalLineSpacing * PARAGRAPH_MULTIPLIER;
        }
        boolean isIndented = false;
        if (leftMargin > 0) {
            float currentX = current.getBoundingBox().getX();
            isIndented =
                    Math.abs(currentX - leftMargin)
                            > INDENT_THRESHOLD;
        }

        return hasLargeVerticalGap || isIndented;
    }

    private PdfParagraph createParagraph(List<PdfLine> lines) {

        PdfParagraph paragraph = new PdfParagraph();

        paragraph.setLines(new ArrayList<>(lines));
        paragraph.setText(buildText(lines));
        paragraph.setBoundingBox(buildBoundingBox(lines));

        return paragraph;
    }

    private String buildText(List<PdfLine> lines) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < lines.size(); i++) {
            builder.append(lines.get(i).getText());
            if (i < lines.size() - 1) {
                builder.append(System.lineSeparator());
            }
        }
        return builder.toString();
    }

    private BoundingBox buildBoundingBox(List<PdfLine> lines) {

        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;

        float maxX = Float.MIN_VALUE;
        float maxY = Float.MIN_VALUE;

        for (PdfLine line : lines) {
            BoundingBox box = line.getBoundingBox();
            minX = Math.min(minX, box.getX());
            minY = Math.min(minY, box.getY());
            maxX = Math.max(maxX,
                    box.getX() + box.getWidth());
            maxY = Math.max(maxY,
                    box.getY() + box.getHeight());
        }

        BoundingBox paragraphBox = new BoundingBox();

        paragraphBox.setX(minX);
        paragraphBox.setY(minY);

        paragraphBox.setWidth(maxX - minX);
        paragraphBox.setHeight(maxY - minY);

        return paragraphBox;
    }

    private void finishCurrentParagraph(
            PdfPage page,
            List<PdfLine> currentParagraph) {
        if (currentParagraph.isEmpty()) {
            return;
        }
        page.getParagraphs()
                .add(createParagraph(currentParagraph));
        currentParagraph.clear();
    }
}