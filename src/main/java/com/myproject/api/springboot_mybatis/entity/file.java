package com.myproject.api.springboot_mybatis.entity;

import java.util.Date;

public class file {
    private int file_id;
    private String file_type;
    private String file_property;
    private String file_name;
    private String file_version;
    private String file_project;
    private String file_uploaddate;
    private String file_updatedate;
    private String file_location;
    private String file_url;
    private String txt_name;
    private char if_issued;
    private char if_delete;
    private char if_submit;
    private int jing_ban_ren;
    private int shen_he_ren;
    private String operatorname;
    private String checker;
    private double contract_amount;
    public String file_code;
    private String customer_name;
    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }
    public String getTxt_name() {
        return txt_name;
    }
    public void setTxt_name(String txt_name) {
        this.txt_name = txt_name;
    }
    public String getFile_url() {
        return file_url;
    }
    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }
    public int getFile_id() {
        return file_id;
    }
    public void setFile_id(int file_id) {
        this.file_id = file_id;
    }
    public String getFile_type() {
        return file_type;
    }
    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }
    public String getFile_property() { return file_property; }
    public void setFile_property(String file_property) { this.file_property = file_property; }
    public String getFile_name() { return file_name; }
    public void setFile_name(String file_name) { this.file_name = file_name; }
    public String getFile_version() { return file_version; }
    public void setFile_version(String file_version) { this.file_version = file_version; }
    public String getFile_project() { return file_project; }
    public void setFile_project(String file_project) { this.file_project = file_project; }
    public String getFile_uploaddate() { return file_uploaddate; }
    public void setFile_uploaddate(String file_uploaddate) { this.file_uploaddate = file_uploaddate; }
    public String getFile_updatedate() { return file_updatedate; }
    public void setFile_updatedate(String file_updatedate) { this.file_updatedate = file_updatedate; }
    public String getFile_location() { return file_location; }
    public void setFile_location(String file_location) { this.file_location = file_location; }
    public char getIf_issued() { return if_issued; }
    public void setIf_issued(char if_issued) { this.if_issued = if_issued; }
    public char getIf_delete() { return if_delete; }
    public void setIf_delete(char if_delete) { this.if_delete = if_delete; }
    public char getIf_submit() { return if_submit; }
    public void setIf_submit(char if_submit) { this.if_submit = if_submit; }
    public int getJing_ban_ren() { return jing_ban_ren; }
    public void setJing_ban_ren(int jing_ban_ren) { this.jing_ban_ren = jing_ban_ren; }
    public int getShen_he_ren() { return shen_he_ren; }
    public void setShen_he_ren(int shen_he_ren) { this.shen_he_ren = shen_he_ren; }
    public String getOperatorname() { return operatorname; }
    public void setOperatorname(String operatorname) { this.operatorname = operatorname; }
    public String getChecker() { return checker; }
    public void setChecker(String checker) { this.checker = checker; }
    public String getFile_code() { return file_code; }
    public void setFile_code(String file_code) { this.file_code = file_code; }
    public double getContract_amount() { return contract_amount; }
    public void setContract_amount(double contract_amount) { this.contract_amount = contract_amount; }
}