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

import org.json.JSONException;
import org.json.JSONObject;


public class AddPetActivity extends AppCompatActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    EditText editName;
    EditText editGender;
    EditText editDOB;
    EditText editBreed;
    EditText editWeight;

    String petName,gender,date_of_birth,breed,weight;


    public static String ipBaseAddress = "http://mdadproject.atspace.cc/petClinic";
    // url to create new product
    //private static final String url_login = "http://172.30.30.97/petclinic/LoginJ.php";
    private static String url_add_pet = AddPetActivity.ipBaseAddress+"/create_productJson.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PET = "pet";
    private static final String TAG_PETNAME = "petName";
    private static final String TAG_GENDER = "gender";
    private static final String TAG_DATE_OF_BIRTH = "date_of_birth";
    private static final String TAG_BREED = "breed";
    private static final String TAG_WEIGHT = "weight";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);


        Log.i("Ip address CREATE ", url_add_pet);
        // Edit Text
        editName = (EditText) findViewById(R.id.editName);
        editGender = (EditText) findViewById(R.id.editGender);
        editDOB = (EditText) findViewById(R.id.editDOB);
        editBreed = (EditText) findViewById(R.id.editBreed);
        editWeight = (EditText) findViewById(R.id.editWeight);

        // Create button
        Button btnCreateProduct = (Button) findViewById(R.id.btnAdd);

        // button click event
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                petName = editName.getText().toString();
                gender = editGender.getText().toString();
                date_of_birth = editDOB.getText().toString();
                breed = editBreed.getText().toString();
                weight = editWeight.getText().toString();


                pDialog = new ProgressDialog(AddPetActivity.this);
                pDialog.setMessage("Adding pet ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();


                JSONObject dataJson = new JSONObject();
                try{
                    dataJson.put(TAG_PETNAME, petName);
                    dataJson.put(TAG_GENDER, gender);
                    dataJson.put(TAG_DATE_OF_BIRTH, date_of_birth);
                    dataJson.put(TAG_BREED, breed);
                    dataJson.put(TAG_WEIGHT, weight);


                }catch(JSONException e){

                }

                postData(url_add_pet,dataJson,1 );


                // creating new product in background thread
                // new CreateNewProduct().execute();
            }
        });
    }


    public void postData(String url, final JSONObject json, final int option){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest json_obj_req = new JsonObjectRequest(
                Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                switch (option){
                    case 1:checkResponseCreate_Product(response); break;

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


    public void checkResponseCreate_Product(JSONObject response)
    {
        Log.i("----Response", response+" ");
        try {
            if(response.getInt(TAG_SUCCESS)==1){

                finish();
                Intent i = new Intent(this, ViewPetListActivity.class);
                startActivity(i);

                // dismiss the dialog once product uupdated
                pDialog.dismiss();

            }else{
                // product with pid not found
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }

    }



}
