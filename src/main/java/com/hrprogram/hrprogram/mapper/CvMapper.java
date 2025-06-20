package com.hrprogram.hrprogram.mapper;

import com.hrprogram.hrprogram.entity.CvEntity;
import com.hrprogram.hrprogram.model.dto.CvDto;
import com.hrprogram.hrprogram.model.request.CvRequest;
import com.hrprogram.hrprogram.response.CvResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CvMapper {
    CvMapper INSTANCE = Mappers.getMapper(CvMapper.class);

//    @Mapping(target = "id",ignore = true)
    CvEntity toEntity(CvRequest cvRequest);

//    @Mapping(target = "id", ignore = true)
    CvDto toDto(CvEntity entity);

    CvEntity responseToEntity(CvResponse cvResponse);

    CvResponse entityToResponse(CvEntity cvEntity);

    CvRequest entityToRequest(CvEntity cvEntity);

    CvRequest toRequest(CvResponse cvResponse);

    CvResponse requestToRespone(CvRequest cvRequest);
}
