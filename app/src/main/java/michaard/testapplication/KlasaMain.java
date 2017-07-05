package michaard.testapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KlasaMain extends Activity{

    static final private int EXIT_DIALOG_ALERT=0;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE=100;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE=200;

    private Dialog createExitDialogAlert(){
        AlertDialog.Builder db=new AlertDialog.Builder(KlasaMain.this);
        db.setTitle("Wyjście");
        db.setMessage("Na pewno chcesz wyjść?");
        db.setCancelable(false);
        db.setPositiveButton("Tak",new Dialog.OnClickListener(){
            public void onClick(DialogInterface dialog, int button){
                Toast.makeText(getApplicationContext(),"Aplikacja wyłączona",Toast.LENGTH_LONG).show();
                KlasaMain.this.finish();
            }
        });
        db.setNegativeButton("Nie",new Dialog.OnClickListener(){
            public void onClick(DialogInterface dialog,int button){
                Toast.makeText(getApplicationContext(),"Wyjście anulowane",Toast.LENGTH_LONG).show();
            }
        });
        return db.create();
    }

    protected Dialog onCreateDialog(int id){
        switch (id){
            case EXIT_DIALOG_ALERT:
                return createExitDialogAlert();
            default:
                return null;
        }
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klasa_main);

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                TextView topText=(TextView) findViewById(R.id.topText);
                topText.setText("Wciśnięto przycisk1!");
            }
        });
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                TextView topText=(TextView) findViewById(R.id.topText);
                topText.setText("Wciśnięto przycisk2!");
            }
        });
        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                TextView topText=(TextView) findViewById(R.id.topText);
                topText.setText("Wciśnięto przycisk3!");
            }
        });
        Button button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                TextView bottomText=(TextView) findViewById(R.id.bottomText);
                EditText firstName=(EditText) findViewById(R.id.editName);
                EditText lastName=(EditText) findViewById(R.id.editSurname);
                if(firstName.getText().length()==0)
                    Toast.makeText(getApplicationContext(),"Pole \"imię\" jest wymagane!",Toast.LENGTH_LONG).show();
                else
                    bottomText.setText(firstName.getText() + " " + lastName.getText());
            }
        });
        Button button5 = (Button) findViewById(R.id.buttonPic);
        button5.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(KlasaMain.this,Obrazek.class);
                startActivity(intent);
            }
        });
        Button button6 = (Button) findViewById(R.id.buttonQuiz);
        button6.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                TextView fullName=(TextView)findViewById(R.id.bottomText);
                String name=fullName.getText().toString().split(" ")[0];
                Intent intent=new Intent(KlasaMain.this,QuizLauncher.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });
        Button button7=(Button)findViewById(R.id.buttonAlerts);
        button7.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(KlasaMain.this,Alerty.class);
                startActivity(intent);
            }
        });
        Button button8=(Button)findViewById(R.id.buttonPhoneList);
        button8.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(KlasaMain.this,ListaTelefonow.class);
                startActivity(intent);
            }
        });
        Button button9=(Button)findViewById(R.id.buttonSoundPlayer);
        button9.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(KlasaMain.this,Odtwarzacz.class);
                startActivity(intent);
            }
        });
        Button button10=(Button)findViewById(R.id.buttonSoundRecorder);
        button10.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(KlasaMain.this,Dzwiekonagrywacz.class);
                startActivity(intent);
            }
        });
        Button button11=(Button)findViewById(R.id.buttonGpsTest);
        button11.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(KlasaMain.this,GpsTest.class);
                startActivity(intent);
            }
        });
        Button button13=(Button)findViewById(R.id.buttonFileManager);
        button13.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(KlasaMain.this,FileManager.class);
                startActivity(intent);
            }
        });
        Button button12=(Button)findViewById(R.id.buttonQuitApp);
        button12.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                showDialog(EXIT_DIALOG_ALERT);
            }
        });
        Button button14=(Button)findViewById(R.id.buttonSensorsTest);
        button14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(KlasaMain.this,Czujniki.class);
                startActivity(intent);
            }
        });
        Button button15=(Button)findViewById(R.id.buttonAparatTest);
        button15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });
        Button button16=(Button)findViewById(R.id.buttonCameraTest);
        button16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivityForResult(intent,CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onBackPressed(){
        showDialog(EXIT_DIALOG_ALERT);
    }
}
