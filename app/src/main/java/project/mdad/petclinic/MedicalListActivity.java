package project.mdad.petclinic;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MedicalListActivity extends ListActivity {


    ArrayList<HashMap<String, String>> medicalList;
    // private ProgressDialog pDialog;
    // url to get all products list
    private static String url_medical_list = MainActivity.ipBaseAddress + "/get_medical_record.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MEDRECORDS = "medRecords";
    private static final String TAG_PID = "pid";
    private static final String TAG_VACCINATION = "vaccination";
    private static final String TAG_DISEASE = "disease";
    private static final String TAG_CHECKUPS = "checkups";
    private static final String TAG_CIRCUMCISION = "circumcision";

    String pid;


    //static InputStream is = null;

    // products JSONArray
    JSONArray medRecords = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_list);

        // Hashmap for ListView
        medicalList = new ArrayList<HashMap<String, String>>();

        Intent i = getIntent();
        // getting product id (pid) from intent
        pid = i.getStringExtra(TAG_PID);
        Toast.makeText(this, "Record " + pid, Toast.LENGTH_SHORT).show();
        // Getting complete product details in background thread
        JSONObject dataJson = new JSONObject();
        try {
            dataJson.put("pid", pid);
            //     dataJson.put("password", "def");

        } catch (JSONException e) {

        }

        // Loading products in Background Thread
        postData(url_medical_list, dataJson);


        // Get listview from list_items.xml
        ListView lv = getListView();

        // on seleting single product
        // launching Edit Product Screen
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                // getting values from selected ListItem
//                String pid = ((TextView) view.findViewById(R.id.pid)).getText().toString();
//                // Starting new intent
//                Intent in = new Intent(getApplicationContext(), MedicalListActivity.class); //edit
//                // sending pid to next activity
//                in.putExtra(TAG_PID, pid);
//                // starting new activity and expecting some response back
//                startActivityForResult(in, 100);

            }
        });

    }

    // Response from Edit Product Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if result code 100 means Continue
        //https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html


        if (resultCode == 100) {
            // if result code 100 is received
            // means user edited/deleted product
            // reload this screen again
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

    }


    public void postData(String url, final JSONObject json) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest json_obj_req = new JsonObjectRequest(
                Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                checkResponse(response);

//                String alert_message;
//                alert_message = response.toString();

//                showAlertDialogue("Response", alert_message);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

//                String alert_message;
//                alert_message = error.toString();

         //       showAlertDialogue("Error", alert_message);

            }

        });

        requestQueue.add(json_obj_req);
    }

    private void checkResponse(JSONObject response) {
        try {
            if (response.getInt(TAG_SUCCESS) == 1) {

                // products found
                // Getting Array of Products
                medRecords = response.getJSONArray(TAG_MEDRECORDS);
                // looping through All Products
                for (int i = 0; i < medRecords.length(); i++) {
                    JSONObject c = medRecords.getJSONObject(i);

                    // Storing each json item in variable
                    String id = c.getString(TAG_PID);
                    String vaccination = c.getString(TAG_VACCINATION);
                    String disease = c.getString(TAG_DISEASE);
                    String checkups = c.getString(TAG_CHECKUPS);
                    String circumcision = c.getString(TAG_CIRCUMCISION);


                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put(TAG_PID, id);
                    map.put(TAG_VACCINATION, vaccination);
                    map.put(TAG_DISEASE, disease);
                    map.put(TAG_CHECKUPS, checkups);
                    map.put(TAG_CIRCUMCISION, circumcision);

                    // adding HashList to ArrayList
                    medicalList.add(map);
                }

                /**
                 * Updating parsed JSON data into ListView
                 * */
                ListAdapter adapter = new SimpleAdapter(
                        MedicalListActivity.this, medicalList,
                        R.layout.list_meds, new String[]{TAG_PID,
                        TAG_VACCINATION, TAG_DISEASE, TAG_CHECKUPS, TAG_CIRCUMCISION},
                        new int[]{R.id.pid, R.id.vaccination, R.id.tvDisease, R.id.tvCheckUps, R.id.tvCircumcision}); //vaccination = name
                // updating listview
                setListAdapter(adapter);

            } else {

            }

        } catch (JSONException e) {
            e.printStackTrace();

        }
        //pDialog.dismiss();
    }

} //end of AllProductsActivity class