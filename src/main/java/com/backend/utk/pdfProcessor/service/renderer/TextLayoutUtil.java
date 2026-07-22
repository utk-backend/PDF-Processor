package com.backend.utk.pdfProcessor.service.renderer;

import org.apache.pdfbox.pdmodel.font.PDFont;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class TextLayoutUtil {

    public static List<String> wrap(
            String text,
            PDFont font,
            float fontSize,
            float width) throws IOException {

        List<String> lines = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        for (String word : text.split("\\s+")) {
            String candidate =
                    current.isEmpty()
                            ? word
                            : current + " " + word;

            float candidateWidth =
                    font.getStringWidth(candidate)
                            / 1000f
                            * fontSize;

            if (candidateWidth > width) {
                lines.add(current.toString());
                current = new StringBuilder(word);
            } else {
                current = new StringBuilder(candidate);
            }
        }

        if (!current.isEmpty()) {
            lines.add(current.toString());
        }
        return lines;
    }

}
