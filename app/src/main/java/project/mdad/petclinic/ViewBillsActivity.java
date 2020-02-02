package project.mdad.petclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    TextView tvDescription;
    TextView tvDateOfBill;
    TextView tvPrice;

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
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_DATEOFBILL = "date_of_bill";
    private static final String TAG_PRICE = "price";


    private static String billPetName = "";
    private static String billBillNumber = "";
    private static String billBillStatus = "";
    private static String billDescription = "";
    private static String billDateOfBill = "";
    private static String billPrice = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bills);
        btnOk = (Button) findViewById(R.id.btnOk);

        // getting product details from intent
        Intent i = getIntent();
        // getting product id (pid) from intent
        pid = i.getStringExtra(TAG_BILLID);

        // Getting complete product details in background thread
        JSONObject dataJson = new JSONObject();
        try {
            dataJson.put("pid", pid);
            //     dataJson.put("password", "def");

        } catch (JSONException e) {

        }

        postData(url_bill_details, dataJson, 1);

        // view pet click event
        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching view pet list Activity
                Intent i = new Intent(getApplicationContext(), BillsHistoryActivity.class);
                startActivity(i);
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
                    case 1:checkResponseEditPet(response); break;

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

    public void checkResponseEditPet(JSONObject response) {
        try {
            if (response.getInt(TAG_SUCCESS) == 1) {
                // successfully received product details
                JSONArray petObj = response.getJSONArray(TAG_BILLHISTORY); // JSON Array
                // get first product object from JSON Array
                JSONObject billHist = petObj.getJSONObject(0);
                billPetName = billHist.getString(TAG_PETNAME);
                billBillNumber = billHist.getString(TAG_BILLNUMBER);
                billBillStatus = billHist.getString(TAG_BILLSTATUS);
                billDescription = billHist.getString(TAG_DESCRIPTION);
                billDateOfBill = billHist.getString(TAG_DATEOFBILL);
                billPrice = billHist.getString(TAG_PRICE);


//                Log.i("---Prod details",prodName+"  "+prodPrice+"  "+prodDesc);
                tvPetName = (TextView) findViewById(R.id.tvPetName);
                tvBillNumber = (TextView) findViewById(R.id.tvBillNumber);
                tvBillStatus = (TextView) findViewById(R.id.tvBillStatus);
                //tvDescription = (EditText) findViewById(R.id.tv);
                //tvDateOfBill = (EditText) findViewById(R.id.etWeight);
                tvPrice = (TextView) findViewById(R.id.tvChargeAmt);


                // display product data in EditText
                tvPetName.setText(billPetName);
                tvBillNumber.setText(billBillNumber);
                tvBillStatus.setText(billBillStatus);
                //tvDescription.setText(billDescription);
               // tvDateOfBill.setText(billDateOfBill);
                tvPrice.setText(billPrice);

            } else {
                // product with pid not found
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }


    }
}
