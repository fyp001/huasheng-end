package com.myproject.api.springboot_mybatis.entity;

import java.util.Date;

public class Tender {
    private Integer id;
    private String tender_year;
    private String tender_date;
    private String project_name;
    private String audit_type;
    private String tender_block;
    private String tender_block_sum;
    private String tender_offer;
    private String tender_flag;
    private String tender_unit;
    private String tender_share;
    private String tender_ceiling;
    private String tender_discount;
    private String tender_job_type;
    private String tender_type;
    private String tender_specific_type;
    private String province;
    private String city;
    private String tender_agency;
    private String tender_way;
    private String tender_responsibility;
    private String department;
    private String tender_contact;
    private String tender_contact_phone;
    private String tender_agency_contact;
    private String tender_agency_contact_phone;
    private String tender_text;
    private String tender_record_scanning;
    private String project_record;
    private String tender_margin;
    private String tender_account;
    private String bank_deposit;
    public char if_issued;
    public char if_delete;
    public char if_submit;
    public int jing_ban_ren;
    public int shen_he_ren;
    public String staff_namej;
    public String staff_names;
    public String file_location;
    public String txt_name;
    public String file_url;
    private String file_uploaddate;
    private String file_updatedate;


    public Tender(Integer id) {
        this.id = id;
    }

    public Tender(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTender_year() {
        return tender_year;
    }

    public void setTender_year(String tender_year) {
        this.tender_year = tender_year;
    }

    public String getTender_date() {
        return tender_date;
    }

    public void setTender_date(String tender_date) {
        this.tender_date = tender_date;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getAudit_type() {
        return audit_type;
    }

    public void setAudit_type(String audit_type) {
        this.audit_type = audit_type;
    }

    public String getTender_block() {
        return tender_block;
    }

    public void setTender_block(String tender_block) {
        this.tender_block = tender_block;
    }

    public String getTender_block_sum() {
        return tender_block_sum;
    }

    public void setTender_block_sum(String tender_block_sum) {
        this.tender_block_sum = tender_block_sum;
    }

    public String getTender_offer() {
        return tender_offer;
    }

    public void setTender_offer(String tender_offer) {
        this.tender_offer = tender_offer;
    }

    public String getTender_flag() {
        return tender_flag;
    }

    public void setTender_flag(String tender_flag) {
        this.tender_flag = tender_flag;
    }

    public String getTender_unit() {
        return tender_unit;
    }

    public void setTender_unit(String tender_unit) {
        this.tender_unit = tender_unit;
    }

    public String getTender_share() {
        return tender_share;
    }

    public void setTender_share(String tender_share) {
        this.tender_share = tender_share;
    }

    public String getTender_ceiling() {
        return tender_ceiling;
    }

    public void setTender_ceiling(String tender_ceiling) {
        this.tender_ceiling = tender_ceiling;
    }

    public String getTender_discount() {
        return tender_discount;
    }

    public void setTender_discount(String tender_discount) {
        this.tender_discount = tender_discount;
    }

    public String getTender_job_type() {
        return tender_job_type;
    }

    public void setTender_job_type(String tender_job_type) {
        this.tender_job_type = tender_job_type;
    }

    public String getTender_type() {
        return tender_type;
    }

    public void setTender_type(String tender_type) {
        this.tender_type = tender_type;
    }

    public String getTender_specific_type() {
        return tender_specific_type;
    }

    public void setTender_specific_type(String tender_specific_type) {
        this.tender_specific_type = tender_specific_type;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTender_agency() {
        return tender_agency;
    }

    public void setTender_agency(String tender_agency) {
        this.tender_agency = tender_agency;
    }

    public String getTender_way() {
        return tender_way;
    }

    public void setTender_way(String tender_way) {
        this.tender_way = tender_way;
    }

    public String getTender_responsibility() {
        return tender_responsibility;
    }

    public void setTender_responsibility(String tender_responsibility) {
        this.tender_responsibility = tender_responsibility;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTender_contact() {
        return tender_contact;
    }

    public void setTender_contact(String tender_contact) {
        this.tender_contact = tender_contact;
    }

    public String getTender_contact_phone() {
        return tender_contact_phone;
    }

    public void setTender_contact_phone(String tender_contact_phone) {
        this.tender_contact_phone = tender_contact_phone;
    }

    public String getTender_agency_contact() {
        return tender_agency_contact;
    }

    public void setTender_agency_contact(String tender_agency_contact) {
        this.tender_agency_contact = tender_agency_contact;
    }

    public String getTender_agency_contact_phone() {
        return tender_agency_contact_phone;
    }

    public void setTender_agency_contact_phone(String tender_agency_contact_phone) {
        this.tender_agency_contact_phone = tender_agency_contact_phone;
    }

    public String getTender_text() {
        return tender_text;
    }

    public void setTender_text(String tender_text) {
        this.tender_text = tender_text;
    }

    public String getTender_record_scanning() {
        return tender_record_scanning;
    }

    public void setTender_record_scanning(String tender_record_scanning) {
        this.tender_record_scanning = tender_record_scanning;
    }

    public String getProject_record() {
        return project_record;
    }

    public void setProject_record(String project_record) {
        this.project_record = project_record;
    }

    public String getTender_margin() {
        return tender_margin;
    }

    public void setTender_margin(String tender_margin) {
        this.tender_margin = tender_margin;
    }

    public String getTender_account() {
        return tender_account;
    }

    public void setTender_account(String tender_account) {
        this.tender_account = tender_account;
    }

    public String getBank_deposit() {
        return bank_deposit;
    }

    public void setBank_deposit(String bank_deposit) {
        this.bank_deposit = bank_deposit;
    }

    public char getIf_issued() {
        return if_issued;
    }

    public void setIf_issued(char if_issued) {
        this.if_issued = if_issued;
    }

    public char getIf_delete() {
        return if_delete;
    }

    public void setIf_delete(char if_delete) {
        this.if_delete = if_delete;
    }

    public char getIf_submit() {
        return if_submit;
    }

    public void setIf_submit(char if_submit) {
        this.if_submit = if_submit;
    }

    public int getJing_ban_ren() {
        return jing_ban_ren;
    }

    public void setJing_ban_ren(int jing_ban_ren) {
        this.jing_ban_ren = jing_ban_ren;
    }

    public int getShen_he_ren() {
        return shen_he_ren;
    }

    public void setShen_he_ren(int shen_he_ren) {
        this.shen_he_ren = shen_he_ren;
    }

    public String getFile_location() {
        return file_location;
    }

    public void setFile_location(String file_location) {
        this.file_location = file_location;
    }

    public String getTxt_name() {
        return txt_name;
    }

    public void setTxt_name(String txt_name) {
        this.txt_name = txt_name;
    }

    public String getFile_uploaddate() {
        return file_uploaddate;
    }

    public void setFile_uploaddate(String file_uploaddate) {
        this.file_uploaddate = file_uploaddate;
    }

    public String getFile_updatedate() {
        return file_updatedate;
    }

    public void setFile_updatedate(String file_updatedate) {
        this.file_updatedate = file_updatedate;
    }

    public String getStaff_namej() {
        return staff_namej;
    }

    public void setStaff_namej(String staff_namej) {
        this.staff_namej = staff_namej;
    }

    public String getStaff_names() {
        return staff_names;
    }

    public void setStaff_names(String staff_names) {
        this.staff_names = staff_names;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }


}
