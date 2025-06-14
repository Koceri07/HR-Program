package com.hrprogram.hrprogram.service;

import com.hrprogram.hrprogram.mapper.CvMapper;
import com.hrprogram.hrprogram.model.dto.CvDto;
import com.hrprogram.hrprogram.model.request.CvRequest;
import com.hrprogram.hrprogram.response.ApiResponse;
import com.hrprogram.hrprogram.repository.CvRepository;
import com.hrprogram.hrprogram.response.CvResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CvService {
    private final CvRepository cvRepository;
    private final CvScrollingService cvScrollingService;


    private AiService aiService;

    @Autowired
    public void setAiService(@Lazy AiService aiService){
        this.aiService = aiService;
    }

    public void createCvPdf(MultipartFile file) throws IOException {
        log.info("Action.createCvPdf.start for {}", file.getName());
        Path path = Paths.get("uploads", file.getOriginalFilename());
        CvDto cvDto = new CvDto();
        cvDto.setCvName(file.getName());
        createCv(cvDto);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());
        log.info("Action.createCvPdf.end for {}", file.getName());
    }

    public void addCv(CvRequest cvRequest){
        log.info("Action.addCv.start for file path {}", cvRequest.getFilePath());
        var jsonResponse = aiService.gptAnalise(cvRequest);
        var cvResponse = cvScrollingService.parseJsonRespone(jsonResponse);
        var cvEntity = CvMapper.INSTANCE.responseToEntity(cvResponse);
        cvEntity.setActive(true);
        cvRepository.save(cvEntity);
        log.info("Action.addCv.end for file path {}", cvRequest.getFilePath());
    }



    public ApiResponse filterCvsByTotalScore(){
        log.info("Action.filterCvsByTotalScore.start");
        var cvs = cvRepository.findAll().stream()
                .map(CvMapper.INSTANCE::entityToResponse)
                .sorted(Comparator.comparing(CvResponse::getTotalPredictedScore).reversed())
                .toList();
        ApiResponse apiResponse = new ApiResponse(cvs);
        log.info("Action.filterCvsByTotalScore.end");
        return apiResponse;
    }

    public ApiResponse filterCvsByExperienceScore(){
        log.info("Action.filterCvsByExperienceScore.start");
        var cvs = cvRepository.findAll().stream()
                .map(CvMapper.INSTANCE::entityToResponse)
                .sorted(Comparator.comparing(CvResponse::getExperiencePredictedScore))
                .toList();
        ApiResponse apiResponse = new ApiResponse(cvs);
        log.info("Action.filterCvsByExperienceScore.end");
        return apiResponse;
    }

    public ApiResponse filterCvsByHardSkillsScore(){
        log.info("Action.filterCvsByHardSkillsScore.start");
        var cvs = cvRepository.findAll().stream()
                .map(CvMapper.INSTANCE::entityToResponse)
                .sorted(Comparator.comparing(CvResponse::getHardSkillsPredictedScore))
                .toList();
        ApiResponse apiResponse = new ApiResponse(cvs);
        log.info("Action.filterCvsByHardSkillsScore.end");
        return apiResponse;
    }

    public ApiResponse filterCvsBySoftSkillsScore(){
        log.info("Action.filterCvsBySoftSkillsScore.start");
        var cvs = cvRepository.findAll().stream()
                .map(CvMapper.INSTANCE::entityToResponse)
                .sorted(Comparator.comparing(CvResponse::getSoftSkillsPredictedScore))
                .toList();
        ApiResponse apiResponse = new ApiResponse(cvs);
        log.info("Action.filterCvsBySoftSkillsScore.end");
        return apiResponse;
    }

    public ApiResponse filterCvsByDate(){
        log.info("Action.filterCvsByDate.start");
        var cvs = cvRepository.findAll().stream()
                .map(CvMapper.INSTANCE::entityToResponse)
                .sorted(Comparator.comparing(CvResponse::getCreateAt))
                .toList();
        ApiResponse apiResponse = new ApiResponse(cvs);
        log.info("Action.filterCvsByDate.end");
        return apiResponse;
    }

    public void rejectCv(List<CvResponse> cvResponses){
        log.info("Action.rejectCv.start");
        cvResponses.stream();
    }

//    public ApiResponse getAllCvs(){
//        log.info("Action.getAllCvs.start");
//        var cvs = cvRepository.findAll().stream()
//                .map(CvMapper.INSTANCE::entityToResponse)
//                .toList();
//        ApiResponse apiResponse = new ApiResponse(cvs);
//        log.info("Action.getAllCvs.end");
//        return apiResponse;
//    }

    public void createCv(CvDto cvDto){
        log.info("Action.createCv.start for id {}", cvDto.getId());
        var cvEntity = CvMapper.INSTANCE.toEntity(cvDto);
        cvEntity.setActive(true);
        cvRepository.save(cvEntity);
        log.info("Action.create.start for id {}", cvDto.getId());
    }

    public ApiResponse getCvById(Long id){
        log.info("Action.getCvById.start for id {}", id);
        var cvEntity = cvRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id Not Found"));
        var cvDto = CvMapper.INSTANCE.toDto(cvEntity);
        ApiResponse apiResponse = new ApiResponse(cvDto);
        log.info("Action.getCvById.end for id {}", id);
        return apiResponse;
    }

    public CvDto getCvDtoById(Long id){
        log.info("Action.getCvDtoById.start for id {}", id);
        var entity = cvRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id Not Found"));
        var cvDto = CvMapper.INSTANCE.toDto(entity);
        log.info("Action.getCvDtoById.end for id {}", id);
        return cvDto;
    }

    public ApiResponse getAllCvs(){
        log.info("Action.getAllCvs.start");
        var cvs = cvRepository.findAll()
                .stream()
                .map(CvMapper.INSTANCE::toDto)
                .toList();
        ApiResponse apiResponse = new ApiResponse(cvs);
        log.info("Action.getAllCvs.end");
        return apiResponse;
    }

    public void softDeleteCvById(Long id){
        log.info("Action.softDeleteCvById.start for id {}", id);
        var cvDto = getCvDtoById(id);
        cvDto.setActive(false);
        log.info("Action.softDeleteCvById.end for id {}", id);
    }

    public void hardDeleteById(Long id){
        log.info("Action.deleteById.start for id {}", id);
        cvRepository.deleteById(id);
        log.info("Action.deleteById.end for id {}", id);
    }
}
