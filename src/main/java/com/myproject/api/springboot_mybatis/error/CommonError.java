package com.myproject.api.springboot_mybatis.error;

//定义一个通用的错误处理接口
public interface CommonError {

    public int getErrCode();

    public String getErrMsg();

    //自定义错误信息
    public CommonError setErrMsg(String errMsg);

}
