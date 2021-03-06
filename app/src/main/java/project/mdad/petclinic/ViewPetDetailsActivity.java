package project.mdad.petclinic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class ViewPetDetailsActivity extends AppCompatActivity {

    EditText txtPetName;
    EditText txtGender;
    EditText txtDOB;
    EditText txtBreed;
    EditText txtWeight;

    Button btnUpdate, btnViewMedRec, btnDelete;
    // Response
    String responseServer;

    JSONObject json = null;

    String pid;
    static InputStream is = null;

    // single product url
    private static final String url_pet_details = MainActivity.ipBaseAddress + "/get_pet_details.php";
    private static final String url_pet_update = MainActivity.ipBaseAddress + "/update_pet.php";
    private static final String url_pet_delete = MainActivity.ipBaseAddress + "/delete_pet.php";
    private static final String url_pet_medRecords = MainActivity.ipBaseAddress+ "/get_medical_record.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PETDETAILS = "petDetails";
    private static final String TAG_PETMEDRECORDS = "medicalRecords";
    private static final String TAG_PID = "pid";
    private static final String TAG_PETNAME = "petName";
    private static final String TAG_GENDER = "gender";
    private static final String TAG_DOB = "date_of_birth";
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
        btnViewMedRec = (Button) findViewById(R.id.btnViewMedRec);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        // getting product details from intent
        Intent i = getIntent();
        // getting product id (pid) from intent
        pid = i.getStringExtra(TAG_PID);

        // Getting complete product details in background thread
        JSONObject dataJson = new JSONObject();
        try {
            dataJson.put("pid", pid);
            //     dataJson.put("password", "def");

        } catch (JSONException e) {

        }

        postData(url_pet_details, dataJson, 1);

        // view pet click event
        btnViewMedRec.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                JSONObject dataJson = new JSONObject();
                try {
                    dataJson.put("pid", pid);
                } catch (JSONException e) {

                }
                postData(url_pet_medRecords,dataJson,3);

                // Launching view pet list Activity
//                Intent i = new Intent(getApplicationContext(), MedicalListActivity.class);
//                startActivity(i);
            }

        });

        // save button click event
        btnUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // getting updated data from EditTexts
                String petName = txtPetName.getText().toString();
                String gender = txtGender.getText().toString();
                String dob = txtDOB.getText().toString();
                String breed = txtBreed.getText().toString();
                String weight = txtWeight.getText().toString();


                // starting background task to update product
                JSONObject dataJson = new JSONObject();
                try {
                    dataJson.put("pid", pid);
                    dataJson.put(TAG_PETNAME, petName);
                    dataJson.put(TAG_GENDER, gender);
                    dataJson.put(TAG_DOB, dob);
                    dataJson.put(TAG_BREED, breed);
                    dataJson.put(TAG_WEIGHT, weight);

                } catch (JSONException e) {

                }
                postData(url_pet_update, dataJson, 2);
            }
        });

        // Delete button click event
        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // deleting product in background thread

                JSONObject dataJson = new JSONObject();
                try {
                    dataJson.put("pid", pid);
                } catch (JSONException e) {

                }
                postData(url_pet_delete, dataJson, 2);

            }
        });

    }

    public void postData(String url, final JSONObject json, final int option) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest json_obj_req = new JsonObjectRequest(
                Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                switch (option) {
                    case 1:
                        checkResponseEditPet(response);
                        break;
                    case 2:
                        checkResponseSave_delete_Pet(response);
                        break;
                    case 3:
                        checkResponseMedRecord(response);
                        break;


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

    public void checkResponseMedRecord(JSONObject response) {
        try {
            if (response.getInt("success") == 1) {
                finish();
                Intent i = new Intent(getApplicationContext(), MedicalListActivity.class);
                i.putExtra(TAG_PID,pid);
                startActivity(i);


            } else {
                Toast.makeText(this, "No record", Toast.LENGTH_SHORT).show();
                // product with pid not found
            }

        } catch (JSONException e) {

            e.printStackTrace();

        }

    }

    public void checkResponseSave_delete_Pet(JSONObject response) {

        try {
            if (response.getInt("success") == 1) {
                // successfully updated
                Intent i = getIntent();
                // send result code 100 to notify about product update
                setResult(100, i);
                finish();

            } else {
                // product with pid not found
            }

        } catch (JSONException e) {

            e.printStackTrace();

        }


    }

    public void checkResponseEditPet(JSONObject response) {
        try {
            if (response.getInt(TAG_SUCCESS) == 1) {
                // successfully received product details
                JSONArray petObj = response.getJSONArray(TAG_PETDETAILS); // JSON Array
                // get first product object from JSON Array
                JSONObject petDetail = petObj.getJSONObject(0);
                petPetName = petDetail.getString(TAG_PETNAME);
                petGender = petDetail.getString(TAG_GENDER);
                petDOB = petDetail.getString(TAG_DOB);
                petBreed = petDetail.getString(TAG_BREED);
                petWeight = petDetail.getString(TAG_WEIGHT);


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

            } else {
                // product with pid not found
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }


    }

}
