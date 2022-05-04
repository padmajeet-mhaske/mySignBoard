package com.montclair.mhaskep1.registerandlogin.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedList;
import java.util.List;

public class QuestionAnswer implements Parcelable {

    private String question;
    private String type;
    private List<String> options = new LinkedList<>();
    private List<String> choices = new LinkedList<>();
    private int questionNumber;

    public QuestionAnswer(String question, String type, List<String> options, int questionNumber) {
        this.question = question;
        this.type = type;
        this.options = options;
        this.questionNumber = questionNumber;
    }

    public QuestionAnswer() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.question);
        dest.writeString(this.type);
        dest.writeStringList(this.options);
        dest.writeStringList(this.choices);
        dest.writeInt(this.questionNumber);
    }

    protected QuestionAnswer(Parcel in) {
        this.question = in.readString();
        this.type = in.readString();
        this.options = in.createStringArrayList();
        this.choices = in.createStringArrayList();
        this.questionNumber = in.readInt();
    }

    public static final Creator<QuestionAnswer> CREATOR = new Creator<QuestionAnswer>() {
        @Override
        public QuestionAnswer createFromParcel(Parcel source) {
            return new QuestionAnswer(source);
        }

        @Override
        public QuestionAnswer[] newArray(int size) {
            return new QuestionAnswer[size];
        }
    };
}
