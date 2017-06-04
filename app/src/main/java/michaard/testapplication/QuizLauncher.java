package michaard.testapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class QuizLauncher extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_launcher);
        String userName=getIntent().getStringExtra("name");
        if(!userName.equals("Imię")){
            TextView greeting=(TextView)findViewById(R.id.greeting);
            greeting.setText("Witaj, "+userName+"!");
        }
    }
    public void startQuiz(View v){
        Intent intent=new Intent(QuizLauncher.this,Quiz.class);
        startActivity(intent);
        finish();
    }
    public void exitQuizLauncher(View v){
        QuizLauncher.this.finish();
    }
    public void onBackPressed(){
        QuizLauncher.this.finish();
    }
}
