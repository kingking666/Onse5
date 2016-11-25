package com.example.delitto.myapplication;

/**
 * Created by Delitto on 2016/11/18.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    //请求码
    private static final int REQUEST_SIGNUP = 0;

    private EditText _phoneText;
    private EditText _passwordText;
    private Button _loginButton;
    private TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _phoneText = (EditText) findViewById(R.id.input_phone);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _loginButton = (Button) findViewById(R.id.btn_login);
        _signupLink = (TextView) findViewById(R.id.link_signup);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 跳转到注册页面
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        //添加事件监听将点击编辑框以外的控件时关闭编辑框
        findViewById(R.id.view_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.view_login:
                        InputMethodManager imm = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        //     System.out.println("1");
                        break;
                }
            }
        });
    }

    public void login() {
     //调试信息
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Light_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("登录中...");
        progressDialog.show();

        String phone = _phoneText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: 连接数据库验证登录信息
        //3秒计时器
        //3秒后进入主页面
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // 登陆成功/失败
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
       // System.out.println("1");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //未知原因SignupActivity不会回传信息
      //  System.out.println("0");
       if (requestCode == REQUEST_SIGNUP) {

           if (resultCode == RESULT_OK) {

               //注册结果返回
               /** TODO 注册成功后的结果返回逻辑处理
                *  设置为注册成功后自动登录
                */

               this.finish();
           }
       }

    }

    @Override
    public void onBackPressed() {
        //登陆界面按下物理返回键把程序置为后台
        moveTaskToBack(true);
    }
    //登陆成功
    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
//登录失败
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "登录失败", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }
    //有效性判断（暂定）
    public boolean validate() {
        boolean valid = true;

        String phone = _phoneText.getText().toString();
        String password = _passwordText.getText().toString();

        if (phone.isEmpty() || phone.length() != 11) {
            _phoneText.setError("请输入正确的手机号码");
            valid = false;
        } else {
            _phoneText.setError(null);
        }
        //密码有效性（暂定）
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("密码长度在4-10位之间");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
