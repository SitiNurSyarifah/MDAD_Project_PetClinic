package project.mdad.petclinic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class ViewPetParticularsActivity extends AppCompatActivity {
    EditText editName;
    EditText editGender;
    EditText editDOB;
    EditText editBreed;
    EditText editWeight;
    Button btnUpdate;
    Button btnView;
    // Response
    String responseServer;

    JSONObject json=null;

    String pid;
    static InputStream is = null;
    // Progress Dialog
    private ProgressDialog pDialog;



    // single product url
    private static final String url_pet_details = MainActivity.ipBaseAddress+"/get_user_pets.php";

    // url to update product
    private static final String url_update_pet = MainActivity.ipBaseAddress+"/update_pets.php";

    // url to delete product
    private static final String url_medical_records = MainActivity.ipBaseAddress+"/get_medical_record.php";
    // 152.226.144.250
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PET = "pet";
    private static final String TAG_PID = "pid";
    private static final String TAG_PETNAME = "petName";
    private static final String TAG_GENDER = "gender";
    private static final String TAG_DATE_OF_BIRTH = "date_of_birth";
    private static final String TAG_BREED = "breed";
    private static final String TAG_WEIGHT = "weight";

    private static String petName="";
    private static String gender="";
    private static String date_of_birth="";
    private static String breed="";
    private static String weight="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pet_particulars);
        setContentView(R.layout.activity_medical_records);

        Log.i("url_product_details", url_pet_details);
        // save button
        btnUpdate = (Button) findViewById(R.id.btnEdit);

        Log.i("url_product_details", url_medical_records);
        btnView = (Button) findViewById(R.id.btnView);

        // getting product details from intent
        Intent i = getIntent();

        // getting product id (pid) from intent
        pid = i.getStringExtra(TAG_PID);

        //  Log.i("----------Extra:::",pid);



        // Getting complete product details in background thread

        JSONObject dataJson = new JSONObject();
        try{
            dataJson.put("pid", pid);
            //     dataJson.put("password", "def");

        }catch(JSONException e){

        }

        postData(url_pet_details,dataJson,1 );


        // save button click event
        btnUpdate.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View arg0) {

                // getting updated data from EditTexts
                String petName = editName.getText().toString();
                String gender = editGender.getText().toString();
                String date_of_birth = editDOB.getText().toString();
                String breed = editBreed.getText().toString();
                String weight = editWeight.getText().toString();

                pDialog = new ProgressDialog(ViewPetParticularsActivity.this);
                pDialog.setMessage("Updating pet detail ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();

                // starting background task to update product
                JSONObject dataJson = new JSONObject();
                try{
                    dataJson.put("pid", pid);
                    dataJson.put(TAG_PETNAME, petName);
                    dataJson.put(TAG_GENDER, gender);
                    dataJson.put(TAG_DATE_OF_BIRTH, date_of_birth);
                    dataJson.put(TAG_BREED, breed);
                    dataJson.put(TAG_WEIGHT, weight);


                }catch(JSONException e){

                }

                postData(url_update_pet,dataJson,2 );

            }
        });

        // View med records


    }

    public void postData(String url, final JSONObject json, final int option){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest json_obj_req = new JsonObjectRequest(
                Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                switch (option){
                    case 1:checkResponseEditProduct(response); break;
                    case 2:checkResponseView_View_Pet(response); break;

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




    public void checkResponseView_View_Pet(JSONObject response)
    {

        try {

            // dismiss the dialog once product updated
            pDialog.dismiss();
            if(response.getInt("success")==1){
                //successfully update
                Intent i = getIntent();
                //   send result code 100 to notify about product update
                setResult(100, i);
                finish();

            }else{
                // product with pid not found
            }

        } catch (JSONException e) {

            e.printStackTrace();

        }


    }
    public void checkResponseEditProduct(JSONObject response)
    {
        try {
            if(response.getInt("success")==1){
                // successfully received product details
                JSONArray productObj = response.getJSONArray(TAG_PET); // JSON Array
                // get first product object from JSON Array
                JSONObject product = productObj.getJSONObject(0);
                petName=product.getString(TAG_PETNAME);
                gender=product.getString(TAG_GENDER);
                date_of_birth=product.getString(TAG_DATE_OF_BIRTH);
                weight=product.getString(TAG_WEIGHT);
                breed=product.getString(TAG_BREED);

//                Log.i("---Prod details",petName+"  "+gender+"  "+date_of_birth" "+breed+" "+breed);
                editName = (EditText) findViewById(R.id.editName);
                editGender = (EditText) findViewById(R.id.editGender);
                editDOB = (EditText) findViewById(R.id.editDOB);
                editBreed = (EditText) findViewById(R.id.editBreed);
                editWeight = (EditText) findViewById(R.id.editWeight);

                // display product data in EditText
                editName.setText(petName);
                editGender.setText(gender);
                editDOB.setText(date_of_birth);
                editBreed.setText(breed);
                editWeight.setText(weight);



            }else{
                // product with pid not found
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }


    }


}//end class