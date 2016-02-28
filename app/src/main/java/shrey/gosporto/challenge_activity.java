package shrey.gosporto;

import android.content.Intent;
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
import android.widget.ListView;
import android.widget.TextView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class challenge_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listView;
    private CardArrayAdapter cardArrayAdapter;
    private FloatingActionButton activity,challenge,leaderboard,home,my_activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        View view = null;
        TextView title;

//        view = this.getLayoutInflater().inflate(R.layout.listview,
//                container, false);
//
//        container.addView(view);

        listView = (ListView) findViewById(R.id.card_listView);
        //h++;
//                    listView.addHeaderView(new View(getActivity().getApplicationContext()));
//                    listView.addFooterView(new View(getActivity().getApplicationContext()));

        cardArrayAdapter = new CardArrayAdapter(this.getApplicationContext(), R.layout.list_item_card);
        listView.setAdapter(cardArrayAdapter);
//                    url1 = "http://www.instasports.co/campaign/webservice/activity.php?product_category_id=3";
//                    list.clear();
//                    new sendjson1().execute();
//                    list.trimToSize();
//                    while(v == 0){
//
//                    }
//                    v=0;


        Card card = new Card("Running","Very Light","27 Feb 2016 4:00 pm","500","400");
        cardArrayAdapter.add(card);

        Card card1 = new Card("Football","Moderate","27 Feb 2016 2:00 pm","1500","600");
        cardArrayAdapter.add(card1);

        Card card2 = new Card("Cricket","Light","27 Feb 2016 10:00 am","500","400");
        cardArrayAdapter.add(card2);

        Card card3 = new Card("Walking","Very Light","26 Feb 2016 10:00 pm","500","100");
        cardArrayAdapter.add(card3);

        Card card4 = new Card("Running","Hard","26 Feb 2016 9:00 am","1500","700");
        cardArrayAdapter.add(card4);

        Card card5 = new Card("Football","Hard","25 Feb 2016 6:00 pm","700","500");
        cardArrayAdapter.add(card5);


        listView.setAdapter(cardArrayAdapter);











        activity = (FloatingActionButton) findViewById(R.id.fab32);
        challenge = (FloatingActionButton) findViewById(R.id.fab22);
        leaderboard = (FloatingActionButton) findViewById(R.id.fab12);
        home = (FloatingActionButton) findViewById(R.id.fab02);
        my_activity= (FloatingActionButton) findViewById(R.id.fab52);


        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent();
                Intent intent = new Intent(challenge_activity.this,add_activity.class);
                startActivity(intent);
            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent();
                Intent intent = new Intent(challenge_activity.this,landing_page.class);
                startActivity(intent);
            }
        });

        challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent();
                Intent intent = new Intent(challenge_activity.this,challenge_activity.class);
                startActivity(intent);
            }
        });

        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                Intent intent = new Intent(challenge_activity.this, leaderboard.class);
                startActivity(intent);
            }
        });

        my_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent();
                Intent intent = new Intent(challenge_activity.this,my_activities.class);
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
        getMenuInflater().inflate(R.menu.my_activities, menu);
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
}
