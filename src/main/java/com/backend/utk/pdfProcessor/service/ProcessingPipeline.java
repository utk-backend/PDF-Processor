package com.backend.utk.pdfProcessor.service;

import com.backend.utk.pdfProcessor.exception.PdfProcessingException;
import com.backend.utk.pdfProcessor.model.PdfProcessingContext;
import com.backend.utk.pdfProcessor.service.processor.PdfProcessor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProcessingPipeline {
    private final List<PdfProcessor> processors = new ArrayList<>();

    public ProcessingPipeline addProcessor(PdfProcessor processor) {
        processors.add(processor);
        return this;
    }

    public void execute(PdfProcessingContext context) {
        for (PdfProcessor processor : processors) {
            try {
                processor.process(context);
            } catch (IOException ex) {
                log.error("Error occurred while running the processor: {}", processor.getClass().getName());
                throw new PdfProcessingException(ex);
            }
        }
    }
}
