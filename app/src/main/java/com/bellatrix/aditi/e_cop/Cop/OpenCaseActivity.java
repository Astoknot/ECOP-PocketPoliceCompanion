package com.bellatrix.aditi.e_cop.Cop;

import android.content.ContentValues;
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

public class OpenCaseActivity extends AppCompatActivity {

    private Button button;
    private String courtId, caseNum, lawyer, prosecutor, description, fir;
    private TextView firNum;
    private SQLiteDatabase sdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_case);

        fir = String.valueOf(getIntent().getIntExtra("FIRNo",0));

        ECopDbHelper dbHelper = new ECopDbHelper(this);
        sdb = dbHelper.getWritableDatabase();

        button = findViewById(R.id.button);
        firNum = findViewById(R.id.firtext);
        firNum.setText("FIR Number "+fir);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptopencase();
                finish();
            }
        });
        Toast.makeText(this,fir,Toast.LENGTH_SHORT).show();
    }


    void attemptopencase()
    {
        courtId = ((EditText)findViewById(R.id.courtId)).getText().toString().trim();
        caseNum = ((EditText)findViewById(R.id.caseNum)).getText().toString().trim();
        lawyer = ((EditText)findViewById(R.id.lawyer)).getText().toString().trim();
        prosecutor = ((EditText)findViewById(R.id.prosecutor)).getText().toString().trim();
        description = ((EditText)findViewById(R.id.description)).getText().toString().trim();
        if(!(courtId.equals("")||caseNum.equals("")||lawyer.equals("")||prosecutor.equals("")||description.equals("")))
        {
            if(opencase()==-1)
                Toast.makeText(this,"Unsuccessful",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this,"Successful",Toast.LENGTH_SHORT).show();
        }

    }

    private long opencase()
    {
        ContentValues ab = new ContentValues();
        ab.put(Contract.CasesEntry.COLUMN_COURT_ID,courtId);
        ab.put(Contract.CasesEntry.COLUMN_CASE_NUMBER,caseNum);
        ab.put(Contract.CasesEntry.COLUMN_LAWYER,lawyer);
        ab.put(Contract.CasesEntry.COLUMN_PROSECUTOR,prosecutor);
        ab.put(Contract.CasesEntry.COLUMN_DESCRIPTION,description);
        ab.put(Contract.CasesEntry.COLUMN_FIR_NUMBER,fir);

        //Toast.makeText(AddScheduleActivity.this,"successfully added",Toast.LENGTH_SHORT).show();
        //mAdapter.swapCursor(getAllGuests());
        return sdb.insert(Contract.CasesEntry.TABLE_NAME,null,ab);
    }
}
