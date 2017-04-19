package michaard.testapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Alerty extends Activity{

    static final private int SIMPLE_DIALOG_ALERT=1;
    static final private int CUSTOM_DIALOG_ALERT=2;

    private Dialog createSimpleDialogAlert(){
        AlertDialog.Builder db=new AlertDialog.Builder(Alerty.this);
        db.setTitle("Bzdurne prawo USA");
        db.setMessage("W Ventura County (Kalifornia) koty i psy nie mogą uprawiać seksu bez zezwolenia.");
        return db.create();
    }

    private View getCustomDialogLayout(){
        LayoutInflater inflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.custom_dialog_layout,(ViewGroup)findViewById(R.id.customDialogView));
    }

    private Dialog createCustomDialogAlert(){
        AlertDialog.Builder db=new AlertDialog.Builder(Alerty.this);
        db.setTitle("Własny dialog");
        db.setView(getCustomDialogLayout());
        return db.create();
    }

    protected Dialog onCreateDialog(int id){
        switch(id){
            case SIMPLE_DIALOG_ALERT:
                return createSimpleDialogAlert();
            case CUSTOM_DIALOG_ALERT:
                return createCustomDialogAlert();
            default:
                return null;
        }
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerty);

        Button button1=(Button)findViewById(R.id.buttonSDA);
        button1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                showDialog(SIMPLE_DIALOG_ALERT);
            }
        });

        Button button2=(Button)findViewById(R.id.buttonCDA);
        button2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                showDialog(CUSTOM_DIALOG_ALERT);
            }
        });

        Button button3=(Button)findViewById(R.id.buttonQuitAlerty);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Alerty.this.finish();
            }
        });
    }
}
