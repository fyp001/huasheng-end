package com.myproject.api.springboot_mybatis.entity;

public class StaffClient{
    private Integer c_s_id;
    private String staff_account;
    private String staff_name;
    private String staff_phone;
    private String client_id;
    private String project_id;
    private String client_eval_content;
    private String client_eval_score;
    private String client_eval_date;

    public Integer getC_s_id() {
        return c_s_id;
    }

    public void setC_s_id(Integer c_s_id) {
        this.c_s_id = c_s_id;
    }

    public String getStaff_account() {
        return staff_account;
    }

    public void setStaff_account(String staff_account) {
        this.staff_account = staff_account;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public String getStaff_phone() {
        return staff_phone;
    }

    public void setStaff_phone(String staff_phone) {
        this.staff_phone = staff_phone;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getClient_eval_content() {
        return client_eval_content;
    }

    public void setClient_eval_content(String client_eval_content) {
        this.client_eval_content = client_eval_content;
    }

    public String getClient_eval_score() {
        return client_eval_score;
    }

    public void setClient_eval_score(String client_eval_score) {
        this.client_eval_score = client_eval_score;
    }

    public String getClient_eval_date() {
        return client_eval_date;
    }

    public void setClient_eval_date(String client_eval_date) {
        this.client_eval_date = client_eval_date;
    }

    @Override
    public String toString() {
        return "StaffClient{" +
                "c_s_id=" + c_s_id +
                ", staff_account='" + staff_account + '\'' +
                ", staff_name='" + staff_name + '\'' +
                ", staff_phone='" + staff_phone + '\'' +
                ", client_id='" + client_id + '\'' +
                ", project_id='" + project_id + '\'' +
                ", client_eval_content='" + client_eval_content + '\'' +
                ", client_eval_score='" + client_eval_score + '\'' +
                ", client_eval_date='" + client_eval_date + '\'' +
                '}';
    }
}
