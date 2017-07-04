package michaard.testapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Odtwarzacz extends Activity{

    static final private int LIST_DIALOG_ALERT=1;
    static final private int ADD_DESCRIPTION_ALERT=2;
    static private int CHOSEN_TUNE=0;
    static private MediaPlayer mp;
    static private String fileName;
    static private String tuneNameText="[nie wybrano utworu]";

    private Dialog createListDialogAlert(){
        AlertDialog.Builder db=new AlertDialog.Builder(this);
        final String[] options={"Blackheart","Imperial March"};
        final TextView tuneName=(TextView)findViewById(R.id.tuneNameText);
        db.setTitle("Lista utworów do odtworzenia");
        db.setItems(options,new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int pos){
                switch (pos){
                    case 0:
                        if(mp!=null)
                            mp.release();
                        mp=MediaPlayer.create(Odtwarzacz.this,R.raw.blackheart);
                        mp.start();
                        tuneNameText="Two Steps From Hell - Blackheart";
                        tuneName.setText(tuneNameText);
                        CHOSEN_TUNE=1;
                        fileName="blackheart.txt";
                        break;
                    case 1:
                        if(mp!=null)
                            mp.release();
                        mp=MediaPlayer.create(Odtwarzacz.this,R.raw.imperial_march);
                        mp.start();
                        tuneNameText="John Williams - Imperial March";
                        tuneName.setText(tuneNameText);
                        CHOSEN_TUNE=2;
                        fileName="imperial_march.txt";
                        break;
                    default:
                        break;
                }
                loadDescriptionManager(1);
            }
        });
        return db.create();
    }

    private View getAddDescriptionAlertLayout(){
        LayoutInflater inflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.add_tune_desc_layout,(ViewGroup)findViewById(R.id.addTuneDescView));
    }

    private Dialog createAddDescriptionAlert(){
        AlertDialog.Builder db=new AlertDialog.Builder(this);
        db.setTitle("Dodawanie opisu");
        View addDescView=getAddDescriptionAlertLayout();

        Button buttonSave=(Button)addDescView.findViewById(R.id.buttonSaveTuneDescription);
        final EditText description=(EditText)addDescView.findViewById(R.id.tuneDescriptionEditText);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(description.getText().length()==0){
                    Toast.makeText(getApplicationContext(),"Nic nie wpisano!",Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        OutputStreamWriter out=new OutputStreamWriter((openFileOutput(fileName,MODE_APPEND)));
                        out.write(description.getText().toString());
                        out.write('\n');
                        Toast.makeText(getApplicationContext(),"Zapisano opis do "+fileName,Toast.LENGTH_SHORT).show();
                        out.close();
                    }
                    catch(IOException e){
                        Toast.makeText(getApplicationContext(),"Zapis się nie powiódł",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        db.setView(addDescView);
        return db.create();
    }

    protected Dialog onCreateDialog(int id){
        switch (id){
            case LIST_DIALOG_ALERT:
                return createListDialogAlert();
            case ADD_DESCRIPTION_ALERT:
                return createAddDescriptionAlert();
            default:
                return null;
        }
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odtwarzacz);

        TextView tuneName=(TextView)findViewById(R.id.tuneNameText);
        tuneName.setText(tuneNameText);

        loadDescriptionManager(CHOSEN_TUNE);

        Button button1=(Button)findViewById(R.id.buttonPlaylist);
        Button button2=(Button)findViewById(R.id.buttonStartSound);
        Button button3=(Button)findViewById(R.id.buttonStopSound);
        Button button4=(Button)findViewById(R.id.ButtonQuitOdtwarzacz);
        Button button5=(Button)findViewById(R.id.buttonAddDescription);
        Button button6=(Button)findViewById(R.id.buttonLoadDescription);
        Button button7=(Button)findViewById(R.id.buttonCleanDescription);

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
                    Toast.makeText(getApplicationContext(),"Nie wybrano utworu",Toast.LENGTH_SHORT).show();
            }
        });

        button3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(mp!=null)
                    mp.pause();
                else
                    Toast.makeText(getApplicationContext(),"Nie wybrano utworu",Toast.LENGTH_SHORT).show();
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

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(CHOSEN_TUNE==0){
                    Toast.makeText(getApplicationContext(),"Nie wybrano utworu",Toast.LENGTH_SHORT).show();
                }
                else {
                    showDialog(ADD_DESCRIPTION_ALERT);
                }
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                loadDescriptionManager(1);
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });
    }

    private void loadDescriptionManager(int o){
        switch (o){
            case 0:
                break;
            default:
                loadDescription();
                break;
        }
    }

    private void loadDescription(){
        if(CHOSEN_TUNE==0){
            Toast.makeText(getApplicationContext(),"Nie wybrano utworu",Toast.LENGTH_SHORT).show();
        }
        else{
            TextView tv=(TextView)findViewById(R.id.tuneDescriptionText);
            StringBuilder text=new StringBuilder();
            try{
                InputStream in=openFileInput(fileName);
                if(in!=null){
                    InputStreamReader instr=new InputStreamReader(in);
                    BufferedReader br=new BufferedReader(instr);
                    String line;
                    while((line=br.readLine())!=null){
                        text.append(line);
                        text.append('\n');
                    }
                    br.close();
                    instr.close();
                    in.close();
                    tv.setText(text);
                    return;
                }
            }
            catch(FileNotFoundException e){
                Toast.makeText(getApplicationContext(),"Plik opisu nie istnieje!",Toast.LENGTH_SHORT).show();
            }
            catch(IOException e){
                Toast.makeText(getApplicationContext(),"Coś poszło nie tak z plikiem opisu!",Toast.LENGTH_SHORT).show();
            }
            tv.setText("[brak opisu]");
        }
    }

    private void clear(){
        if(fileName!=null) {
            File file = new File(getFileStreamPath(fileName).toString());
            boolean deleted = file.delete();
            TextView description = (TextView) findViewById(R.id.tuneDescriptionText);
            description.setText("[brak opisu]");
            if (deleted) {
                Toast.makeText(this, "Wyczyszczono", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Nie wyczyszczono", Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(this, "Nie ma czego usuwać", Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed(){
        if(mp!=null) {
            mp.stop();
            mp.release();
        }
        Odtwarzacz.this.finish();
    }
}
