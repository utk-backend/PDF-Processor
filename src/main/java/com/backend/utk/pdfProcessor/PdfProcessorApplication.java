package com.backend.utk.pdfProcessor;

import com.backend.utk.pdfProcessor.model.PdfDocument;
import com.backend.utk.pdfProcessor.model.PdfPage;
import com.backend.utk.pdfProcessor.model.PdfProcessingContext;
import com.backend.utk.pdfProcessor.processor.extractor.CharacterExtractionProcessor;
import com.backend.utk.pdfProcessor.service.ProcessingPipeline;
import org.apache.pdfbox.Loader;
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
        context.setSourceDocument(Loader.loadPDF(pdfPath.toFile()));

        ProcessingPipeline pipeline = new ProcessingPipeline()
                .addProcessor(new CharacterExtractionProcessor());

        pipeline.execute(context);
        PdfDocument document = context.getPdfDocument();

        System.out.println("Pages : " + document.getPages().size());

        int pageLimit = 20;
        for (PdfPage page : document.getPages()) {
            if(page.getPageNumber() > pageLimit){
                break;
            }
            System.out.println("--------------------------------");
            System.out.println("Page : " + page.getPageNumber());
            System.out.println("Characters : " + page.getCharacters().size());

            // Print the first 20 characters for verification
            page.getCharacters()
                    .stream()
                    .limit(40)
                    .forEach(character ->
                            System.out.printf("%s (%.2f, %.2f)%n",
                                    character.getValue(),
                                    character.getBoundingBox().getX(),
                                    character.getBoundingBox().getY()));

        }
        context.getSourceDocument().close();
    }
}
