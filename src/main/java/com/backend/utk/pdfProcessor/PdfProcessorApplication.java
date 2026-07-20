package com.backend.utk.pdfProcessor;

import com.backend.utk.pdfProcessor.model.PdfDocument;
import com.backend.utk.pdfProcessor.model.PdfPage;
import com.backend.utk.pdfProcessor.model.PdfProcessingContext;
import com.backend.utk.pdfProcessor.processor.builder.LineBuilder;
import com.backend.utk.pdfProcessor.processor.builder.ParagraphBuilder;
import com.backend.utk.pdfProcessor.processor.cleaner.MetadataCleaner;
import com.backend.utk.pdfProcessor.processor.cleaner.UnicodeCleaner;
import com.backend.utk.pdfProcessor.processor.cleaner.WhitespaceCleaner;
import com.backend.utk.pdfProcessor.processor.extractor.CharacterExtractor;
import com.backend.utk.pdfProcessor.processor.DocumentLoader;
import com.backend.utk.pdfProcessor.processor.extractor.WordExtractor;
import com.backend.utk.pdfProcessor.service.ProcessingPipeline;
import com.backend.utk.pdfProcessor.util.DebugLogger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Path;

@SpringBootApplication
public class PdfProcessorApplication implements CommandLineRunner {
    private static final String PDF_DOCUMENT = "The Ultimate Hitchhikers Guide tothe Galaxy Omnibus - Douglas Adams.pdf";
    private static final int PAGE_LIMIT = 20;

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
                .addProcessor(new UnicodeCleaner());


        pipeline.execute(context);

        PdfDocument document = context.getPdfDocument();

        for (PdfPage page : document.getPages()) {
            if (page.getPageNumber() > PAGE_LIMIT) {
                break;
            }
            DebugLogger.logPageDetails(page);
        }
        context.getSourceDocument().close();
    }
}
