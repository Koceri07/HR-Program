package com.hrprogram.hrprogram.controller;

import com.hrprogram.hrprogram.model.dto.CvDto;
import com.hrprogram.hrprogram.model.request.CvRequest;
import com.hrprogram.hrprogram.response.ApiResponse;
import com.hrprogram.hrprogram.service.CvService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;

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

    @PostMapping("/add")
    public void add(@RequestBody CvRequest cvRequest){
        cvService.addCv(cvRequest);
    }

    @PostMapping
    public void create(@RequestBody CvDto cvDto){
        cvService.createCv(cvDto);
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

    @GetMapping("{id}")
    public ApiResponse getById(@PathVariable Long id){
        return cvService.getCvById(id);
    }

    @GetMapping
    public ApiResponse getAll(){
        return cvService.getAllCvs();
    }

    @DeleteMapping("{id}")
    public void hardDelete(@PathVariable Long id){
        cvService.hardDeleteById(id);
    }

    @PutMapping("{id}")
    public void softDelet(){

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
