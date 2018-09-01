package com.bellatrix.aditi.e_cop.Cop;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bellatrix.aditi.e_cop.Database.Contract;
import com.bellatrix.aditi.e_cop.Database.ECopDbHelper;
import com.bellatrix.aditi.e_cop.R;
import com.bellatrix.aditi.e_cop.SendMail;

public class RejectComplaintActivity extends AppCompatActivity {

    private String reason,complaint,user;
    private Button button;
    private TextView complaintNo;
    private SQLiteDatabase sdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reject_complaint);

        ECopDbHelper dbHelper = new ECopDbHelper(this);
        sdb = dbHelper.getWritableDatabase();

        //Bundle bundle = getIntent().getExtras();
        complaint = String.valueOf(getIntent().getIntExtra("complaintNo",0));
        user = getIntent().getStringExtra("userId");

        complaintNo = (TextView)findViewById(R.id.complainNo);
        complaintNo.setText("Complaint "+complaint);

        button = (Button)findViewById(R.id.rejectButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptReject();
            }
        });
    }

    private void attemptReject()
    {
        reason = ((EditText)findViewById(R.id.reason)).getText().toString();
        if(reject()>0)
        {
            sendEmail();
            finish();
        }
        else
        {
            Log.e("0 rows effected","Deletion Failed");
        }
    }

    private int reject()
    {
        String where[]={complaint};
        return sdb.delete(Contract.ComplainEntry.TABLE_NAME,
                Contract.ComplainEntry.COLUMN_COMPLAIN_NUMBER+"=?",
                where);
    }

    private void sendEmail()
    {
        String subject = "Your Complaint has been rejected";
        String message = "Hello User,\n Your complaint number "
                +complaint
                +"for the following reason(s):\n"
                +reason
                +"\n\n";

        SendMail sm = new SendMail(this, getEmail(), subject, message);
        sm.execute();
    }

    private String getEmail()
    {
        String email = "";
        String[] where={user};
        Cursor c = sdb.query(Contract.UserEntry.TABLE_NAME,
                null,
                Contract.UserEntry.COLUMN_ADHAAR_NUMBER+"=?",
                where,
                null,
                null,
                null,
                null);

        if (c != null) {
            c.moveToFirst();
            email = c.getString(c.getColumnIndex(Contract.UserEntry.COLUMN_EMAIL));
        }
        c.close();
        return email;
    }
}
