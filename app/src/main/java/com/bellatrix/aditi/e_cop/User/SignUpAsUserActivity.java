package com.bellatrix.aditi.e_cop.User;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bellatrix.aditi.e_cop.Database.Contract;
import com.bellatrix.aditi.e_cop.Database.ECopDbHelper;
import com.bellatrix.aditi.e_cop.R;
import com.bellatrix.aditi.e_cop.SendMail;

import java.util.Random;

public class SignUpAsUserActivity extends AppCompatActivity {

    Button registerButton;
    String fName,lName,aadhar,email,contact,password;
    private SQLiteDatabase sdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_as_user);

        ECopDbHelper dbHelper = new ECopDbHelper(this);
        sdb = dbHelper.getWritableDatabase();

        registerButton = (Button)findViewById(R.id.register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSignUp();
            }
        });

    }

    void attemptSignUp()
    {
        fName = ((EditText)findViewById(R.id.et_first_name)).getText().toString().trim();
        lName = ((EditText)findViewById(R.id.et_last_name)).getText().toString().trim();
        aadhar = ((EditText)findViewById(R.id.et_aadhar)).getText().toString().trim();
        email = ((EditText)findViewById(R.id.et_email)).getText().toString().trim();
        contact = ((EditText)findViewById(R.id.et_contact)).getText().toString().trim();

        if(!(fName.equals("")||aadhar.equals("")||email.equals("")||contact.equals("")))
        {
            //try {
            if(aadhar.length()==12) //throw new NumberFormatException("");
            { //Integer.parseInt(aadhar.substring(0,6));
                //Integer.parseInt(aadhar.substring(6));

                password = passwordUtil();
                addUser();
                addCredentials();
                sendEmail();
            }
            else
            {
                Toast.makeText(this,"Aadhar Number be NUMBER of 12 digits",Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this,"All fields are compulsory",Toast.LENGTH_SHORT).show();
        }

    }
    private long addUser()
    {

        ContentValues cv = new ContentValues();
        cv.put(Contract.UserEntry.COLUMN_ADHAAR_NUMBER,aadhar);
        cv.put(Contract.UserEntry.COLUMN_FIRST_NAME,fName);
        cv.put(Contract.UserEntry.COLUMN_LAST_NAME,lName);
        cv.put(Contract.UserEntry.COLUMN_EMAIL,email);
        cv.put(Contract.UserEntry.COLUMN_CONTACT_NUMBER,contact);
        //Toast.makeText(AddScheduleActivity.this,"successfully added",Toast.LENGTH_SHORT).show();
        //mAdapter.swapCursor(getAllGuests());
        return sdb.insert(Contract.UserEntry.TABLE_NAME,null,cv);
    }
    private long addCredentials()
    {
        ContentValues cv = new ContentValues();
        cv.put(Contract.CredentialsEntry.COLUMN_ID,aadhar);
        cv.put(Contract.CredentialsEntry.COLUMN_PASSWORD,password);
        return sdb.insert(Contract.CredentialsEntry.TABLE_NAME,null,cv);
    }
    private void sendEmail()
    {
        String subject = "Registration Details For ECop";
        String message = "Hello User,\n You have successfully signed in. Here is your password: "
                +password
                +".\n For safety reasons, we recommend you to change you password by signing in your account.";

        SendMail sm = new SendMail(this, email, subject, message);
        sm.execute();
    }

    private String passwordUtil()
    {
        String s="";
        int i=8;
        while(i-->0) {
            Random rand = new Random();
            int y = rand.nextInt(10);
                s = s+String.valueOf(y);
        }
        return s;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
