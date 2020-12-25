package com.myproject.api.springboot_mybatis.entity;

public class Question {
    private Integer id;
    private String question_pre;
    private String q_word;
    private String question_aft;

    public Question() {
    }
    public Question(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion_pre() {
        return question_pre;
    }

    public void setQuestion_pre(String question_pre) {
        this.question_pre = question_pre;
    }

    public String getQ_word() {
        return q_word;
    }

    public void setQ_word(String q_word) {
        this.q_word = q_word;
    }

    public String getQuestion_aft() {
        return question_aft;
    }

    public void setQuestion_aft(String question_aft) {
        this.question_aft = question_aft;
    }
}
