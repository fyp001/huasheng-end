package com.myproject.api.springboot_mybatis.dao;

import com.myproject.api.springboot_mybatis.entity.MyFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileMapper {

    /**
     * 文档/合同查询(全部)
     * 后台发送对应文档类型的请求，后端拉取数据库中的`filetable`
     * 根据请求的文档类型与表中的`file_type`属性匹配
     * 筛选出所有符合类型的文档，并返回对应的条项
     */
    List<MyFile> QueryFile(MyFile f);

    /**
     * 文档合同上传
     * 后台发送上传文档的请求，并将文件以参数方式传入后端，后端检测合法性，
     * 检测完合法性通过后，根据传入的文件参数(比如文档名，文档类型，文档说明)
     * 在数据库`filetable`表中新增一行实例来存放。
     * @return
     */
    void insert(MyFile f);

    /**
     * 文档下载
     * 后台发送需要下载的文档类型和编号给后端
     * 在接收后端的请求后，在数据库的`filetable`表中筛选符合请求文档类型及编号的文档
     * 返回其在数据库中存储的文件存放路径;
     * @return
     */
    List<MyFile> download(MyFile f);



    /**
     * 文档查询(单个)
     * 后台发送对应类型和文档编号给后端
     * 在接收后端的请求后，后端拉取数据库中的`filetable`
     * 根据请求的文档类型及文档编号与表中的`file_type`及’file_id’属性匹配，
     * 筛选出所有符合类型的文档，并返回对应的条项
     * @return
     */
    List<MyFile> SearchFile(MyFile f);



    /**
     * 文档合同修改
     * 修改文档合同中的字段
     * @return
     */
    void update(MyFile f);

    /**
     * 文档列表查询
     * 查询所有文档
     * @return
     */
    List<MyFile> GetAllFile();
    List<MyFile> GetAllCon();
    /**
     * 文档合同删除
     * @return
     */
    void delete(MyFile f);

    /**
     * 经办人获取所有合同列表
     * @return
     */
    List<MyFile> GetAllContract(MyFile f);
    /**
     * 经办人获取所有文档列表
     * @return
     */
    List<MyFile> GetOperator(MyFile f);
    /**
     * 审核人获取所有文档列表
     * @return
     */
    List<MyFile> GetChecker(MyFile f);
    /**
     * 审核人获取所有合同列表
     * @return
     */
    List<MyFile> GetAllContractChecker(MyFile f);
    /**
     * 根据id获取名字
     * @return
     */
    String GetName(int shen_he_ren);
    /**
     * 经办人提交文档/合同
     * @return
     */
    void submitfile(MyFile f);
    /**
     * 审核人审核通过
     * @return
     */
    void checkpass(MyFile f);
    /**
     * 审核人审核退回
     * @return
     */
    void checknotpass(MyFile f);
    /**
     * 审核人删除（无用）
     * @return
     */
    void checkdelete(MyFile f);
    /**
     * 获取所有已提交的合同/文档
     * @return
     */
    List<MyFile> getAllCheckerFile();
}