package com.backend.utk.pdfProcessor;

import com.backend.utk.pdfProcessor.model.PdfDocument;
import com.backend.utk.pdfProcessor.model.PdfPage;
import com.backend.utk.pdfProcessor.model.PdfProcessingContext;
import com.backend.utk.pdfProcessor.processor.builder.LineBuilderProcessor;
import com.backend.utk.pdfProcessor.processor.extractor.CharacterExtractionProcessor;
import com.backend.utk.pdfProcessor.processor.extractor.LoadDocumentProcessor;
import com.backend.utk.pdfProcessor.processor.extractor.WordExtractionProcessor;
import com.backend.utk.pdfProcessor.service.ProcessingPipeline;
import com.backend.utk.pdfProcessor.util.DebugLogger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Path;

@SpringBootApplication
public class PdfProcessorApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PdfProcessorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Path pdfPath = Path.of("The Ultimate Hitchhikers Guide tothe Galaxy Omnibus - Douglas Adams.pdf");
        PdfProcessingContext context = new PdfProcessingContext();
        context.setInputFile(pdfPath);

        ProcessingPipeline pipeline = new ProcessingPipeline()
                .addProcessor(new LoadDocumentProcessor())
                .addProcessor(new CharacterExtractionProcessor())
                .addProcessor(new WordExtractionProcessor())
                .addProcessor(new LineBuilderProcessor());

        pipeline.execute(context);

        PdfDocument document = context.getPdfDocument();

        System.out.println("Pages : " + document.getPages().size());

        int pageLimit = 20;
        for (PdfPage page : document.getPages()) {
            if (page.getPageNumber() > pageLimit) {
                break;
            }
            DebugLogger.logPageDetails(page);
        }
        context.getSourceDocument().close();
    }
}
