package com.backend.utk.pdfProcessor.service.renderer;

import com.backend.utk.pdfProcessor.model.RenderParagraph;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class PdfRenderer {

    public void render(List<RenderParagraph> paragraphs, OutputStream output) throws IOException {

        PDDocument document = new PDDocument();
        RenderPageLayout layout = RenderPageLayout.PAPERBACK;

        PDRectangle rectangle =
                new PDRectangle(
                        layout.getPageWidth(),
                        layout.getPageHeight());

        PDPage page = new PDPage(rectangle);
        document.addPage(page);
        PDPageContentStream stream = new PDPageContentStream(document, page);
        String fontPath = "src/main/resources/static/NotoSerif-Regular.ttf";
        PDFont font = PDType0Font.load(
                document,
                Files.newInputStream(Path.of(fontPath)));
        RenderContext context =
                new RenderContext(
                        document,
                        page,
                        stream,
                        page.getMediaBox().getHeight() - RenderConstant.TOP_MARGIN,
                        font,
                        layout);

        for (RenderParagraph paragraph : paragraphs) {
            drawParagraph(context, paragraph);
        }

        context.getContentStream().close();
        document.save(output);
        document.close();
    }

    private void drawParagraph(
            RenderContext context,
            RenderParagraph paragraph) throws IOException {

        List<String> lines =
                TextLayoutUtil.wrap(
                        paragraph.text(),
                        context.getFont(),
                        paragraph.paragraphStyle().fontSize(),
                        context.getPage().getMediaBox().getWidth()
                                - RenderConstant.LEFT_MARGIN
                                - RenderConstant.RIGHT_MARGIN);

        ensurePageSpace(context, lines.size());

        for (String line : lines) {
            context.getContentStream().beginText();
            context.getContentStream().setFont(
                    context.getFont(),
                    paragraph.paragraphStyle().fontSize());
            context.getContentStream().newLineAtOffset(
                    RenderConstant.LEFT_MARGIN,
                    context.getCursorY());
            context.getContentStream().showText(line);
            context.getContentStream().endText();
            context.setCursorY(context.getCursorY() - paragraph.paragraphStyle().lineSpacing());
        }

        context.setCursorY(context.getCursorY() - paragraph.paragraphStyle().spacingAfter());
    }

    private void ensurePageSpace(
            RenderContext context,
            int lineCount) throws IOException {

        float needed = lineCount * RenderConstant.LINE_SPACING + RenderConstant.PARAGRAPH_SPACING;

        if (context.getCursorY() - needed > RenderConstant.BOTTOM_MARGIN) {
            return;
        }

        context.getContentStream().close();
        RenderPageLayout layout = RenderPageLayout.PAPERBACK;

        PDRectangle rectangle =
                new PDRectangle(
                        layout.getPageWidth(),
                        layout.getPageHeight());

        PDPage page = new PDPage(rectangle);
        context.getDocument().addPage(page);

        PDPageContentStream stream = new PDPageContentStream(context.getDocument(), page);

        context.setPage(page);
        context.setContentStream(stream);
        context.setCursorY(page.getMediaBox().getHeight() - RenderConstant.TOP_MARGIN);
    }
}