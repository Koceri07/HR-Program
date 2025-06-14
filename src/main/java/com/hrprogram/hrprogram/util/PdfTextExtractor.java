package com.hrprogram.hrprogram.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

@Slf4j
public class PdfTextExtractor {
    public String extarcText(String pdfFilePath){
        log.info("Action.extarcText.start for file path {}", pdfFilePath);
        //        String text = "";
//        PDDocument document = null;
        try{
            PDDocument document = PDDocument.load(new File(pdfFilePath));

            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            document.close();
            log.info("Action.extractText.end for file path {}", pdfFilePath);
            return text;
        }
        catch (Exception e){
            log.error("Action.extractText.error for file path {}", pdfFilePath);
            return "";
        }
    }

    public PDDocumentInformation getPdfInfo(String filePath){

        try {
            PDDocument document = PDDocument.load(new File(filePath));

            PDDocumentInformation documentInformation = document.getDocumentInformation();
            return documentInformation;

        } catch (IOException e) {
            return null;
        }
    }
}
