package com.exam.common.other;

/**
 * Created by LX on 2017/8/11.
 * 分类试题各类型数量统计
 */
public class QuestionType {
    private Integer QuestionAll;
    private Integer QuestionSignal;
    private Integer QuestionMultiple;
    private Integer QuestionJudgement;

    public Integer getQuestionAll() {
        return QuestionAll;
    }

    public void setQuestionAll(Integer questionAll) {
        QuestionAll = questionAll;
    }

    public Integer getQuestionSignal() {
        return QuestionSignal;
    }

    public void setQuestionSignal(Integer questionSignal) {
        QuestionSignal = questionSignal;
    }

    public Integer getQuestionMultiple() {
        return QuestionMultiple;
    }

    public void setQuestionMultiple(Integer questionMultiple) {
        QuestionMultiple = questionMultiple;
    }

    public Integer getQuestionJudgement() {
        return QuestionJudgement;
    }

    public void setQuestionJudgement(Integer questionJudgement) {
        QuestionJudgement = questionJudgement;
    }
}
