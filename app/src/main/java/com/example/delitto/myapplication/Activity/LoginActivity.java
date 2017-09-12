package com.example.delitto.myapplication.Activity;

/**
 * Created by Delitto on 2016/11/18.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delitto.myapplication.R;
import com.example.delitto.myapplication.Tools.ECTools;
import com.example.delitto.myapplication.Tools.AppTools;
import httpservice.LoginPost;

import static com.example.delitto.myapplication.Action.LoginAction.GetLogin;
import static com.example.delitto.myapplication.Tools.AppTools.GotoActivity;
import com.example.delitto.myapplication.Action.LoginAction;
import com.example.delitto.myapplication.User.UserThis;
import com.example.delitto.myapplication.util.PreferenceUtils;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    //请求码
    private static final int REQUEST_SIGNUP = 0;

    private EditText _phoneText;
    private EditText _passwordText;
    private Button _loginButton;
    private TextView _signupLink;
    private static Handler mHandler = new Handler();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //无论如何，只要进入登录页面 则退出当前登陆用户（如果有的话）
        ECTools.ECLogout();
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
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("登录中...");
        progressDialog.show();

       String phone = _phoneText.getText().toString();

       String password = _passwordText.getText().toString();
        UserThis.setUserPhone(phone);
        /*
        EMClient.getInstance().login("text1","1",new EMCallBack(){
            @Override
            public void onSuccess(){
                Intent intent = new Intent(LoginActivity.this, ChatActivity.class);

                startActivity(intent);
                progressDialog.dismiss();
                finish();
            }
            public void onError(int i,String s){}
            public void onProgress(int i,String s){}
        });*/


        // 登陆成功/失败

        GetLogin(_phoneText.getText().toString(),_passwordText.getText().toString());
        ECTools.ECLogin(_phoneText.getText().toString(),_passwordText.getText().toString());

        // TODO: 连接验证登录信息
        //3秒计时器
        //3秒后进入主页面
        /*final Handler mHandler = new Handler();*/
        /*
        new Thread() {
            @Override
            public void run() {
                final String info = LoginPost.executeHttpPost(_phoneText.getText().toString(), _passwordText.getText().toString());*/
        /*Runnable runnable*/
                mHandler.postDelayed(new Runnable() {
                    //判断是否登陆成功 因为本身是异步方法，但内部类不允许使用外部类变量，故登录操作放在这里
                    //再根据登陆结果以flag的形式传入跳转页面
                    boolean flag;
                    int msgflag;

                    public void run() {

                        flag = LoginAction.getLoginFlag();
                        msgflag = ECTools.refreshCallBack();
                        if (flag) {
                            if (msgflag == 1) {
                                //mHandler.removeCallbacks(this);
                                onLoginSuccess();
                                progressDialog.dismiss();
                                _loginButton.setEnabled(true);
                                finish();
                            } else if (msgflag == 0) {
                                Toast.makeText(getBaseContext(), "登录失败,连接服务器失败", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                _loginButton.setEnabled(true);
                                ECTools.ECLogout();
                                return;
                            } else {
                                Toast.makeText(getBaseContext(), "登录失败", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                _loginButton.setEnabled(true);
                                return;
                            }
                        }
                        else
                        {
                            Toast.makeText(getBaseContext(), "登录失败", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            _loginButton.setEnabled(true);
                            return;
                        }
                    }

                }, 3000);
            }


                /*mHandler.postDelayed(runnable, 3000);*/


       // System.out.println("1");




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
        //记录登录状态
        PreferenceUtils.setPrefInt(LoginActivity.this,"Ones5",1);
        PreferenceUtils.setPrefString(LoginActivity.this,"Ones5User",_phoneText.getText().toString());
        startActivity(AppTools.GotoActivity(LoginActivity.this,MainActivity.class));
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
