package com.hrprogram.hrprogram.controller;

import com.hrprogram.hrprogram.model.request.CvRequest;
import com.hrprogram.hrprogram.model.request.PdfRequest;
import com.hrprogram.hrprogram.repository.CvRepository;
import com.hrprogram.hrprogram.response.CvResponse;
import com.hrprogram.hrprogram.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/ai")
@RequiredArgsConstructor
public class AiController {
    private final AiService aiService;

    //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


    @PostMapping()
    public String pdfAnalise(@RequestBody PdfRequest pdfRequest){
        return aiService.pdfAnalise(pdfRequest.getPdfFilePath());
    }

    @PostMapping("/train")
    public String aiTrain(@RequestBody String csvPath){
        return aiService.trainModel(csvPath);
    }

    @PostMapping("/parse_cv")
    public String parseCv(@RequestBody PdfRequest pdfRequest){
        return aiService.parseCv(pdfRequest.getPdfFilePath());
    }


    @PostMapping("/gpt")
    public String gptAnalise(@RequestBody CvRequest cvRequest){
        return aiService.gptAnalise(cvRequest);
    }

    @PostMapping("gpt/all")
    public List<CvResponse> gptAnaliseWithDb(@RequestBody List<String> keywords){
        return aiService.gptAnalisewithDb(keywords);
    }
}
