package com.montclair.mhaskep1.registerandlogin;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.montclair.mhaskep1.registerandlogin.model.QuestionAnswer;
import com.montclair.mhaskep1.registerandlogin.util.Constants;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * Question fragment is used to display question, answers and action buttons
 *
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {
    //local caopy of questions object
    private QuestionAnswer questionAnswer;

    private OnFragmentInteractionListener mListener;
    private Bundle outState = null;
    private LinearLayout linearLayout;
    private TextView errMsg;

    public QuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param questionAnswer Model for Question and options
     * @return A new instance of fragm ent QuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionFragment newInstance(QuestionAnswer questionAnswer) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.parcel_question, questionAnswer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            questionAnswer = getArguments().getParcelable(Constants.parcel_question);
        }

        Toast.makeText(this.getContext(), String.format(" Question : %s \n\nAnswer %s", questionAnswer.getQuestion(), questionAnswer.getQuestionNumber()), Toast.LENGTH_LONG).show();
    }

    /**
     * Used for init of fragment with questions and desired answers
     * based on type/seq multichoice or radio buttons are used
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_question, container, false);

        //Question
        TextView question = (TextView) view.findViewById(R.id.tv_frg_question);
        question.setText(questionAnswer.getQuestion());

        errMsg = (TextView) view.findViewById(R.id.tv_frg_err_msg);

        //Answers
        linearLayout = (LinearLayout) view.findViewById(R.id.ll_frg_question);
        if(questionAnswer.getQuestionNumber() < 5) {
            for(String option : questionAnswer.getOptions()){

                CheckBox checkBox = new CheckBox(view.getContext());
                checkBox.setText(option);
                linearLayout.addView(checkBox);

            }
        } else {

            RadioGroup rg = new RadioGroup(linearLayout.getContext()); //create the RadioGroup
            rg.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL
            int count = 0;
            for(String option : questionAnswer.getOptions()){
                RadioButton radioButton = new RadioButton(rg.getContext());
                radioButton.setText(option);
                radioButton.setId(count);
                rg.addView(radioButton);
                count++;
            }

            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    //RadioButton rb = group.findViewById(checkedId);
                    //questionAnswer.getChoices().clear();
                    //questionAnswer.getChoices().add(rb.getText().toString());
                }
            });

            linearLayout.addView(rg);//you add the whole RadioGroup to the layout


        };

        //Next Prev Buttons
        Button next_button = (Button) view.findViewById(R.id.bt_frg_next_ques);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean opted = false;
                errMsg.setVisibility(View.GONE);
                List<String> choices = new LinkedList<>();

                for (int i = 0; i < linearLayout.getChildCount(); i++) {
                    View childAt = linearLayout.getChildAt(i);
                    if (childAt instanceof CheckBox) {
                        //validate your CheckBox
                        CheckBox c = (CheckBox) childAt;
                        if(c.isChecked()) {
                            opted = true;
                            questionAnswer.getChoices().add(c.getText().toString());
                            choices.add(c.getText().toString());
                        }
                    } else if (childAt instanceof RadioGroup) {
                        //validate RadioButton
                        RadioGroup r = (RadioGroup) childAt;
                        if(r.getCheckedRadioButtonId() != -1) {
                            RadioButton checkedButton = r.findViewById(r.getCheckedRadioButtonId());
                            opted = true;
                            questionAnswer.getChoices().add(checkedButton.getText().toString());
                            choices.add(checkedButton.getText().toString());
                        }
                    } //etc. If it fails anywhere, just return false.
                }

                if(opted) {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(String.format("Your Choices are : %s ", questionAnswer.getChoices()))
                            .setTitle("Submit Answer?")
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                    onNextPressed(questionAnswer);
                                }
                            })
                            .setNegativeButton("Dismiss", null);
                    // Create the AlertDialog object and return it
                    builder.create();
                    builder.show();

                } else
                {

                    errMsg.setVisibility(View.VISIBLE);
                    Toast.makeText(v.getContext(), String.format("Please select at least one answer"), Toast.LENGTH_LONG).show();

                }

            }
        });
        Button prev_button = (Button) view.findViewById(R.id.bt_frg_prev_ques);
        prev_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean opted = false;
                errMsg.setVisibility(View.GONE);
                List<String> choices = new LinkedList<>();

                for (int i = 0; i < linearLayout.getChildCount(); i++) {
                    View childAt = linearLayout.getChildAt(i);
                    if (childAt instanceof CheckBox) {
                        //validate your CheckBox
                        CheckBox c = (CheckBox) childAt;
                        if(c.isChecked()) {
                            opted = true;
                            choices.add(c.getText().toString());
                        }
                    } else if (childAt instanceof RadioButton) {
                        //validate RadioButton
                        RadioGroup r = (RadioGroup) childAt;
                        if(r.getCheckedRadioButtonId() != -1) {
                            RadioButton checkedButton = r.findViewById(r.getCheckedRadioButtonId());
                            opted = true;
                            questionAnswer.getChoices().add(checkedButton.getText().toString());
                            choices.add(checkedButton.getText().toString());
                        }
                    } //etc. If it fails anywhere, just return false.
                }

                if(opted) {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(String.format("Your Choices are : %s", choices))
                            .setTitle("Submit Answer?")
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    onPrevPressed(questionAnswer);
                                }
                            })
                            .setNegativeButton("Dismiss", null);
                    // Create the AlertDialog object and return it
                    builder.create();
                    builder.show();
                } else
                {

                    errMsg.setVisibility(View.VISIBLE);
                    Toast.makeText(v.getContext(), String.format("Please select at least one answer"), Toast.LENGTH_LONG).show();

                }
            }
        });

        if (questionAnswer.getType() == "Checkbox") {
            //read all optios and create checkboxes
            // Checkboxes used for multiple choices
            questionAnswer.getOptions();


        } else {
            //read a;; options and create radio
            //Radio utton only one choice
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(QuestionAnswer questionAnswer) {

    }

    /**
     * on next button click, Hooks to call activity
     *
     * @param questionAnswer
     */
    public void onNextPressed(QuestionAnswer questionAnswer) {
        if (mListener != null) {
            questionAnswer.setChoices(this.questionAnswer.getChoices());
            mListener.onNext(questionAnswer);
        }
    }

    /**
     * on prev button click, hook is used to call activity
     *
     * @param questionAnswer
     */
    public void onPrevPressed(QuestionAnswer questionAnswer) {
        if (mListener != null) {
            questionAnswer.setChoices(this.questionAnswer.getChoices());
            mListener.onPrev(questionAnswer);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(QuestionAnswer questionAnswer);

        void onNext(QuestionAnswer questionAnswer);

        void onPrev(QuestionAnswer questionAnswer);
    }
}
