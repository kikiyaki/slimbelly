package com.hfad.slimbelly.intro;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import com.hfad.slimbelly.R;


public class IntroActivity extends Activity {

    final String manufacturerXiaomi = "xiaomi";
    final String manufacturerHuawei = "huawei";
    final String manufacturerMeizu = "meizu";
    final String manufacturer = android.os.Build.MANUFACTURER;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        if (manufacturer.equalsIgnoreCase(manufacturerXiaomi)) {
            message = this.getString(R.string.push_dialog_xiaomi);
        } else if (manufacturer.equalsIgnoreCase(manufacturerHuawei)) {
            message = this.getString(R.string.push_dialog_huawei);
        } else if (manufacturer.equalsIgnoreCase(manufacturerMeizu)) {
            message = this.getString(R.string.push_dialog_meizu);
        } else {
            return;
        }

        TextView textView = (TextView) findViewById(R.id.textIntro);
        textView.setText(message);
    }

    public void onClickIntro(View view) {
        Intent intent = new Intent();
        if (manufacturer.equalsIgnoreCase(manufacturerXiaomi)) {
            intent.setComponent(new ComponentName("com.miui.securitycenter",
                    "com.miui.permcenter.autostart.AutoStartManagementActivity"));
        } else if (manufacturer.equalsIgnoreCase(manufacturerHuawei)) {
            intent.setComponent(new ComponentName("com.huawei.systemmanager",
                    "com.huawei.systemmanager.optimize.process.ProtectActivity"));
        } else if (manufacturer.equalsIgnoreCase(manufacturerMeizu)) {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", this.getPackageName(), null);
            intent.setData(uri);
        }
        this.startActivity(intent);
    }

    public void onClickNext(View view) {
        Intent intent = new Intent(IntroActivity.this, SecondActivity.class);
        startActivity(intent);
    }

}
