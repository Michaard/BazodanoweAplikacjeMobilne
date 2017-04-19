package michaard.testapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListaTelefonow extends Activity{

    private ListView listView;
    private String[] phones;

    private void initResources(){
        Resources res=getResources();
        phones=res.getStringArray(R.array.phonesRes);
    }

    private void initPhonesListView(){
        listView.setAdapter(new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1,phones));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent,View v,int pos,long id){
                if(pos==0){
                    Intent intent=new Intent(ListaTelefonow.this,Htc.class);
                    startActivity(intent);
                }
                else if(pos==1){
                    Intent intent=new Intent(ListaTelefonow.this,Samsung.class);
                    startActivity(intent);
                }
                else if(pos==2){
                    Intent intent=new Intent(ListaTelefonow.this,Nokia.class);
                    startActivity(intent);
                }
            }
        });
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_telefonow);
        listView=(ListView)findViewById(R.id.telephones);
        initResources();
        initPhonesListView();
    }
}
