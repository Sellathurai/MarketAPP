package com.greeno.marketplace;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.communicator.ConfigureLink;
import com.communicator.JsonPostToFetchData;
import com.communicator.JsonRequestData;
import com.communicator.TaskCompleteListener;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.util.CommonClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author SELLATHURAI
 * @Email sella.ragavan@greeno.in,
 * @Created 15 APR 2016
 */

public class RegisterActivity extends AppCompatActivity {


    CommonClass commonClass;
    FloatingActionButton actionButton;
    Toolbar mToolbar;
    private Button sendOTP;
    private Dialog OTPDialog;
    private EditText verifyOTP;
    private AppCompatEditText mobileNumberRegister, nameRegister, passwordRegister, retypeRegister, emailRegister, pincodeRegister;
    private TextInputLayout numberLayout, nameLayout, passwordLayout, retypeLayout, emailLayout, pincodeLayout;
    private String mobileNumber, registerName, registerPassword, userType = "", bufferOTP = "", OTP;
    private RadioGroup radioUserTypeGroup;
    private RadioButton radioUsertypeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register_user);

        setTitle("Register");

        radioUserTypeGroup = (RadioGroup) findViewById(R.id.radioGroup);
        //registerBtn = (Button) findViewById(R.id.registerButton);

        mobileNumberRegister = (AppCompatEditText) findViewById(R.id.mobileNumberRegister);
        nameRegister = (AppCompatEditText) findViewById(R.id.nameRegister);
        passwordRegister = (AppCompatEditText) findViewById(R.id.passwordRegister);
        emailRegister = (AppCompatEditText) findViewById(R.id.emailId);
        pincodeRegister = (AppCompatEditText) findViewById(R.id.pincodeNumber);


        numberLayout = (TextInputLayout) findViewById(R.id.productName);
        nameLayout = (TextInputLayout) findViewById(R.id.categoryLayout);
        passwordLayout = (TextInputLayout) findViewById(R.id.passwordLayout);
        pincodeLayout = (TextInputLayout) findViewById(R.id.pincodelayout);
        emailLayout = (TextInputLayout) findViewById(R.id.emailLayout);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mToolbar.setTitle("User Registration");

        commonClass = new CommonClass(RegisterActivity.this);
        buildActionButton();
        mobileNumberRegister.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailLayout.setError(null);
                numberLayout.setError(null);

                passwordLayout.setError(null);
                nameLayout.setError(null);
                pincodeLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 10) {
                    numberLayout.setError(null);

                }
            }
        });
        nameRegister.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 2) {
                    nameLayout.setError(null);
                }
            }
        });
        pincodeRegister.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 6) {
                    pincodeLayout.setError(null);
                }
            }
        });


    }

    /* validates the fields */
    private Boolean validateFields() {


        if (nameRegister.getText().length() == 0) {
            nameLayout.setError(getResources().getString(R.string.User_name));
            return false;
        } else if (nameRegister.getText().toString().length() < 3) {
            nameLayout.setError(getResources().getString(R.string.Valid_name));
            return false;
        } else if (mobileNumberRegister.getText().length() == 0) {
            numberLayout.setError(getResources().getString(R.string.Mobile_nummber));
            return false;
        } else if (mobileNumberRegister.getText().toString().length() < 10) {
            numberLayout.setError(getResources().getString(R.string.Mobile_verify));
            return false;
        } else if (passwordRegister.getText().length() == 0) {
            passwordLayout.setError(getResources().getString(R.string.Password));
            return false;
        } else if (passwordRegister.getText().toString().length() < 7) {
            passwordLayout.setError(getResources().getString(R.string.Password_min));
            return false;
        } else if (!emailRegister.getText().toString().equalsIgnoreCase("")) {
            if (!emailValidator(emailRegister.getText().toString())) {

                emailLayout.setError(getResources().getString(R.string.Email));
                return false;
            }
        } else if (pincodeRegister.getText().length() == 0) {
            pincodeLayout.setError(getResources().getString(R.string.Pincode));
            return false;
        } else if (pincodeRegister.getText().toString().length() < 6) {
            passwordLayout.setError(getResources().getString(R.string.Pincode_valid));
            return false;
        }


        emailLayout.setError(null);
        numberLayout.setError(null);

        passwordLayout.setError(null);
        nameLayout.setError(null);
        pincodeLayout.setError(null);

        return true;
    }

    /**
     * validate your email address format. Ex-akhi@mani.com
     */
    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    /* access web service */

    private void webServiceTask() {
        JSONObject jsonObject = new JSONObject();

        try {
            // jsonObject.accumulate("shopId", ""+ShopDetailsActivity.SHOP_ID);

            jsonObject.accumulate("name", nameRegister.getText().toString());
            jsonObject.accumulate("mobile", mobileNumberRegister.getText().toString());
            jsonObject.accumulate("id", "0");
            jsonObject.accumulate("email", emailRegister.getText().toString());
            jsonObject.accumulate("password", passwordRegister.getText().toString());
            jsonObject.accumulate("userType", userType);
            jsonObject.accumulate("pincode", pincodeRegister.getText().toString());
            JsonRequestData requestData = new JsonRequestData();
            requestData.setUrl(ConfigureLink.REGISTRATION);//TODO
            System.out.println("Request" + jsonObject.toString());
            System.out.println("Request URL" + ConfigureLink.REGISTRATION);


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

        JsonPostToFetchData task = new JsonPostToFetchData(RegisterActivity.this, requestData);
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

                bufferOTP = jsonObject.getString("otp");
                mobileNumberVerification();
                // toastMessage("Thank you for your valuable request ,We will get back to you soon");

            } else if (status.equalsIgnoreCase("existing")) {

                commonClass.displayToast("This mobile no. is already Exist");

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

    /* OTP verify Dialog*/
    public void mobileNumberVerification() {
        try {
            OTPDialog = new Dialog(RegisterActivity.this);
            // OTPDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            OTPDialog.setContentView(R.layout.otp_verifications);
            OTPDialog.setCanceledOnTouchOutside(false);
            OTPDialog.setCancelable(false);
            OTPDialog.setTitle("Verify your mobile number");
            verifyOTP = (EditText) OTPDialog.findViewById(R.id.OTPnumber);

            sendOTP = (Button) OTPDialog.findViewById(R.id.OTPsend);

            sendOTP.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // verifyOTP();
                    if (commonClass.isInternetConnected()) {
                        webServiceTaskOTPVerify();
                    } else {
                        commonClass.displayToast("No internet connection");
                    }

                }
            });
            OTPDialog.show();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     * Verify OTP number
     */
    private void verifyOTP() {

        if (bufferOTP.equalsIgnoreCase(verifyOTP.getText().toString())) {


            OTPDialog.dismiss();
            commonClass.displayToast(getResources().getString(R.string.Registration));
            finish();

        } else {
            commonClass.displayToast("Please check your OTP");
        }
    }

    /* action button is created */
    private void buildActionButton() {
        //  https://github.com/oguzbilgener/CircularFloatingActionMenu
        ImageView icon = new ImageView(this);
        icon.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("ic_check_white_48dp", "drawable", getPackageName())));

        FloatingActionButton actionButton = new FloatingActionButton.Builder(RegisterActivity.this)
                .setContentView(icon)
                .setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_blue_purple))
                .build();

        actionButton.setVisibility(View.VISIBLE);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    int selectedId = radioUserTypeGroup.getCheckedRadioButtonId();
                    radioUsertypeButton = (RadioButton) findViewById(selectedId);
                    userType = radioUsertypeButton.getText().toString();

                    if (userType.toString().equalsIgnoreCase("Sell"))
                        userType = "S";

                    else
                        userType = "B";

                    mobileNumber = mobileNumberRegister.getText().toString();
                    registerName = nameRegister.getText().toString();
                    registerPassword = passwordRegister.getText().toString();
                    if (commonClass.isInternetConnected()) {
                        webServiceTask();
                    } else {
                        commonClass.displayToast("Internet is required.");
                    }
                }

            }
        });
    }

    /* handles the visibility of the action button*/
    private void showActionButton() {
        if (mobileNumberRegister.getText().length() == 0 && nameRegister.getText().length() == 0
                && passwordRegister.getText().length() == 0 && retypeRegister.getText().length() == 0)
            actionButton.setVisibility(View.INVISIBLE);
        else
            actionButton.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /* access web service to verify OTP*/

    private void webServiceTaskOTPVerify() {
        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.accumulate("otp", verifyOTP.getText().toString());

            JsonRequestData requestData = new JsonRequestData();
            requestData.setUrl(ConfigureLink.OTP_VERIFY);//DODO
            System.out.println("Request" + jsonObject.toString());

            requestData.setRequestdata(jsonObject.toString());

            configureOTPVerifyTask(requestData);


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Configure asyntask and proceed with listener
     */
    private void configureOTPVerifyTask(final JsonRequestData requestData) {

        JsonPostToFetchData task = new JsonPostToFetchData(RegisterActivity.this, requestData);
        task.setFetchMyData(new TaskCompleteListener<JsonRequestData>() {
            @Override
            public void onTaskComplete(JsonRequestData jsonRequestData) {
                parseOTPJsonResponse(jsonRequestData);

            }

        });
        task.execute("");

    }

    /**
     * Parse json data for OTP
     */
    private void parseOTPJsonResponse(JsonRequestData requestData) {
        try {
            JSONObject jsonObject = new JSONObject(requestData.getResult());
            System.out.println("Response " + requestData.getResult());
            String status = jsonObject.getString("status");

            if (status.equalsIgnoreCase("success")) {
                commonClass.displayToast(getResources().getString(R.string.Registration));
                commonClass.displayToast("Your mobile number has been verified successfully");
                commonClass.displayToast("Login credentials has sent to registered mobile number");
                OTPDialog.dismiss();
                finish();

            } else {

                commonClass.displayToast("Please check your OTP");

            }

        } catch (Exception e) {
            commonClass.displayToast(getResources().getString(R.string.Error_message));
            //finish();
        }

    }

}


