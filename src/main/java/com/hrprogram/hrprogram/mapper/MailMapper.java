package com.hrprogram.hrprogram.mapper;

import com.hrprogram.hrprogram.entity.MailEntity;
import com.hrprogram.hrprogram.model.request.MailRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MailMapper {

    MailMapper INSTANCE = Mappers.getMapper(MailMapper.class);

    MailEntity toEntity(MailRequest mailRequest);

    MailRequest toRequest(MailEntity mailEntity);
}
