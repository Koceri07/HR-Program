package com.hrprogram.hrprogram.mapper;

import com.hrprogram.hrprogram.entity.RejectMailTemplateEntity;
import com.hrprogram.hrprogram.model.request.RejectMailTemplateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RejectedMailMapper {
    RejectedMailMapper INSTANCE = Mappers.getMapper(RejectedMailMapper.class);

    RejectMailTemplateEntity requestToEntity(RejectMailTemplateRequest rejectMailTemplateRequest);

    RejectMailTemplateRequest entityToRequest(RejectMailTemplateEntity rejectMailTemplateEntity);
}
