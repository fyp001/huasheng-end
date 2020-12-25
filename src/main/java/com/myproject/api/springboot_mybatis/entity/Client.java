package com.myproject.api.springboot_mybatis.entity;

public class Client {
    private String client_id;
    private String client_name;
    private String client_type;
    private String client_quality;
    private String client_p_type;
    private String client_p_name;
    private String client_p_account;
    private String client_work_address;
    private String client_register_address;
    private String client_email;
    private String client_permission;
    private String client_representative;
    private String client_business;
    private String client_registered_capital;
    private String client_person_name;
    private String client_person_phone;

    public Client(){}

    public Client(String client_id, String client_name, String client_type,
                  String client_quality, String client_p_type, String client_p_name, String client_p_account,
                  String client_work_address, String client_register_address, String client_email, String client_permission,
                  String client_representative, String client_business, String client_registered_capital) {
        this.client_id = client_id;
        this.client_name = client_name;
        this.client_type = client_type;
        this.client_quality = client_quality;
        this.client_p_type = client_p_type;
        this.client_p_name = client_p_name;
        this.client_p_account = client_p_account;
        this.client_work_address = client_work_address;
        this.client_register_address = client_register_address;
        this.client_email = client_email;
        this.client_permission = client_permission;
        this.client_representative = client_representative;
        this.client_business = client_business;
        this.client_registered_capital = client_registered_capital;
    }

    public Client(String client_id,String client_name, String client_type, String client_work_address, String client_representative,
                  String client_business, String client_registered_capital,String client_person_name,String client_person_phone) {
        this.client_id = client_id;
        this.client_name = client_name;
        this.client_type = client_type;
        this.client_work_address = client_work_address;
        this.client_representative = client_representative;
        this.client_business = client_business;
        this.client_registered_capital = client_registered_capital;
        this.client_person_name=client_person_name;
        this.client_person_phone=client_person_phone;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getClient_type() {
        return client_type;
    }

    public void setClient_type(String client_type) {
        this.client_type = client_type;
    }

    public String getClient_quality() {
        return client_quality;
    }

    public void setClient_quality(String client_quality) {
        this.client_quality = client_quality;
    }

    public String getClient_p_type() {
        return client_p_type;
    }

    public void setClient_p_type(String client_p_type) {
        this.client_p_type = client_p_type;
    }

    public String getClient_p_name() {
        return client_p_name;
    }

    public void setClient_p_name(String client_p_name) {
        this.client_p_name = client_p_name;
    }

    public String getClient_p_account() {
        return client_p_account;
    }

    public void setClient_p_account(String client_p_account) {
        this.client_p_account = client_p_account;
    }

    public String getClient_work_address() {
        return client_work_address;
    }

    public void setClient_work_address(String client_work_address) {
        this.client_work_address = client_work_address;
    }

    public String getClient_register_address() {
        return client_register_address;
    }

    public void setClient_register_address(String client_register_address) {
        this.client_register_address = client_register_address;
    }

    public String getClient_email() {
        return client_email;
    }

    public void setClient_email(String client_email) {
        this.client_email = client_email;
    }

    public String getClient_permission() {
        return client_permission;
    }

    public void setClient_permission(String client_permission) {
        this.client_permission = client_permission;
    }

    public String getClient_representative() {
        return client_representative;
    }

    public void setClient_representative(String client_representative) {
        this.client_representative = client_representative;
    }

    public String getClient_business() {
        return client_business;
    }

    public void setClient_business(String client_business) {
        this.client_business = client_business;
    }

    public String getClient_registered_capital() {
        return client_registered_capital;
    }

    public void setClient_registered_capital(String client_registered_capital) {
        this.client_registered_capital = client_registered_capital;
    }

    public String getClient_person_name() {
        return client_person_name;
    }

    public void setClient_person_name(String client_person_name) {
        this.client_person_name = client_person_name;
    }

    public String getClient_person_phone() {
        return client_person_phone;
    }

    public void setClient_person_phone(String client_person_phone) {
        this.client_person_phone = client_person_phone;
    }

    @Override
    public String toString() {
        return "Client{" +
                "client_id='" + client_id + '\'' +
                ", client_name='" + client_name + '\'' +
                ", client_type='" + client_type + '\'' +
                ", client_quality='" + client_quality + '\'' +
                ", client_p_type='" + client_p_type + '\'' +
                ", client_p_name='" + client_p_name + '\'' +
                ", client_p_account='" + client_p_account + '\'' +
                ", client_work_address='" + client_work_address + '\'' +
                ", client_register_address='" + client_register_address + '\'' +
                ", client_email='" + client_email + '\'' +
                ", client_permission='" + client_permission + '\'' +
                ", client_representative='" + client_representative + '\'' +
                ", client_business='" + client_business + '\'' +
                ", client_registered_capital='" + client_registered_capital + '\'' +
                ", client_person_name='" + client_person_name + '\'' +
                ", client_person_phone='" + client_person_phone + '\'' +
                '}';
    }
}
