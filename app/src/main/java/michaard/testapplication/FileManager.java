package michaard.testapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManager extends Activity{
    static final private String fileName="plikTestowy.txt";
    static final private String dirName="/Pliki Testowe/";
    static File textFile;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_manager);

        File sdcard=Environment.getExternalStorageDirectory();
        final File dir=new File(sdcard.getAbsolutePath()+dirName);
        if(!dir.exists()) {
            dir.mkdir();
        }

        textFile=new File(dir,fileName);

        loadFile();

        Button button1=(Button)findViewById(R.id.buttonSaveTextFile);
        Button button2=(Button)findViewById(R.id.buttonLoadTextFile);
        Button button3=(Button)findViewById(R.id.buttonDeleteTextFile);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et=(EditText)findViewById(R.id.textFileEditText);
                String text=et.getText().toString();
                if(et.getText().length()==0){
                    Toast.makeText(getApplicationContext(),"Nic nie wpisano!",Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        FileOutputStream os = new FileOutputStream(textFile);
                        os.write(text.getBytes());
                        os.close();
                        Toast.makeText(getApplicationContext(), "Zapisano do "+textFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        Toast.makeText(getApplicationContext(), "Nie znaleziono pliku!", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), "Coś poszło nie tak!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFile();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });
    }

    private void clear(){
        if(textFile.exists()) {
            boolean deleted = textFile.delete();
            TextView description = (TextView) findViewById(R.id.textFileContent);
            description.setText("[plik pusty]");
            if (deleted)
                Toast.makeText(this, "Wyczyszczono", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Nie wyczyszczono", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Plik nie istnieje", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFile(){
        if(textFile.exists()) {
            int length = (int) textFile.length();
            if (length == 0) {
                TextView description = (TextView) findViewById(R.id.tuneDescriptionText);
                description.setText("[plik pusty]");
            } else {
                byte[] bytes = new byte[length];
                try {
                    FileInputStream in = new FileInputStream(textFile);
                    in.read(bytes);
                    in.close();
                    Toast.makeText(getApplicationContext(), "Wczytano plik", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Nie znaleziono pliku!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "Coś poszło nie tak!", Toast.LENGTH_SHORT).show();
                }
                String textFileContent = new String(bytes);
                TextView tv = (TextView) findViewById(R.id.textFileContent);
                tv.setText(textFileContent);
            }
        }
        else {
            Toast.makeText(this, "Plik nie istnieje", Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed(){
        FileManager.this.finish();
    }
}