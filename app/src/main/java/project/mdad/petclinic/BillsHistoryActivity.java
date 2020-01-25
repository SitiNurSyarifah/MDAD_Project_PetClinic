package project.mdad.petclinic;

import android.app.ListActivity;
import android.os.Bundle;
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

public class BillsHistoryActivity extends ListActivity {

    ArrayList<HashMap<String, String>> billHistoryList;
    private static String url_bill_history = MainActivity.ipBaseAddress+"/bill_history.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_BILLHISTORY = "billHistory";
    private static final String TAG_BILLID = "bill_id";
    private static final String TAG_PETNAME = "petName";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_DATEOFBILL = "date_of_bill";
    private static final String TAG_PRICE = "price";

    // products JSONArray
    JSONArray billHistory = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills_history);

        billHistoryList = new ArrayList<HashMap<String, String>>();

        // Loading products in Background Thread
        postData(url_bill_history,null );
        // Get listview from list_items.xml
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
                billHistory = response.getJSONArray(TAG_BILLHISTORY);

                // looping through All Products
                for (int i = 0; i < billHistory.length(); i++) {
                    JSONObject c = billHistory.getJSONObject(i);

                    // Storing each json item in variable
                    String billID = c.getString(TAG_BILLID);
                    String petName = c.getString(TAG_PETNAME);
                    String description = c.getString(TAG_DESCRIPTION);
                    String dateOfBill = c.getString(TAG_DATEOFBILL);
                    String price = c.getString(TAG_PRICE);


                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put(TAG_BILLID, billID);
                    map.put(TAG_PETNAME, petName);
                    map.put(TAG_DESCRIPTION, description);
                    map.put(TAG_DATEOFBILL, dateOfBill);
                    map.put(TAG_PRICE, price);


                    // adding HashList to ArrayList
                    billHistoryList.add(map);
                }

                /**
                 * Updating parsed JSON data into ListView
                 * */
                ListAdapter adapter = new SimpleAdapter(
                        BillsHistoryActivity.this, billHistoryList,
                        R.layout.list_bill_history, new String[] { TAG_BILLID,
                        TAG_PETNAME,TAG_DESCRIPTION,TAG_DATEOFBILL,TAG_PRICE},
                        new int[] { R.id.pid, R.id.petName,
                                R.id.description,R.id.dateOfBill,R.id.price });
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
