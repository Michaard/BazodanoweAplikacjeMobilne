package michaard.testapplication;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import java.io.IOException;

public class Dzwiekonagrywacz extends Activity{

    private MediaRecorder myAudioRecorder;
    private MediaPlayer mp;
    private Button buttonStopRec;
    private Button buttonPlayRec;
    private static final int RECORD_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int permissionCheckWES=ContextCompat.checkSelfPermission(Dzwiekonagrywacz.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheckRA=ContextCompat.checkSelfPermission(Dzwiekonagrywacz.this,android.Manifest.permission.RECORD_AUDIO);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dziwiekonagrywacz);

        if(permissionCheckWES==PackageManager.PERMISSION_GRANTED){
            if(permissionCheckRA==PackageManager.PERMISSION_GRANTED){
                String fileName=Environment.getExternalStorageDirectory().getAbsolutePath();
                fileName+="/audiorecordtest.3gp";

                buttonStopRec=(Button)findViewById(R.id.buttonStopRec);
                buttonPlayRec=(Button)findViewById(R.id.buttonPlayRec);

                buttonStopRec.setClickable(false);
                buttonPlayRec.setClickable(false);

                myAudioRecorder = new MediaRecorder();
                mp=new MediaPlayer();
                myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                myAudioRecorder.setOutputFile(fileName);

                try {
                    mp.setDataSource(fileName);
                }
                catch(IOException e){
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Log.i("Błąd", "Brak pozwolenia na użycie mikrofonu!");
                makeRequest();
            }
        }
        else{
            Log.i("Błąd", "Brak pozwolenia na użycie zewnętrznego miejsca zapisu!");
            makeRequest();
        }
    }
    public void onBackPressed(){
        stopPlayingRecording();
        Dzwiekonagrywacz.this.finish();
    }

    public void startRecording() {
        try{
            myAudioRecorder.prepare();
            myAudioRecorder.start();
            buttonStopRec.setClickable(true);
        }
        catch(IOException e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void stopRecording() {
        myAudioRecorder.stop();
        myAudioRecorder.release();
        myAudioRecorder=null;
        buttonPlayRec.setClickable(true);
    }

    public void playRecording() {
        int permissionCheck=ContextCompat.checkSelfPermission(Dzwiekonagrywacz.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permissionCheck!=PackageManager.PERMISSION_GRANTED){
            Log.i("Błąd!","Brak pozwolenia na odtworzenie pliku!");
            makeRequest();
        }
        else {
            try {
                mp.prepare();
                mp.start();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void stopPlayingRecording() {
        mp.stop();
        mp.release();
        mp=null;
    }

    protected void makeRequest(){
        ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.RECORD_AUDIO,android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},RECORD_REQUEST_CODE);
    }

    public void onRequestPermissionsResult(int requestCode,String permissions[],int[] grantResults){
        switch(requestCode){
            case RECORD_REQUEST_CODE:{
                if(grantResults.length==0 || grantResults[0]!=PackageManager.PERMISSION_GRANTED || grantResults[1]!=PackageManager.PERMISSION_GRANTED || grantResults[2]!=PackageManager.PERMISSION_GRANTED)
                    Log.i("Błąd","Użytkownik nie udzielił pozwolenia!");
                else{
                    Log.i("Błąd", "Użytkownik udzielił pozwolenie!");
                }
                return;
            }
        }
    }
}
