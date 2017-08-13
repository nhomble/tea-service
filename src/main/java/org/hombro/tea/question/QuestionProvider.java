package org.hombro.tea.question;

import io.vertx.core.json.Json;
import org.hombro.tea.question.code.CodeAnswer;
import org.hombro.tea.question.code.CodeAnswerResult;
import org.hombro.tea.question.code.CodingQuestion;
import org.hombro.tea.question.code.Language;
import org.hombro.tea.util.IOHelper;

import java.util.Arrays;
import java.util.List;

/**
 * Created by nicolas on 8/12/2017.
 */
public class QuestionProvider {

    public List<String> getQuestions() {
        return Arrays.asList("test");
    }

    public CodingQuestion getQuestionInfo(Language language, String name) {
        String questionString = IOHelper.getQuestionFromFile(language.toString() + "/" + name);
        return Json.decodeValue(questionString, CodingQuestion.class);
    }

    public CodeAnswerResult testAnswer(String content, Language language, String name) {
        CodeAnswer answer = answer = Json.decodeValue(content, CodeAnswer.class)
                .setCodingQuestion(getQuestionInfo(language, name));
        return answer.test();
    }
}
