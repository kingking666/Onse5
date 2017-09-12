package com.example.delitto.myapplication.Activity;

/**
 * Created by Delitto on 2016/12/3.
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.delitto.myapplication.Action.RegistinfoAction;
import com.example.delitto.myapplication.R;

public class RegistinfoActivity extends AppCompatActivity {
    private EditText _unameText;
    private EditText _signatureText;
    private RadioGroup _sex;
    private RadioButton _sexmale;
    private RadioButton _sexfemale;
    private Button _next;
    private ProgressDialog progressDialog;
    private String sex;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registinfo);
        _unameText=(EditText)findViewById(R.id.input_uname);
        _signatureText=(EditText)findViewById(R.id.input_signature);
        _sex=(RadioGroup)findViewById(R.id.sexradio);
        _sexmale=(RadioButton)findViewById(R.id.radioMale);
        _sexfemale=(RadioButton)findViewById(R.id.radioFemale) ;
        _next=(Button)findViewById(R.id.btn_infonext);
        progressDialog = new ProgressDialog(RegistinfoActivity.this, R.style.AppTheme_Light_Dialog);

        _sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                RadioButton rb =(RadioButton)findViewById(radioButtonId);
                // TODO 获取得到的性别选项
                sex=rb.getText().toString();
            }
        });
        _next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registInfo();
            }
        });
    }
    public void registInfo(){
        String uname;
        String signature;
        String sex;


        uname=_unameText.getText().toString();
        signature=_signatureText.getText().toString();
        sex=_sexmale.isChecked()?"男":"女";


        if(uname.isEmpty()) {
            _unameText.setError("昵称不能为空！");
            return;
        }
        if(uname.length()>12){
            _unameText.setError("昵称过长!");
            return;
        }
        if(signature.length()>20) {
            _signatureText.setError("个性签名过长!");
            return;
        }
        // TODO 连接更新操作
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("保存中...");
        progressDialog.show();
        RegistinfoAction.GetRegistinfo(uname,sex,signature);
        //3秒后消失
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if(RegistinfoAction.getRegistinfoFlag()) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            progressDialog.dismiss();
                        }
                    }
                }, 1200);


    }
}
