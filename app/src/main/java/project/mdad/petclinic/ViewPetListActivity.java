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
import android.widget.TextView;

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


public class ViewPetListActivity extends ListActivity {


    ArrayList<HashMap<String, String>> petList;
    private static String url_pet_list = MainActivity.ipBaseAddress + "/get_user_pets.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PETDETAILS = "petDetails";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "petName";

    // products JSONArray
    JSONArray petDetails = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pet_list);

        petList = new ArrayList<HashMap<String, String>>();

        // Loading products in Background Thread
        postData(url_pet_list, null);


        // Get listview from list_items.xml
        ListView lv = getListView();

        // on seleting single product
        // launching Edit Product Screen
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String pid = ((TextView) view.findViewById(R.id.pid)).getText().toString();
                // Starting new intent
                Intent in = new Intent(getApplicationContext(), ViewPetDetailsActivity.class);
                // sending pid to next activity
                in.putExtra(TAG_PID, pid);
                // starting new activity and expecting some response back
                startActivityForResult(in, 100);


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
                checkResponse(response, json);

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

//                showAlertDialogue("Error", alert_message);

            }

        });

        requestQueue.add(json_obj_req);
    }

    private void checkResponse(JSONObject response, JSONObject creds) {
        try {
            if (response.getInt(TAG_SUCCESS) == 1) {

                // products found
                // Getting Array of Products
                petDetails = response.getJSONArray(TAG_PETDETAILS);

                // looping through All Products
                for (int i = 0; i < petDetails.length(); i++) {
                    JSONObject c = petDetails.getJSONObject(i);

                    // Storing each json item in variable
                    String id = c.getString(TAG_PID);
                    String petName = c.getString(TAG_NAME);

                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put(TAG_PID, id);
                    map.put(TAG_NAME, petName);

                    // adding HashList to ArrayList
                    petList.add(map);
                }

                /**
                 * Updating parsed JSON data into ListView
                 * */
                ListAdapter adapter = new SimpleAdapter(
                        ViewPetListActivity.this, petList,
                        R.layout.list_pets, new String[]{TAG_PID,
                        TAG_NAME},
                        new int[]{R.id.pid, R.id.petName});
                // updating listview
                setListAdapter(adapter);

            } else {

            }

        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

} //end of AllProductsActivity class