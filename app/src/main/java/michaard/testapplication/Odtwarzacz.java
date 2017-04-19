package michaard.testapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Odtwarzacz extends Activity{

    static final private int LIST_DIALOG_ALERT=3;
    private MediaPlayer mp;

    private Dialog createListDialogAlert(){
        AlertDialog.Builder db=new AlertDialog.Builder(this);
        final String[] options={"Blackheart","Imperial March"};
        db.setTitle("Lista utworów do odtworzenia");
        db.setItems(options,new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int pos){
                switch (pos){
                    case 0:
                        if(mp!=null)
                            mp.release();
                        mp=MediaPlayer.create(Odtwarzacz.this,R.raw.blackheart);
                        mp.start();
                        break;
                    case 1:
                        if(mp!=null)
                            mp.release();
                        mp=MediaPlayer.create(Odtwarzacz.this,R.raw.imperial_march);
                        mp.start();
                        break;
                    default:
                        break;
                }
            }
        });
        return db.create();
    }

    protected Dialog onCreateDialog(int id){
        switch (id){
            case LIST_DIALOG_ALERT:
                return createListDialogAlert();
            default:
                return null;
        }
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odtwarzacz);

        Button button1=(Button)findViewById(R.id.buttonPlaylist);
        Button button2=(Button)findViewById(R.id.buttonStartSound);
        Button button3=(Button)findViewById(R.id.buttonStopSound);
        Button button4=(Button)findViewById(R.id.ButtonQuitOdtwarzacz);

        button1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                showDialog(LIST_DIALOG_ALERT);
            }
        });

        button2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(mp!=null)
                    mp.start();
                else
                    return;
            }
        });

        button3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(mp!=null)
                    mp.pause();
                else
                    return;
            }
        });

        button4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(mp!=null) {
                    mp.stop();
                    mp.release();
                }
                Odtwarzacz.this.finish();
            }
        });
    }
}
