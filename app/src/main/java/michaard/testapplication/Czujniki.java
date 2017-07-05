package michaard.testapplication;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Czujniki extends Activity implements SensorEventListener {

    private Sensor orientationSensor;
    private SensorManager sensorManager;

    private float[] orientationData=null;

    private float currentDegree=0;
    private float rotation;

    private ImageView compassArrow;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_czujniki);

        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);

        TextView sltv=(TextView)findViewById(R.id.sensorListTextView);

        List<Sensor> sensorList=sensorManager.getSensorList(Sensor.TYPE_ALL);

        String sensorListStr="";
        for(Sensor s : sensorList){
            sensorListStr+="- "+s.getName()+"\n";
        }

        sltv.setText(sensorListStr);

        orientationSensor=sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        compassArrow=(ImageView)findViewById(R.id.compassArrowView);

        if(orientationSensor==null) {
            Toast.makeText(getApplicationContext(), "Brak czujnika orientacji!", Toast.LENGTH_LONG).show();
        }
    }

    protected void onResume(){
        super.onResume();
        if(orientationSensor!=null)
            sensorManager.registerListener(this,orientationSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void onSensorChanged(SensorEvent event){
        if(event.sensor.getType()==Sensor.TYPE_ORIENTATION){
            orientationData=new float[3];
            System.arraycopy(event.values,0,orientationData,0,3);
        }
        if(orientationData!=null){
            rotation=orientationData[0];

            RotateAnimation animation=new RotateAnimation(currentDegree,-rotation, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);

            animation.setDuration(210);

            animation.setFillAfter(true);

            compassArrow.startAnimation(animation);
            currentDegree=-rotation;
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }
}
