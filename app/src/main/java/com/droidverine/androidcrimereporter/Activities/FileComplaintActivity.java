package com.droidverine.androidcrimereporter.Activities;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.droidverine.androidcrimereporter.R;
import com.droidverine.androidcrimereporter.utils.CustomOnItemSelectedListener;
import com.droidverine.androidcrimereporter.utils.DetailsManager;
import com.droidverine.androidcrimereporter.utils.MySingltone;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileComplaintActivity extends AppCompatActivity implements View.OnClickListener ,AdapterView.OnItemSelectedListener{
    EditText nameedt,addressedt,contactedt,compliantedt,e5,e6;
    Button b1,b2;
    Bitmap bitmap;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_complaint);
        compliantedt=(EditText)findViewById(R.id.missing);

        b1=(Button)findViewById(R.id.missregbut);
        b1.setOnClickListener(this);
        b2=(Button)findViewById(R.id.imggal);
        b2.setOnClickListener(this);
        addListenerOnSpinnerItemSelection();

    }
    public void addListenerOnSpinnerItemSelection() {
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.missregbut:
                upload ();
                break;
            case R.id.imggal:
                imgchoose();
                break;
        }

    }
    public void imgchoose()
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100 && resultCode==RESULT_OK && data!=null)
        {

            ImageView img=(ImageView)findViewById(R.id.imgselected);
            Uri path=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                img.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public  void upload()
    {
        Toast.makeText(getApplicationContext(),
                "OnClickListener : " +
                        "\nSpinner 1 : "+ String.valueOf(spinner.getSelectedItem()),
                Toast.LENGTH_SHORT).show();
        nameedt=(EditText)findViewById(R.id.editText);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringreq=new StringRequest(Request.Method.POST,"http://myappapi.esy.es/registerfir2.php",  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.v("abc","upload");
                    JSONObject js=new JSONObject(response);
                    String rr=js.getString("response");
                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        },new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("ERR",error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                String name=nameedt.getText().toString().trim();
                EditText addredt=(EditText)findViewById(R.id.edtxtaddress);
                String address=addredt.getText().toString().trim();
                contactedt=(EditText)findViewById(R.id.editText3) ;
                EditText otherinfo=(EditText)findViewById(R.id.otherinfo);
                String contact=contactedt.getText().toString().trim();
                params.put("complainer", new DetailsManager(getApplicationContext()).getUserEmail());
                params.put("name",name);
                params.put("address",address);
                params.put("contact",contactedt.getText().toString());
                params.put("Complaint",String.valueOf(spinner.getSelectedItem()));
                params.put("convict","NA");
                params.put("convictaddress","NA");
                params.put("otherinfo",otherinfo.getText().toString());
                params.put("img",convert(bitmap));
                return params;
            }
        };

        MySingltone mySingltone=new MySingltone(getApplicationContext());
        stringreq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringreq);

        // / mySingltone.getmInstance(getApplicationContext()).addTorequestquere(stringreq);
    }
    private String convert(Bitmap bitmap)
    {
        ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytearray);
        byte[] img =bytearray.toByteArray();
        return Base64.encodeToString(img, Base64.DEFAULT);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(),
                "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }}