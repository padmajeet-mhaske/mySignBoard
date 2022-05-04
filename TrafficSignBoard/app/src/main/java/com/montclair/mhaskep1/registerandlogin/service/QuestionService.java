package com.montclair.mhaskep1.registerandlogin.service;

import android.util.Log;

import com.montclair.mhaskep1.registerandlogin.DataProvider.QuestionProvider;
import com.montclair.mhaskep1.registerandlogin.ml.GuessIris;
import com.montclair.mhaskep1.registerandlogin.model.QuestionAnswer;
import com.montclair.mhaskep1.registerandlogin.util.Constants;

import java.util.List;
import java.util.Map;

public class QuestionService {

    public static QuestionAnswer findQuestionBySeq(int qNumber, Map<Integer, List<String>> choices){

        if(qNumber < 5) {
            return QuestionProvider.findQuestionBySeq(qNumber);
        }
        else {
            //do analysis and guess answer
            String species = "Rose";

            species = GuessIris.predict(getValue(choices.get(1)),
                    getValue(choices.get(2)),
                    getValue(choices.get(3)),
                    getValue(choices.get(4))
                    );

            QuestionAnswer fifthQuestion = QuestionProvider.findQuestionBySeq(qNumber);

            fifthQuestion.setQuestion(String.format(fifthQuestion.getQuestion(), species));


            return fifthQuestion;
        }


    }

    public static double getValue(List<String> choices){
        double total = 0;
        for(String val : choices){
            Log.e("questionservice", String.format("key::%s::  double ::%s::", val, Constants.valueMap.get(val)) );
            total = total + Double.valueOf(Constants.valueMap.get(val));
        }

        return total/choices.size();

    }
}
