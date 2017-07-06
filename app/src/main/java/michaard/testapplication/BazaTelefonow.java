package michaard.testapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class BazaTelefonow extends Activity{

    static final private int ADD_DIALOG_ALERT=1;
    static final private int REM_DIALOG_ALERT=2;
    static final private int MOD_DIALOG_ALERT=3;

    private ListView listView;
    private String[] phones;
    private PhonesDBAdapter dbAdapter;

    private View getAddPhoneDialogAlert(){
        LayoutInflater inflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.db_add_phone_layout,(ViewGroup)findViewById(R.id.dbAddPhoneLayout));
    }

    private View getRemovePhoneDialogAlert(){
        LayoutInflater inflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.db_remove_phone_layout,(ViewGroup)findViewById(R.id.dbRemovePhoneLayout));
    }

    private View getModifyPhoneDialogAlert(){
        LayoutInflater inflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.db_modify_phone_layout,(ViewGroup)findViewById(R.id.dbModifyPhoneLayout));
    }

    private Dialog createAddPhoneDialogAlert(){
        AlertDialog.Builder db=new AlertDialog.Builder(BazaTelefonow.this);
        db.setTitle("Dodaj telefon");
        final View addPhoneView=getAddPhoneDialogAlert();

        Button button=(Button)addPhoneView.findViewById(R.id.buttonSavePhoneDataToDB);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText brandEt=(EditText)addPhoneView.findViewById(R.id.dbAddPhoneBrandET);
                EditText nameEt=(EditText)addPhoneView.findViewById(R.id.dbAddPhoneNameET);

                if(brandEt.getText().length()!=0 && nameEt.getText().length()!=0){
                    dbAdapter.insertPhone(brandEt.getText().toString(),nameEt.getText().toString());
                    update();
                    Toast.makeText(getApplicationContext(),"Dodano telefon",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Uzupełnij wszystkie pola!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        db.setView(addPhoneView);
        return db.create();
    }

    private Dialog createRemovePhoneDialogAlert(){
        AlertDialog.Builder db=new AlertDialog.Builder(BazaTelefonow.this);
        db.setTitle("Usuń telefon");
        final View removePhoneView=getRemovePhoneDialogAlert();

        Button button=(Button)removePhoneView.findViewById(R.id.buttonRemovePhoneDataFromDB);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText idEt=(EditText)removePhoneView.findViewById(R.id.dbRemovePhoneIdET);

                if(idEt.getText().length()!=0){
                    dbAdapter.deleteData(Integer.parseInt(idEt.getText().toString()));
                    update();
                    Toast.makeText(getApplicationContext(),"Usunięto telefon",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Uzupełnij wszystkie pola!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        db.setView(removePhoneView);
        return db.create();
    }

    private Dialog createModifyPhoneDialogAlert(){
        AlertDialog.Builder db=new AlertDialog.Builder(BazaTelefonow.this);
        db.setTitle("Zmodyfikuj telefon");
        final View modifyPhoneView=getModifyPhoneDialogAlert();

        Button button=(Button)modifyPhoneView.findViewById(R.id.buttonSaveModifiedPhoneDataToDB);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText idEt=(EditText)modifyPhoneView.findViewById(R.id.dbModifyPhoneIdET);
                EditText brandEt=(EditText)modifyPhoneView.findViewById(R.id.dbModifyPhoneBrandET);
                EditText nameEt=(EditText)modifyPhoneView.findViewById(R.id.dbModifyPhoneNameET);

                if(idEt.getText().length()!=0 && brandEt.getText().length()!=0 && nameEt.getText().length()!=0){
                    dbAdapter.updateData(Integer.parseInt(idEt.getText().toString()),brandEt.getText().toString(),nameEt.getText().toString());
                    update();
                    Toast.makeText(getApplicationContext(),"Zmodyfikowano telefon",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Uzupełnij wszystkie pola!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        db.setView(modifyPhoneView);
        return db.create();
    }

    protected Dialog onCreateDialog(int id){
        switch(id){
            case ADD_DIALOG_ALERT:
                return createAddPhoneDialogAlert();
            case REM_DIALOG_ALERT:
                return createRemovePhoneDialogAlert();
            case MOD_DIALOG_ALERT:
                return createModifyPhoneDialogAlert();
            default:
                return null;
        }
    }

    private void initResources(){
        Cursor cursor=dbAdapter.getAllData();
        int count=cursor.getCount();
        phones=new String[count];
        cursor.moveToFirst();
        int i=0;
        while (cursor.isAfterLast()==false){
            phones[i]=cursor.getString(cursor.getColumnIndex("id"))+". ";
            phones[i]+=cursor.getString(cursor.getColumnIndex("marka"))+" ";
            phones[i]+=cursor.getString(cursor.getColumnIndex("nazwa"));
            cursor.moveToNext();
            i++;
        }
    }

    private void initPhonesList(){
        listView.setAdapter(new ArrayAdapter<>(getBaseContext(),android.R.layout.simple_list_item_1,phones));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baza_telefonow);

        dbAdapter=new PhonesDBAdapter(this);
        dbAdapter.open();
        listView = (ListView) findViewById(R.id.phonesDBlist);
        /*for(int i=1;i<=15;i++) {
            dbAdapter.insertPhone("Test","v. 0."+i);
        }*/

        update();

        Button addPhone=(Button)findViewById(R.id.buttonAddPhoneToDB);
        Button removePhone=(Button)findViewById(R.id.buttonRemovePhoneFromDB);
        Button modifyPhone=(Button)findViewById(R.id.buttonModifyPhoneInDB);

        addPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(ADD_DIALOG_ALERT);
            }
        });
        removePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(REM_DIALOG_ALERT);
            }
        });
        modifyPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(MOD_DIALOG_ALERT);
            }
        });
    }

    public void update(){
        initResources();
        initPhonesList();
    }

    public void onBackPressed(){
        dbAdapter.close();
        BazaTelefonow.this.finish();
    }
}
