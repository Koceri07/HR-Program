package com.hrprogram.hrprogram.config;


import com.hrprogram.hrprogram.model.request.AiRequest;
import com.hrprogram.hrprogram.model.request.CvRequest;
import com.hrprogram.hrprogram.model.request.TrainRequest;
import com.hrprogram.hrprogram.response.CvResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "aiClient", url = "http://localhost:5001")
public interface AIClientFeign {

    @PostMapping(value = "/analise_cv", consumes = "application/json")
    String sendPdfTextToAi(@RequestBody AiRequest aiRequest);

    @PostMapping(value = "/train", consumes = "application/json")
    String trainModel(@RequestBody TrainRequest trainRequest);

    @PostMapping(value = "/parse_cv", consumes = "application/json")
    String parseCv(@RequestBody AiRequest aiRequest);

    @PostMapping(value = "/gpt", consumes = "application/json")
    String gptAnalise(@RequestBody CvRequest cvRequest);
}
