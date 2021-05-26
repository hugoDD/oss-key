package org.maxkey.domain.result;

import lombok.Data;
import org.maxkey.web.WebContext;

@Data
public class ResponseResult<T> {
    // Some example codes here:
    // code == 20000: success
    // code == 50001: invalid access token
    // code == 50002: already login in other place
    // code == 50003: access token expired
    // code == 50004: invalid user (user not exist)
    // code == 50005: username or password is incorrect

    // code == 50201: 更新不成功
    private int code=20000;
    private T data;

    private ResponseResult(){

    }


    public static  <T> ResponseResult<T> newInstance(){
        return new ResponseResult<>();
    }

    public static  <T> ResponseResult<T> newInstance(int code){
        ResponseResult responseResult = new ResponseResult<>();
        responseResult.setCode(code);
        return responseResult;
    }

    public static  <T> ResponseResult<T> newInstance(T data){
        ResponseResult<T> responseResult =   new ResponseResult<>();
        responseResult.setData(data);
        return  responseResult;
    }

    public static  <T> ResponseResult<T> newInstance(int code ,T data){
        ResponseResult<T> responseResult =   new ResponseResult<>();
        responseResult.setCode(code);
        responseResult.setData(data);
        return  responseResult;
    }

    public static ResponseResult<String> newI18nInstance(String i18nCode){
        ResponseResult<String> responseResult =   new ResponseResult<>();
        String message = WebContext.getI18nValue(i18nCode);
        responseResult.setData(message);
        return  responseResult;
    }

    public static ResponseResult<String> newI18nInstance(int code,String i18nCode){
        ResponseResult<String> responseResult =   new ResponseResult<>();
        String message = WebContext.getI18nValue(i18nCode);
        responseResult.setCode(code);
        responseResult.setData(message);
        return  responseResult;
    }


}
