package project.mdad.petclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText mPassword,mUserName;
    private Button btnSignIn;
    // url to update product
    private static final String url_login = "http://mdad.atspace.cc/petClinic/loginJ.php";
//    private static final String url_login = "http://192.168.0.111/petClinic/loginJ.php";


    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPassword = (EditText) findViewById(R.id.password);
        mUserName = (EditText) findViewById(R.id.username);
        btnSignIn = (Button) findViewById(R.id.btnLogin);

        // view products click event
        btnSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String pw= mPassword.getText().toString();
                String uName= mUserName.getText().toString();

                if(pw.isEmpty())
                {
                    mPassword.setError(getString(R.string.error_field_required));

                }else

                if(uName.isEmpty())
                {
                    mUserName.setError(getString(R.string.error_field_required));

                }else
                {
                    JSONObject dataJson = new JSONObject();
                    try{
                        dataJson.put("username", uName);
                        dataJson.put("password", pw);


                    }catch(JSONException e){

                    }

                    postData(url_login,dataJson,1 );

                }

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
                    case 1:checkResponseLogin(response); break;

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


    public void checkResponseLogin(JSONObject response)
    {
        Log.i("----Response", response+" "+url_login);
        try {
            if(response.getInt(TAG_SUCCESS)==1){

                finish();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);



            }else{
                Toast.makeText(this, "Wrong Password", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }

    }

}
