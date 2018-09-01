package com.bellatrix.aditi.e_cop.User;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bellatrix.aditi.e_cop.Database.Contract;
import com.bellatrix.aditi.e_cop.Database.ECopDbHelper;
import com.bellatrix.aditi.e_cop.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileComplaintActivity extends AppCompatActivity {

    EditText victim,description;
    Button submit;
    private SQLiteDatabase sdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_complaint);

        sdb = new ECopDbHelper(this).getWritableDatabase();

        victim = (EditText)findViewById(R.id.victim);
        description = (EditText)findViewById(R.id.des);
        submit = (Button)findViewById(R.id.submitbt);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData(getIntent().getStringExtra("userId"));
                onBackPressed();
            }
        });
    }

    private long insertData(String USER)
    {
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        ContentValues cv = new ContentValues();
        cv.put(Contract.ComplainEntry.COLUMN_VICTIM,victim.getText().toString());
        cv.put(Contract.ComplainEntry.COLUMN_DESCRIPTION,description.getText().toString());
        cv.put(Contract.ComplainEntry.COLUMN_USER,USER);
        cv.put(Contract.ComplainEntry.COLUMN_FILED_DATE,date);
        //Toast.makeText(AddScheduleActivity.this,"successfully added",Toast.LENGTH_SHORT).show();
        //mAdapter.swapCursor(getAllGuests());
        return sdb.insert(Contract.ComplainEntry.TABLE_NAME,null,cv);
    }
}
