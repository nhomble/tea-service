package org.hombro.tea.db;

import org.hombro.tea.question.code.CodingQuestion;
import org.hombro.tea.question.code.Language;

import java.util.List;

/**
 * Created by nicolas on 9/12/2017.
 */
public interface QuestionStore {
    List<String> getQuestions();
    CodingQuestion getQuestionInfo(Language language, String name);
}
