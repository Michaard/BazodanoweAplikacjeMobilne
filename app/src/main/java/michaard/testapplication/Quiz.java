package michaard.testapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Quiz extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);
    }
    public void quizResult(View v){

        RadioGroup radioGroup1=(RadioGroup)findViewById(R.id.radioGroup1);
        RadioButton button13=(RadioButton)findViewById(R.id.radioButton13);

        RadioGroup radioGroup2=(RadioGroup)findViewById(R.id.radioGroup2);
        RadioButton button21=(RadioButton)findViewById(R.id.radioButton21);

        RadioGroup radioGroup3=(RadioGroup)findViewById(R.id.radioGroup3);
        RadioButton button32=(RadioButton)findViewById(R.id.radioButton32);

        RadioGroup radioGroup4=(RadioGroup)findViewById(R.id.radioGroup4);
        RadioButton button44=(RadioButton)findViewById(R.id.radioButton44);

        RadioGroup radioGroup5=(RadioGroup)findViewById(R.id.radioGroup5);
        RadioButton button52=(RadioButton)findViewById(R.id.radioButton52);

        int checkRG1=radioGroup1.getCheckedRadioButtonId();
        int checkRG2=radioGroup2.getCheckedRadioButtonId();
        int checkRG3=radioGroup3.getCheckedRadioButtonId();
        int checkRG4=radioGroup4.getCheckedRadioButtonId();
        int checkRG5=radioGroup5.getCheckedRadioButtonId();

        if(checkRG1==-1 || checkRG2==-1 || checkRG3==-1 || checkRG4==-1 || checkRG5==-1){
            Toast.makeText(getApplicationContext(),"Nie wszystkie odpowiedzi zostały zaznaczone!",Toast.LENGTH_LONG).show();
        }
        else{
            int points=0;
            RadioButton checker;

            checker=(RadioButton)findViewById(checkRG1);
            if(checker.equals(button13))
                points++;

            checker=(RadioButton)findViewById(checkRG2);
            if(checker.equals(button21))
                points++;

            checker=(RadioButton)findViewById(checkRG3);
            if(checker.equals(button32))
                points++;

            checker=(RadioButton)findViewById(checkRG4);
            if(checker.equals(button44))
                points++;

            checker=(RadioButton)findViewById(checkRG5);
            if(checker.equals(button52))
                points++;

            if(points==5)
                Toast.makeText(getApplicationContext(),"Gratulacje! Wynik: "+points+"/5",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(),"Wynik: "+points+"/5",Toast.LENGTH_LONG).show();
        }
    }
    public void quizQuit(View v){
        finish();
    }
}
