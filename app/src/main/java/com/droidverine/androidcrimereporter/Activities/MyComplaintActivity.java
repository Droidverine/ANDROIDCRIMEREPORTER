package com.droidverine.androidcrimereporter.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.droidverine.androidcrimereporter.Adapters.MyCompsAdapter;
import com.droidverine.androidcrimereporter.Models.Complaints;
import com.droidverine.androidcrimereporter.R;
import com.droidverine.androidcrimereporter.utils.DetailsManager;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyComplaintActivity extends AppCompatActivity {
List<Complaints> complaintsList;
    MyCompsAdapter myCompsAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_complaint);
        recyclerView=(RecyclerView)findViewById(R.id.recyclermycomps);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        getOthers();
    }
    public void getOthers() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "http://myappapi.esy.es/AndroidCrimeReporter/GetMyComplaints.php?"+new DetailsManager(getApplicationContext()).getUserEmail(),
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String name,url,foodstatus,paystatus,contact,team,venue,foodcode;
                        JSONObject json = null;
                        Complaints complaints;
                        complaintsList=new ArrayList<>();
                        try {
                            Log.d("getting Missing", "done");


                            for(int i = 0; i<=response.length(); i++) {
                                complaints=new Complaints();

                                json = response.getJSONObject(i);
                                name = json.get("name").toString();
                                url=json.get("url").toString();
                                complaints.setName(name);
                                complaints.setLocation("Dadar");
                                complaints.setUrl(url);
                                complaints.setOtherinfo("Blah Blah");
                                complaintsList.add(complaints);
                                Log.d("gheto",name+" url ="+url);
                                myCompsAdapter=new MyCompsAdapter(complaintsList,getApplicationContext());
                                myCompsAdapter.notifyDataSetChanged();
                                recyclerView.setAdapter(myCompsAdapter);

                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

// Do something when error occurred

                    }



                }

        );  // Add JsonArrayRequest to the RequestQueue
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);




    }
}
