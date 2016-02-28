package shrey.gosporto;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class leaderboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    Spinner spinner,spinner2;
    int spinner_pos,spinner_pos2;
    jsonparser jsonParser = new jsonparser();
    ProgressDialog pDialog1;
    String server = "192.168.191.1";
    ArrayList<CharSequence> challenge_list = new ArrayList<CharSequence>();
    ArrayAdapter<CharSequence> adapter4 ;
    int flag =0;
    private com.github.clans.fab.FloatingActionButton activity,challenge,leaderboard,home,my_activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        final ListView listview = (ListView) findViewById(R.id.listview);
        String[] values = new String[] { "RANK    NAME          STEPS/SCORE","1.   tushar sharma      123","2.  sarthak sahni      321",
        "3.  Shrey     1212"};

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);


        activity = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab32);
        challenge = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab22);
        leaderboard = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab12);
        home = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab02);
        my_activity= (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab52);


        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent();
                Intent intent = new Intent(leaderboard.this,add_activity.class);
                startActivity(intent);
            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent();
                Intent intent = new Intent(leaderboard.this,landing_page.class);
                startActivity(intent);
            }
        });

        challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent();
                Intent intent = new Intent(leaderboard.this,challenge_activity.class);
                startActivity(intent);
            }
        });

        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                Intent intent = new Intent(leaderboard.this, leaderboard.class);
                startActivity(intent);
            }
        });

        my_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent();
                Intent intent = new Intent(leaderboard.this,my_activities.class);
                startActivity(intent);
            }
        });

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);


        ArrayList<CharSequence> choice = new ArrayList<CharSequence>();

        choice.add("Steps");
        choice.add("Challenges");


        ArrayList<CharSequence> filters = new ArrayList<CharSequence>();

        filters.add("Today");
        filters.add("Last Week");
        filters.add("All Time");


        ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, choice);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // spinner.setPrompt("Select your favorite Planet!");
// Apply the adapter to the spinner


        final ArrayAdapter<CharSequence> adapter3 = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, filters);
// Specify the layout to use when the list of choices appears
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter2);

        spinner_pos = spinner.getSelectedItemPosition();



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {


                if(pos==0)
                {
                   spinner2.setAdapter(adapter3);
                   flag =1;// Toast.makeText(getApplicationContext(),"first",Toast.LENGTH_SHORT).show();
                }
                else
                {
                   new sendjson1().execute();// Toast.makeText(getApplicationContext(),"second",Toast.LENGTH_SHORT).show();
                }

            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing, just another required interface callback
            }

        }); // (optional)


        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                if(flag==1)
                {
                   // new sendjson2().execute(Integer.toString(pos));
                }

                if(flag==2)
                {
                    //new sendjson3().execute(Integer.toString(pos));
                }

            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing, just another required interface callback
            }

        }); // (optional)



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
        getMenuInflater().inflate(R.menu.leaderboard, menu);
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

        if (id == R.id.nav_camara) {
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

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }


    class sendjson1 extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog1 = new ProgressDialog(leaderboard.this);
            pDialog1.setMessage("Please wait...");
            pDialog1.setIndeterminate(false);
            pDialog1.setCancelable(false);
            pDialog1.show();

        }

        protected String doInBackground(String... args) {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                //button.setText("Network Connected");




                // Building Parameters




                // getting JSON Object
                // Note that create product url accepts POST method
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                //JSONObject json1 = jsonParser1.makeHttpRequest(url1,
                //       "POST", params);
                challenge_list.clear();

                final String url = "http://"+server+"/challenge.php";


                JSONObject json1 = jsonParser.makeHttpRequest(url,
                        "POST", params);
                try {
                    JSONArray array1 = json1.getJSONArray("challenge_name");
                    for(int i =0; i< array1.length();i++)
                    {
                        challenge_list.add(array1.getString(i));
                    }
                }
                catch(Exception e)
                {

                }



            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog1.dismiss();
           adapter4 = new ArrayAdapter<CharSequence>(getApplicationContext(), android.R.layout.simple_spinner_item, challenge_list);
// Specify the layout to use when the list of choices appears
            adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter4);
            flag =2 ;



        }





    }


    class sendjson2 extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog1 = new ProgressDialog(leaderboard.this);
            pDialog1.setMessage("Please wait...");
            pDialog1.setIndeterminate(false);
            pDialog1.setCancelable(false);
            pDialog1.show();

        }

        protected String doInBackground(String... args) {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                //button.setText("Network Connected");


                String id = args[0];



                // Building Parameters




                // getting JSON Object
                // Note that create product url accepts POST method
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                //JSONObject json1 = jsonParser1.makeHttpRequest(url1,
                //       "POST", params);

                final String url = "http://"+server+"/.php";


                JSONObject json1 = jsonParser.makeHttpRequest(url,
                        "POST", params);
                try {
                    JSONArray array1 = json1.getJSONArray("challenge_name");
                    for(int i =0; i< array1.length();i++)
                    {
                        challenge_list.add(array1.getString(i));
                    }
                }
                catch(Exception e)
                {

                }



            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog1.dismiss();
//            adapter4 = new ArrayAdapter<CharSequence>(getApplicationContext(), android.R.layout.simple_spinner_item, challenge_list);
//// Specify the layout to use when the list of choices appears
//            adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spinner2.setAdapter(adapter4);
//            flag =2 ;



        }





    }

    class sendjson3 extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog1 = new ProgressDialog(leaderboard.this);
            pDialog1.setMessage("Please wait...");
            pDialog1.setIndeterminate(false);
            pDialog1.setCancelable(false);
            pDialog1.show();

        }

        protected String doInBackground(String... args) {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                //button.setText("Network Connected");




                // Building Parameters
                String id = args[0];



                // getting JSON Object
                // Note that create product url accepts POST method
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                //JSONObject json1 = jsonParser1.makeHttpRequest(url1,
                //       "POST", params);

                final String url = "http://"+server+"/.php";


                JSONObject json1 = jsonParser.makeHttpRequest(url,
                        "POST", params);
                try {
                    JSONArray array1 = json1.getJSONArray("challenge_name");
                    for(int i =0; i< array1.length();i++)
                    {
                        challenge_list.add(array1.getString(i));
                    }
                }
                catch(Exception e)
                {

                }



            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog1.dismiss();
//            adapter4 = new ArrayAdapter<CharSequence>(getApplicationContext(), android.R.layout.simple_spinner_item, challenge_list);
//// Specify the layout to use when the list of choices appears
//            adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spinner2.setAdapter(adapter4);
//            flag =2 ;



        }





    }
}
