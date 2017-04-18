package michaard.testapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class Nokia extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nokia);
    }
    public void exitNokia(View v){
        finish();
    }
}
