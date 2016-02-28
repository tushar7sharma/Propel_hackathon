package shrey.gosporto;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import shrey.gosporto.Battery;
import shrey.gosporto.ColorPickerDialog;
import shrey.gosporto.L;
import shrey.gosporto.LeParams;
import shrey.gosporto.MiBand;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

public class MiOverviewActivityAfter extends Activity implements Observer {

    private static final UUID UUID_MILI_SERVICE = UUID
            .fromString("0000fee0-0000-1000-8000-00805f9b34fb");
    private static final UUID UUID_CHAR_pair = UUID
            .fromString("0000ff0f-0000-1000-8000-00805f9b34fb");
    private static final UUID UUID_CHAR_CONTROL_POINT = UUID
            .fromString("0000ff05-0000-1000-8000-00805f9b34fb");
    private static final UUID UUID_CHAR_REALTIME_STEPS = UUID
            .fromString("0000ff06-0000-1000-8000-00805f9b34fb");
    private static final UUID UUID_CHAR_ACTIVITY = UUID
            .fromString("0000ff07-0000-1000-8000-00805f9b34fb");
    private static final UUID UUID_CHAR_LE_PARAMS = UUID
            .fromString("0000ff09-0000-1000-8000-00805f9b34fb");
    private static final UUID UUID_CHAR_DEVICE_NAME = UUID
            .fromString("0000ff02-0000-1000-8000-00805f9b34fb");
    private static final UUID UUID_CHAR_BATTERY = UUID
            .fromString("0000ff0c-0000-1000-8000-00805f9b34fb");



    TextView dis,cal,time,date,dur,steps;

    private String match_uid,p_uid,Team_uid,duration,sport,format,match_id;
    jsonparser jsonParser4 = new jsonparser();
    int j=0;
    Double x,y;
    Dialog d2;

    public static final String PREFS_NAME = "MyPrefsFile";

    String mac_address = "88:0F:10:66:6F:83";
    String mac_address1 = "88:0F:10:66:63:99";

    // BLUETOOTH
    private String mDeviceAddress;
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mBluetoothMi;
    private BluetoothGatt mGatt;

    private MiBand mMiBand = new MiBand();
    //String server = "www.sportswave.co.in/intranet/pebble";


    // UI
    private TextView mTVSteps;
    private TextView mTVBatteryLevel;
    private ProgressBar mLoading;
    Bundle M;
    LinearLayout l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_mi_overview2);

        dis = (TextView) findViewById(R.id.textView13);
        cal = (TextView) findViewById(R.id.textView11);
        steps = (TextView) findViewById(R.id.Text3);
        date = (TextView) findViewById(R.id.Text12);
        time = (TextView) findViewById(R.id.Text0);
        dur = (TextView) findViewById(R.id.Text);

        l = (LinearLayout)findViewById(R.id.textHolder);







        mDeviceAddress = mac_address1;
        mMiBand.addObserver(this);
        mMiBand.mBTAddress = mDeviceAddress;
        Toast.makeText(getApplicationContext(), mDeviceAddress, Toast.LENGTH_SHORT).show();



        mTVSteps = (TextView) findViewById(R.id.text_steps);
        mTVSteps.setText(mDeviceAddress);
        //mTVSteps
        mTVBatteryLevel = (TextView) findViewById(R.id.text_battery_level);
        mLoading = (ProgressBar) findViewById(R.id.laoding);

        mBluetoothManager = ((BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE));
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        mBluetoothMi = mBluetoothAdapter.getRemoteDevice(mDeviceAddress);

    }

    @Override
    public void onResume() {
        super.onResume();
        mGatt = mBluetoothMi.connectGatt(this, false, mGattCallback);
        mGatt.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        mGatt.disconnect();
        mGatt.close();
        mGatt = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_leparams:
                if(mMiBand.mLeParams == null) {
                    L.toast(this, "No LE params received yet");
                    return true;
                }
                Intent intent = new Intent(getApplicationContext(), MiLeParamsActivity.class);
                intent.putExtra("params", mMiBand.mLeParams);
                startActivity(intent);
                break;
            case R.id.action_ledcolor:
                new ColorPickerDialog(this, null, "", Color.BLUE, Color.RED).show();
                break;
        }
        return true;
    }

    private void pair() {

        BluetoothGattCharacteristic chrt = getMiliService().getCharacteristic(
                UUID_CHAR_pair);

        chrt.setValue(new byte[] { 2 });

        mGatt.writeCharacteristic(chrt);
        System.out.println("pair sent");
    }

    private void request(UUID what) {
        mGatt.readCharacteristic(getMiliService().getCharacteristic(what));
    }

    private void setColor(byte r, byte g, byte b) {
        BluetoothGattCharacteristic theme = getMiliService().getCharacteristic(
                UUID_CHAR_CONTROL_POINT);
        theme.setValue(new byte[] { 14, r, g, b, 0 });
        mGatt.writeCharacteristic(theme);
    }

    private BluetoothGattService getMiliService() {
        return mGatt.getService(UUID_MILI_SERVICE);

    }

    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {

        int state = 0;

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                pair();
            }

        }

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status,
                                            int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                gatt.discoverServices();
            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt,
                                          BluetoothGattCharacteristic characteristic, int status) {
            // this is called tight after pair()
            // setColor((byte)127, (byte)0, (byte)0);
            request(UUID_CHAR_REALTIME_STEPS); // start with steps
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic, int status) {
            byte[] b = characteristic.getValue();
            Log.i(characteristic.getUuid().toString(), "state: " + state
                    + " value:" + Arrays.toString(b));

            // handle value
            if (characteristic.getUuid().equals(UUID_CHAR_REALTIME_STEPS))
                mMiBand.setSteps(0xff & b[0] | (0xff & b[1]) << 8);
            else if (characteristic.getUuid().equals(UUID_CHAR_BATTERY)) {
                Battery battery = Battery.fromByte(b);
                mMiBand.setBattery(battery);
            } else if (characteristic.getUuid().equals(UUID_CHAR_DEVICE_NAME)) {
                mMiBand.setName(new String(b));
            } else if (characteristic.getUuid().equals(UUID_CHAR_LE_PARAMS)) {
                LeParams params = LeParams.fromByte(b);
                mMiBand.setLeParams(params);
            }

            // proceed with state machine (called in the beginning)
            state++;
            switch (state) {
                case 0:
                    request(UUID_CHAR_REALTIME_STEPS);
                    break;
                case 1:
                    request(UUID_CHAR_BATTERY);
                    break;
                case 2:
                    request(UUID_CHAR_DEVICE_NAME);
                    break;
                case 3:
                    request(UUID_CHAR_LE_PARAMS);
                    break;
            }
        }
    };

    @Override
    public void update(Observable observable, Object data) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                mLoading.setVisibility(View.GONE);
                ((LinearLayout) findViewById(R.id.textHolder))
                        .setVisibility(View.VISIBLE);
                mTVSteps.setText(mMiBand.mSteps + "");
                if (mMiBand.mBattery != null){
                    mTVBatteryLevel.setText(mMiBand.mBattery.mBatteryLevel
                            + "%");
                    j++;}
                //Toast.makeText(getApplicationContext(),Integer.toString(mMiBand.mSteps), Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(),Integer.toString(j), Toast.LENGTH_SHORT).show();
                if(j==1) {
                    Toast.makeText(getApplicationContext(), mDeviceAddress + "   " + mMiBand.mSteps, Toast.LENGTH_SHORT).show();
                    final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("steps_after", Integer.toString(mMiBand.mSteps));
                    editor.putString("mi_over", "yes");
                    // Commit the edits!
                    editor.commit();


                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    time.setText(sdf.format(c.getTime()));

                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    date.setText(df.format(c.getTime()));

                    final String steps_taken = settings.getString("steps_before", "0");
                    final String venue = settings.getString("venue", "delhi");
                    final String sport = settings.getString("sport", "cricket");
                    steps.setText(Integer.toString(mMiBand.mSteps-Integer.parseInt(steps_taken)));
                    x = Double.parseDouble(steps.getText().toString());

                    x = x*0.8;
                    y = 0.073 * x;


                    dis.setText(String.valueOf(x));
                    cal.setText(String.valueOf(y));
                    try {
                        long a = sdf.parse(sdf.format(c.getTime())).getTime() - sdf.parse(settings.getString("start_time", "00:00:00")).getTime();
                        a = a / 6000;
                        int b = (int)a;
                        dur.setText(Integer.toString(b));
                    }
                    catch(Exception a)
                    {

                    }
                    Button b = new Button(getApplicationContext());
                    b.setText("Go");
                    l.addView(b);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            new sendjson().execute(venue,sport, settings.getString("start_time", "00:00:00") , date.getText().toString(), dur.getText().toString(), steps.getText().toString(),String.valueOf(x),String.valueOf(y),"2");



                        }
                    });


//                    Intent i = new Intent();
//                    Intent intent = new Intent(MiOverviewActivityAfter.this, mi_end.class);
//                    startActivity(intent);
                    //show1();


                }
            }
        });
    }




    public void show1()
    {

        d2 = new Dialog(MiOverviewActivityAfter.this);
        d2.setTitle("");
        d2.setContentView(R.layout.dialog_mi_over);
        final Button start_mi = (Button) d2.findViewById(R.id.button_start_mi);
        final Button end_mi = (Button) d2.findViewById(R.id.button_end_mi);

        start_mi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent();
                Intent intent = new Intent(MiOverviewActivityAfter.this,add_activity.class);
                startActivity(intent);
            }
        });


        end_mi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        d2.show();

    }




    class sendjson extends AsyncTask<String, String, String> {
        protected String doInBackground(String... args) {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                //button.setText("Network Connected");
                final String url1 = "http://" + "192.168.191.50" + "/insert.php";


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




                JSONObject json1 = jsonParser4.makeHttpRequest(url1,
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
            Intent i = new Intent();
                    Intent intent = new Intent(MiOverviewActivityAfter.this, landing_page.class);
                    startActivity(intent);

        }


    }



}
