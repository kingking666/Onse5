package com.example.delitto.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by pokedo on 2016/11/30.
 */

//导入这句
//import android.support.v7.preference.PreferenceFragmentCompat;
@SuppressLint("ValidFragment")
public class SettingFragment extends PreferenceFragmentCompat {
    private Activity activity;

    public SettingFragment(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreatePreferences(final Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.setting_general);

        findPreference("change_theme").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return false;
            }
        });

        findPreference("switch_notification").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return false;
            }
        });

        findPreference("lighteness").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return false;
            }
        });

        findPreference("publish_log").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return false;
            }
        });

        findPreference("get_log").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return false;
            }
        });

        findPreference("account_password").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return false;
            }
        });

        findPreference("feedback").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                feedback();
                return false;
            }
        });


        findPreference("agreement").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return false;
            }
        });

        findPreference("open_source").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return false;
            }
        });

        findPreference("update").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                update();
                return false;
            }
        });

//        Preference preference = findPreference("exit");
//        preference.setWidgetLayoutResource(R.layout.layout_exit_button);
////        Log.d("~name",""+preference.get);
//        findPreference("exit").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
////                Log.d("~click", "true");
////                Log.d("~inflate", view + "");
////                Log.d("~test", "" + view.isShown());
////
////                Button button = (Button) view.findViewById(R.id.exit_button);
////                button.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View view) {
////                        Log.d("~test", "true");
////                    }
////                });
//                return false;
//            }
//        });

    }

//    public void exit (View v) {
//        Log.d("~", "ffff");
//    }

    protected void update() {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("检查更新中...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();

                        final AlertDialog.Builder dia = new AlertDialog.Builder(activity);
                        dia.setMessage("Onse5已是最新版本");
                        dia.setCancelable(true);
                        dia.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        dia.show();
                    }
                }, 2000);
    }


    protected void feedback() {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"k515576745@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "万事屋使用意见与建议");
            intent.putExtra(Intent.EXTRA_TEXT,
                    "设备型号:" + Build.MODEL + "\n"
                            + "SDK版本:" + Build.VERSION.RELEASE + "\n"
                            + "版本:1.0");
            activity.startActivity(Intent.createChooser(intent, "Send mail"));

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(activity, "没有找到邮件类APP", Toast.LENGTH_SHORT).show();
        }
    }
}
