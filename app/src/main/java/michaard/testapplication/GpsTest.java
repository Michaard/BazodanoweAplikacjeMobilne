package michaard.testapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.app.Activity;
import android.widget.Toast;

public class GpsTest extends Activity implements LocationListener{
    private TextView dostawca;
    private TextView dlugosc;
    private TextView szerokosc;

    private Criteria cr;
    private Location loc;
    private String mojDostawca;

    private LocationManager locMng;

    private final int LOCATION_REQUEST_CODE=102;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_test);

        dostawca=(TextView)findViewById(R.id.dostawca);
        dlugosc=(TextView)findViewById(R.id.dlugosc);
        szerokosc=(TextView)findViewById(R.id.szerokosc);

        if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
            cr = new Criteria();

            locMng = (LocationManager)getSystemService(LOCATION_SERVICE);
            if(locMng!=null){
                mojDostawca = locMng.getBestProvider(cr,true);
                locMng.requestLocationUpdates(mojDostawca,400,0.1f,this);
                loc = locMng.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
            else {
                Toast.makeText(getApplicationContext(),"LocationManager is null",Toast.LENGTH_SHORT).show();
            }
            if(loc!=null && mojDostawca!=null){
                Toast.makeText(getApplicationContext(),"I'm in!",Toast.LENGTH_SHORT).show();
                dostawca.setText("dostawca: " + mojDostawca);
                dlugosc.setText("długość geograficzna: " + loc.getLongitude());
                szerokosc.setText("szerokość geograficzna: " + loc.getLatitude());
            }
            else{
                if(loc==null) {
                    Toast.makeText(getApplicationContext(), "Location is null; Provider = " + mojDostawca, Toast.LENGTH_SHORT).show();
                    dostawca.setText("dostawca: " + mojDostawca);
                    dlugosc.setText("długość geograficzna: 0.0");
                    szerokosc.setText("szerokość geograficzna: 0.0");
                }
                else
                    Toast.makeText(getApplicationContext(),"Provider is null",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"Permission to get location denied!",Toast.LENGTH_SHORT).show();
            makeRequest();
        }
    }

    @Override
    protected void onResume(){
        //Toast.makeText(getApplicationContext(),"GPS resumed!",Toast.LENGTH_SHORT).show();
        super.onResume();
        int permissionCheck= ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck!= PackageManager.PERMISSION_GRANTED)
            Toast.makeText(getApplicationContext(),"Permission to get location denied!",Toast.LENGTH_SHORT).show();
        else
            locMng.requestLocationUpdates(mojDostawca,400,0.1f,this);
    }

    @Override
    protected void onPause(){
        //Toast.makeText(getApplicationContext(),"GPS paused!",Toast.LENGTH_SHORT).show();
        super.onPause();
        locMng.removeUpdates(this);
    }

    @Override
    public void onProviderEnabled(String string){
        Toast.makeText(getApplicationContext(),"Provider enabled",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onStatusChanged(String string,int i,Bundle bundle){
        Toast.makeText(getApplicationContext(),"Status changed",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onProviderDisabled(String string){
        Toast.makeText(getApplicationContext(),"Provider disabled",Toast.LENGTH_SHORT).show();
    }
    public void onLocationChanged(Location location){
        //Toast.makeText(getApplicationContext(),"Location changed!",Toast.LENGTH_SHORT).show();
        mojDostawca=locMng.getBestProvider(cr,true);
        int permissionCheck= ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck!= PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(),"Permission to get location denied!",Toast.LENGTH_SHORT).show();
            makeRequest();
        }
        else {
            loc = locMng.getLastKnownLocation(mojDostawca);
            dostawca.setText("dostawca: " + mojDostawca);
            dlugosc.setText("długość geograficzna: " + location.getLongitude());
            szerokosc.setText("szerokość geograficzna: " + location.getLatitude());
        }
    }

    protected void makeRequest(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);
    }

    public void onRequestPermissionsResult(int requestCode,String permissions[],int[] grantResults){
        switch(requestCode){
            case LOCATION_REQUEST_CODE:{
                if(grantResults.length==0 || grantResults[0]!=PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(getApplicationContext(),"Permission to get location denied!",Toast.LENGTH_SHORT).show();
                else{
                    Toast.makeText(getApplicationContext(),"Permission to get location granted!",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void onBackPressed(){
        GpsTest.this.finish();
    }
}