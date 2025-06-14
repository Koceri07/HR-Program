package com.hrprogram.hrprogram.controller;

import com.hrprogram.hrprogram.model.request.PdfRequest;
import com.hrprogram.hrprogram.util.PdfTextExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/pdf-extractor")
@RequiredArgsConstructor
public class PdfTextExtractorController {
    private final PdfTextExtractor pdfTextExtractor;

//    @Autowired
//    private PdfTextExtractor pdfTextExtractor;

    @PostMapping()
    public String pdfExtractor(@RequestBody PdfRequest pdfRequest){
        return pdfTextExtractor.extarcText(pdfRequest.getPdfFilePath());
    }
}
