package com.hrprogram.hrprogram.config;

import com.hrprogram.hrprogram.util.PdfTextExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class PdfConfig {

    @Bean
    public PdfTextExtractor pdfTextExtractor(){
        return new PdfTextExtractor();
    }
}
