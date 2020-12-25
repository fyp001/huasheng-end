package com.myproject.api.springboot_mybatis.entity;

public class Project {
    public int project_id;
    public String project_type;
    public String project_name;
    public String project_client;
    public String project_reportnumber;
    public String project_class;
    public String project_qualitycontroler;
    public String project_head;
    public String project_members;
    public String project_starttime;
    public String project_endtime;
    public String project_comment;
    public double project_assets;

    public double project_audit;
    public double project_reduction;
    public String project_accountant;

    public String getStaff_namej() {
        return staff_namej;
    }

    public void setStaff_namej(String staff_namej) {
        this.staff_namej= staff_namej;
    }

    public String getStaff_namea() {
        return staff_names;
    }

    public void setStaff_names(String staff_names) {
        this.staff_names= staff_names;
    }


    public String project_costengineer;
    public String project_taxaccountant;
    public String project_partner;
    public String project_construction;
    public String project_code;
    public String if_issued;
    public String if_delete;
    public String if_submit;
    public int jing_ban_ren;
    public int shen_he_ren;

    public String staff_namej;
    public String staff_names;


    public String getif_issued() {
        return if_issued;
    }

    public void setif_issued(String if_issued) {
        this.if_issued = if_issued;
    }

    public String getif_delete() {
        return if_delete;
    }

    public void setif_delete(String if_delete) {
        this.if_delete = if_delete;
    }

    public String getif_submit() {
        return if_submit;
    }

    public void setif_submit(String if_submit) {
        this.if_submit = if_submit;
    }

    public int getjing_ban_ren() {
        return jing_ban_ren;
    }

    public void setjing_ban_ren(int jing_ban_ren) {
        this.jing_ban_ren = jing_ban_ren;
    }

    public int getshen_he_ren() {
        return shen_he_ren;
    }

    public void setshen_he_ren(int shen_he_ren) {
        this.shen_he_ren = shen_he_ren;
    }
    /*   public String user_name;
       public String password;

       public String getuser_name() {
           return user_name;
       }

       public void setuser_name(String user_name) {
           this.user_name = user_name;
       }

       public String getpassword() {
           return password;
       }

       public void setpassword(String password) {
           this.password = password;
       }
       */
    public int getproject_id() {
        return project_id;
    }

    public void setproject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getproject_name() {
        return project_name;
    }

    public void setproject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_client() {
        return project_client;
    }

    public void setProject_client(String project_client) {
        this.project_client = project_client;
    }


    public String getProject_reportnumber() {
        return project_reportnumber;
    }

    public void setProject_reportnumber(String project_reportnumber) {
        this.project_reportnumber = project_reportnumber;
    }

    public String getproject_type() {
        return project_type;
    }

    public void setproject_type(String project_type) {
        this.project_type = project_type;
    }

    public String getproject_class() {
        return project_class;
    }

    public void setproject_class(String project_class) {
        this.project_class = project_class;

    }
    public String getProject_qualitycontroler() {
        return project_qualitycontroler;
    }

    public void setProject_qualitycontroler(String project_qualitycontroler) {
        this.project_qualitycontroler = project_qualitycontroler;

    }

    public String getProject_head() {
        return project_head;
    }

    public void setProject_head(String project_head) {
        this.project_head = project_head;
    }

    public String getProject_members() {
        return project_members;
    }

    public void setProject_members(String project_members) {
        this.project_members = project_members;
    }

    public String getProject_comment() {
        return project_comment;
    }

    public void setProject_comment(String project_comment) {
        this.project_comment = project_comment;
    }

    public double getProject_assets() {
        return project_assets;
    }

    public void setProject_assets(double project_assets) {
        this.project_assets = project_assets;
    }

    public double getProject_audit() {
        return project_audit;
    }

    public void setProject_audit(double project_audit) {
        this.project_audit = project_audit;
    }

    public double getProject_reduction() {
        return project_reduction;
    }

    public void setProject_reduction(double project_reduction) {
        this.project_reduction = project_reduction;
    }

    public String getProject_accountant() {
        return project_accountant;
    }

    public void setProject_accountant(String project_accountant) {
        this.project_accountant = project_accountant;
    }

    public String getProject_costengineer() {
        return project_costengineer;
    }

    public void setProject_costengineer(String project_costengineer) {
        this.project_costengineer = project_costengineer;
    }

    public String getProject_taxaccountant() {
        return project_taxaccountant;
    }

    public void setProject_taxaccountant(String project_taxaccountant) {
        this.project_taxaccountant = project_taxaccountant;
    }


    public String getProject_partner() {
        return project_partner;
    }

    public void setProject_partner(String project_partner) {
        this.project_partner = project_partner;
    }

    public String getProject_construction() {
        return project_construction;
    }

    public void setProject_construction(String project_construcion) {
        this.project_construction = project_construcion;
    }

    public String getProject_code() {
        return project_code;
    }

    public void setProject_code(String project_code) {
        this.project_code = project_code;
    }

    public String getProject_starttime() {
        return project_starttime;
    }

    public void setProject_starttime(String project_starttime) {
        this.project_starttime = project_starttime;
    }

    public String getProject_endtime() {
        return project_endtime;
    }

    public void setProject_endtime(String project_endtime) {
        this.project_endtime = project_endtime;
    }
}

