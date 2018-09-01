package com.bellatrix.aditi.e_cop.Cop;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

import org.w3c.dom.Text;

public class LodgeFIRActivity extends AppCompatActivity {

    private String user,type,description,victim,accused,complaint;
    private SQLiteDatabase sdb;
    private Button button;
    private TextView complaintNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lodge_fir);

        complaint = String.valueOf(getIntent().getIntExtra("complaintNo",0));
        user = getIntent().getStringExtra("userId");

        complaintNo = (TextView)findViewById(R.id.complainNo);
        complaintNo.setText("Complaint "+complaint);

        ECopDbHelper dbHelper = new ECopDbHelper(this);
        sdb = dbHelper.getWritableDatabase();

        button = (Button)findViewById(R.id.lodgeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptAddFIR();
            }
        });

    }

    private void attemptAddFIR()
    {
        type = ((EditText)findViewById(R.id.type)).getText().toString().trim();
        description = ((EditText)findViewById(R.id.description)).getText().toString().trim();
        victim = ((EditText)findViewById(R.id.victim)).getText().toString().trim();
        accused = ((EditText)findViewById(R.id.accused)).getText().toString().trim();
        if(!(type.equals("")||description.equals(""))) {
            if(addFIR()==-1)
                Toast.makeText(this,"Lodging FIR unsuccessful",Toast.LENGTH_LONG).show();
            else
            {
                Toast.makeText(this,"FIR lodged successfully",Toast.LENGTH_LONG).show();
                deleteComplaint();
            }
            finish();
        }
        else
        {
            Toast.makeText(this,"Compulsory fields are empty",Toast.LENGTH_SHORT).show();
        }
    }

    private long addFIR()
    {
        ContentValues cv = new ContentValues();
        cv.put(Contract.FIREntry.COLUMN_USER, user);
        cv.put(Contract.FIREntry.COLUMN_TYPE,type);
        cv.put(Contract.FIREntry.COLUMN_DESCRIPTION,description);
        if(!victim.equals(""))
            cv.put(Contract.FIREntry.COLUMN_VICTIM,victim);
        if(!accused.equals(""))
            cv.put(Contract.FIREntry.COLUMN_ACCUSED,accused);
        return sdb.insert(Contract.FIREntry.TABLE_NAME,null,cv);
    }

    private int deleteComplaint()
    {
        String where[]={complaint};
        return sdb.delete(Contract.ComplainEntry.TABLE_NAME,
                Contract.ComplainEntry.COLUMN_COMPLAIN_NUMBER+"=?",
                where);
    }
}
