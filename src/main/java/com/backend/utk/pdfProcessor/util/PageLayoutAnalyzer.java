package com.backend.utk.pdfProcessor.util;

import com.backend.utk.pdfProcessor.model.PageLayout;
import com.backend.utk.pdfProcessor.model.PdfLine;
import com.backend.utk.pdfProcessor.model.PdfPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageLayoutAnalyzer {

    public static PageLayout analyze(PdfPage page) {
        PageLayout pageLayout = new PageLayout();
        pageLayout.setNormalLineSpacing(calculateNormalLineSpacing(page));
        pageLayout.setCommonLeftMargin(calculateCommonLeftMargin(page));
        return pageLayout;
    }

    private static float calculateNormalLineSpacing(PdfPage page) {
        List<PdfLine> lines = page.getLines();
        if (lines.size() < 2) {
            return 0;
        }
        Map<Integer, Integer> frequency = new HashMap<>();
        for (int i = 1; i < lines.size(); i++) {
            PdfLine previous = lines.get(i - 1);
            PdfLine current = lines.get(i);
            float gap = Math.abs(
                    current.getBoundingBox().getY()
                            - previous.getBoundingBox().getY());
            int roundedGap = Math.round(gap);
            frequency.merge(roundedGap, 1, Integer::sum);
        }

        return frequency.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(0);
    }

    private static float calculateCommonLeftMargin(PdfPage page) {
        List<PdfLine> lines = page.getLines();
        if (lines.isEmpty()) {
            return 0;
        }
        Map<Integer, Integer> frequency = new HashMap<>();
        for (PdfLine line : lines) {
            int x = Math.round(line.getBoundingBox().getX());
            frequency.merge(x, 1, Integer::sum);
        }
        return frequency.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(0);
    }
}
