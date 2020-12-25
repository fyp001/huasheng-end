package com.myproject.api.springboot_mybatis.dao;


import com.myproject.api.springboot_mybatis.entity.Question;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QandAMapper {
    int insertQuestion(Question question);
    int deleteQuestion(Integer id);
    int updateQuestion(Integer id,String question_pre,String q_word,String question_aft);
//    Client readClient(int id);
    List<Question> readAllQuestion();
}
