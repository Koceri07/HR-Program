package com.hrprogram.hrprogram.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private String text;
    private String code;
    private Object data;

    public ApiResponse(Object data){
        code = "200";
        text = "successfully";
        this.data = data;
    }

    public void unSuccsessfully(Object data){
        setCode("500");
        setText("Bad Request");
        setData(data);
    }
}
