package com.hrprogram.hrprogram.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrprogram.hrprogram.exception.OpenAIParseException;
import com.hrprogram.hrprogram.response.CvResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CvScrollingService {
    private final ObjectMapper objectMapper;

    public CvResponse parseJsonRespone(String jsonResponse){
        log.info("Action.parseJsonResponse.start");
        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode resultNode = rootNode.get("result");

            return objectMapper.treeToValue(resultNode, CvResponse.class);
        }
        catch (Exception e){
            throw new OpenAIParseException("Response Can`t Parsed");
        }


    }
}
