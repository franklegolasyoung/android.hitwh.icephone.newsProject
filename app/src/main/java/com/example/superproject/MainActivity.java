package com.example.superproject;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    //TextView responseText;    //数据测试
    String phone;
    String passwd;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login = findViewById(R.id.btn1);
        Button register = findViewById(R.id.btn2);
        final EditText phoneT = findViewById(R.id.phone);
        final EditText passwdT = findViewById(R.id.passwd);

        passwdT.setTransformationMethod(PasswordTransformationMethod.getInstance());
        phone = phoneT.getText().toString().trim();
        passwd = passwdT.getText().toString().trim();
        phoneT.addTextChangedListener(mTextWatcher);
        passwdT.addTextChangedListener(mTextWatcher);

        login.setOnClickListener(new View.OnClickListener() {//登录点击事件
            @Override
            public void onClick(View v) {
                phone = phoneT.getText().toString();
                passwd = passwdT.getText().toString();
                if(phone.length()==0)   //健壮性检验
                { Toast.makeText(MainActivity.this,"请输入手机号", Toast.LENGTH_SHORT).show();}
                else if(passwd.length()==0)
                { Toast.makeText(MainActivity.this,"请输入密码", Toast.LENGTH_SHORT).show();}
                else {
                    sendRequest(loginNet(),true);   //网络请求
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {//注册点击事件
            @Override
            public void onClick(View v) {
                phone = phoneT.getText().toString();
                passwd = passwdT.getText().toString();
                if(phone.length()==0)   //健壮性检验
                { Toast.makeText(MainActivity.this,"请输入手机号", Toast.LENGTH_SHORT).show();}
                else if(passwd.length()==0)
                { Toast.makeText(MainActivity.this,"请输入密码", Toast.LENGTH_SHORT).show();}
                else {
                    sendRequest(registerNet(),false);   //网络请求
                }
            }
        });
    }

    private String loginNet(){
        return "https://www.apiopen.top/login?key=00d91e8e0cca2b76f515926a36db68f5";
    }
    private String registerNet(){
        return "https://www.apiopen.top/createUser?key=00d91e8e0cca2b76f515926a36db68f5";
    }

    TextWatcher mTextWatcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO Auto-generated method stub
        }
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
        }
    };

    private void sendRequest(final String net,final boolean a) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("phone", phone).add("passwd", passwd).build();
                    Request request = new Request.Builder().url(net).post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    //先调用JSONObject检测code是多少
                    JSONObject jsonObject = new JSONObject(responseData);
                    int coded = jsonObject.getInt("code");
                    //Log.d("MainActivity", "code is " + coded);    //数据测试
                    if(coded == 200)    //code200成功
                        if(a) { //登录成功
                            Gson gson = new Gson();
                            Details details = gson.fromJson(responseData, Details.class);
                            //showResponse(details.getMsg());
                            Intent intent = new Intent(MainActivity.this, showNews.class);
                            intent.putExtra("phone",phone);
                            intent.putExtra("time",details.data.getCreateTime());
                            startActivity(intent);
                        }
                        else {  //注册成功
                            EditText phoneT = findViewById(R.id.phone);
                            EditText passwdT = findViewById(R.id.passwd);
                            phoneT.setText("");
                            passwdT.setText("");
                            Looper.prepare();
                            Toast.makeText(MainActivity.this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    //code202出错
                    else {
                        Gson gson = new Gson();
                        Wrong wrong = gson.fromJson(responseData, Wrong.class);
                        //showResponse(wrong.getMsg());
                        switch (wrong.getMsg()) {
                            case "用户已注册！":
                                Looper.prepare();
                                Toast.makeText(MainActivity.this, "账号已注册，请直接登录", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                                break;
                            case "用户不存在！":
                                Looper.prepare();
                                Toast.makeText(MainActivity.this, "账号不存在，请先注册", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                                break;
                            default:
                                Looper.prepare();
                                Toast.makeText(MainActivity.this, wrong.getMsg(), Toast.LENGTH_SHORT).show();
                                Looper.loop();
                                break;
                        }
                    }
                } catch (Exception e) { e.printStackTrace(); }
            }
        }).start();
    }
    /*private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() { //将结果显示到界面上 数据测试
                responseText.setText(response);
            }
        });
    }*/
}