package com.montclair.mhaskep1.registerandlogin.util;

import java.util.HashMap;

public class Constants {

    public static final int REGISTER_USER = 100;
    public static final int LOGIN_USER = 101;
    public static final int LANDING_PAGE = 102;
    public static final int QUESTIONS_PAGE = 103;
    public static final int RESULTANALYSIS_PAGE = 104;

    public static final String parcel_question = "parcel_question";
    public static final String q1 = "1";
    public static final String q2 = "2";
    public static final String q3 = "3";
    public static final String q4 = "4";
    public static final String q5 = "5";

    public static final String[] questionsArray = new String[]{
            "How much is petal length",
            "How much is petal width",
            "How much is sepal length",
            "How much is sepal width",
            "As per Machine Learning model,\nThis flower species should be:\n\n\"%s\"\n\nDoes this prediction matches?"
    };

    public static final String[][] answersArray = new String[][]{
            {
                    " 0 - 5 ",
                    " 5 - 6 ",
                    " 6 - 7 ",
                    " 7 - 10 "
            },
            {
                    " 0 - 1 ",
                    " 1 - 2 ",
                    " 2 - 3 ",
                    " 3 - 7 "
            },
            {
                    " 3 - 4 ",
                    " 4 - 5 ",
                    " 6 - 7 ",
                    " 7 - 10 "
            },
            {
                    " 0 - 1 ",
                    " 1 - 2 ",
                    " 2 - 3 ",
                    " 3 - 7 "
            },
            {
                    " Yes ",
                    " No "
            }

    };

    public static HashMap<String, String> valueMap = new HashMap<>();

    static {
        valueMap.put(" 1 - 2 ","1.5");
        valueMap.put(" 2 - 3 ","2.5");
        valueMap.put(" 4 - 5 ","4.5");
        valueMap.put(" 5 - 6 ","5.5");
        valueMap.put(" 6 - 7 ","6.5");
        valueMap.put(" 0 - 1 ","0.5");
        valueMap.put(" 3 - 4 ","3.5");
        valueMap.put(" 0 - 5 ", "4.5");
        valueMap.put(" 3 - 7 ","3.5");
        valueMap.put(" 7 - 10 ","8");
    }

}
