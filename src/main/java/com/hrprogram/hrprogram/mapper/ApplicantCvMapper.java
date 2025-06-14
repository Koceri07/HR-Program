package com.hrprogram.hrprogram.mapper;

import com.hrprogram.hrprogram.entity.ApplicantCvEntity;
import com.hrprogram.hrprogram.entity.CvEntity;
import com.hrprogram.hrprogram.model.request.ApplicantCvRequest;
import com.hrprogram.hrprogram.model.request.CvRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ApplicantCvMapper {

    ApplicantCvMapper INSTANCE = Mappers.getMapper(ApplicantCvMapper.class);

    ApplicantCvEntity toEntity(ApplicantCvRequest applicantCvRequest);

    ApplicantCvRequest toRequest(ApplicantCvEntity applicantCvEntity);

    CvRequest aplicantToRequest(ApplicantCvRequest applicantCvRequest);

    ApplicantCvRequest requestToApplicant(CvRequest cvRequest);

    CvEntity applicantRequestToCvEntity(ApplicantCvRequest request);



}
