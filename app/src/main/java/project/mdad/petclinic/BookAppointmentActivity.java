package project.mdad.petclinic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BookAppointmentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //Button btnchangedate;
    Button btnConfirm;
    CalendarView calendarView;
    TextView booking_date;
    //Spinner spinner;
    JSONObject json = null;
    String pid;

    static InputStream is = null;
    // Progress Dialog
    private ProgressDialog pDialog;
    private static final String url_booking = MainActivity.ipBaseAddress + "/confirm_booking.php";
    private static final String url_update_booking = MainActivity.ipBaseAddress+"/update_booking.php";
   // private static final String TAG_BOOKING = "booking";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PID = "pid";
    private static final String TAG_BOOKING_DATE = "booking_date";
    private static final String TAG_TIME_SLOTS = "time_slots";

    String date;
    String time_slots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        Log.i("url_booking", url_booking);
        Log.i("url_update_booking", url_update_booking);
        // save button

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setMinDate(calendarView.getDate());
        booking_date = (TextView) findViewById(R.id.booking_date);
        //CalendarView min = CalendarView();
        //calendarView.setMinDate(min);
        //btnchangedate = (Button) findViewById(R.id.btnchangedate);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("9am");
        categories.add("10am");
        categories.add("11am");
        categories.add("12pm");
        categories.add("1pm");
        categories.add("2pm");
        categories.add("3pm");
        categories.add("4pm");
        categories.add("5pm");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


        Intent i = getIntent();
        pid = i.getStringExtra(TAG_PID);
        JSONObject dataJson = new JSONObject();
        try {
            dataJson.put("pid", pid);
            //     dataJson.put("password", "def");

        } catch (JSONException e) {

        }

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                booking_date.setText(date);
                //calendarView.setMinDate(Long.parseLong(date));
            }
             //calendarView.getDatePicker().setMinDate(System.current() - 1000); //disable previous dates

        });


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = booking_date.getText().toString();
                time_slots = spinner.getSelectedItem().toString();
                Intent intent = new Intent(getApplicationContext(), SuccessBookedActivity.class);
                intent.putExtra("data", String.valueOf(spinner.getSelectedItem()));
                intent.putExtra("date", String.valueOf(booking_date.getText()));
                startActivity(intent);

                JSONObject dataJson = new JSONObject();
                try {
                    dataJson.put(TAG_BOOKING_DATE, date); //booking_date
                    dataJson.put(TAG_TIME_SLOTS, time_slots);
                } catch (JSONException e) {

                }

                postData(url_booking, dataJson, 1);

            }
        });

    //btnchangedate.setOnClickListener(new View.OnClickListener() {
      //  @Override
        //public void onClick(View arg0) {
          //  pDialog = new ProgressDialog(BookAppointmentActivity.this);
            //pDialog.setIndeterminate(false);
            //pDialog.setCancelable(true);
            //pDialog.show();

            // deleting product in background thread

            //JSONObject dataJson = new JSONObject();
            //try{
              //  dataJson.put("pid", pid);
            //}catch(JSONException e){

            //}
            //postData(url_update_booking,dataJson,2);


        //}
    //});

}
    public void postData(String url, final JSONObject json, final int option){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest json_obj_req = new JsonObjectRequest(
                Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                switch (option){
                    case 1:checkResponseCreate_Product(response);
                   // case 2:checkResponseEditProduct(response);
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
    public void checkResponseCreate_Product(JSONObject response)
    {
        Log.i("----Response", response+" ");
        try {
            if(response.getInt(TAG_SUCCESS)==1){
                //finish();
                //Intent i = new Intent(getApplicationContext(), SuccessBookedActivity.class);
                //startActivity(i);

                // dismiss the dialog once product uupdated
//                pDialog.dismiss();

            }else{
                // product with pid not found
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }

    }

    //public void checkResponseEditProduct(JSONObject response)
    //{
      //  try {
        //    if(response.getInt("success")==1){
                // successfully received product details
          //      JSONArray productObj = response.getJSONArray(TAG_BOOKING); // JSON Array
                // get first product object from JSON Array
            //    JSONObject product = productObj.getJSONObject(0);
              //  date=product.getString(TAG_BOOKING_DATE);
                //time_slots=product.getString(TAG_TIME_SLOTS);


//                Log.i("---Prod details",prodName+"  "+prodPrice+"  "+prodDesc);
                //booking_date = (TextView) findViewById(R.id.booking_date);
                //final Spinner spinner = (Spinner) findViewById(R.id.spinner);


                // display product data in EditText
                //booking_date.setText(date);
                //spinner.getSelectedItem();




//            }else{
                // product with pid not found
  //          }

    //    } catch (JSONException e) {
      //      e.printStackTrace();

        //}


    //}

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected Time: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }


}
