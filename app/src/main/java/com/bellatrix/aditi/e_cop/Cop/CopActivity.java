package com.bellatrix.aditi.e_cop.Cop;

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

import com.bellatrix.aditi.e_cop.ChangePasswordActivity;
import com.bellatrix.aditi.e_cop.Database.Contract;
import com.bellatrix.aditi.e_cop.Database.ECopDbHelper;
import com.bellatrix.aditi.e_cop.LoginActivity;
import com.bellatrix.aditi.e_cop.R;
import com.bellatrix.aditi.e_cop.User.UserActivity;
import com.bellatrix.aditi.e_cop.ViewCasesActivity;
import com.bellatrix.aditi.e_cop.ViewComplaintsActivity;
import com.bellatrix.aditi.e_cop.ViewFIRActivity;

public class CopActivity extends AppCompatActivity {

    private SQLiteDatabase sdb;
    private String id;

    private Button checkCases, checkComplaint;
    private TextView cop,copid,rank,designation,station;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cop);

        id = getIntent().getStringExtra("cop");

        ECopDbHelper dbHelper = new ECopDbHelper(this);
        sdb = dbHelper.getWritableDatabase();

        cop = (TextView)findViewById(R.id.Cname);
        copid = (TextView)findViewById(R.id.copIdIs);
        rank = (TextView)findViewById(R.id.rankIs);
        designation = (TextView)findViewById(R.id.desIs);
        station = (TextView)findViewById(R.id.place);
        checkCases = (Button)findViewById(R.id.Lodge);
        checkComplaint = (Button)findViewById(R.id.check_complaint);

        setInfo();

        checkCases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CopActivity.this, ViewCasesActivity.class));
            }
        });

        checkComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CopActivity.this, ViewComplaintsActivity.class));
            }
        });
    }

    private void setInfo()
    {
        String[] where={id};
        Cursor c = sdb.query(Contract.CopEntry.TABLE_NAME,
                null,
                Contract.CopEntry.COLUMN_COP_ID+"=?",
                where,
                null,
                null,
                null,
                null);

        if (c != null) {
            c.moveToFirst();
            cop.setText(c.getString(c.getColumnIndex(Contract.CopEntry.COLUMN_FIRST_NAME))
                    + " " + c.getString(c.getColumnIndex(Contract.CopEntry.COLUMN_LAST_NAME)));
            rank.setText(c.getString(c.getColumnIndex(Contract.CopEntry.COLUMN_RANK)));
            designation.setText(c.getString(c.getColumnIndex(Contract.CopEntry.COLUMN_POST)));
            copid.setText(id);
        }
        c.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cop_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId())
        {
            case R.id.view_fir:
                Intent intentvc = new Intent(this, ViewFIRActivity.class);
                //intentvc.putExtra("userId",USER);
                startActivity(intentvc);
                break;
            case R.id.log_out:
                startActivity(new Intent(CopActivity.this,LoginActivity.class));
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
