package michaard.testapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Obrazek extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.obrazek);
    }
    /*public void changePic(View v){
        ImageView imageView=(ImageView)findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.android2);
    }*/
    public void changePicPrev(View v){
        ImageView imageView=(ImageView)findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.android);
    }
    public void changePicNext(View v){
        ImageView imageView=(ImageView)findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.android2);
    }
    public void back(View v){
        finish();
    }
}