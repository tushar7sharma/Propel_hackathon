package shrey.gosporto;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResult;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class add_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FloatingActionButton activity,challenge,leaderboard,home,my_activity;
    RadioGroup group;
    Dialog d1,d2,d3;
    TextView dis,cal,time,date,dur,steps;
    private Spinner venue,sport,intensity,fit_sessions;
    jsonparser jsonparser = new jsonparser();
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    ArrayAdapter<String> adapter3;
    ArrayAdapter<String> adapter4;
    String[] venue_names,venue_ids,sport_names,sport_ids,intensity_ids,intensity_names,fit_array;
    public static final String PREFS_NAME = "MyPrefsFile";
    String remark;
    boolean remark_flag;
    ArrayList<String> attributes = new ArrayList<String>();

    private final int REQ_CODE_SPEECH_INPUT = 100;


    private static final String TAG = "MyActivity";
    private GoogleApiClient mClient;
    long startTime,endTime;
    Runnable fvf ;
    DataReadRequest readRequest = null;
    ArrayList<sessions> session_list = new ArrayList<sessions>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        attributes.add("cricket");
        attributes.add("swimming");
        attributes.add("football");
        attributes.add("running");
        attributes.add("tennis");
        attributes.add("table tennis");
        attributes.add("squash");
        attributes.add("basketball");
        attributes.add("cycling");
        attributes.add("volleyball");

        sport = (Spinner)findViewById(R.id.spinner);
        venue = (Spinner)findViewById(R.id.spinner2);
        Button speak_activity = (Button) findViewById(R.id.button4);



        group = (RadioGroup) findViewById(R.id.group);

        activity = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab32);
        challenge = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab22);
        leaderboard = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab12);
        home = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab02);
        my_activity= (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab52);


        new sendjson1().execute();

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int index = group.indexOfChild(findViewById(group.getCheckedRadioButtonId()));
                switch (index) {
                    case 0: {

                        buildFitnessClient();

                        Calendar cal = Calendar.getInstance();
                        Date now = new Date();
                        cal.setTime(now);
                        endTime = cal.getTimeInMillis();
//        cal.add(Calendar.WEEK_OF_YEAR, -1);
//        cal.add(Calendar.DAY_OF_YEAR,-1);
                        startTime = cal.getTimeInMillis() - 86400000;

                        java.text.DateFormat dateFormat = DateFormat.getDateInstance();
                        Log.i(TAG, "Range Start: " + dateFormat.format(startTime));
                        Log.i(TAG, "Range End: " + dateFormat.format(endTime));
                        break;
                    }
                    case 1: {
                        //show1();
                        Intent intent = new Intent(add_activity.this, MiOverviewActivity.class);
//                        intent.putExtra("venue", venue_ids[venue.getSelectedItemPosition()]);
//                        intent.putExtra("sport", sport_ids[sport.getSelectedItemPosition()]);
                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("venue", venue_ids[venue.getSelectedItemPosition()]);
                        editor.putString("sport", sport_ids[sport.getSelectedItemPosition()]);

                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        editor.putString("start_time", sdf.format(c.getTime()));

                        //editor.putString("steps_before", Integer.toString(mMiBand.mSteps));
                        // Commit the edits!
                        editor.commit();


                        startActivity(intent);
                        break;
                    }
                    case 2: {
                        show2();
                        break;
                    }
                }


            }
        });



        speak_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInput();
            }
        });


        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
//                Intent intent = new Intent(landing_page.this,add_activity.class);
//                startActivity(intent);
            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                Intent intent = new Intent(add_activity.this, landing_page.class);
                startActivity(intent);
            }
        });

        challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
//                Intent intent = new Intent(landing_page.this,challenge_activity.class);
//                startActivity(intent);
            }
        });

        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                Intent intent = new Intent(add_activity.this, leaderboard.class);
                startActivity(intent);
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void show2()
    {

        d1 = new Dialog(add_activity.this);
        d1.setTitle("");
        d1.setContentView(R.layout.dialog2);
        final Button add = (Button) d1.findViewById(R.id.button);
        final TimePicker t = (TimePicker) d1.findViewById(R.id.timePicker);
        final EditText e = (EditText) d1.findViewById(R.id.Text);
        final EditText steps = (EditText) d1.findViewById(R.id.Text3);
        final EditText start_time = (EditText) d1.findViewById(R.id.editText0);
        final EditText date_day = (EditText) d1.findViewById(R.id.editText10);
        final EditText date_month = (EditText) d1.findViewById(R.id.editText11);
        final EditText date_year = (EditText) d1.findViewById(R.id.editText12);

        final TextView cal = (TextView) d1.findViewById(R.id.textView11);
        final TextView dis = (TextView) d1.findViewById(R.id.textView13);
        intensity = (Spinner) d1.findViewById(R.id.spinner3);



        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                double x = Double.parseDouble(steps.getText().toString());

                x = x*0.8;
                double y = 0.073 * x;

                new sendjson().execute(venue_ids[venue.getSelectedItemPosition()],sport_ids[sport.getSelectedItemPosition()],  start_time.getText().toString(), date_year.getText().toString() + "-" + date_month.getText().toString() + "-" + date_day.getText().toString(), e.getText().toString(), steps.getText().toString(),String.valueOf(x),String.valueOf(y),intensity_ids[intensity.getSelectedItemPosition()]);
            }
        });
        //TextView e = (TextView) d1.findViewById(R.id.editText);

        //Toast.makeText(getApplicationContext(), Integer.toString(t.getCurrentHour()) + "   " + Integer.toString(t.getCurrentMinute()), Toast.LENGTH_SHORT).show();


//        t.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//            @Override
//            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//                System.out.println("What time is it? "
//                        + String.valueOf(view.getCurrentHour()) + ":"
//                        + String.valueOf(view.getCurrentMinute()));
//
//                dis.setText(hourOfDay);
//                Toast.makeText(getApplicationContext(), Integer.toString(view.getCurrentHour()) + "   " + Integer.toString(view.getCurrentMinute()), Toast.LENGTH_SHORT).show();
//
//            }
//
//        });
//

        e.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(!(e.getText().toString()).equals("")){
                double x = Double.parseDouble(e.getText().toString());
                x = x*0.8;
                double y = 0.073 * x;
                dis.setText(String.valueOf(x));
                cal.setText(String.valueOf(y));
                }
                //dis.setText(Double.toString(x));
                //cal.setText(Double.toString(y));
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });


        adapter3 = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.spinner_design, intensity_names);
        adapter3.setDropDownViewResource(R.layout.spinner_design);
        intensity.setAdapter(adapter3);

        d1.show();

    }



    public void show3()
    {


        d3 = new Dialog(add_activity.this);
        d3.setTitle("");
        d3.setContentView(R.layout.dialog3);

        dis = (TextView) d3.findViewById(R.id.textView13);
        cal = (TextView) d3.findViewById(R.id.textView11);
        steps = (TextView) d3.findViewById(R.id.Text3);
        date = (TextView) d3.findViewById(R.id.Text12);
        time = (TextView) d3.findViewById(R.id.Text0);
        dur = (TextView) d3.findViewById(R.id.Text);
        final Spinner in = (Spinner) d3.findViewById(R.id.spinner3);

        fit_sessions = (Spinner)d3.findViewById(R.id.spinner4);

        Button b = (Button) d3.findViewById(R.id.button3);
        adapter3 = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.spinner_design, intensity_names);
        adapter3.setDropDownViewResource(R.layout.spinner_design);
        in.setAdapter(adapter3);

        adapter4 = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.spinner_design, fit_array);
        adapter4.setDropDownViewResource(R.layout.spinner_design);
        fit_sessions.setAdapter(adapter4);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Calendar c = Calendar.getInstance();

                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                date.setText(df.format(c.getTime()));

               String start =  session_list.get(fit_sessions.getSelectedItemPosition()).getStart_time();
                String end = session_list.get(fit_sessions.getSelectedItemPosition()).getStart_time();


                SimpleDateFormat formatd = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

//                Date d1 = null;
//                Date d2 = null;
//                try {
//                    d1 = formatd.parse(start);
//                    d2 = formatd.parse(end);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//
//
//                long diff = d1.getTime() - d2.getTime();
//                Log.i(TAG, "duration    --"+diff);


                java.util.Date date1 = new Date(start);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String format = formatter.format(date1);
                Log.i(TAG, "date    --"+format);


                java.util.Date date2 = new Date(start);
                SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm:ss");
                String format2 = formatter2.format(date2);

                Log.i(TAG, "time    --"+format2);


                new sendjson().execute(venue_ids[venue.getSelectedItemPosition()],sport_ids[sport.getSelectedItemPosition()], format2 , format, "10", steps.getText().toString(), dis.getText().toString(), cal.getText().toString(), "2");


            }
        });


        fit_sessions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String a = session_list.get(fit_sessions.getSelectedItemPosition()).getSteps();
                steps.setText(session_list.get(fit_sessions.getSelectedItemPosition()).getSteps());
                double x = Double.parseDouble(a);

                x = x*0.8;
                double y = 0.073 * x;


                dis.setText(String.valueOf(x));
                cal.setText(String.valueOf(y));



            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing, just another required interface callback
            }

        });

        d3.show();

    }



    public void show1()
    {

        d2 = new Dialog(add_activity.this);
        d2.setTitle("");
        d2.setContentView(R.layout.dialog_mi);
        final Button start_mi = (Button) d2.findViewById(R.id.button_start_mi);
        final Button end_mi = (Button) d2.findViewById(R.id.button_end_mi);

        start_mi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent();
                Intent intent = new Intent(add_activity.this,MiOverviewActivity.class);
                startActivity(intent);
            }
        });


        end_mi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        d2.show();

    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    class sendjson extends AsyncTask<String, String, String> {
        protected String doInBackground(String... args) {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                //button.setText("Network Connected");
                final String url1 = "http://" + "192.168.191.1" + "/insert.php";


                // Building Parameters
                List<NameValuePair> params2 = new ArrayList<NameValuePair>();
                params2.add(new BasicNameValuePair("venue_id", args[0]));
                params2.add(new BasicNameValuePair("sport_id", args[1]));
                params2.add(new BasicNameValuePair("start_time", args[2]));
                params2.add(new BasicNameValuePair("date", args[3]));
                params2.add(new BasicNameValuePair("duration", args[4]));
                params2.add(new BasicNameValuePair("steps_taken", args[5]));
                params2.add(new BasicNameValuePair("distance", args[6]));
                params2.add(new BasicNameValuePair("calories", args[7]));
                params2.add(new BasicNameValuePair("intensity", args[8]));




                JSONObject json1 = jsonparser.makeHttpRequest(url1,
                        "GET", params2);
                JSONArray receive = null;

                try {

                    JSONArray j1 = json1.getJSONArray("names");
                    JSONArray j2 = json1.getJSONArray("id");

//                    trigger_names = new String[j1.length()];
//                    trigger_ids = new String[j2.length()];

                    for (int i = 0; i < j1.length(); i++) {
//                        trigger_names[i] = j1.getString(i);
//                        trigger_ids[i]= j2.getString(i);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }


        protected void onPostExecute(String url) {


        }


    }






    class sendjson1 extends AsyncTask<String, String, String> {
        protected String doInBackground(String... args) {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                //button.setText("Network Connected");
                final String url1 = "http://" + "192.168.191.1" + "/sport_venue.php";


                // Building Parameters
                List<NameValuePair> params2 = new ArrayList<NameValuePair>();



                JSONObject json1 = jsonparser.makeHttpRequest(url1,
                        "GET", params2);
                JSONArray receive = null;

                try {

                    JSONArray j1 = json1.getJSONArray("venue_name");
                    JSONArray j2 = json1.getJSONArray("venue_id");
                    JSONArray j3 = json1.getJSONArray("sport_name");
                    JSONArray j4 = json1.getJSONArray("sport_id");
                    JSONArray j5 = json1.getJSONArray("intensity_name");
                    JSONArray j6 = json1.getJSONArray("intensity_id");

                    venue_names = new String[j1.length()];
                    venue_ids = new String[j2.length()];
                    sport_names = new String[j3.length()];
                    sport_ids = new String[j4.length()];
                    intensity_ids = new String[j5.length()];
                    intensity_names = new String[j6.length()];

                    for (int i = 0; i < j1.length(); i++) {
                        venue_names[i] = j1.getString(i);
                        venue_ids[i]= j2.getString(i);

                    }

                    for (int i = 0; i < j4.length(); i++) {
                        sport_names[i] = j3.getString(i);
                        sport_ids[i]= j4.getString(i);

                    }

                    for (int i = 0; i < j5.length(); i++) {
                        intensity_names[i] = j5.getString(i);
                        intensity_ids[i]= j6.getString(i);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }


        protected void onPostExecute(String url) {

            Toast.makeText(getApplicationContext(), venue_names[0], Toast.LENGTH_SHORT).show();


            adapter = new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.spinner_design, venue_names);
            adapter.setDropDownViewResource(R.layout.spinner_design);



            adapter2 = new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.spinner_design, sport_names);
            adapter2.setDropDownViewResource(R.layout.spinner_design);
            sport.setAdapter(adapter2);
            venue.setAdapter(adapter);





        }


    }

    private void buildFitnessClient() {
        if (mClient == null  ) {
            mClient = new GoogleApiClient.Builder(this)
                    .addApi(Fitness.HISTORY_API)
                    .addApi(Fitness.RECORDING_API)
                    .addScope(new Scope(Scopes.FITNESS_LOCATION_READ))
                    .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))

                    .addConnectionCallbacks(
                            new GoogleApiClient.ConnectionCallbacks() {
                                @Override
                                public void onConnected(Bundle bundle) {
                                    Log.i(TAG, "Connected!!!");

                                    readRequest = new DataReadRequest.Builder()
                                            .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                                                    //.bucketByActivityType(1, TimeUnit.SECONDS)
                                            .bucketByActivitySegment(1, TimeUnit.MINUTES)

                                                    //.bucketByTime(1,TimeUnit.DAYS)
                                            .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                                            .build();

                                    new sendjson2().execute();


                                }


                                class sendjson2 extends AsyncTask<String, String, String> {


                                    protected String doInBackground(String... args) {


                                        DataReadResult dataReadResult =
                                                Fitness.HistoryApi.readData(mClient, readRequest).await(1, TimeUnit.MINUTES);
                                        Log.i(TAG, dataReadResult.toString());

                                        List<Bucket> abc = new ArrayList<Bucket>();
                                        abc = dataReadResult.getBuckets();
                                        for (int i = 0; i < abc.size(); i++) {
//                                            Log.i(TAG, abc.get(i).getDataSets().get(0).getDataPoints().get(0).toString());
//                                            Log.i(TAG, Long.toString(abc.get(i).getDataSets().get(0).getDataPoints().get(0).getEndTime(TimeUnit.MILLISECONDS)));
                                            //session_list.clear();
                                            dumpDataSet(abc.get(i).getDataSets().get(0));
                                        }


                                        return null;
                                    }

                                    protected void onPostExecute(String file_url) {

                                        fit_array = new String[session_list.size()];

                                        for (int i = 0; i < session_list.size(); i++) {
                                            fit_array[i] = session_list.get(i).getStart_time() + " - " + session_list.get(i).getEnd_time();
                                        }



                                        show3();


                                    }


                                }


                                @Override
                                public void onConnectionSuspended(int i) {
                                    // If your connection to the sensor gets lost at some point,
                                    // you'll be able to determine the reason and react to it here.
                                    if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST) {
                                        Log.i(TAG, "Connection lost.  Cause: Network Lost.");
                                    } else if (i
                                            == GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
                                        Log.i(TAG,
                                                "Connection lost.  Reason: Service Disconnected");
                                    }
                                }
                            }
                    )
                    .enableAutoManage(this, 0, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult result) {
                            Log.i(TAG, "Google Play services connection failed. Cause: " +
                                    result.toString());
//                            Snackbar.make(
//                                    MainActivity.this.findViewById(R.id.activity_main),
//                                    "Exception while connecting to Google Play services: " +
//                                            result.getErrorMessage(),
//                                    Snackbar.LENGTH_LONG).show();
                        }
                    })
                    .build();
        }
    }

    private void dumpDataSet(DataSet dataSet) {
        Log.i(TAG, "Data returned for Data type: " + dataSet.getDataType().getName());
//        DateFormat dateFormat = getTimeInstance();
        java.text.DateFormat dateFormat2 = DateFormat.getDateInstance();
        java.text.DateFormat dateFormat = DateFormat.getDateTimeInstance();

        for (DataPoint dp : dataSet.getDataPoints()) {
            Log.i(TAG, "Data point:");
            Log.i(TAG, "\tType: " + dp.getDataType().getName());
            Log.i(TAG, "\tStart: " + dateFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)));
            Log.i(TAG, "\tEnd: " + dateFormat.format(dp.getEndTime(TimeUnit.MILLISECONDS)));
            for(Field field : dp.getDataType().getFields()) {
                Log.i(TAG, "\tField: " + field.getName() +
                        " Value: " + dp.getValue(field));
                sessions s = new sessions(dateFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)),dateFormat.format(dp.getEndTime(TimeUnit.MILLISECONDS)),dp.getValue(field).toString());
                session_list.add(s);
            }


        }
        for(int i=0; i<session_list.size();i++) {
            Log.i(TAG, "start--" +session_list.get(i).getStart_time()+"  end--"+session_list.get(i).getEnd_time()+"  steps"+session_list.get(i).getSteps());
        }

    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    remark = result.get(0).toLowerCase();
                    //  button.setText(remark);
                    int i;
                    remark_flag = false;
                    for(i = 0 ; i < attributes.size() ; i++)
                    {
                        if (remark.equals(attributes.get(i)))
                        {
                            //remarks.setText(result.get(0));


                            sport.setSelection(i);
                            sport.setSelection(i,true);
                            Toast.makeText(getApplicationContext(),
                                    result.get(0) + sport_names[i],
                                    Toast.LENGTH_SHORT).show();
                            remark_flag = true;
                            break;
                        }
                    }

                    if(remark_flag == false)
                    {
                        Toast.makeText(getApplicationContext(),
                                "Sorry.. "+remark+" is not a valid keyword",
                                Toast.LENGTH_SHORT).show();
                    }

                }
                break;
            }

        }
    }
}
