package com.backend.utk.pdfProcessor.exception;

public class PdfProcessingException extends RuntimeException{
    public PdfProcessingException(Exception ex){
        super("Error: " + ex.getMessage() + "|" + ex.getStackTrace());
    }
}
