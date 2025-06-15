package com.hrprogram.hrprogram.service;

import com.hrprogram.hrprogram.config.AIClientFeign;
import com.hrprogram.hrprogram.mapper.ApplicantCvMapper;
import com.hrprogram.hrprogram.model.request.AiRequest;
import com.hrprogram.hrprogram.model.request.ApplicantCvRequest;
import com.hrprogram.hrprogram.model.request.CvRequest;
import com.hrprogram.hrprogram.model.request.TrainRequest;
import com.hrprogram.hrprogram.repository.CvRepository;
import com.hrprogram.hrprogram.response.CvResponse;
import com.hrprogram.hrprogram.util.PdfTextExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AiService {
    private final AIClientFeign aiClientFeign;
    private final PdfTextExtractor pdfTextExtractor;
    private final ApplicantCvService applicantCvService;
    private final CvScrollingService cvScrollingService;
    private final CvRepository cvRepository;

    public String pdfAnalise(String path){
        String text = pdfTextExtractor.extarcText(path);
        return aiClientFeign.sendPdfTextToAi(new AiRequest(text));
    }

    public String trainModel(String csvPath){
        return aiClientFeign.trainModel(new TrainRequest("AI-train-key", csvPath));
    }

    public String parseCv(String filePath){
        String text = pdfTextExtractor.extarcText(filePath);
        return aiClientFeign.parseCv(new AiRequest(text));
    }

    public String gptAnalise(CvRequest cvRequest){
        cvRequest.setText(pdfTextExtractor.extarcText(cvRequest.getFilePath()));
        PDDocumentInformation pdfInfo = pdfTextExtractor.getPdfInfo(cvRequest.getFilePath());
        return aiClientFeign.gptAnalise(cvRequest);
    }


    public String gptAnaliseWithKeywords(CvRequest cvRequest, List<String> keyword){
        cvRequest.setText(pdfTextExtractor.extarcText(cvRequest.getFilePath()));
        cvRequest.setKeywords(keyword);
        return aiClientFeign.gptAnalise(cvRequest);
    }



    public void gptAnalisewithDb(List<String> keywords){
        log.info("Action.gptAnalisewithDb.start");
        var cvs = applicantCvService.getAllCvs();
        List<CvResponse> cvResponses = new ArrayList<>();

        for (ApplicantCvRequest request : cvs){
            var cv = ApplicantCvMapper.INSTANCE.aplicantToRequest(request);
            var json = gptAnaliseWithKeywords(cv, keywords);
            var cvResponse =  cvScrollingService.parseJsonRespone(json);

            cvResponses.add(cvResponse);
        }

        for (CvResponse response : cvResponses){
            cvRepository.updateScoresById(response.getTotalPredictedScore(), response.getExperiencePredictedScore(), response.getHardSkillsPredictedScore(),response.getSoftSkillsPredictedScore(),response.getId());
            cvRepository.updateContactInfoById(response.getEmail(), response.getPhone(), response.getId());
        }
        log.info("Action.gptAnalisewithDb.end");
    }




}
