package com.example.delitto.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by pokedo on 2016/11/18.
 */

public class TaskFragment extends Fragment {
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private EditText publicEdittext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_fragment, container, false);

        spinner = (Spinner) view.findViewById(R.id.spinner);
        String[] ITEMS = {"自定义", "0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9", "1", "1.5", "2", "2.5",
                "3", "3.5", "4", "4.5", "5", "5.5", "6", "6.5", "7", "7.5", "8", "8.5", "9", "9.5", "10"};
        adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //edittext失去焦点，无效
//        publicEdittext = (EditText) view.findViewById(R.id.public_describe);
//        publicEdittext.clearFocus();

        return view;
    }
}
