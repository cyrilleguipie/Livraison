package com.foodaclic.livraison.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.foodaclic.livraison.BuildConfig;
import com.foodaclic.livraison.MainApplication;
import com.foodaclic.livraison.R;
import com.foodaclic.livraison.service.AuthService;
import com.foodaclic.livraison.service.rest.RestClient;
import com.foodaclic.livraison.utils.Constants;
import com.foodaclic.livraison.utils.CustomDialog;

import com.foodaclic.livraison.utils.PermissionUtils;
import com.foodaclic.livraison.utils.Utils;
import com.foodaclic.livraison.utils.event.AndroidBusProvider;
import com.foodaclic.livraison.utils.event.NetworkOperationEvent;
import com.fasterxml.jackson.databind.JsonNode;
import com.squareup.otto.Subscribe;


public class AuthActivity extends AppCompatActivity {

    private static final String TAG = "AuthActivity";
    private ProgressDialog progressDialog;
    private EditText editLogin, editPassword;
    private Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        initViews();
        handleViewEvents();
        PermissionUtils.askForPermissions(this);
    }


    private void validate() {
        String login = editLogin.getText().toString();
        String password = editPassword.getText().toString();

        if (login.isEmpty() || password.isEmpty()) {
            Toast.makeText(AuthActivity.this, getString(R.string.blank), Toast.LENGTH_SHORT).show();
            if (login.isEmpty()) {
                editLogin.setError(getString(R.string.emailIsEmpty));
            }
            if (password.isEmpty()) {
                editPassword.setError(getString(R.string.passwordIsEmpty));
            }
        } else {
            if (password.length() < 4) {
                editPassword.setError(getString(R.string.char_min));
            } else {
                startService(new Intent(AuthActivity.this, AuthService.class)
                .putExtra("login", login)
                .putExtra("password", password));
            }
        }
    }



    private void initViews() {

        editLogin =  findViewById(R.id.edit_login);
        editPassword = findViewById(R.id.edit_password);
        btnLogin =  findViewById(R.id.btn_login);
    }

    private void handleViewEvents() {

        editLogin.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                // TODO Auto-generated method stub
                if (actionId == EditorInfo.IME_ACTION_NEXT) {

                    editPassword.requestFocus();
                    return true;
                }
                return false;
            }

        });

        editPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                // TODO Auto-generated method stub
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    validate();

                    return true;

                }
                return false;
            }

        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validate();

            }
        });

    }



    @Override public void onPause() {

        AndroidBusProvider.getInstance().unregister(this);
        super.onPause();
    }

    @Override protected void onResume() {


        AndroidBusProvider.getInstance().register(this);
        super.onResume();
    }

    @Subscribe public void onNetworkOperationEvent(NetworkOperationEvent event) {

        // Log.i(LOG_TAG, "I received an event : " + event.getClass().getName() + " : " + event.getMessage());
        if (event.hasStarted()) {
            progressDialog = ProgressDialog.show(AuthActivity.this, "Connexion en cours", "Veuillez patienter");
        }else if (event.hasFinishedAll()) {
            progressDialog.dismiss();
                startActivity(new Intent(AuthActivity.this, MainActivity.class));
                finish();
        } else if (event.hasFailed()) {
            // hideProgressBar();
            Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }



}
