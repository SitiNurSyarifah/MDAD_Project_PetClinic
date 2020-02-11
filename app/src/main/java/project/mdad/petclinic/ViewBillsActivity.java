package project.mdad.petclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.io.InputStream;

public class ViewBillsActivity extends AppCompatActivity {

    TextView tvPetName;
    TextView tvBillNumber;
    TextView tvBillStatus;
    TextView tvChargeAmt;
    TextView tvTotalAmt;


    Button btnOk;
    // Response
    String responseServer;

    JSONObject json = null;

    String pid;
    static InputStream is = null;

    // single product url
    private static final String url_bill_details = MainActivity.ipBaseAddress + "/get_bill_details.php";


    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_BILLHISTORY = "billHistory";
    private static final String TAG_BILLID = "bill_id";
    private static final String TAG_PETNAME = "petName";
    private static final String TAG_BILLNUMBER = "bill_number";
    private static final String TAG_BILLSTATUS = "bill_status";
    private static final String TAG_CHARGEAMOUNT = "charge_amount";
    private static final String TAG_TOTALAMOUNT = "total_amount";


    private static String billPetName = "";
    private static String billBillNumber = "";
    private static String billBillStatus = "";
    private static String billChargeAmount = "";
    private static String billTotalAmt = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bills);
        btnOk = (Button) findViewById(R.id.btnOk);

        // getting bill details from intent
        Intent i = getIntent();
        // getting bill id (pid) from intent
        pid = i.getStringExtra(TAG_BILLID);

        // Getting complete bill details in background thread
        JSONObject dataJson = new JSONObject();
        try {
            dataJson.put("pid", pid);

        } catch (JSONException e) {

        }

        postData(url_bill_details, dataJson, 1);

        // view bill history click event
        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching view bill history Activity
                Intent i = new Intent(getApplicationContext(), BillsHistoryActivity.class);
                startActivity(i);
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
                        checkResponseViewBill(response);
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

    public void checkResponseViewBill(JSONObject response) {
        try {
            if (response.getInt(TAG_SUCCESS) == 1) {
                // successfully received bill details
                JSONArray petObj = response.getJSONArray(TAG_BILLHISTORY); // JSON Array
                // get first bill object from JSON Array
                JSONObject billHist = petObj.getJSONObject(0);
                billPetName = billHist.getString(TAG_PETNAME);
                billBillNumber = billHist.getString(TAG_BILLNUMBER);
                billBillStatus = billHist.getString(TAG_BILLSTATUS);
                billChargeAmount = "$" + billHist.getString(TAG_CHARGEAMOUNT);
                billTotalAmt = "$" + billHist.getString(TAG_TOTALAMOUNT);


                tvPetName = (TextView) findViewById(R.id.tvPetName);
                tvBillNumber = (TextView) findViewById(R.id.tvBillNumber);
                tvBillStatus = (TextView) findViewById(R.id.tvBillStatus);
                tvChargeAmt = (TextView) findViewById(R.id.tvChargeAmt);
                tvTotalAmt = (TextView) findViewById(R.id.tvTotalAmt);


                // display bill data in EditText
                tvPetName.setText(billPetName);
                tvBillNumber.setText(billBillNumber);
                tvBillStatus.setText(billBillStatus);
                tvChargeAmt.setText(billChargeAmount);
                tvTotalAmt.setText(billTotalAmt);


            } else {
                // product with pid not found
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }


    }
}
