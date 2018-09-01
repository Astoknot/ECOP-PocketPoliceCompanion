package com.bellatrix.aditi.e_cop.Court;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bellatrix.aditi.e_cop.Cop.CopActivity;
import com.bellatrix.aditi.e_cop.Database.Contract;
import com.bellatrix.aditi.e_cop.Database.ECopDbHelper;
import com.bellatrix.aditi.e_cop.LoginActivity;
import com.bellatrix.aditi.e_cop.R;
import com.bellatrix.aditi.e_cop.ViewCasesActivity;
import com.bellatrix.aditi.e_cop.ViewFIRActivity;

public class CourtActivity extends AppCompatActivity {

    private SQLiteDatabase sdb;
    private TextView courtid,location,cType;
    private Button viewCases;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_court);

        id = getIntent().getStringExtra("court");

        ECopDbHelper dbHelper = new ECopDbHelper(this);
        sdb = dbHelper.getWritableDatabase();

        courtid = (TextView)findViewById(R.id.CourtIdis);
        location = (TextView)findViewById(R.id.Locationis);
        cType = (TextView)findViewById(R.id.CourtTypeis);
        viewCases = (Button)findViewById(R.id.Update);

        setInfo();

        viewCases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourtActivity.this, ViewCasesActivity.class);
                intent.putExtra("copOrCourt",true);
                startActivity(intent);
            }
        });

        //Toast.makeText(this,id,Toast.LENGTH_LONG).show();

    }

    private void setInfo()
    {
        String[] where={id};
        Cursor c = sdb.query(Contract.CourtEntry.TABLE_NAME,
                null,
                Contract.CourtEntry.COLUMN_COURT_ID+"=?",
                where,
                null,
                null,
                null,
                null);

        if (c != null) {
            c.moveToFirst();
            location.setText(c.getString(c.getColumnIndex(Contract.CourtEntry.COLUMN_CITY)));
            cType.setText(c.getString(c.getColumnIndex(Contract.CourtEntry.COLUMN_TYPE)));
            courtid.setText(id);
        }
        c.close();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.court_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId())
        {
            case R.id.log_out:
                startActivity(new Intent(CourtActivity.this,LoginActivity.class));
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
