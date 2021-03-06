package com.example.customnavdrawer.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.customnavdrawer.MainActivity;
import com.example.customnavdrawer.R;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseConverterFragment extends Fragment {

    Button convertButton;
    EditText editText;
    TextView textView;
    private String result;
    private int sBase;
    private int dBase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_converter, container, false);

        Spinner spinnerNumberBaseFrom = (Spinner) view.findViewById(R.id.spinner_number_base_from);
        Spinner spinnerNumberBaseTo = (Spinner) view.findViewById(R.id.spinner_number_base_to);
        convertButton = (Button)view.findViewById(R.id.btn_convert);
        editText = (EditText)view.findViewById(R.id.editText);
        textView = (TextView)view.findViewById(R.id.textView);


        NumberBase[] numberBases = NumberBaseDataUtils.getNumberBase();

        ArrayAdapter<NumberBase> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item,
                numberBases);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerNumberBaseFrom.setAdapter(adapter);
        spinnerNumberBaseTo.setAdapter(adapter);

        spinnerNumberBaseFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onNumberBaseFromSelectedHandler(parent,view,position,id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerNumberBaseTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onNumberBaseToSelectedHandler(parent,view,position,id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        try {
            convertButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) throws NumberFormatException {
                    textView.setText("");
                    String inputNumber = editText.getText().toString();
                    boolean checkValid = false;
                    if(sBase == 2){
                        if(isInteger(inputNumber) && isBinaryNumber(inputNumber)){
                            checkValid = true;
                        }
                    }else if(sBase == 10){
                        if(isInteger(inputNumber)){
                            checkValid = true;
                        }
                    }else if(sBase == 8){
                        if(isInteger(inputNumber) && isOctalNumber(inputNumber)){
                            checkValid = true;
                        }
                    }else if(sBase == 16){
                        if(isHexNumber(inputNumber)){
                            checkValid = true;
                        }
                    }
                    if(checkValid){
                        result = baseConversion(inputNumber, sBase, dBase);
                        textView.setText(result);
                    }else{
                        Toast.makeText(getActivity(), "Invalid Input Number", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (NumberFormatException e){
            Log.e("KhanhBQ", e.toString());
        }

        return view;
    }

    private void onNumberBaseFromSelectedHandler(AdapterView<?> adapterView, View view, int position, long id) {
        Adapter adapter = adapterView.getAdapter();
        NumberBase numberBase = (NumberBase) adapter.getItem(position);
        String tmp = numberBase.getNumberBase();
        switch (tmp) {
            case "Binary":
                sBase = 2;
                break;
            case "Decimal":
                sBase = 10;
                break;
            case "Octal":
                sBase = 8;
                break;
            case "Hex":
                sBase = 16;
                break;
            default:
                sBase = -1;
                break;
        }
    }

    private void onNumberBaseToSelectedHandler(AdapterView<?> adapterView, View view, int position, long id) {
        Adapter adapter = adapterView.getAdapter();
        NumberBase numberBase = (NumberBase) adapter.getItem(position);
        String tmp = numberBase.getNumberBase();
        switch (tmp) {
            case "Binary":
                dBase = 2;
                break;
            case "Decimal":
                dBase = 10;
                break;
            case "Octal":
                dBase = 8;
                break;
            case "Hex":
                dBase = 16;
                break;
            default:
                dBase = -1;
                break;
        }
    }

    @SuppressLint("NewApi")
    private String baseConversion(String number, int sBase, int dBase){
        long result = 0;
        try {
            result = Long.parseUnsignedLong(number, sBase);
        } catch (NumberFormatException e) {
            Log.v("KHANHBQ", "parseInt Overflow");
        }
        if (result != 0)
            return Long.toString(result, dBase);
        else
            return "Overflow, please choose a smaller number";
    }

    private static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    private static boolean isBinaryNumber(String input)
    {
        int num = Integer.parseInt(input);
        if (num == 0 || num == 1
                || num < 0) {
            return false;
        }

        while (num != 0) {
            if (num % 10 > 1) {
                return false;
            }
            num /= 10;
        }
        return true;
    }

    private static boolean isOctalNumber(String input)
    {
        int num = Integer.parseInt(input);
        while (num != 0) {
            if (num % 10 > 7) {
                return false;
            }
            num /= 10;
        }
        return true;
    }

    private static boolean isHexNumber(String num){
        Pattern HEXADECIMAL_PATTERN = Pattern.compile("\\p{XDigit}+");

        final Matcher matcher = HEXADECIMAL_PATTERN.matcher(num);
        return matcher.matches();
    }
}
