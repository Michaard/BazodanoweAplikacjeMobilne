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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class Dzwiekonagrywacz extends Activity{

    private MediaRecorder myAudioRecorder;
    private MediaPlayer mp;
    private Button startRec;
    private Button stopRec;
    private Button playRec;
    private String fileName;
    private static final int RECORD_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int permissionCheckWES=ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheckRA=ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dziwiekonagrywacz);

        fileName=Environment.getExternalStorageDirectory().getAbsolutePath();
        fileName+="/audiorecordtest.3gp";

        startRec=(Button)findViewById(R.id.buttonStartRec);
        stopRec=(Button)findViewById(R.id.buttonStopRec);
        playRec=(Button)findViewById(R.id.buttonPlayRec);

        stopRec.setClickable(false);
        playRec.setClickable(false);

        myAudioRecorder = new MediaRecorder();
        mp = new MediaPlayer();

        if(permissionCheckWES==PackageManager.PERMISSION_GRANTED){
            if(permissionCheckRA==PackageManager.PERMISSION_GRANTED){

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

        startRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    startRec.setClickable(false);
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                    stopRec.setClickable(true);
                }
                catch(IOException e){
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        stopRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopRec.setClickable(false);
                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder=null;
                playRec.setClickable(true);
                try{
                    mp.prepare();
                }
                catch(IOException e){
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        playRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permissionCheck=ContextCompat.checkSelfPermission(Dzwiekonagrywacz.this,Manifest.permission.READ_EXTERNAL_STORAGE);
                if(permissionCheck!=PackageManager.PERMISSION_GRANTED){
                    Log.i("Błąd!","Brak pozwolenia na odtworzenie pliku!");
                    makeRequest();
                }
                else {
                    mp.start();
                }
            }
        });
    }
    public void onBackPressed(){
        stopPlayingRecording();
        clear();
        Dzwiekonagrywacz.this.finish();
    }

    public void stopPlayingRecording() {
        mp.stop();
        mp.release();
        mp=null;
    }

    public void clear(){
        File file=new File(fileName);
        boolean deleted=file.delete();
        if(deleted)
            Toast.makeText(this,"Wyczyszczono",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this,"Nie wyczyszczono",Toast.LENGTH_LONG).show();
    }

    protected void makeRequest(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},RECORD_REQUEST_CODE);
    }

    public void onRequestPermissionsResult(int requestCode,String permissions[],int[] grantResults){
        switch(requestCode){
            case RECORD_REQUEST_CODE:{
                if(grantResults.length==0 || grantResults[0]!=PackageManager.PERMISSION_GRANTED || grantResults[1]!=PackageManager.PERMISSION_GRANTED || grantResults[2]!=PackageManager.PERMISSION_GRANTED)
                    Log.i("Błąd","Użytkownik nie udzielił pozwolenia!");
                else{
                    Log.i("Błąd", "Użytkownik udzielił pozwolenie!");
                }
            }
        }
    }
}
