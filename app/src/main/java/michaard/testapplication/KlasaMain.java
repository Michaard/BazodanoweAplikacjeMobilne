package michaard.testapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class KlasaMain extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1=(Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                TextView topText=(TextView)findViewById(R.id.topText);
                topText.setText("Wciśnięto przycisk1!");
            }
        });
        Button button2=(Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                TextView topText=(TextView)findViewById(R.id.topText);
                topText.setText("Wciśnięto przycisk2!");
            }
        });
        Button button3=(Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                TextView topText=(TextView)findViewById(R.id.topText);
                topText.setText("Wciśnięto przycisk3!");
            }
        });
        Button button4=(Button)findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                TextView bottomText=(TextView)findViewById(R.id.bottomText);
                EditText firstName=(EditText)findViewById(R.id.editName);
                EditText lastName=(EditText)findViewById(R.id.editSurname);
                bottomText.setText(firstName.getText()+" "+lastName.getText());
            }
        });
        Button button5=(Button)findViewById(R.id.buttonPic);
        button5.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(KlasaMain.this,Obrazek.class);
                startActivity(intent);
            }
        });
    }
}
