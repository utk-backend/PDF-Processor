package com.backend.utk.pdfProcessor.util;

import com.backend.utk.pdfProcessor.model.*;

import java.util.ArrayList;
import java.util.List;

public class LineBuilderUtil {
    private static final float LINE_THRESHOLD = 2.0f;
    private static final String SEPARATOR = " ";

    public void build(PdfPage page) {
        List<PdfWord> currentLine = new ArrayList<>();
        PdfWord previousWord = null;
        for (PdfWord current : page.getWords()) {
            if(previousWord != null && startsNewLine(previousWord, current)){
                finishCurrentLine(page, currentLine);
            }
            currentLine.add(current);
            previousWord = current;
        }
        finishCurrentLine(page, currentLine);
    }

    private boolean startsNewLine(PdfWord previous, PdfWord current) {
        float yDifference =
                Math.abs(
                        previous.getBoundingBox().getY()
                                - current.getBoundingBox().getY());

        return yDifference > LINE_THRESHOLD;
    }

    private PdfLine createLine(List<PdfWord> words) {
        PdfLine pdfLine = new PdfLine();
        pdfLine.setWords(new ArrayList<>(words));
        pdfLine.setBoundingBox(buildBoundingBox(words));
        pdfLine.setText(buildText(words));
        return pdfLine;
    }

    private String buildText(List<PdfWord> words) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0 ; i < words.size() ; i++){
            builder.append(words.get(i).getText());
            if(i < words.size() - 1){
                builder.append(SEPARATOR);
            }
        }
        return builder.toString();
    }

    private BoundingBox buildBoundingBox(List<PdfWord> words) {
        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;

        float maxX = Float.MIN_VALUE;
        float maxY = Float.MIN_VALUE;

        for (PdfWord word : words) {
            BoundingBox box = word.getBoundingBox();
            minX = Math.min(minX, box.getX());
            minY = Math.min(minY, box.getY());
            maxX = Math.max(maxX, box.getX() + box.getWidth());
            maxY = Math.max(maxY, box.getY() + box.getHeight());
        }

        BoundingBox lineBox = new BoundingBox();

        lineBox.setX(minX);
        lineBox.setY(minY);
        lineBox.setWidth(maxX - minX);
        lineBox.setHeight(maxY - minY);

        return lineBox;
    }

    private void finishCurrentLine(PdfPage page, List<PdfWord> currentLine) {
        if(currentLine.isEmpty()){
            return;
        }
        PdfLine pdfLine = createLine(currentLine);
        page.getLines().add(pdfLine);
        currentLine.clear();
    }
}
