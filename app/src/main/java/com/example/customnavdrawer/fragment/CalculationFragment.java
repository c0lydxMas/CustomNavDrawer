package com.example.customnavdrawer.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.customnavdrawer.R;

import java.math.BigDecimal;
import java.util.Arrays;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;


public class CalculationFragment extends Fragment implements View.OnClickListener {

    private boolean dotUsed = false;
    private boolean equalClicked = false;
    private String lastExpression = "";

    private final static int IS_NUMBER = 0;
    private final static int IS_OPERAND = 1;
    private final static int IS_DOT = 2;


    Button buttonNumber0;
    Button buttonNumber1;
    Button buttonNumber2;
    Button buttonNumber3;
    Button buttonNumber4;
    Button buttonNumber5;
    Button buttonNumber6;
    Button buttonNumber7;
    Button buttonNumber8;
    Button buttonNumber9;

    Button buttonClear;
    Button buttonDivision;
    Button buttonMultiplication;
    Button buttonSubtraction;
    Button buttonAddition;
    Button buttonReverse;
    Button buttonBackspace;
    Button buttonClearEntry;
    Button buttonEqual;
    Button buttonDot;

    TextView textViewInputNumbers;
    TextView textViewResult;

    ScriptEngine scriptEngine;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);

        scriptEngine = new ScriptEngineManager().getEngineByName("rhino");

        initializeVariables(view);
        setOnClickListeners();


        return view;
    }

    private void initializeVariables(View view){
        buttonNumber0 = view.findViewById(R.id.button_0);
        buttonNumber1 = view.findViewById(R.id.button_1);
        buttonNumber2 = view.findViewById(R.id.button_2);
        buttonNumber3 = view.findViewById(R.id.button_3);
        buttonNumber4 = view.findViewById(R.id.button_4);
        buttonNumber5 = view.findViewById(R.id.button_5);
        buttonNumber6 = view.findViewById(R.id.button_6);
        buttonNumber7 = view.findViewById(R.id.button_7);
        buttonNumber8 = view.findViewById(R.id.button_8);
        buttonNumber9 = view.findViewById(R.id.button_9);

        buttonBackspace = view.findViewById(R.id.button_backspace);
        buttonClear = view.findViewById(R.id.button_clear);
        buttonClearEntry = view.findViewById(R.id.button_clear_entry);
        buttonMultiplication = view.findViewById(R.id.button_multiplication);
        buttonSubtraction = view.findViewById(R.id.button_subtraction);
        buttonAddition = view.findViewById(R.id.button_addition);
        buttonEqual = view.findViewById(R.id.button_equal);
        buttonDot = view.findViewById(R.id.button_dot);
        buttonDivision = view.findViewById(R.id.button_division);
        buttonReverse = view.findViewById(R.id.button_reverse);

        textViewInputNumbers = view.findViewById(R.id.textview_input);
        textViewResult = view.findViewById(R.id.textview_result);


    }

    private void setOnClickListeners(){

        buttonNumber0.setOnClickListener(this);
        buttonNumber1.setOnClickListener(this);
        buttonNumber2.setOnClickListener(this);
        buttonNumber3.setOnClickListener(this);
        buttonNumber4.setOnClickListener(this);
        buttonNumber5.setOnClickListener(this);
        buttonNumber6.setOnClickListener(this);
        buttonNumber7.setOnClickListener(this);
        buttonNumber8.setOnClickListener(this);
        buttonNumber9.setOnClickListener(this);

        buttonClear.setOnClickListener(this);
        buttonClearEntry.setOnClickListener(this);
        buttonBackspace.setOnClickListener(this);
        buttonDivision.setOnClickListener(this);
        buttonMultiplication.setOnClickListener(this);
        buttonSubtraction.setOnClickListener(this);
        buttonAddition.setOnClickListener(this);
        buttonEqual.setOnClickListener(this);
        buttonDot.setOnClickListener(this);
        buttonReverse.setOnClickListener(this);
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.button_0:
                if (addNumber("0")) equalClicked = false;
                break;
            case R.id.button_1:
                if (addNumber("1")) equalClicked = false;
                break;
            case R.id.button_2:
                if (addNumber("2")) equalClicked = false;
                break;
            case R.id.button_3:
                if (addNumber("3")) equalClicked = false;
                break;
            case R.id.button_4:
                if (addNumber("4")) equalClicked = false;
                break;
            case R.id.button_5:
                if (addNumber("5")) equalClicked = false;
                break;
            case R.id.button_6:
                if (addNumber("6")) equalClicked = false;
                break;
            case R.id.button_7:
                if (addNumber("7")) equalClicked = false;
                break;
            case R.id.button_8:
                if (addNumber("8")) equalClicked = false;
                break;
            case R.id.button_9:
                if (addNumber("9")) equalClicked = false;
                break;
            case R.id.button_addition:
                if (addOperand("+")) equalClicked = false;
                break;
            case R.id.button_subtraction:
                if (addOperand("-")) equalClicked = false;
                break;
            case R.id.button_multiplication:
                if (addOperand("x")) equalClicked = false;
                break;
            case R.id.button_division:
                if (addOperand("\u00F7")) equalClicked = false;
                break;
            case R.id.button_dot:
                if (addDot()) equalClicked = false;
                break;
            case R.id.button_clear:
                textViewInputNumbers.setText("");
                textViewResult.setText("");
                dotUsed = false;
                equalClicked = false;
                break;
            case R.id.button_clear_entry:
                textViewInputNumbers.setText("");
                equalClicked = false;
                break;
            case R.id.button_backspace:
                String tmp = textViewInputNumbers.getText().toString();
                if(tmp.length()>0){
                    textViewInputNumbers.setText(tmp.substring(0,tmp.length()-1));
                    equalClicked = false;
                }
                break;
            case R.id.button_equal:
                if (!textViewInputNumbers.getText().toString().equals(""))
                    calculate(textViewInputNumbers.getText().toString());
                break;
            case R.id.button_reverse:
                String result = textViewResult.getText().toString();
                String input = textViewInputNumbers.getText().toString();
                if(result.length() > 0){
                    if(result.charAt(0) == '-'){
                        textViewResult.setText(result.substring(1));
                    }else{
                        textViewResult.setText("-" + result);
                    }
                }else if(input.length() > 0){
                    if(input.charAt(0) == '-'){
                        textViewResult.setText(input.substring(1));
                    }else{
                        textViewResult.setText("-" + input);
                    }
                }

        }
    }

    @SuppressLint("SetTextI18n")
    private boolean addDot()
    {
        boolean done = false;

        if (textViewInputNumbers.getText().length() == 0)
        {
            textViewInputNumbers.setText("0.");
            dotUsed = true;
            done = true;
        } else if (dotUsed)
        {
        } else if (defineLastCharacter(textViewInputNumbers.getText().charAt(textViewInputNumbers.getText().length() - 1) + "") == IS_OPERAND)
        {
            textViewInputNumbers.setText(textViewInputNumbers.getText() + "0.");
            done = true;
            dotUsed = true;
        } else if (defineLastCharacter(textViewInputNumbers.getText().charAt(textViewInputNumbers.getText().length() - 1) + "") == IS_NUMBER)
        {
            textViewInputNumbers.setText(textViewInputNumbers.getText() + ".");
            done = true;
            dotUsed = true;
        }
        return done;
    }

    private int defineLastCharacter(String lastCharacter)
    {
        try
        {
            Integer.parseInt(lastCharacter);
            return IS_NUMBER;
        } catch (NumberFormatException ignored)
        {
        }

        if ((lastCharacter.equals("+") || lastCharacter.equals("-") || lastCharacter.equals("x") || lastCharacter.equals("\u00F7") || lastCharacter.equals("%")))
            return IS_OPERAND;

        if (lastCharacter.equals("."))
            return IS_DOT;

        return -1;
    }

    @SuppressLint("SetTextI18n")
    private boolean addOperand(String operand)
    {
        boolean done = false;
        int operationLength = textViewInputNumbers.getText().length();
        int resultLength = textViewResult.getText().length();
        if (resultLength > 0)
        {
            textViewInputNumbers.setText(textViewResult.getText()+operand);
            dotUsed = false;
            equalClicked = false;
            lastExpression = "";
            done = true;

        }else if(operationLength > 0){
            String lastInput = textViewInputNumbers.getText().charAt(operationLength - 1) + "";

            if (lastInput.equals("+") || lastInput.equals("-") || lastInput.equals("*") || lastInput.equals("\u00F7"))
            {
                Toast.makeText(getActivity().getApplicationContext(), "Wrong format", Toast.LENGTH_SHORT).show();
            } else if (defineLastCharacter(lastInput) == IS_NUMBER)
            {
                textViewInputNumbers.setText(textViewInputNumbers.getText() + operand);
                dotUsed = false;
                equalClicked = false;
                lastExpression = "";
                done = true;
            }
        } else
        {
            Toast.makeText(getActivity().getApplicationContext(), "Wrong Format. Operand Without any numbers?", Toast.LENGTH_SHORT).show();
        }
        return done;
    }

    @SuppressLint("SetTextI18n")
    private boolean addNumber(String number)
    {
        boolean done = false;
        int operationLength = textViewInputNumbers.getText().length();
        if (operationLength > 0)
        {
            String lastCharacter = textViewInputNumbers.getText().charAt(operationLength - 1) + "";
            int lastCharacterState = defineLastCharacter(lastCharacter);

            if (operationLength == 1 && lastCharacterState == IS_NUMBER && lastCharacter.equals("0"))
            {
                textViewInputNumbers.setText(number);
                done = true;
            }  else if (lastCharacterState == IS_NUMBER || lastCharacterState == IS_OPERAND || lastCharacterState == IS_DOT)
            {
                textViewInputNumbers.setText(textViewInputNumbers.getText() + number);
                done = true;
            }
        } else
        {
            textViewInputNumbers.setText(textViewInputNumbers.getText() + number);
            done = true;
        }
        return done;
    }


    @SuppressLint("SetTextI18n")
    private void calculate(String input)
    {
        String result = "";
        try
        {
            if (equalClicked)
            {
                // temp = input + lastExpression;

                String last = "";
                String tmp = textViewInputNumbers.getText().toString();
                for(int i = 0; i < tmp.length(); ++i){
                    String cop = tmp.charAt(i) + "";
                    if(defineLastCharacter(cop) == IS_OPERAND){
                        last = tmp.substring(i);
                        break;
                    }
                }
                String currentResult = textViewResult.getText().toString();
                textViewInputNumbers.setText(currentResult + last);

                String newInput = textViewInputNumbers.getText().toString();

                result = scriptEngine.eval(newInput.replaceAll("x", "*").replaceAll("[^\\x00-\\x7F]", "/")).toString();
                BigDecimal decimal = new BigDecimal(result);
                result = decimal.setScale(12, BigDecimal.ROUND_HALF_UP).toPlainString();

            } else
            {
                //saveLastExpression(input);

                result = scriptEngine.eval(input.replaceAll("x", "*").replaceAll("[^\\x00-\\x7F]", "/")).toString();
                BigDecimal decimal = new BigDecimal(result);
                result = decimal.setScale(12, BigDecimal.ROUND_HALF_UP).toPlainString();
                equalClicked = true;
            }


        } catch (Exception e)
        {
            Toast.makeText(getActivity().getApplicationContext(), "Wrong Format", Toast.LENGTH_SHORT).show();
            return;
        }

        if (result.equals("Infinity"))
        {
            Toast.makeText(getActivity().getApplicationContext(), "Division by zero is not allowed", Toast.LENGTH_SHORT).show();
            textViewResult.setText(input);

        } else if (result.contains("."))
        {
            result = result.replaceAll("\\.?0*$", "");
            textViewResult.setText(result);
        }
    }

    private void saveLastExpression(String input)
    {
        String lastOfExpression = input.charAt(input.length() - 1) + "";
        if (input.length() > 1)
        {
            if (defineLastCharacter(lastOfExpression + "") == IS_NUMBER)
            {
                lastExpression = lastOfExpression;
                for (int i = input.length() - 2; i >= 0; i--)
                {
                    String last = input.charAt(i) + "";
                    if (defineLastCharacter(last) == IS_NUMBER || defineLastCharacter(last) == IS_DOT)
                    {
                        lastExpression = last + lastExpression;
                    } else if (defineLastCharacter(last) == IS_OPERAND)
                    {
                        lastExpression = last + lastExpression;
                        break;
                    }
                    if (i == 0)
                    {
                        lastExpression = "";
                    }
                }
            }
        }
    }
}
