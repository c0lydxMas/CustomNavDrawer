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

import com.example.customnavdrawer.R;

public class LengthConverterFragment extends Fragment {
    Button convertButton;
    EditText editText;
    TextView textView;
    private String result;
    private int sBase;
    private int dBase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_length_converter, container, false);

        Spinner spinnerLengthBaseFrom = (Spinner) view.findViewById(R.id.spinner_length_base_from);
        Spinner spinnerLengthBaseTo = (Spinner) view.findViewById(R.id.spinner_length_base_to);
        convertButton = (Button) view.findViewById(R.id.btn_convert_length);
        editText = (EditText) view.findViewById(R.id.editTextLength);
        textView = (TextView) view.findViewById(R.id.textViewLength);


        LengthBase[] lengthBases = LengthBaseDataUtils.getLengthBase();

        ArrayAdapter<LengthBase> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item,
                lengthBases);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerLengthBaseFrom.setAdapter(adapter);
        spinnerLengthBaseTo.setAdapter(adapter);

        spinnerLengthBaseFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onLengthBaseFromSelectedHandler(parent, view, position, id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerLengthBaseTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onLengthBaseToSelectedHandler(parent, view, position, id);
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
                    String inputLength = editText.getText().toString();

                    if (isInteger(inputLength)) {
                        result = lengthConversion(inputLength, sBase, dBase);
                        textView.setText(result);
                    } else {
                        Toast.makeText(getActivity(), "Invalid Input Length", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (NumberFormatException e) {
            Log.e("KhanhBQ", e.toString());
        }

        return view;
    }

    private void onLengthBaseFromSelectedHandler(AdapterView<?> adapterView, View view, int position, long id) {
        Adapter adapter = adapterView.getAdapter();
        LengthBase lengthBase = (LengthBase) adapter.getItem(position);
        String tmp = lengthBase.getLengthBase();
        switch (tmp) {
            case "Centimeter":
                sBase = 0;
                break;
            case "Meter":
                sBase = 1;
                break;
            case "Inch":
                sBase = 2;
                break;
            default:
                sBase = -1;
                break;
        }
    }

    private void onLengthBaseToSelectedHandler(AdapterView<?> adapterView, View view, int position, long id) {
        Adapter adapter = adapterView.getAdapter();
        LengthBase lengthBase = (LengthBase) adapter.getItem(position);
        String tmp = lengthBase.getLengthBase();
        switch (tmp) {
            case "Centimeter":
                dBase = 0;
                break;
            case "Meter":
                dBase = 1;
                break;
            case "Inch":
                dBase = 2;
                break;
            default:
                dBase = -1;
                break;
        }
    }

    @SuppressLint("NewApi")
    private String lengthConversion(String length, int sBase, int dBase){
        double result = 0;

        if(sBase == 0){
            switch (dBase){
                case 0:
                    result = Double.parseDouble(length);
                    break;
                case 1:
                    result = Double.parseDouble(length)/100;
                    break;
                case 2:
                    result = Double.parseDouble(length)*0.3937;
                    break;
                default:
                    result = -1;
            }
        }

        if(sBase == 1){
            switch (dBase){
                case 0:
                    result = Double.parseDouble(length)*100;
                    break;
                case 1:
                    result = Double.parseDouble(length);
                    break;
                case 2:
                    result = Double.parseDouble(length)*39.3700787;
                    break;
                default:
                    result = -1;
            }
        }

        if(sBase == 2){
            switch (dBase){
                case 0:
                    result = Double.parseDouble(length)* 2.54;
                    break;
                case 1:
                    result = Double.parseDouble(length)*2.54/100;
                    break;
                case 2:
                    result = Double.parseDouble(length);
                    break;
                default:
                    result = -1;
            }
        }


        return String.valueOf(result);
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

}
