package com.bellatrix.aditi.e_cop.Cop;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bellatrix.aditi.e_cop.Database.Contract;
import com.bellatrix.aditi.e_cop.Database.ECopDbHelper;
import com.bellatrix.aditi.e_cop.R;

public class UpdateCaseActivity extends AppCompatActivity {

    private String caseNo,des,updateNo;
    private Button button;

    private SQLiteDatabase sdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_case);

        ECopDbHelper dbHelper = new ECopDbHelper(this);
        sdb = dbHelper.getReadableDatabase();

        caseNo = getIntent().getStringExtra("case");
        ((TextView)findViewById(R.id.case_number)).setText(caseNo);

        button = findViewById(R.id.button_update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptToUpdate();
                finish();
            }
        });
    }

    private void attemptToUpdate()
    {
        des = ((EditText)findViewById(R.id.et_des)).getText().toString().trim();
        Cursor cursor = getCursor();
        if(cursor!=null)
        {
            updateNo = String.valueOf(cursor.getCount()+1);
        }
        else
            updateNo = String.valueOf(1);
        if(addUpdate()==-1)
            Toast.makeText(this,"Unsuccessful",Toast.LENGTH_SHORT).show();
    }

    private Cursor getCursor()
    {
        String[] where={caseNo};
        return sdb.query(Contract.UpdatesEntry.TABLE_NAME,
                null,
                Contract.UpdatesEntry.COLUMN_CASE_NUMBER+"=?",
                where,
                null,
                null,
                null,
                null);
    }

    private long addUpdate()
    {

        ContentValues cv = new ContentValues();
        cv.put(Contract.UpdatesEntry.COLUMN_UPDATE_NUMBER,updateNo);
        cv.put(Contract.UpdatesEntry.COLUMN_CASE_NUMBER,caseNo);
        cv.put(Contract.UpdatesEntry.COLUMN_DESCRIPTION,des);

        return sdb.insert(Contract.UpdatesEntry.TABLE_NAME,null,cv);
    }
}
