package com.greeno.marketplace;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.communicator.ConfigureLink;
import com.communicator.JsonPostToFetchData;
import com.communicator.JsonRequestData;
import com.communicator.TaskCompleteListener;
import com.util.CommonClass;
import com.util.GenericAction;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;

/**
 * @author SELLATHURAI
 * @Email sella.ragavan@greeno.in,
 * @Created 24 MAR 2016
 */

@SuppressWarnings("deprecation")
public class LoginActivity extends AppCompatActivity {

    private final String TAG_DONE = "LoginSubmit";
    private static final String TAG_FORGOT_PWD = "ForgotPassword";
    public static final String USER_NAME = "username";
    public static final String USER_ID = "user_Id";
    public static final String USER_MOBILE = "mobile";
    public static final String PASSWORD = "password";
    public static final String STATUS = "satus";
    public static final String LOGIN_PREF = "login_pref";

    AppCompatEditText mobileNumberLogin, passwordLogin;  //mobileNumberLogin
    TextInputLayout numberLayout, passwordLayout;
    //    TextView forgotPassword, mobileCode;
    boolean isItForgotPwd = false;
    Button registration, login;
    int countSeconds = 0, versionCode = 0;
    long coordinatorId = 0;
    CommonClass commonClass;
    Toolbar mToolbar;
    Timer T;
    private TextView forgotPasswrdBtn;
    private Dialog forgotPaswwordDialog;
    Typeface typeface;
    private Button submitMobile;

    private EditText mobileNumberForgot;

    @Override
    public void onBackPressed() {
        LoginActivity.this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        mobileNumberLogin = (AppCompatEditText) findViewById(R.id.mobileNumberLogin);
        passwordLogin = (AppCompatEditText) findViewById(R.id.passwordLogin);
        numberLayout = (TextInputLayout) findViewById(R.id.productName);
        passwordLayout = (TextInputLayout) findViewById(R.id.passwordLayout);

        forgotPasswrdBtn = (TextView) findViewById(R.id.forgotPassword);
        login = (Button) findViewById(R.id.login);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        mToolbar.setTitle("Market Login");

        commonClass = new CommonClass(getApplicationContext());
        forgotPasswrdBtn.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        final SharedPreferences userDetails = getSharedPreferences(
                LoginActivity.LOGIN_PREF, MODE_PRIVATE);
        final SharedPreferences.Editor editor = userDetails.edit();

        if (getSharedPreferences(LoginActivity.LOGIN_PREF, MODE_PRIVATE)
                .getBoolean(LoginActivity.STATUS, false)) {
            moveToMainScreen();
        }

        mobileNumberLogin.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                numberLayout.setError(null);
                passwordLayout.setError(null);
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        passwordLogin.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                numberLayout.setError(null);
                passwordLayout.setError(null);
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        forgotPasswrdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mobileNumberVerification();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateNumber()) {
                    webServiceTask();
                }
            }
        });
    }



    /* Validate mobile number and Password */

    protected boolean validateNumber() {
        if (mobileNumberLogin.getText().length() == 0) {
            numberLayout.setError("Please enter the mobile number.");
            return false;
        } else if (mobileNumberLogin.getText().length() < 10) {
            numberLayout.setError("Mobile number should be 10 digit");
            return false;
        } else if (passwordLogin.getText().toString().length() == 0) {
            passwordLayout.setError("Password is required.");
            return false;
        } else if (passwordLogin.getText().toString().length() < 7) {
            passwordLayout.setError("Password should be minimum of 7 characters.");
            return false;
        }
        numberLayout.setError(null);
        passwordLayout.setError(null);
        return true;
    }
  /* Validate mobile number  */

    protected boolean validateNumberForgotPassword() {
        if (mobileNumberForgot.getText().length() == 0) {

            commonClass.displayToast("Please enter the mobile number");
            return false;
        } else if (mobileNumberForgot.getText().length() < 10) {

            commonClass.displayToast("Mobile number should be 10 digit");
            return false;
        }
        return true;
    }

    /* login page nagivation   */
    public void moveToMainScreen() {
        Intent moveToNextScreen = new Intent(LoginActivity.this, MainActivity.class);
        LoginActivity.this.startActivity(moveToNextScreen);
        //LoginActivity.this.finish();
    }
    /* registration page  */

    public void moveToRegistration() {
        Intent moveToNextScreen = new Intent(LoginActivity.this, RegisterActivity.class);
        LoginActivity.this.startActivity(moveToNextScreen);

    }


    /* access web service for login */
    private void webServiceTask() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.accumulate("mobile", mobileNumberLogin.getText().toString());
            jsonObject.accumulate("password", passwordLogin.getText().toString());
            JsonRequestData requestData = new JsonRequestData();
            requestData.setUrl(ConfigureLink.LOGIN);//TODO
            System.out.println("Request" + jsonObject.toString());
            requestData.setRequestdata(jsonObject.toString());
            configureTask(requestData);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Configure asyntask and proceed with listener
     */
    private void configureTask(final JsonRequestData requestData) {

        JsonPostToFetchData task = new JsonPostToFetchData(LoginActivity.this, requestData);
        task.setFetchMyData(new TaskCompleteListener<JsonRequestData>() {
            @Override
            public void onTaskComplete(JsonRequestData jsonRequestData) {
                parseJsonResponse(jsonRequestData);

            }

        });
        task.execute("");

    }

    /**
     * Parse json data
     */
    private void parseJsonResponse(JsonRequestData requestData) {
        try {
            JSONObject jsonObject = new JSONObject(requestData.getResult());
            System.out.println("Response " + requestData.getResult());
            String status = jsonObject.getString("status");

            if (status.equalsIgnoreCase("success")) {

                String userId = jsonObject.getString("userId");
                String userType = jsonObject.getString("userType");
                String username = jsonObject.getString("name");
                GenericAction.USER_ID = userId;
                commonClass.displayToast("User has logged in successfully");
                SharedPreferences prefs = getSharedPreferences(LoginActivity.LOGIN_PREF,
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(LoginActivity.USER_ID, userId);
                editor.putString(LoginActivity.USER_NAME, username);
                editor.putString(LoginActivity.USER_MOBILE, mobileNumberLogin.getText().toString());
                editor.putBoolean(LoginActivity.STATUS, true);
                editor.apply();
                editor.commit();

                mobileNumberLogin.setText("");
                passwordLogin.setText("");
                moveToMainScreen();
            } else if (status.equalsIgnoreCase("new")) {
                commonClass.displayToast("This mobile no. is not registered");
            } else {

                commonClass.displayToast("Mobile number or password is Incorrect");

            }

        } catch (Exception e) {
            commonClass.displayToast("Could not proceed .Please try after some time");
            //finish();
        }

    }


    /**
     * Showing toast message
     */
    public void toastMessage(String alert) {
        Toast toast = Toast.makeText(getApplicationContext(), alert,
                Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.register_user) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    /* Mobile verifications*/
    public void mobileNumberVerification() {
        try {
            forgotPaswwordDialog = new Dialog(LoginActivity.this);
            //   forgotPaswwordDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            forgotPaswwordDialog.setContentView(R.layout.forgot_password);
            forgotPaswwordDialog.setCanceledOnTouchOutside(false);
            // forgotPaswwordDialog.setCancelable(false);
            forgotPaswwordDialog.setTitle("Forgot Password          ");

            mobileNumberForgot = (EditText) forgotPaswwordDialog.findViewById(R.id.mobileNumberForgot);

            submitMobile = (Button) forgotPaswwordDialog.findViewById(R.id.submit);

            submitMobile.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (validateNumberForgotPassword()) {
                        webServiceTaskForgotPassword();
                    }

                }
            });
            forgotPaswwordDialog.show();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    /* access web service for Forgot password*/

    private void webServiceTaskForgotPassword() {
        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.accumulate("mobile", mobileNumberForgot.getText().toString());

            JsonRequestData requestData = new JsonRequestData();
            requestData.setUrl(ConfigureLink.FORGOT_PASSWORD);//DODO
            System.out.println("Request" + jsonObject.toString());

            requestData.setRequestdata(jsonObject.toString());

            configureForgotPaswordTask(requestData);


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Configure asyntask and proceed with listener
     */
    private void configureForgotPaswordTask(final JsonRequestData requestData) {

        JsonPostToFetchData task = new JsonPostToFetchData(LoginActivity.this, requestData);
        task.setFetchMyData(new TaskCompleteListener<JsonRequestData>() {
            @Override
            public void onTaskComplete(JsonRequestData jsonRequestData) {
                parseForgotJsonResponse(jsonRequestData);

            }

        });
        task.execute("");

    }

    /**
     * Parse json data forgot password
     */
    private void parseForgotJsonResponse(JsonRequestData requestData) {
        try {
            JSONObject jsonObject = new JSONObject(requestData.getResult());
            System.out.println("Response " + requestData.getResult());
            String status = jsonObject.getString("status");

            if (status.equalsIgnoreCase("success")) {
                commonClass.displayToast("Your password has been sent to registered mobile number");
                forgotPaswwordDialog.dismiss();

            } else {

                commonClass.displayToast("This mobile no. is not registered");

            }

        } catch (Exception e) {
            commonClass.displayToast("Could not proceed .Please try after some time");
            //finish();
        }

    }


}



