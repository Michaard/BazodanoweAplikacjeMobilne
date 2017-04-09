package michaard.testapplication;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

public class Obrazek extends Activity{
    ImageView imageView=(ImageView)findViewById(R.id.imageView);
    public void changePic(View v){
        imageView.setImageResource(R.drawable.android2);
    }
}