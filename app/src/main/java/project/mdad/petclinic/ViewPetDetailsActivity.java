package project.mdad.petclinic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewPetDetailsActivity extends AppCompatActivity {

    EditText txtPetName;
    EditText txtGender;
    EditText txtDOB;
    EditText txtBreed;
    EditText txtWeight;

    Button btnEdit;
    // Response
    String responseServer;

    JSONObject json = null;

    String pid;
    static InputStream is = null;

    // single product url
    private static final String url_pet_details = MainActivity.ipBaseAddress + "/get_pet_details.php";



    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PETDETAILS = "petDetails";
    private static final String TAG_PID = "pid";
    private static final String TAG_PETNAME = "petName";
    private static final String TAG_GENDER = "gender";
//    private static final String TAG_DOB = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        private static final String TAG_DOB = "dob";
    private static final String TAG_BREED = "breed";
    private static final String TAG_WEIGHT = "weight";


    private static String petPetName = "";
    private static String petGender = "";
    private static String petDOB = "";
    private static String petBreed = "";
    private static String petWeight = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pet_details);

        Log.i("url_product_details", url_pet_details);
        // getting product details from intent
        Intent i = getIntent();

        // getting product id (pid) from intent
        pid = i.getStringExtra(TAG_PID);

        // Getting complete product details in background thread

        JSONObject dataJson = new JSONObject();
        try{
            dataJson.put("pid", pid);
            //     dataJson.put("password", "def");

        }catch(JSONException e){

        }

        postData(url_pet_details,dataJson,1 );

    }

    public void postData(String url, final JSONObject json, final int option){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest json_obj_req = new JsonObjectRequest(
                Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                switch (option){
                    case 1:checkResponseEditProduct(response); break;
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
//                String alert_message;
//                alert_message = error.toString();
//                showAlertDialogue("Error", alert_message);
            }

        });
        requestQueue.add(json_obj_req);
    }

    public void checkResponseEditProduct(JSONObject response)
    {
        try {
            if(response.getInt(TAG_SUCCESS)==1){
                // successfully received product details
                JSONArray petObj = response.getJSONArray(TAG_PETDETAILS); // JSON Array
                // get first product object from JSON Array
                JSONObject petDetail = petObj.getJSONObject(0);
                petPetName=petDetail.getString(TAG_PETNAME);
                petGender=petDetail.getString(TAG_GENDER);
                petDOB=petDetail.getString(TAG_DOB);
                petBreed=petDetail.getString(TAG_BREED);
                petWeight=petDetail.getString(TAG_WEIGHT);


//                Log.i("---Prod details",prodName+"  "+prodPrice+"  "+prodDesc);
                txtPetName = (EditText) findViewById(R.id.etPetName);
                txtGender = (EditText) findViewById(R.id.etGender);
                txtDOB = (EditText) findViewById(R.id.etDOB);
                txtBreed = (EditText) findViewById(R.id.etBreed);
                txtWeight = (EditText) findViewById(R.id.etWeight);


                // display product data in EditText
                txtPetName.setText(petPetName);
                txtGender.setText(petGender);
                txtDOB.setText(petDOB);
                txtBreed.setText(petBreed);
                txtWeight.setText(petWeight);

            }else{
                // product with pid not found
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }


    }

}
