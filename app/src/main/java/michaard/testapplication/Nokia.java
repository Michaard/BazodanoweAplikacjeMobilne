package michaard.testapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class Nokia extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nokia);
    }
    public void exitNokia(View v){
        Nokia.this.finish();
    }
}
