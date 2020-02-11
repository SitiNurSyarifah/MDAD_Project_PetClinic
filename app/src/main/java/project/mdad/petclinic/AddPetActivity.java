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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;


public class AddPetActivity extends AppCompatActivity {


    EditText inputPetName;
    EditText inputGender;
    EditText inputDOB;
    EditText inputBreed;
    EditText inputWeight;

    String petName, gender, breed, dob, weight;


    // url to create new product

    private static String url_create_pet = MainActivity.ipBaseAddress + "/create_pet.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PETDETAILS = "petDetails";
    private static final String TAG_PID = "pid";
    private static final String TAG_PETNAME = "petName";
    private static final String TAG_GENDER = "gender";
    private static final String TAG_DOB = "date_of_birth";
    private static final String TAG_BREED = "breed";
    private static final String TAG_WEIGHT = "weight";

    private static final String TAG_USERNAME = "username";

    String username;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);


        Log.i("Ip address CREATE ", url_create_pet);
        // Edit Text
        inputPetName = (EditText) findViewById(R.id.etPetName);
        inputGender = (EditText) findViewById(R.id.etGender);
        inputDOB = (EditText) findViewById(R.id.etDOB);
        inputBreed = (EditText) findViewById(R.id.etBreed);
        inputWeight = (EditText) findViewById(R.id.etWeight);

        // Create button
        Button btnAddPet = (Button) findViewById(R.id.btnAddPet);

        Intent i = getIntent();
        // getting product id (pid) from intent
        username = i.getStringExtra(TAG_USERNAME);
        Toast.makeText(this, "Record " + username, Toast.LENGTH_SHORT).show();

        // button click event
        btnAddPet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                petName = inputPetName.getText().toString();
                gender = inputGender.getText().toString();
                dob = inputDOB.getText().toString();
                breed = inputBreed.getText().toString();
                weight = inputWeight.getText().toString();


                JSONObject dataJson = new JSONObject();
                try {
                    dataJson.put("username", username);
                    dataJson.put(TAG_PETNAME, petName);
                    dataJson.put(TAG_GENDER, gender);
                    dataJson.put(TAG_DOB, dob);
                    dataJson.put(TAG_BREED, breed);
                    dataJson.put(TAG_WEIGHT, weight);


                } catch (JSONException e) {

                }

                postData(url_create_pet, dataJson, 1);

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
                        checkResponseCreate_Product(response);
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


    public void checkResponseCreate_Product(JSONObject response) {
        Log.i("----Response", response + " ");
        try {
            if (response.getInt(TAG_SUCCESS) == 1) {

                finish();
                Intent i = new Intent(this, ViewPetListActivity.class);
                i.putExtra(TAG_USERNAME, username);
                startActivity(i);

            } else {
                // product with pid not found
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }

    }

}
