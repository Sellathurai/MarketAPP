package com.greeno.marketplace;

/**
 * @author SELLATHURAI
 * @Email sella.ragavan@greeno.in,
 * @Created 15 APR 2016
 */

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.communicator.ConfigureLink;
import com.communicator.JsonPostToFetchData;
import com.communicator.JsonRequestData;
import com.communicator.TaskCompleteListener;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.util.CommonClass;
import com.util.GenericAction;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONException;
import org.json.JSONObject;


public class PostRequirement extends AppCompatActivity {
    Toolbar mToolbar;
    private String[] CATEGORY_LIST = {"Vegetable", "Grocery", "Fruits", "Oil", "Others"};
    private String[] UNIT_LIST = {"Kg", "Ton", "Quintal", "Litre"};
    private String[] FREQUENCY_LIST = {"Daily", "Weekly", "Monthly", "Yearly"};
    private AppCompatEditText categoryRegister, productnNameRegister, unitReg, quantityReg, priceReg, frequencyReg, descReg;
    private TextInputLayout categoryLayout, productLayout, unitLayout, quantityLayout, priceLayout, frequencyLayout, descLayout;
    private MaterialBetterSpinner categoryRegisterSpinner, unitRegSpinner, frequencyRegSpinner;
    private String categorySpinnerValue = "", unitSpinnerValue = "", freqSpinnerValue = "", userType = "";
    private RadioGroup radioUserTypeGroup;
    private RadioButton radioUsertypeButton;
    private CommonClass commonClass;

    public PostRequirement() {
        // Required empty public constructor TODO
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.post_buy_sell);

        radioUserTypeGroup = (RadioGroup) findViewById(R.id.radioGroup);
        productnNameRegister = (AppCompatEditText) findViewById(R.id.productNameReg);
        quantityReg = (AppCompatEditText) findViewById(R.id.quantity);
        priceReg = (AppCompatEditText) findViewById(R.id.priceReg);
        descReg = (AppCompatEditText) findViewById(R.id.descReg);


        categoryLayout = (TextInputLayout) findViewById(R.id.categoryRegLayout);
        productLayout = (TextInputLayout) findViewById(R.id.productNameLayout);
        unitLayout = (TextInputLayout) findViewById(R.id.unitRegLayout);
        quantityLayout = (TextInputLayout) findViewById(R.id.quantityRegLayout);

        priceLayout = (TextInputLayout) findViewById(R.id.priceLayout);
        frequencyLayout = (TextInputLayout) findViewById(R.id.frequencyRegRegLayout);
        descLayout = (TextInputLayout) findViewById(R.id.descLayout);

        frequencyRegSpinner = (MaterialBetterSpinner) findViewById(R.id.frequencyReg);
        categoryRegisterSpinner = (MaterialBetterSpinner) findViewById(R.id.categoryReg);
        unitRegSpinner = (MaterialBetterSpinner) findViewById(R.id.unitReg);

        loadSpinnerData();
        commonClass = new CommonClass(this);
        buildActionButton();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mToolbar.setTitle("Post Requirement");


        categoryRegisterSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categorySpinnerValue = parent.getItemAtPosition(position).toString();

            }
        });

        unitRegSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                unitSpinnerValue = parent.getItemAtPosition(position).toString();

            }
        });
        frequencyRegSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                freqSpinnerValue = parent.getItemAtPosition(position).toString();

            }
        });


        productnNameRegister.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                productLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        quantityReg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                quantityLayout.setError(null);

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith("0")) {
                    quantityReg.setText("");
                }

            }
        });

        priceReg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                priceReg.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith("0")) {
                    priceReg.setText("");
                }

            }
        });
        descReg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                descLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    /* Load Spinner data*/
    public void loadSpinnerData() {
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, CATEGORY_LIST);
        ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, UNIT_LIST);
        ArrayAdapter<String> frequecnyAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, FREQUENCY_LIST);

        categoryRegisterSpinner.setAdapter(categoryAdapter);
        unitRegSpinner.setAdapter(unitAdapter);
        frequencyRegSpinner.setAdapter(frequecnyAdapter);
    }

    /* Action button is created */
    private void buildActionButton() {

        ImageView icon = new ImageView(this);
        icon.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("ic_check_white_48dp", "drawable", this.getPackageName())));

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
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
                    if (userType.equalsIgnoreCase("I want to sell"))
                        userType = "S";
                    else {
                        userType = "B";
                    }

                    if (commonClass.isInternetConnected()) {
                        taskToPostData();
                    } else {
                        commonClass.displayToast("Internet connection is required.");
                    }
                }

            }
        });
    }

    /* Validates the fields */
    private Boolean validateFields() {


        if (categorySpinnerValue.equalsIgnoreCase("")) {
            commonClass.displayToast(getResources().getString(R.string.category));
            categoryRegisterSpinner.requestFocus();
            return false;
        } else if (productnNameRegister.getText().toString().length() == 0) {
            productLayout.setError(getResources().getString(R.string.product));
            productnNameRegister.setFocusable(true);
            productnNameRegister.requestFocus();
            return false;
        } else if (unitSpinnerValue.equalsIgnoreCase("")) {
            commonClass.displayToast(getResources().getString(R.string.unit));
            unitRegSpinner.setFocusable(true);
            unitRegSpinner.requestFocus();
            return false;
        } else if (quantityReg.getText().toString().length() == 0) {
            quantityLayout.setError(getResources().getString(R.string.quantity));
            quantityReg.setFocusable(true);
            quantityReg.requestFocus();
            return false;
        } else if (priceReg.getText().length() == 0) {
            priceLayout.setError(getResources().getString(R.string.price));
            priceReg.setFocusable(true);
            priceReg.requestFocus();

            return false;
        } else if (freqSpinnerValue.equalsIgnoreCase("")) {

            commonClass.displayToast(getResources().getString(R.string.frequency));
            frequencyRegSpinner.setFocusable(true);
            frequencyRegSpinner.requestFocus();
            return false;
        } else if (descReg.getText().length() == 0) {
            descLayout.setError(getResources().getString(R.string.description));
            descReg.setFocusable(true);
            descReg.requestFocus();
            return false;
        }
        productLayout.setError(null);
        categoryLayout.setError(null);
        unitLayout.setError(null);
        quantityLayout.setError(null);
        priceLayout.setError(null);
        frequencyLayout.setError(null);
        descLayout.setError(null);
        return true;
    }



    /* access web service to verify OTP*/

    private void taskToPostData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("reqStatus", "no");
            jsonObject.accumulate("id", "0");
            jsonObject.accumulate("category", categorySpinnerValue);
            jsonObject.accumulate("daysCount", "1");
            jsonObject.accumulate("productName", productnNameRegister.getText().toString());
            jsonObject.accumulate("unit", unitSpinnerValue);
            jsonObject.accumulate("quantity", quantityReg.getText().toString());
            jsonObject.accumulate("price", priceReg.getText().toString());
            jsonObject.accumulate("frequency", freqSpinnerValue);
            jsonObject.accumulate("description", descReg.getText().toString());
            jsonObject.accumulate("userType", userType);
            jsonObject.accumulate("userId", GenericAction.USER_ID);
            JsonRequestData requestData = new JsonRequestData();
            requestData.setUrl(ConfigureLink.POST_DATA);
            System.out.println("Request" + jsonObject.toString());
            requestData.setRequestdata(jsonObject.toString());
            configurePostReqTask(requestData);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Configure Asyntask and proceed with listener
     */
    private void configurePostReqTask(final JsonRequestData requestData) {

        JsonPostToFetchData task = new JsonPostToFetchData(this, requestData);
        task.setFetchMyData(new TaskCompleteListener<JsonRequestData>() {
            @Override
            public void onTaskComplete(JsonRequestData jsonRequestData) {
                parsePostJsonResponse(jsonRequestData);

            }

        });
        task.execute("");

    }

    /**
     * Parse json data for POST
     */
    private void parsePostJsonResponse(JsonRequestData requestData) {
        try {
            JSONObject jsonObject = new JSONObject(requestData.getResult());
            System.out.println("Response " + requestData.getResult());
            String status = jsonObject.getString("status");

            if (status.equalsIgnoreCase("success")) {
                commonClass.displayToast(getResources().getString(R.string.product_Reg));
                //moveToBuyListPage();
                finish();

            } else {
                commonClass.displayToast(getResources().getString(R.string.product_Not_reg));

            }

        } catch (Exception e) {
            commonClass.displayToast(getResources().getString(R.string.Error_message));

        }

    }

    /* *
    Menu creation and option
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
