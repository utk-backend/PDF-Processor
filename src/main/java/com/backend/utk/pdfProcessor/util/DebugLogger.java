package com.backend.utk.pdfProcessor.util;

import com.backend.utk.pdfProcessor.model.PdfPage;

public class DebugLogger {

    private static final int CHARACTER_LIMIT_PER_PAGE = 40;

    public static void logPageDetails(PdfPage page) {
        System.out.println("\n========================================================");
        System.out.printf(
                "Page: %d | Characters: %d%n",
                page.getPageNumber(),
                page.getCharacters().size()
        );
        System.out.println("========================================================");
        logParagraphs(page);
        System.out.println("========================================================");
    }

    private static void logParagraphs(PdfPage page) {
        System.out.printf("%-5s %-30s %-12s %-12s %-8s %-8s%n",
                "No.", "Word", "X", "Y", "Width", "Height");

        System.out.println("----------------------------------------------------------------------------");

        int index = 1;
        for (var paragraph : page.getParagraphs()) {
            System.out.printf("%-5d %-30s %-12.2f %-12.2f %-8.2f %-8.2f%n",
                    index++,
                    paragraph.getText(),
                    paragraph.getBoundingBox().getX(),
                    paragraph.getBoundingBox().getY(),
                    paragraph.getBoundingBox().getWidth(),
                    paragraph.getBoundingBox().getHeight());
        }
    }

    private static void logLine(PdfPage page) {
        System.out.printf("%-5s %-30s %-12s %-12s %-8s %-8s%n",
                "No.", "Word", "X", "Y", "Width", "Height");

        System.out.println("----------------------------------------------------------------------------");

        int index = 1;
        for (var line : page.getLines()) {
            System.out.printf("%-5d %-30s %-12.2f %-12.2f %-8.2f %-8.2f%n",
                    index++,
                    line.getText(),
                    line.getBoundingBox().getX(),
                    line.getBoundingBox().getY(),
                    line.getBoundingBox().getWidth(),
                    line.getBoundingBox().getHeight());
        }
    }

    private static void logWords(PdfPage page) {

        System.out.printf("%-5s %-30s %-12s %-12s %-8s %-8s%n",
                "No.", "Word", "X", "Y", "Width", "Height");

        System.out.println("----------------------------------------------------------------------------");

        int index = 1;
        for (var word : page.getWords()) {
            System.out.printf("%-5d %-30s %-12.2f %-12.2f %-8.2f %-8.2f%n",
                    index++,
                    word.getText(),
                    word.getBoundingBox().getX(),
                    word.getBoundingBox().getY(),
                    word.getBoundingBox().getWidth(),
                    word.getBoundingBox().getHeight());
        }
    }

    private void logCharacters(PdfPage page) {
        System.out.printf("%-5s %-10s %-12s %-12s%n",
                "No.", "Character", "X", "Y");
        System.out.println("--------------------------------------------------------");

        int index = 1;
        for (var character : page.getCharacters().stream().limit(CHARACTER_LIMIT_PER_PAGE).toList()) {
            System.out.printf("%-5d %-10s %-12.2f %-12.2f%n",
                    index++,
                    printable(String.valueOf(character.getValue())),
                    character.getBoundingBox().getX(),
                    character.getBoundingBox().getY());
        }
    }

    private static String printable(String value) {
        return switch (value) {
            case " " -> "[space]";
            case "\n" -> "[newline]";
            case "\t" -> "[tab]";
            default -> value;
        };
    }
}