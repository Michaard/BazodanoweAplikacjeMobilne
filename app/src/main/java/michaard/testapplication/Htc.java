package michaard.testapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class Htc extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.htc);
    }
    public void exitHtc(View v){
        finish();
    }
}
