package project.mdad.petclinic;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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


    ArrayList<HashMap<String, String>> petsList;

    // url to get all products list
    private static String url_all_pets = MainActivity.ipBaseAddress + "/get_all_pets.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PETDETAILS = "petDetails";
    private static final String TAG_PID = "pid";
    private static final String TAG_PETNAME = "petName";

    // products JSONArray
    JSONArray petDetails = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pet_list);

         Log.i("------url_all_products",url_all_pets);
        // Hashmap for ListView
        petsList = new ArrayList<HashMap<String, String>>();

        // Loading products in Background Thread
        postData(url_all_pets, null);

        // Get listview from list_pets.xml
        ListView lv = getListView();

    }

    public void postData(String url, final JSONObject json){
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

    private void checkResponse(JSONObject response, JSONObject creds){
        try {
            if(response.getInt(TAG_SUCCESS)==1){

                // products found
                // Getting Array of Products
                petDetails = response.getJSONArray(TAG_PETDETAILS);

                // looping through All Products
                for (int i = 0; i < petDetails.length(); i++) {
                    JSONObject c = petDetails.getJSONObject(i);

                    // Storing each json item in variable
                    String id = c.getString(TAG_PID);
                    String name = c.getString(TAG_PETNAME);

                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put(TAG_PID, id);
                    map.put(TAG_PETNAME, name);

                    // adding HashList to ArrayList
                    petsList.add(map);
                }

                /**
                 * Updating parsed JSON data into ListView
                 * */
                ListAdapter adapter = new SimpleAdapter(
                        ViewPetListActivity.this, petsList,
                        R.layout.list_pets, new String[] { TAG_PID,
                        TAG_PETNAME},
                        new int[] { R.id.pid, R.id.name });
                // updating listview
                setListAdapter(adapter);

            }
            else{

            }

        } catch (JSONException e) {
            e.printStackTrace();

        }

    }


}
