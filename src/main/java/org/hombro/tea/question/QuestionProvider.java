package org.hombro.tea.question;

import io.vertx.core.json.Json;
import org.hombro.tea.db.FirebaseQuestions;
import org.hombro.tea.db.QuestionStore;
import org.hombro.tea.question.code.CodeAnswer;
import org.hombro.tea.question.code.CodeAnswerResult;
import org.hombro.tea.question.code.CodingQuestion;
import org.hombro.tea.question.code.Language;

import java.util.List;

/**
 * Created by nicolas on 8/12/2017.
 */
public class QuestionProvider {
    private QuestionStore questionStore = new FirebaseQuestions();

    public List<String> getQuestions() {
        return questionStore.getQuestions();
    }

    public CodingQuestion getQuestionInfo(Language language, String name) {
        return questionStore.getQuestionInfo(language, name);
    }

    public CodeAnswerResult testAnswer(String content, Language language, String name) {
        CodeAnswer answer = Json.decodeValue(content, CodeAnswer.class)
                .setCodingQuestion(getQuestionInfo(language, name));
        return answer.test();
    }
}
