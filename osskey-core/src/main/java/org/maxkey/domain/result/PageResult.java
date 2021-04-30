package org.maxkey.domain.result;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    // Some example codes here:
    // code == 20000: success
    // code == 50001: invalid access token
    // code == 50002: already login in other place
    // code == 50003: access token expired
    // code == 50004: invalid user (user not exist)
    // code == 50005: username or password is incorrect
    private int code=20000;
    private PageData<T> data;


    private PageResult(){

    }




    public static  <T> PageResult<T> newInstance(){
        return new PageResult<>();
    }

    public static  <T> PageResult<T> newInstance(int code){
        PageResult result =new PageResult<>();
        result.setCode(code);
        return result;
    }

    public static  <T> PageResult<T> newInstance(List<T> data){
        PageResult<T> responseResult =   new PageResult<>();
        PageData<T> pageData  = new PageData<>();
        pageData.setItems(data);
        responseResult.setData(pageData);
        return  responseResult;
    }

    public static  <T> PageResult<T> newInstance(long total,List<T> data){
        PageResult<T> responseResult =   new PageResult<>();
        PageData<T> pageData  = new PageData<>();
        pageData.setItems(data);
        pageData.setTotal(total);
        responseResult.setData(pageData);
        return  responseResult;
    }
}
