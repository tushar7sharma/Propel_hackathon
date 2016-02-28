package shrey.gosporto;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class landing_page extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private FloatingActionButton activity,challenge,leaderboard,home,my_activity;
    private ImageView rec;
    TextView s,d,c;
    private ListView listView;
    int t;

    String s_,d_,c_,min_,max_,target,score_today;
    jsonparser jsonparser = new jsonparser();
    ArrayList<card_data> activities = new ArrayList<card_data>();
    private CardArrayAdapter cardArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        s = (TextView) findViewById(R.id.steps);
        d = (TextView) findViewById(R.id.distance);
        c = (TextView) findViewById(R.id.calories);
        setSupportActionBar(toolbar);
        rec = (ImageView) this.findViewById(R.id.rec);


        final FloatingActionMenu fab = (FloatingActionMenu) findViewById(R.id.menu2);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        //new sendjson3().execute();
        //new sendjson1().execute();


            s.setText("2000");
            d.setText("1600");
            c.setText("113");


            listView = (ListView) findViewById(R.id.card_listView);
////            //h++;
//////                    listView.addHeaderView(new View(getActivity().getApplicationContext()));
//////                    listView.addFooterView(new View(getActivity().getApplicationContext()));
////
            cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.list_item_card);
            listView.setAdapter(cardArrayAdapter);
////            for (int i = 0; i < (activities.size()-1); i++)
////            {
////
////                Card card = new Card(activities.get(i).getTitle(), activities.get(i).getVenue(),activities.get(i).getTime(),activities.get(i).getCost(),activities.get(i).getSport_id());
////                cardArrayAdapter.add(card);
////            }
//
        Card card = new Card("Running","Very Light","28 Feb 2016 1:00 pm","500","400");
                    cardArrayAdapter.add(card);

        Card card1 = new Card("Football","Moderate","28 Feb 2016 12:10 pm","1500","600");
        cardArrayAdapter.add(card1);

        Card card2 = new Card("Cricket","Light","27 Feb 2016 10:00 pm","500","400");
        cardArrayAdapter.add(card2);

        Card card3 = new Card("Walking","Very Light","27 Feb 2016 9:00 pm","500","100");
        cardArrayAdapter.add(card3);

        Card card4 = new Card("Running","Hard","27 Feb 2016 7:00 pm","1500","700");
        cardArrayAdapter.add(card4);

        Card card5 = new Card("Football","Hard","27 Feb 2016 6:00 pm","700","500");
        cardArrayAdapter.add(card5);
////
////
//

            listView.setAdapter(cardArrayAdapter);




            Bitmap bitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            rec.setImageBitmap(bitmap);
//
//            // Rectangle
//
//
            Paint p = new Paint();
//////        RectF rectF = new RectF(50, 120, 100, 220);
//////        p.setColor(Color.BLACK);
//////        canvas.drawArc(rectF, -90, 90, false, p);
////
////
////
            final RectF oval = new RectF();

            p.setStyle(Paint.Style.FILL_AND_STROKE);
//
//
//  /*
//   * drawArc(RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint)
//   *
//   * oval - The bounds of oval used to define the shape and size of the arc
//   * startAngle - Starting angle (in degrees) where the arc begins
//   * sweepAngle - Sweep angle (in degrees) measured clockwise
//   * useCenter - If true, include the center of the oval in the arc, and close it if it is being stroked. This will draw a wedge
//   * paint - The paint used to draw the arc
//   */
//
//
        p.setColor(Color.RED);
        oval.set(0, 20, 300, 320);
        canvas.drawArc(oval, 0, 160, true, p);

        p.setColor(Color.WHITE);

        oval.set(50, 70, 250, 270);
        canvas.drawArc(oval, 0, 160, true, p);
//
//
//
//
////            if(Integer.parseInt(score_today) > Integer.parseInt(max_)){
////                p.setColor(Color.RED);
////            oval.set(0, 20, 300, 320);
////            canvas.drawArc(oval, 0, 360, true, p);
////
////
////
////            p.setColor(Color.TRANSPARENT);
////
////            oval.set(50, 70, 250, 270);
////            canvas.drawArc(oval, 0, 360, true, p);}
////
////            else if((Integer.parseInt(score_today) <= Integer.parseInt(max_)) && (Integer.parseInt(score_today) > Integer.parseInt(min_))){
////                float angle = (Integer.parseInt(score_today)/Integer.parseInt(target))*360;
////                p.setColor(Color.GREEN);
////                oval.set(0, 20, 300, 320);
////                canvas.drawArc(oval, 0, angle, true, p);
////
////
////
////                p.setColor(Color.TRANSPARENT);
////
////                oval.set(50, 70, 250, 270);
////                canvas.drawArc(oval, 0, angle, true, p);}
////
////
////            else if((Integer.parseInt(score_today) <= Integer.parseInt(min_))){
////                float angle = (Integer.parseInt(score_today)/Integer.parseInt(target))*360;
////                p.setColor(Color.YELLOW);
////                oval.set(0, 20, 300, 320);
////                canvas.drawArc(oval, 0, angle, true, p);
////
////
////
////                p.setColor(Color.TRANSPARENT);
////
////                oval.set(50, 70, 250, 270);
////                canvas.drawArc(oval, 0, angle, true, p);}
//
//

            RectF rectF = new RectF(20, 400, 825, 430);
            p.setColor(Color.GREEN);
            canvas.drawRect(rectF, p);


            RectF rectF1 = new RectF(20, 510, 600, 540);
            p.setColor(Color.GREEN);
            canvas.drawRect(rectF1,p);









//        Paint paint = new Paint();
//        paint.setColor(Color.GREEN);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(10);
//        float leftx = 20;
//        float topy = 20;
//        float rightx = 150;
//        float bottomy = 60;
//        canvas.drawRect(leftx, topy, rightx, bottomy, paint);



        activity = (FloatingActionButton) findViewById(R.id.fab32);
        challenge = (FloatingActionButton) findViewById(R.id.fab22);
        leaderboard = (FloatingActionButton) findViewById(R.id.fab12);
        home = (FloatingActionButton) findViewById(R.id.fab02);
        my_activity= (FloatingActionButton) findViewById(R.id.fab52);


        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent();
                Intent intent = new Intent(landing_page.this,add_activity.class);
                startActivity(intent);
            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent();
                Intent intent = new Intent(landing_page.this,landing_page.class);
                startActivity(intent);
            }
        });

        challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent();
                Intent intent = new Intent(landing_page.this,challenge_activity.class);
                startActivity(intent);
            }
        });

        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                Intent intent = new Intent(landing_page.this, leaderboard.class);
                startActivity(intent);
            }
        });

        my_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent();
                Intent intent = new Intent(landing_page.this,my_activities.class);
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
        getMenuInflater().inflate(R.menu.landing_page, menu);
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



    class sendjson2 extends AsyncTask<String, String, String> {
        protected String doInBackground(String... args) {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                //button.setText("Network Connected");
                final String url1 = "http://" + "192.168.191.1" + "/activity_date.php";


                // Building Parameters
                List<NameValuePair> params2 = new ArrayList<NameValuePair>();



                JSONObject json1 = jsonparser.makeHttpRequest(url1,
                        "GET", params2);
                JSONArray receive = null;

                try {

                    JSONArray j1 = json1.getJSONArray("steps");
                    JSONArray j2 = json1.getJSONArray("cal");
                    JSONArray j3 = json1.getJSONArray("distance");
                    JSONArray j11 = json1.getJSONArray("type");
                    JSONArray j12 = json1.getJSONArray("sport_name");
                    JSONArray j13 = json1.getJSONArray("start_time");
                    JSONArray j14 = json1.getJSONArray("steps_taken");
                    JSONArray j15 = json1.getJSONArray("calories");
                    JSONArray j16 = json1.getJSONArray("distance_");
                    JSONArray j17 = json1.getJSONArray("intensity");





//                    trigger_names = new String[j1.length()];
//                    trigger_ids = new String[j2.length()];


                    s_ = j1.get(0).toString();
                    d_ = j3.get(0).toString();
                    c_ = j2.get(0).toString();
                    for(int i = 0 ; i < j11.length() ; i++) {

                        card_data card_node = new card_data(j12.get(i).toString(), j17.get(i).toString(), j13.get(i).toString(), j16.get(i).toString(), j15.get(i).toString(), j11.get(i).toString());
                        activities.add(card_node);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }


        protected void onPostExecute(String url) {
            s.setText(s_);
            d.setText(d_);
            c.setText(c_);


            listView = (ListView) findViewById(R.id.card_listView);
            //h++;
//                    listView.addHeaderView(new View(getActivity().getApplicationContext()));
//                    listView.addFooterView(new View(getActivity().getApplicationContext()));

            cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.list_item_card);
            listView.setAdapter(cardArrayAdapter);
            for (int i = 0; i < (activities.size()-1); i++)
            {

                Card card = new Card(activities.get(i).getTitle(), activities.get(i).getVenue(),activities.get(i).getTime(),activities.get(i).getCost(),activities.get(i).getSport_id());
                cardArrayAdapter.add(card);
            }

            listView.setAdapter(cardArrayAdapter);


        }


    }



    class sendjson3 extends AsyncTask<String, String, String> {
        protected String doInBackground(String... args) {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                //button.setText("Network Connected");
                final String url1 = "http://" + "192.168.191.1" + "/activity_date.php";


                // Building Parameters
                List<NameValuePair> params2 = new ArrayList<NameValuePair>();



                JSONObject json1 = jsonparser.makeHttpRequest(url1,
                        "GET", params2);
                JSONArray receive = null;

                try {

                    JSONArray j1 = json1.getJSONArray("steps");
                    JSONArray j2 = json1.getJSONArray("cal");
                    JSONArray j3 = json1.getJSONArray("distance");
                    JSONArray j01 = json1.getJSONArray("projection");


                    JSONArray j11 = json1.getJSONArray("type");
                    JSONArray j12 = json1.getJSONArray("sport_name");
                    JSONArray j13 = json1.getJSONArray("start_time");
                    JSONArray j14 = json1.getJSONArray("steps_taken");
                    JSONArray j15 = json1.getJSONArray("calories");
                    JSONArray j16 = json1.getJSONArray("distance_");
                    JSONArray j17 = json1.getJSONArray("intensity");
                    t = j01.length();
//
//
//
//
//
////                    trigger_names = new String[j1.length()];
////                    trigger_ids = new String[j2.length()];
//
//
                    s_ = j1.get(0).toString();
                    s_ = j1.getString(0);
                    d_ = j3.get(0).toString();
                    c_ = j2.get(0).toString();

                    min_=j01.get(0).toString();
                    max_=j01.get(1).toString();
                    target=j01.get(2).toString();
                    score_today=j01.get(3).toString();



                    for(int i = 0 ; i < j11.length() ; i++) {

                        card_data card_node = new card_data(j12.get(i).toString(), j17.get(i).toString(), j13.get(i).toString(), j16.get(i).toString(), j15.get(i).toString(), j11.get(i).toString());
                        activities.add(card_node);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }


        protected void onPostExecute(String url) {
            Toast.makeText(getApplicationContext(),
                    s_ + " " + d_+" " +c_  +" " +score_today + " " +max_ + " " + min_ +" " + target + Integer.toString(t),
                    Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), s_,Toast.LENGTH_SHORT).show();
            s.setText(s_);
            d.setText(d_);
            c.setText(c_);

//
//            listView = (ListView) findViewById(R.id.card_listView);
////            //h++;
//////                    listView.addHeaderView(new View(getActivity().getApplicationContext()));
//////                    listView.addFooterView(new View(getActivity().getApplicationContext()));
////
//            cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.list_item_card);
//            listView.setAdapter(cardArrayAdapter);
//            for (int i = 0; i < (activities.size()-1); i++)
//            {
//
//                Card card = new Card(activities.get(i).getTitle(), activities.get(i).getVenue(),activities.get(i).getTime(),activities.get(i).getCost(),activities.get(i).getSport_id());
//                cardArrayAdapter.add(card);
//            }
//
//            listView.setAdapter(cardArrayAdapter);
//
//
//
//
//            Bitmap bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
//            Canvas canvas = new Canvas(bitmap);
//            rec.setImageBitmap(bitmap);
//
//            // Rectangle
//
//
//            Paint p = new Paint();
//////        RectF rectF = new RectF(50, 120, 100, 220);
//////        p.setColor(Color.BLACK);
//////        canvas.drawArc(rectF, -90, 90, false, p);
////
////
////
//            final RectF oval = new RectF();
//
//            p.setStyle(Paint.Style.FILL_AND_STROKE);
//
//
//  /*
//   * drawArc(RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint)
//   *
//   * oval - The bounds of oval used to define the shape and size of the arc
//   * startAngle - Starting angle (in degrees) where the arc begins
//   * sweepAngle - Sweep angle (in degrees) measured clockwise
//   * useCenter - If true, include the center of the oval in the arc, and close it if it is being stroked. This will draw a wedge
//   * paint - The paint used to draw the arc
//   */
//
//
//
////
//            if(Integer.parseInt(score_today) > Integer.parseInt(max_)){
//                p.setColor(Color.RED);
//            oval.set(0, 20, 300, 320);
//            canvas.drawArc(oval, 0, 360, true, p);
//
//
//
//            p.setColor(Color.TRANSPARENT);
//
//            oval.set(50, 70, 250, 270);
//            canvas.drawArc(oval, 0, 360, true, p);}
//
//            else if((Integer.parseInt(score_today) <= Integer.parseInt(max_)) && (Integer.parseInt(score_today) > Integer.parseInt(min_))){
//                float angle = (Integer.parseInt(score_today)/Integer.parseInt(target))*360;
//                p.setColor(Color.GREEN);
//                oval.set(0, 20, 300, 320);
//                canvas.drawArc(oval, 0, angle, true, p);
//
//
//
//                p.setColor(Color.TRANSPARENT);
//
//                oval.set(50, 70, 250, 270);
//                canvas.drawArc(oval, 0, angle, true, p);}
//
//
//            else if((Integer.parseInt(score_today) <= Integer.parseInt(min_))){
//                float angle = (Integer.parseInt(score_today)/Integer.parseInt(target))*360;
//                p.setColor(Color.YELLOW);
//                oval.set(0, 20, 300, 320);
//                canvas.drawArc(oval, 0, angle, true, p);
//
//
//
//                p.setColor(Color.TRANSPARENT);
//
//                oval.set(50, 70, 250, 270);
//                canvas.drawArc(oval, 0, angle, true, p);}
//
//
//
//            RectF rectF = new RectF(280, 340, 200*Integer.parseInt(min_), 360);
//            p.setColor(Color.GREEN);
//            canvas.drawRect(rectF, p);
//
//
//            RectF rectF1 = new RectF(280, 380, 200*Integer.parseInt(max_), 400);
//            p.setColor(Color.GREEN);
//            canvas.drawRect(rectF1,p);



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

                    s_ = j1.getString(0);

//                    for (int i = 0; i < j1.length(); i++) {
//                        venue_names[i] = j1.getString(i);
//                        venue_ids[i]= j2.getString(i);
//
//                    }
//
//                    for (int i = 0; i < j4.length(); i++) {
//                        sport_names[i] = j3.getString(i);
//                        sport_ids[i]= j4.getString(i);
//
//                    }
//
//                    for (int i = 0; i < j5.length(); i++) {
//                        intensity_names[i] = j5.getString(i);
//                        intensity_ids[i]= j6.getString(i);
//
//                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }


        protected void onPostExecute(String url) {

            Toast.makeText(getApplicationContext(), s_+"    jbjbj", Toast.LENGTH_SHORT).show();
        }


    }

}
