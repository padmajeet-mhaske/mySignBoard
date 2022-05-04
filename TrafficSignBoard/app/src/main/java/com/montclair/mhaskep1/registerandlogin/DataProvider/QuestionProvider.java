package com.montclair.mhaskep1.registerandlogin.DataProvider;

import com.montclair.mhaskep1.registerandlogin.model.QuestionAnswer;
import com.montclair.mhaskep1.registerandlogin.util.Constants;

import java.util.Arrays;

public class QuestionProvider {
    public static QuestionAnswer findQuestionBySeq(int qNumber) {
        QuestionAnswer questionAnswer = new QuestionAnswer();
        questionAnswer.setQuestion(Constants.questionsArray[qNumber - 1]);
        questionAnswer.setType("Single");
        questionAnswer.setOptions(Arrays.asList(Constants.answersArray[qNumber - 1]));
        questionAnswer.setQuestionNumber(qNumber);

        return questionAnswer;
    }
}
