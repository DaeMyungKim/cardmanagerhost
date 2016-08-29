package com.kdml.cardmanagerhost;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.api.model.StringList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kdml.cardmanagerhost.DTO.Cost;
import com.kdml.cardmanagerhost.DTO.CostData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView listView1;
    IconTextListAdapter adapter;
    private DatabaseReference mDatabase;
    HashMap<String,CostData> map;
//test
    @Override
    protected void onResume() {
        super.onResume();

        setData();
    }
    public void setData()
    {
        if(map==null)
            map =new HashMap<String, CostData>();
        else
            map.clear();

        mDatabase = FirebaseDatabase.getInstance().getReference("costsmap");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String,HashMap> maptest = new HashMap<>();

                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    try{
                        for(DataSnapshot shot : snapshot.getChildren()) {
                            try{
                                for(DataSnapshot sh : shot.getChildren()) {
                                    try{

                                        String key = sh.getKey();
                                        Log.d("kdml",sh.toString());
                                        CostData cd = sh.getValue(CostData.class);
                                        Log.d("kdml",cd.getCardName()+ cd.getEmail()+ cd.getCost());
                                        map.put(cd.getCardName()+cd.getEmail(),cd);
                                    }catch(ClassCastException e){
                                        e.printStackTrace();
                                    }

                                }
                            }catch(ClassCastException e){
                                e.printStackTrace();
                            }
                        }
                    }
                    catch (ClassCastException e)
                    {
                        e.printStackTrace();
                    }
                }
                setListData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("kdml", "Failed to read value. : " + databaseError.toException());
            }
        });

    }

    public void setListData()
    {
        // 어댑터 객체 생성
        adapter = new IconTextListAdapter(this);

        // 아이템 데이터 만들기
        Resources res = getResources();
        for(CostData cd : map.values())
        {
            adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon05), cd.getCardName(), cd.getName()+" "+cd.getPhone(), cd.getCost()));
        }

        /*
        adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon06), "고스톱 - 강호동 버전", "26,000 다운로드", "1500 원"));
        adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon05), "친구찾기 (Friends Seeker)", "300,000 다운로드", "900 원"));
        adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon06), "강좌 검색", "120,000 다운로드", "900 원"));
        adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon05), "지하철 노선도 - 서울", "4,000 다운로드", "1500 원"));
        adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon06), "지하철 노선도 - 도쿄", "6,000 다운로드", "1500 원"));
        adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon05), "지하철 노선도 - LA", "8,000 다운로드", "1500 원"));
        adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon06), "지하철 노선도 - 워싱턴", "7,000 다운로드", "1500 원"));
        adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon05), "지하철 노선도 - 파리", "9,000 다운로드", "1500 원"));
        adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon06), "지하철 노선도 - 베를린", "38,000 다운로드", "1500 원"));
        */
        // 리스트뷰에 어댑터 설정
        listView1.setAdapter(adapter);

        // 새로 정의한 리스너로 객체를 만들어 설정
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IconTextItem curItem = (IconTextItem) adapter.getItem(position);
                String[] curData = curItem.getData();

                Toast.makeText(getApplicationContext(), "Selected : " + curData[0], Toast.LENGTH_LONG).show();
            }

        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Refresh...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                onResume();
            }
        });
        // 리스트뷰 객체 참조
        listView1 = (ListView) findViewById(R.id.listView1);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
