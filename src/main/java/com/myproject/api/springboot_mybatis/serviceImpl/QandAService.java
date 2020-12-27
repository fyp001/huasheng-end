package com.myproject.api.springboot_mybatis.serviceImpl;

import com.myproject.api.springboot_mybatis.dao.QandAMapper;
import com.myproject.api.springboot_mybatis.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QandAService {
    @Autowired
    QandAMapper qandAMapper;

    public int insertQuestion(Question question){
        return qandAMapper.insertQuestion(question);
    }

    public int deleteQuestion(Integer id){
        return qandAMapper.deleteQuestion(id);
    }

    public int updateQuestion(Integer id,String question_pre,String q_word,String question_aft){
        return qandAMapper.updateQuestion(id,question_pre,q_word,question_aft);
    }

    public List<Question> readAllQuestion(){
        return qandAMapper.readAllQuestion();
    }

}
