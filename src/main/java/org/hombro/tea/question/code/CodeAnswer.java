package org.hombro.tea.question.code;

import org.hombro.tea.question.code.test.java.JavaTest;

/**
 * Created by nicolas on 8/12/2017.
 */
public class CodeAnswer {
    private String code;
    private CodingQuestion question;

    public CodeAnswer setCodingQuestion(CodingQuestion question) {
        this.question = question;
        return this;
    }

    public CodingQuestion getCodingQuestion() {
        return question;
    }

    public CodeAnswer setCodeAnswer(String code) {
        this.code = code;
        return this;
    }

    public String getCode() {
        return code;
    }

    public CodeAnswerResult test() {
        switch(question.getLanguage()){
            case JAVA: return JavaTest.INSTANCE.isCorrect(this);
            default: throw new RuntimeException("unhandled language");
        }
    }
}
