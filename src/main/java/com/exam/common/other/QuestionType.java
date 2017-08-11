package com.exam.common.other;

/**
 * Created by LX on 2017/8/11.
 * 分类试题各类型数量统计
 */
public class QuestionType {
    private int QuestionAll;
    private int QuestionSignal;
    private int QuestionMultiple;
    private int QuestionJudgement;

    public int getQuestionAll() {
        return QuestionAll;
    }

    public void setQuestionAll(int questionAll) {
        QuestionAll = questionAll;
    }

    public int getQuestionSignal() {
        return QuestionSignal;
    }

    public void setQuestionSignal(int questionSignal) {
        QuestionSignal = questionSignal;
    }

    public int getQuestionMultiple() {
        return QuestionMultiple;
    }

    public void setQuestionMultiple(int questionMultiple) {
        QuestionMultiple = questionMultiple;
    }

    public int getQuestionJudgement() {
        return QuestionJudgement;
    }

    public void setQuestionJudgement(int questionJudgement) {
        QuestionJudgement = questionJudgement;
    }
}
