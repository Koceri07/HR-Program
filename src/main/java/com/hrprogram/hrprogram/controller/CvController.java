package com.hrprogram.hrprogram.controller;

import com.hrprogram.hrprogram.model.dto.CvDto;
import com.hrprogram.hrprogram.model.request.CvRequest;
import com.hrprogram.hrprogram.response.ApiResponse;
import com.hrprogram.hrprogram.response.CvResponse;
import com.hrprogram.hrprogram.service.CvService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5500")
@RestController
@RequestMapping("/v1/cvs")
@RequiredArgsConstructor
public class CvController {
    private final CvService cvService;

    @PostMapping("/upload")
    public void createPdf(@RequestParam("file")MultipartFile file) throws IOException {
        cvService.createCvPdf(file);
    }

    @PostMapping("/add-ai")
    public void add(@RequestBody CvRequest cvRequest){
        cvService.addCvWithAi(cvRequest);
    }

    @PostMapping
    public void create(@RequestBody CvRequest cvRequest){
        cvService.createCv(cvRequest);
    }

    @PostMapping("/auto-mail")
    public void AutoMail(@RequestBody List<CvResponse> cvResponses){
        cvService.autoMail(cvResponses);
    }

    @GetMapping("/filter/total")
    public ApiResponse filterByTotalScore(){
        return cvService.filterCvsByTotalScore();
    }

    @GetMapping("/filter/experience")
    public ApiResponse filterByExperienceScore(){
        return cvService.filterCvsByExperienceScore();
    }

    @GetMapping("/filter/hard-skills")
    public ApiResponse filterByHardSkills(){
        return cvService.filterCvsByHardSkillsScore();
    }

    @GetMapping("/filter/soft-skills")
    public ApiResponse filterBySoftSkills(){
        return cvService.filterCvsBySoftSkillsScore();
    }

    @GetMapping("get/{id}")
    public ApiResponse getById(@PathVariable Long id){
        return cvService.getCvById(id);
    }

    @GetMapping("/get/position")
    public List<CvResponse> getByPosition(@RequestBody String position){
        return cvService.getByPosition(position);
    }

    @GetMapping("/get-all")
    public ApiResponse getAll(){
        return cvService.getAllCvs();
    }

    @DeleteMapping("{id}")
    public void hardDelete(@PathVariable Long id){
        cvService.hardDeleteById(id);
    }

    @PutMapping("{id}")
    public void softDelete(@PathVariable Long id){
        cvService.softDeleteCvById(id);
    }
    @PutMapping("/accept-cvs")
    public void acceptCvs(List<CvResponse> acceptedCvs){
        cvService.acceptCvs(acceptedCvs);
    }

    @Bean
    public WebMvcConfigurer configurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5500")
                        .allowedMethods("POST, GET");
            }
        };
    }

}
