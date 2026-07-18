package com.backend.utk.pdfProcessor.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PdfParagraph {
    private List<PdfLine> lines = new ArrayList<>();
    private BoundingBox boundingBox;
}
