package com.hrprogram.hrprogram.mapper;

import com.hrprogram.hrprogram.entity.AcceptMailtemplateEntity;
import com.hrprogram.hrprogram.model.request.AcceptMailtemplateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AcceptMailtemplateMapper {
    AcceptMailtemplateMapper INSTANCE = Mappers.getMapper(AcceptMailtemplateMapper.class);

    AcceptMailtemplateEntity requestToEntity(AcceptMailtemplateRequest acceptMailtemplateRequest);

    AcceptMailtemplateRequest entityToRequest(AcceptMailtemplateEntity acceptMailtemplateEntity);

}
