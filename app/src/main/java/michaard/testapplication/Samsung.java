package michaard.testapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class Samsung extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_samsung);
    }
    public void exitSamsung(View v){
        Samsung.this.finish();
    }
    public void onBackPressed(){
        Samsung.this.finish();
    }
}
