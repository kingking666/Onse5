package com.example.delitto.myapplication;

/**
 * Created by Delitto on 2016/11/18.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import cn.ciaapp.sdk.CIAService;
import cn.ciaapp.sdk.VerificationListener;


public class SignupActivity extends AppCompatActivity {


    private EditText _phoneText;
    private EditText _verificationText;
    private Button _verificationButton;
    private EditText _passwordText;
    private EditText _reEnterPasswordText;
    private Button _signupButton;
    private TextView _loginLink;
    private ProgressDialog progressDialog;
    //获取验证按钮宽度
    private static int buttonWidth;
    //第几次获取验证
    private static int frequencyVerify=0;
    //获取验证后距离下一次获取的计时
    private TimeCount time;
    //
    private Handler signupHandler;

    /**
    *  isVerify 表示验证类型
    *  0表示本机号码验证失败
    *  1表示本机号码验证成功
     *  2表示进入非本机号码验证模式
    *  3表示非本机号码验证成功
    *  4表示非本机号码验证失败(验证码错误)
    *  5表示非本机号码验证失败(验证码失效)
    *  6表示非本机号码验证失败(验证码输入错误次数过多(3次)，需要重新验证)
    */
    private int isVerify;
    private int localNumber=0;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化验证API
        CIAService.init(this, "a732352b3199422e86742fcce7da595a", "9bfdac8597da423aac0a02e68a4f4054");


        setContentView(R.layout.activity_signup);


        _phoneText = (EditText) findViewById(R.id.input_phone);
        _verificationText = (EditText) findViewById(R.id.input_verification);
        _verificationButton = (Button) findViewById(R.id.btn_verification);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _reEnterPasswordText = (EditText) findViewById(R.id.input_reEnterPassword);
        _signupButton = (Button) findViewById(R.id.btn_signup);
        _loginLink = (TextView) findViewById(R.id.link_login);

        progressDialog = new ProgressDialog(SignupActivity.this, R.style.AppTheme_Light_Dialog);
      /*  signupHandler = new Handler(){

            //TODO 注册验证联网用Handler

            @Override
            public void handleMessage(Message msg) {
                boolean isNetErr = msg.getData().getBoolean("isNetErr");
                if(progressDialog != null){
                    progressDialog.dismiss();
                }
                if(isNetErr){
                    Toast.makeText(SignupActivity.this, "当前网络不可用", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SignupActivity.this, "错误的用户名或者密码", Toast.LENGTH_SHORT).show();
                }
            }
        };*/

        //40秒重新获取验证时间
        time = new TimeCount(40000, 1000);

        //验证手机号码的按钮先填满
        if(!isVerifyTextDismiss()){
            dismissVerifyText();
        }

        //一开始注册按钮不可使用，手机验证后才可使用
        _signupButton.setEnabled(false);

        //添加事件监听将点击编辑框以外的控件时关闭编辑框
        findViewById(R.id.view_signup).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.view_signup:
                        InputMethodManager imm = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                   //     System.out.println("1");
                        break;
                }

            }
        });
        //注册按钮事件监听
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signup();
            }
        });

        //获取验证按钮事件监听
        _verificationButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(frequencyVerify!=0){
                    dismissVerifyText();
                }
                CIAService.cancelVerification();
                getVerification();
                time.start();
                frequencyVerify++;
            }
        });

        //跳转登录页面事件监听
        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到登陆界面
                CIAService.cancelVerification();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }
        });
    }



    //注册
    public void signup() {
/*
        if (!validate()) {

                onSignupFailed();
                return;

        }*/
        //点击注册后直到消息返回前，注册按钮不可再点击
        _signupButton.setEnabled(false);

        //进度圈
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("注册中...");
        progressDialog.show();


        // TODO: 注册验证
        String mobile = _phoneText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();
        System.out.println(isVerify);
        if(validate(mobile,password,reEnterPassword)){
            codeVerify();
         /*
         Thread signupThread = new Thread(new SignupHandler());
            signupThread.start();
            */

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {if(localNumber==1) {
                            onSignupSuccess();
                            progressDialog.dismiss();
                        }
                        else if(isVerify>1) {
                            // 注册成功/失败
                            if (isVerify != 2) {
                                if (isVerify == 3) {
                                    onSignupSuccess();
                                    progressDialog.dismiss();
                                } else {
                                    System.out.println(isVerify);
                                    onSignupFailed();
                                    progressDialog.dismiss();
                                    return;
                                }
                            }
                        }

                        else {
                            onSignupFailed();
                            progressDialog.dismiss();
                        }


                            // onSignupFailed();

                        }
                    }, 1000);

        }
        else
            _signupButton.setEnabled(true);


        //1秒计时器
        //注册成功后 进度圈消失(调试用)

    }

    //物理按下返回键返回登录页面
    @Override
    public void onBackPressed(){
        CIAService.cancelVerification();
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
        this.finish();
        overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
      //  System.out.print("1");
    }

    //手机号码来电验证
    public void getVerification() {
        String phoneNumber = _phoneText.getText().toString();
        CIAService.startVerification(phoneNumber,new VerificationListener(){
            /**
             * 校验结果回调
             * @param status        验证状态码
             * @param msg           验证状态的描述
             * @param transId       当前验证请求的流水号，服务器可以根据流水号查询验证的状态
             */
            @Override
            public void onStateChange(int status, String msg, String transId) {


                /**
                 * status 是返回的状态码，CIAService包含了一些常量
                 * @see CIAService.VERIFICATION_SUCCESS 验证成功
                 * @see CIAService.VERIFICATION_FAIL 验证失败，请查看 msg 参数描述，例如手机号码格式错误，手机号格式一般需要开发者先校验
                 * @see CIAService.SECURITY_CODE_MODE   验证码模式
                 *      验证码模式：需要提示用户输入验证码，调用
                 *      @see CIAService.getSecurityCode()    获取当前的验证码，格式类似05311234****，需要提示用户****部分是输入的验证码内容
                 * @see CIAService.REQUEST_EXCEPTION    发生异常，msg 是异常描述，例如没有网络连接，网络连接状况一般需要开发者先判断
                 *
                 * 其他情况，status不在上述常量中，是服务器返回的错误，查看 msg 描述，例如 appId 和 authKey 错误。
                 */

                switch (status) {
                    case CIAService.VERIFICATION_SUCCESS: // 验证成功
                        //注册按钮 变为可使用
                        _signupButton.setEnabled(true);
                        showToast("验证成功");
                        isVerify=1;
                        localNumber=1;
                    //    iconChangeInEditTextOnCheck();
                        break;

                    case CIAService.SECURITY_CODE_MODE: // 进入非本机号码验证码模式
                        // 出现输入验证码的EditText，并提示用户输入验证码

                        isVerify = 2;
                        _signupButton.setEnabled(true);
                        showVerifyText();

                        break;


                    case CIAService.VERIFICATION_FAIL:
                        showToast("验证失败：" + msg);
                        isVerify=0;
                        break;

                    case CIAService.REQUEST_EXCEPTION:
                        showToast("请求异常：" + msg);
                        isVerify=0;
                        break;

                    default:
                        // 服务器返回的错误
                        showToast(msg);
                        isVerify=0;

                }
            }
        });
    }

    //非本机号码验证模式
    public void codeVerify() {
        String code = _verificationText.getText().toString();
        CIAService.verifySecurityCode(code, new VerificationListener() {
            /**
             * 校验结果回调
             *
             * @param status  验证状态码
             * @param msg     验证状态的描述
             * @param transId 当前验证请求的流水号，服务器可以根据流水号查询验证的状态
             */
            @Override
            public void onStateChange(int status, String msg, String transId) {

                /**
                 * status 是返回的状态码，CIAService包含了一些常量
                 * @see CIAService.VERIFICATION_SUCCESS    验证成功
                 * @see CIAService.SECURITY_CODE_WRONG 验证码输入错误
                 * @see CIAService.SECURITY_CODE_EXPIRED   验证码失效，需要重新验证
                 * @see CIAService.SECURITY_CODE_EXPIRED_INPUT_OVERRUN  验证码输入错误次数超限(3次)，需要重新验证
                 */
                switch (status) {
                    case CIAService.VERIFICATION_SUCCESS: // 验证成功
                        // TODO 进入下一步业务逻辑
                        isVerify=3;
                        break;
                    case CIAService.SECURITY_CODE_WRONG: // 验证码输入错误

                        isVerify=4;
                        break;
                    case CIAService.SECURITY_CODE_EXPIRED:  // 验证码失效，需要重新验证
                        isVerify=5;
                        break;
                    case CIAService.SECURITY_CODE_EXPIRED_INPUT_OVERRUN:    // 验证码输入错误次数过多(3次)，需要重新验证
                        isVerify=6;
                        break;
                }

            }
        });
    }

    //显示消息
    private void showToast(String txt) {

        Toast.makeText(this, txt, Toast.LENGTH_LONG).show();
    }

    //注册成功
    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        //信息回传
        setResult(RESULT_OK, null);
        //由于未知原因无法跳转至LoginAcitivy回传信息
        //先用Intent 跳转至MainActivity
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

        this.finish();
        //未知原因 finish()之后不会跳转到Login页面
        //TODO 这里注册成功跳转到主页面


    }

    //注册失败

    /**
     ** 4表示非本机号码验证失败(验证码错误)
    *  5表示非本机号码验证失败(验证码失效)
    *  6表示非本机号码验证失败(验证码输入错误次数过多(3次)，需要重新验证)
     */
    public void onSignupFailed() {
        String verifyInfo;
        switch (isVerify)
        {
            case 4:
                verifyInfo=new String(":验证码错误");
                break;
            case 5:
                verifyInfo=new String(":验证码失效");
                break;
            case 6:
                verifyInfo=new String(":验证码输入错误次数过多");
                break;
            default:
                verifyInfo=new String("");
        }
        verifyInfo="注册失败"+verifyInfo;
        Toast.makeText(getBaseContext(), verifyInfo, Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
    }

    //有效性判断
    public boolean validate(String mobile,String password,String reEnterPassword) {

        boolean valid = true;

        if (mobile.isEmpty() || mobile.length() != 11) {
            _phoneText.setError("请输入有效的手机号码");
            valid = false;
            progressDialog.dismiss();
        } else {
            _phoneText.setError(null);

        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("密码长度在4-10位之间");
            valid = false;
            progressDialog.dismiss();
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("两次输入密码不一致");
            valid = false;
            progressDialog.dismiss();
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }

    //测试中的功能
    public void iconChangeInEditTextOnBlank()
    {
        Drawable nav_blank = ContextCompat.getDrawable(getApplicationContext(),R.drawable.nav_blank);
        nav_blank.setBounds(0, 0, nav_blank.getMinimumWidth(), nav_blank.getMinimumHeight());
        _phoneText.setCompoundDrawables(null,null,nav_blank,null);
    }
    public void iconChangeInEditTextOnCheck()
    {
        Drawable nav_check = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_check_circle_white_24dp);
        nav_check.setBounds(0, 0, nav_check.getMinimumWidth(), nav_check.getMinimumHeight());
        _phoneText.setCompoundDrawables(null,null,nav_check,null);
    }

    //测试中的功能
    //TODO 信息传输
    private void setMsg(String msg, boolean isHas){
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putBoolean(msg, isHas);
        message.setData(bundle);
        signupHandler.sendMessage(message);
    }

    //计时，控制最多每60秒才能发送一次验证
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        //倒计时
        @Override
        public void onTick(long millisUntilFinished) {
            _verificationButton.setEnabled(false);
            _verificationButton.setText("("+millisUntilFinished / 1000 +") 秒后可重新验证");
        }
        //计时结束
        @Override
        public void onFinish() {
            _verificationButton.setText("重新获取验证来电");
            _verificationButton.setEnabled(true);
        }
    }

    //显示验证码输入框
    public void showVerifyText(){
        System.out.println(buttonWidth);
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) _verificationButton.getLayoutParams();
        linearParams.width= buttonWidth;
        _verificationButton.setLayoutParams(linearParams);
        _verificationText.setVisibility(View.VISIBLE);
    }

    //隐藏验证码输入框
    public void dismissVerifyText(){
        //消失前清空文本框
        _verificationText.setText("");
        _verificationText.setVisibility(View.GONE);
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) _verificationButton.getLayoutParams();
        buttonWidth=linearParams.width;
        System.out.println(buttonWidth);
        linearParams.width= _phoneText.getLayoutParams().width;
        _verificationButton.setLayoutParams(linearParams);
    }
    //软缎验证码输入框是否隐藏
    public boolean isVerifyTextDismiss(){
        if(_verificationButton.getLayoutParams().width== _phoneText.getLayoutParams().width) {
            return true;
        }
        else
            return false;
    }


}
