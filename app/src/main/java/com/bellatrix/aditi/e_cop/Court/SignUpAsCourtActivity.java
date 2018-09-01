package com.bellatrix.aditi.e_cop.Court;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bellatrix.aditi.e_cop.Database.Contract;
import com.bellatrix.aditi.e_cop.Database.ECopDbHelper;
import com.bellatrix.aditi.e_cop.R;

public class SignUpAsCourtActivity extends AppCompatActivity {

    Button registerCourt;
    String courtid,type,location,password,cPassword;
    private SQLiteDatabase sdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_as_court);

        ECopDbHelper dbHelper = new ECopDbHelper(this);
        sdb = dbHelper.getWritableDatabase();

        registerCourt = (Button)findViewById(R.id.register);

        registerCourt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSignUp();
            }
        });
    }

    void attemptSignUp()
    {
        courtid = ((EditText)findViewById(R.id.et_courtid)).getText().toString().trim();
        type = ((EditText)findViewById(R.id.et_type)).getText().toString().trim();
        location = ((EditText)findViewById(R.id.et_location)).getText().toString().trim();
        password = ((EditText)findViewById(R.id.tv_pswrd)).getText().toString().trim();
        cPassword = ((EditText)findViewById(R.id.tv_cnfrm_psrd)).getText().toString().trim();

        if(!(courtid.equals("")||type.equals("")||location.equals("")||password.equals("")||cPassword.equals("")))
        {
            if(password.length()>=8)
            {
                if(password.equals(cPassword))
                {
                    //boolean b2=Pattern.compile(copid).matcher("CO"+post.substring(0,1).matches();
                    if(courtid.length()==10&&courtid.substring(0,2).equalsIgnoreCase("CO")&&courtid.substring(2,4).equalsIgnoreCase(location.substring(0,2)))
                    {
                        try {
                            int x = Integer.parseInt(courtid.substring(4));
                            addCourt();
                            addCredentials();
                            Toast.makeText(this,"Done",Toast.LENGTH_LONG).show();
                            finish();
                        }
                        catch (NumberFormatException e)
                        {

                        }
                    }
                    else
                    {
                        Toast.makeText(this,"Invalid CourtID, TRY AGAIN",Toast.LENGTH_LONG).show();
                    }

                    // startActivity(new Intent(SignUpAsUserActivity.this,SignUpConfirmationActivity.class));

                }
                else
                {
                    Toast.makeText(this,"Password not matched",Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(this,"Password length should be at least 8",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(this,"All fields are compulsory",Toast.LENGTH_LONG).show();
        }

    }

    private long addCourt()
    {

        ContentValues ab = new ContentValues();
        ab.put(Contract.CourtEntry.COLUMN_COURT_ID,courtid);
        ab.put(Contract.CourtEntry.COLUMN_TYPE,type);
        ab.put(Contract.CourtEntry.COLUMN_CITY,location);
        //Toast.makeText(AddScheduleActivity.this,"successfully added",Toast.LENGTH_SHORT).show();
        //mAdapter.swapCursor(getAllGuests());
        return sdb.insert(Contract.CourtEntry.TABLE_NAME,null,ab);
    }
    private long addCredentials()
    {
        ContentValues ab = new ContentValues();
        ab.put(Contract.CredentialsEntry.COLUMN_ID,courtid);
        ab.put(Contract.CredentialsEntry.COLUMN_PASSWORD,password);
        return sdb.insert(Contract.CredentialsEntry.TABLE_NAME,null,ab);
    }
}
