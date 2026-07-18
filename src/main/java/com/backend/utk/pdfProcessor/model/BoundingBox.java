package com.backend.utk.pdfProcessor.model;

import lombok.Data;

@Data
public class BoundingBox {
    private float x;
    private float y;
    private float width;
    private float height;
}
