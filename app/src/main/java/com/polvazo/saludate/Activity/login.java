package com.polvazo.saludate.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.polvazo.saludate.Constans.Contants;
import com.polvazo.saludate.Models.userLogin;
import com.polvazo.saludate.R;
import com.polvazo.saludate.Service.ServiceGenerator;
import com.polvazo.saludate.Service.userService;
import com.polvazo.saludate.Util.preferencia;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {

    private Button login;
    private Button salir;
    private EditText usuario;
    private EditText contrasenha;
    private CheckBox recordar;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private String user;
    private String pass;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login = (Button) findViewById(R.id.btn_login);
        recordar = (CheckBox) findViewById(R.id.check_recordar);
        usuario = (EditText) findViewById(R.id.et_username);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        contrasenha = (EditText) findViewById(R.id.et_password);

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            usuario.setText(loginPreferences.getString("username", ""));
            contrasenha.setText(loginPreferences.getString("password", ""));
            recordar.setChecked(true);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(usuario.getWindowToken(), 0);
                loginService();
                login.setEnabled(false);
                if (recordar.isChecked()) {
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.putString("username", user);
                    loginPrefsEditor.putString("password", pass);
                    loginPrefsEditor.commit();
                } else {
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit();
                }


            }
        });


        salir = (Button) findViewById(R.id.btn_salir);
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.a_log_dialogoSalir_title)
                .setMessage(R.string.a_log_dialogoSalir_message)
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        login.super.onBackPressed();
                    }
                }).create().show();
    }

    public void loginService() {

        user = usuario.getText().toString();
        pass = contrasenha.getText().toString();
        userService userservice = ServiceGenerator.createService(userService.class);
        Call<userLogin> call = userservice.getStatus(user, pass);
        call.enqueue(new Callback<userLogin>() {
            @Override
            public void onResponse(Call<userLogin> call, Response<userLogin> response) {
                if (response.isSuccessful()) {
                    Log.i("estado", "ingreso por aqui");
                    userLogin status = response.body();
                    Log.i("estatus", String.valueOf(status.isStatus()));
                    Log.i("username", user);
                    Log.i("password", pass);
                    Log.i("idusuario", String.valueOf(status.getId_user()));
                    String idUser = String.valueOf(status.getId_user());
                    preferencia.Guardar(Contants.ID_USUARIO, idUser, getApplicationContext());
                    Log.i("idusuario", preferencia.obtener(Contants.ID_USUARIO, getApplicationContext()));

                    if (status.isStatus() == true) {
                        Intent intent = new Intent(login.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Toast.makeText(login.this, R.string.a_log_toast_logueado, Toast.LENGTH_SHORT).show();
                        usuario.setText("");
                        contrasenha.setText("");


                    } else {
                        Toast.makeText(login.this, R.string.a_log_toast_autentificacihon, Toast.LENGTH_SHORT).show();
                    }

                } else {
                }
            }

            @Override
            public void onFailure(Call<userLogin> call, Throwable t) {
                login.setEnabled(true);
                Toast.makeText(login.this, "No hay conexion", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
