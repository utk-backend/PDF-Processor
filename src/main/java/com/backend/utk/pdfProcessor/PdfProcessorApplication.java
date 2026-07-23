package com.backend.utk.pdfProcessor;

import com.backend.utk.pdfProcessor.model.PdfDocument;
import com.backend.utk.pdfProcessor.model.PdfProcessingContext;
import com.backend.utk.pdfProcessor.service.processor.builder.LineBuilder;
import com.backend.utk.pdfProcessor.service.processor.builder.ParagraphBuilder;
import com.backend.utk.pdfProcessor.service.processor.cleaner.MetadataCleaner;
import com.backend.utk.pdfProcessor.service.processor.cleaner.PageNumberCleaner;
import com.backend.utk.pdfProcessor.service.processor.cleaner.UnicodeCleaner;
import com.backend.utk.pdfProcessor.service.processor.cleaner.WhitespaceCleaner;
import com.backend.utk.pdfProcessor.service.processor.extractor.CharacterExtractor;
import com.backend.utk.pdfProcessor.service.processor.DocumentLoader;
import com.backend.utk.pdfProcessor.service.processor.extractor.WordExtractor;
import com.backend.utk.pdfProcessor.service.ProcessingPipeline;
import com.backend.utk.pdfProcessor.service.renderer.PdfRenderer;
import com.backend.utk.pdfProcessor.service.renderer.RenderDocument;
import com.backend.utk.pdfProcessor.service.renderer.RenderDocumentMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootApplication
public class PdfProcessorApplication implements CommandLineRunner {
    private static final String PDF_DOCUMENT = "The Guest List - Lucy Foley.pdf";
    private static final int PAGE_LIMIT = 20;
    public static final String OUTPUT_FILE_NAME = "clean-output.pdf";

    public static void main(String[] args) {
        SpringApplication.run(PdfProcessorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Path pdfPath = Path.of(PDF_DOCUMENT);
        PdfProcessingContext context = new PdfProcessingContext();
        context.setInputFile(pdfPath);

        ProcessingPipeline pipeline = new ProcessingPipeline()
                .addProcessor(new DocumentLoader())
                .addProcessor(new CharacterExtractor())
                .addProcessor(new WordExtractor())
                .addProcessor(new LineBuilder())
                .addProcessor(new ParagraphBuilder())
                .addProcessor(new WhitespaceCleaner())
                .addProcessor(new MetadataCleaner())
                .addProcessor(new UnicodeCleaner())
                .addProcessor(new PageNumberCleaner());


        pipeline.execute(context);
        PdfDocument document = context.getPdfDocument();
        RenderDocument renderDocument = new RenderDocumentMapper().map(document);
        PdfRenderer renderer = new PdfRenderer();

        try (OutputStream output = Files.newOutputStream(Path.of(OUTPUT_FILE_NAME))) {
            renderer.render(renderDocument.getRenderParagraphs(), output);
        }

        context.getSourceDocument().close();
    }
}
