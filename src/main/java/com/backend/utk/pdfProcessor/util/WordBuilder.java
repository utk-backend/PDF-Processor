package com.backend.utk.pdfProcessor.util;

import com.backend.utk.pdfProcessor.model.BoundingBox;
import com.backend.utk.pdfProcessor.model.PdfCharacter;
import com.backend.utk.pdfProcessor.model.PdfPage;
import com.backend.utk.pdfProcessor.model.PdfWord;

import java.util.ArrayList;
import java.util.List;

public class WordBuilder {
    private static final float WHITESPACE_MULTIPLIER = 0.25f;
    private static final float LINE_THRESHOLD = 2.0f;

    public void build(PdfPage page) {
        List<PdfCharacter> currentWord = new ArrayList<>();
        PdfCharacter previous = null;
        for (PdfCharacter current : page.getCharacters()) {
            if (isWhiteSpace(current)) {
                finishCurrentWord(page, currentWord);
                previous = null;
                continue;
            }

            if (previous != null && startsNewWord(previous, current)) {
                page.getWords().add(createWord(currentWord));
                currentWord.clear();
            }
            // Additional heuristics can be added here.

            currentWord.add(current);
            previous = current;
        }
        // Handling the last word
        finishCurrentWord(page, currentWord);
    }

    private boolean isWhiteSpace(PdfCharacter character) {
        return Character.isWhitespace(character.getValue());
    }

    private boolean startsNewWord(PdfCharacter previous, PdfCharacter current) {
        if (isNewLine(previous, current)) {
            return true;
        }
        float gap = calculateGap(previous, current);
        return gap > previous.getFontStyle().getSpaceWidth() * WHITESPACE_MULTIPLIER;
    }

    private boolean isNewLine(PdfCharacter previous, PdfCharacter current) {
        float yDifference =
                Math.abs(
                        previous.getBoundingBox().getY()
                                - current.getBoundingBox().getY());
        return yDifference > LINE_THRESHOLD;
    }

    private float calculateGap(PdfCharacter previous, PdfCharacter current) {
        BoundingBox previousBox = previous.getBoundingBox();
        BoundingBox currentBox = current.getBoundingBox();
        float previousEndX = previousBox.getX() + previousBox.getWidth();
        return currentBox.getX() - previousEndX;
    }

    private PdfWord createWord(List<PdfCharacter> characters) {
        PdfWord word = new PdfWord();
        word.setCharacters(new ArrayList<>(characters));
        word.setText(buildText(characters));
        word.setBoundingBox(buildBoundingBox(characters));
        return word;
    }

    private String buildText(List<PdfCharacter> characters) {
        StringBuilder builder = new StringBuilder();
        for (PdfCharacter character : characters) {
            builder.append(character.getValue());
        }
        return builder.toString();
    }

    private BoundingBox buildBoundingBox(List<PdfCharacter> characters) {

        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;

        float maxX = Float.MIN_VALUE;
        float maxY = Float.MIN_VALUE;

        for (PdfCharacter character : characters) {
            BoundingBox box = character.getBoundingBox();
            minX = Math.min(minX, box.getX());
            minY = Math.min(minY, box.getY());
            maxX = Math.max(maxX, box.getX() + box.getWidth());
            maxY = Math.max(maxY, box.getY() + box.getHeight());
        }

        BoundingBox wordBox = new BoundingBox();

        wordBox.setX(minX);
        wordBox.setY(minY);
        wordBox.setWidth(maxX - minX);
        wordBox.setHeight(maxY - minY);

        return wordBox;
    }

    private void finishCurrentWord(
            PdfPage page,
            List<PdfCharacter> currentWord) {
        if (currentWord.isEmpty()) {
            return;
        }
        page.getWords().add(createWord(currentWord));
        currentWord.clear();
    }

}
