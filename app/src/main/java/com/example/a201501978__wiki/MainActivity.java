package com.example.a201501978__wiki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TabActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.time.DayOfWeek;
import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {
    TabHost tabHost;
    View dialogView, listview1;
    Button btnAdd, btnFind;
    ImageButton canImg, glassImg, paperImg, plasticImg, styrofoamImg, vinylImg;
    EditText dlgName, dlgInfor, findText;
    String category, sqlSelect, findText2;
    String[] strs;
    int[] ints;
    Cursor c;
    Spinner spinnerAdd;
    ArrayList<FindList> FindListNew;
    SQLiteDatabase db;
    RecycleDatabaseManager databaseManager;
    MyAdapter myAdapter1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("분리수거wiki");
        DBHelper helper;
        databaseManager = RecycleDatabaseManager.getInstance(this);

        /*helper = new DBHelper(MainActivity.this, "Recycled.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);*/

        //final RecycleDatabaseManager databaseManager = RecycleDatabaseManager.getInstance(this);

        canImg = (ImageButton) findViewById(R.id.canImg);
        glassImg = (ImageButton) findViewById(R.id.glassImg);
        paperImg = (ImageButton) findViewById(R.id.paperImg);
        plasticImg = (ImageButton) findViewById(R.id.plasticImg);
        styrofoamImg = (ImageButton) findViewById(R.id.styrofoamImg);
        vinylImg = (ImageButton) findViewById(R.id.vinylImg);

        canImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent canIntent = new Intent(getApplicationContext(), CanTmi.class);
                startActivity(canIntent);
            }
        });
        glassImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent glassIntent = new Intent(getApplicationContext(), glassTmi.class);
                startActivity(glassIntent);
            }
        });
        paperImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent paperIntent = new Intent(getApplicationContext(), PaperTmi.class);
                startActivity(paperIntent);
            }
        });
        plasticImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent plasticIntent = new Intent(getApplicationContext(), PlasticTmi.class);
                startActivity(plasticIntent);
            }
        });
        styrofoamImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent styrofoamIntent = new Intent(getApplicationContext(), StyrofoamTmi.class);
                startActivity(styrofoamIntent);
            }
        });
        vinylImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vinylIntent = new Intent(getApplicationContext(), VinylTmi.class);
                startActivity(vinylIntent);
            }
        });


        //검색리스트뷰
        this.InitializeFindData();

        ListView listViewNew = (ListView) findViewById(R.id.listviewNew);  //검색목록
        myAdapter1 = new MyAdapter(this, FindListNew);
        listViewNew.setAdapter(myAdapter1);


        //어탭터와 리스트뷰 연결하기
        /*MyAdapter adapter = null;
        adapter = new MyAdapter(listViewNew.getContext(), R.layout.listview_item, c, strs, ints);

        listViewNew.setAdapter(adapter);*/


        //추가버튼 이벤트
        btnAdd = (Button) findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView = (View) View.inflate(MainActivity.this, R.layout.add_dialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);

                dlg.setTitle("분리수거 방법 추가");
                dlg.setIcon(R.drawable.ic_launcher_foreground);
                dlg.setView(dialogView);


                spinnerAdd = (Spinner) dialogView.findViewById(R.id.spinnerAdd);
                spinnerAdd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {   //스피너 이벤트
                        if (position == 0)
                            category = "캔";
                        else if (position == 1)
                            category = "종이";
                        else if (position == 2)
                            category = "플라스틱";
                        else if (position == 3)
                            category = "스티로폼";
                        else if (position == 4)
                            category = "유리병";
                        else if (position == 5)
                            category = "비닐";
                        else if (position == 6)
                            category = "기타";
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        dlgName = (EditText) dialogView.findViewById(R.id.dlgEt1);
                        dlgInfor = (EditText) dialogView.findViewById(R.id.dlgEt2);

                        ContentValues addRowValue = new ContentValues();

                        addRowValue.put("name", dlgName.getText().toString());
                        addRowValue.put("category", category);
                        addRowValue.put("information", dlgInfor.getText().toString());

                        databaseManager.insert(addRowValue);
                        Toast.makeText(MainActivity.this, "추가되었습니다.", Toast.LENGTH_SHORT).show();

                    }
                });
                dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                              /*  Toast toast = new Toast(MainActivity.this);
                                toastView = (View) View.inflate(MainActivity.this, R.layout.toast1, null);
                                toastText = (TextView) toastView.findViewById(R.id.toastText1);
                                toastText.setText("취소했습니다.")
                               */
                    }
                });
                dlg.show();
            }
        });

        btnFind = (Button) findViewById(R.id.btnFind);
        findText = (EditText) findViewById(R.id.findText);


        //탭호스트(분리수거요령, 검색, 추가, 변경사항)
        tabHost = findViewById(R.id.tabHost);
        tabHost.setup();

        tabHost.addTab(tabHost.newTabSpec("MAIN").setIndicator("분리요령").setContent(R.id.tabMain));
        tabHost.addTab(tabHost.newTabSpec("FIND").setIndicator("검색").setContent(R.id.tabFind));
        tabHost.addTab(tabHost.newTabSpec("ADD").setIndicator("추가").setContent(R.id.tabAdd));


        tabHost.setCurrentTab(0);

    }



    //검색버튼
    public void onClickButtonPaired(View view) {
        FindListNew.clear();
        findText2 = findText.getText().toString();

        sqlSelect = "SELECT * FROM RECYCLES WHERE name LIKE '%" + findText2 + "%'";

        String[] columns = new String[]{"name", "category", "information"}; //찾을거
        String[] findColumns = new String[]{"name"}; //조건문


        myAdapter1.notifyDataSetChanged();
        Cursor cursor = databaseManager.query(sqlSelect, null);

        if (cursor != null) {
            //cursor.moveToNext();
            while (cursor.moveToNext()) {
                FindListNew.add(new FindList(cursor.getString(1),
                        cursor.getString(2), cursor.getString(3)));
            }
        }
        if (FindListNew.size() < 1) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
            dlg.setTitle("검색결과가 없습니다."); //제목
            dlg.setMessage("데이터를 추가하시겠습니까?"); // 메시지
//                버튼 클릭시 동작
            dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //토스트 메시지
                    Toast.makeText(MainActivity.this, "데이터를 추가해주세요.", Toast.LENGTH_SHORT).show();
                    dialogView = (View) View.inflate(MainActivity.this, R.layout.add_dialog, null);
                    AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);

                    dlg.setTitle("분리수거 방법 추가");
                    dlg.setIcon(R.drawable.ic_launcher_foreground);
                    dlg.setView(dialogView);
                    EditText dlText = (EditText) dialogView.findViewById(R.id.dlgEt1);
                    dlText.setText(findText2);

                    spinnerAdd = (Spinner) dialogView.findViewById(R.id.spinnerAdd);
                    spinnerAdd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {   //스피너 이벤트
                            if (position == 0)
                                category = "캔";
                            else if (position == 1)
                                category = "종이";
                            else if (position == 2)
                                category = "플라스틱";
                            else if (position == 3)
                                category = "스티로폼";
                            else if (position == 4)
                                category = "유리병";
                            else if (position == 5)
                                category = "비닐";
                            else if (position == 6)
                                category = "기타";
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            dlgName = (EditText) dialogView.findViewById(R.id.dlgEt1);
                            dlgInfor = (EditText) dialogView.findViewById(R.id.dlgEt2);

                            ContentValues addRowValue = new ContentValues();

                            addRowValue.put("name", dlgName.getText().toString());
                            addRowValue.put("category", category);
                            addRowValue.put("information", dlgInfor.getText().toString());

                            databaseManager.insert(addRowValue);
                            Toast.makeText(MainActivity.this, "추가되었습니다.", Toast.LENGTH_SHORT).show();

                        }
                    });
                    dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dlg.show();
                }
            });
            dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            dlg.show();
        }

    }


    //검색시 리스트 출력
    public void InitializeFindData() {
        FindListNew = new ArrayList<FindList>();

        //신제품 메뉴
        FindListNew.add(new FindList("이름", "종류", "상세"));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 1, 0, "메뉴");
        menu.add(0, 2, 0, "장바구니");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Intent menuIntent = new Intent(this, MainActivity.class);
                startActivity(menuIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

