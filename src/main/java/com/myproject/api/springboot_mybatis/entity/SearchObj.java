package com.myproject.api.springboot_mybatis.entity;

import java.util.Objects;

public class SearchObj {
    String begin;
    String end;
    String projectClass;
    String projectHead;
    String type;

    @Override
    public String toString() {
        return "SearchObj{" +
                "begin='" + begin + '\'' +
                ", end='" + end + '\'' +
                ", projectClass='" + projectClass + '\'' +
                ", projectHead='" + projectHead + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchObj searchObj = (SearchObj) o;
        return Objects.equals(begin, searchObj.begin) && Objects.equals(end, searchObj.end) && Objects.equals(projectClass, searchObj.projectClass) && Objects.equals(projectHead, searchObj.projectHead) && Objects.equals(type, searchObj.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(begin, end, projectClass, projectHead, type);
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

    public String getProjectClass() {
        return projectClass;
    }

    public void setProjectClass(String projectClass) {
        this.projectClass = projectClass;
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



    public SearchObj(String begin, String end, String projectClass, String projectHead, String type) {
        this.begin = begin;
        this.end = end;
        this.projectClass = projectClass;
        this.projectHead = projectHead;
        this.type = type;
    }


}
