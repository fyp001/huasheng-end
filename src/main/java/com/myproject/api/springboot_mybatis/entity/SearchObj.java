package com.myproject.api.springboot_mybatis.entity;

import java.util.Objects;

public class SearchObj {
    String begin;
    String end;
    String projectType;
    String projectHead;
    String type;
    char if_issued;

    @Override
    public String toString() {
        return "SearchObj{" +
                "begin='" + begin + '\'' +
                ", end='" + end + '\'' +
                ", projectType ='" + projectType+ '\'' +
                ", projectHead='" + projectHead + '\'' +
                ", type='" + type + '\'' +
                ", if_issued=" + if_issued +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchObj searchObj = (SearchObj) o;
        return if_issued == searchObj.if_issued &&
                Objects.equals(begin, searchObj.begin) &&
                Objects.equals(end, searchObj.end) &&
                Objects.equals(projectType, searchObj.projectType) &&
                Objects.equals(projectHead, searchObj.projectHead) &&
                Objects.equals(type, searchObj.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(begin, end, projectType, projectHead, type, if_issued);
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectHead() {
        return projectHead;
    }

    public void setProjectHead(String projectHead) {
        this.projectHead = projectHead;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public char getIf_issued() {
        return if_issued;
    }

    public void setIf_issued(char if_issued) {
        this.if_issued = if_issued;
    }

    public SearchObj(String begin, String end, String projectType, String projectHead, String type, char if_issued) {
        this.begin = begin;
        this.end = end;
        this.projectType = projectType;
        this.projectHead = projectHead;
        this.type = type;
        this.if_issued = if_issued;
    }


}
