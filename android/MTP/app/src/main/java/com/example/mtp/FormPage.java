package com.example.mtp;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.common.collect.Range;

import org.json.JSONException;
import org.json.JSONObject;

public class FormPage extends AppCompatActivity {
    EditText etName,etKms,etSpeed,etWall21;
    RadioGroup rgCategoryEvent,rgCrossTraining,rgCategory;
    TextView tvmarathonName;
    String Namestr,Kmsstr,Speedstr,Wall21str;


    //defining AwesomeValidation object
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_page);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //Assigning edit text variables
        etName = findViewById(R.id.et_name);
        etKms = findViewById(R.id.et_kms);
        etSpeed = findViewById(R.id.et_speed);
        etWall21 = findViewById(R.id.et_wall21);

        //Assigning text view variables
        tvmarathonName = findViewById(R.id.tv_marathonName);

        //Initializing radio group variables
        rgCategoryEvent = (RadioGroup) findViewById(R.id.rg_categoryEvent);
        rgCrossTraining = (RadioGroup) findViewById(R.id.rg_CrossTraining);
        rgCategory = (RadioGroup) findViewById(R.id.rg_category);
    }
    public void MoveResult(View view) {
        String name=etName.getText().toString();
        String mname = tvmarathonName.getText().toString();

        int CategoryEvent = rgCategoryEvent.getCheckedRadioButtonId();
        RadioButton rd_CategoryEvent = findViewById(CategoryEvent);

        int CrossTraining = rgCrossTraining.getCheckedRadioButtonId();
        RadioButton rd_CrossTraining = findViewById(CrossTraining);

        int Category = rgCrossTraining.getCheckedRadioButtonId();
        RadioButton rd_Category = findViewById(Category);

        //adding validation to edittexts
        //validation for name
        awesomeValidation.addValidation(this, R.id.et_name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$",R.string.nameerror);
        //awesomeValidation.addValidation(this, R.id.tv_marathonName, "Prague17",R.string.mnameerror);
        //validation for float values
        awesomeValidation.addValidation(this, R.id.et_kms, Range.closed(10.0f, 150.0f), R.string.kmserror);
        awesomeValidation.addValidation(this, R.id.et_speed, Range.closed(5.0f, 20.0f), R.string.speederror);
        awesomeValidation.addValidation(this, R.id.et_wall21, Range.closed(1.0f, 5.0f), R.string.wall21error);

        if(CategoryEvent == -1) {
            Toast.makeText(FormPage.this, "please enter the Category Event Value", Toast.LENGTH_SHORT).show();
        } else {
            String CEstr = rd_CategoryEvent.getText().toString();
        }
        if (CrossTraining == -1) {
            Toast.makeText(FormPage.this, "please enter the Cross Training Value", Toast.LENGTH_SHORT).show();
        } else{
            String CTstr = rd_CrossTraining.getText().toString();
        }
        if (Category == -1) {
            Toast.makeText(FormPage.this, "please enter the Category Value", Toast.LENGTH_SHORT).show();
        } else {
            String Catstr = rd_Category.getText().toString();
        }

        //validation
        if (awesomeValidation.validate()) {

            if( !TextUtils.isEmpty(Namestr) && ( !TextUtils.isEmpty(Kmsstr) && ( !TextUtils.isEmpty(Speedstr) &&( !TextUtils.isEmpty(Wall21str)))))
            {
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                final String url = "";
                JSONObject postParams = new JSONObject();
                try {
                    postParams.put("Name", etName);
                    postParams.put("Category of the event", rgCategoryEvent);
                    postParams.put("Marathon Name", tvmarathonName);
                    postParams.put("Kms", etKms);
                    postParams.put("Speed", etSpeed);
                    postParams.put("Category of the athlete", rgCategory);
                    postParams.put("CrossTraining ", rgCrossTraining);
                    postParams.put("Name", etWall21);
                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, postParams, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("On Response", "onResponse: " + response.toString());
                        Intent i=new Intent(FormPage.this,ResPage.class);
                        i.putExtra("key",name);
                        i.putExtra("key",response.toString());
                        startActivity(i);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("On Error",error.toString());
                        Toast.makeText(FormPage.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(jsonObjectRequest);
            }
        }

    }
}
