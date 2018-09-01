package com.bellatrix.aditi.e_cop.Cop;

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

public class SignUpAsCopActivity extends AppCompatActivity {

    Button registerCop;
    String fName,lName,copid,rank,post,password,cPassword;
    private SQLiteDatabase sdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_as_cop);

        ECopDbHelper dbHelper = new ECopDbHelper(this);
        sdb = dbHelper.getWritableDatabase();


        registerCop = (Button)findViewById(R.id.register);

        registerCop.setOnClickListener(new View.OnClickListener() {
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
            copid = ((EditText)findViewById(R.id.et_copid)).getText().toString().trim();
            rank = ((EditText)findViewById(R.id.et_rank)).getText().toString().trim();
            post = ((EditText)findViewById(R.id.et_post)).getText().toString().trim();
            password = ((EditText)findViewById(R.id.tv_pswrd)).getText().toString().trim();
            cPassword = ((EditText)findViewById(R.id.tv_cnfrm_psrd)).getText().toString().trim();

            if(!(fName.equals("")||copid.equals("")||rank.equals("")||post.equals("")||password.equals("")||cPassword.equals("")))
            {
                if(password.length()>=8)
                {
                    if(password.equals(cPassword))
                    {


                        //boolean b2=Pattern.compile(copid).matcher("CO"+post.substring(0,1).matches();
                        if(copid.length()==8&&copid.substring(0,2).equalsIgnoreCase("PO"))
                        {
                            try {
                                int x = Integer.parseInt(copid.substring(4));
                                addCop();
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
                            Toast.makeText(this,"Invalid Copid, TRY AGAIN",Toast.LENGTH_LONG).show();
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

    private long addCop()
    {

        ContentValues ab = new ContentValues();
        ab.put(Contract.CopEntry.COLUMN_COP_ID,copid);
        ab.put(Contract.CopEntry.COLUMN_FIRST_NAME,fName);
        ab.put(Contract.CopEntry.COLUMN_LAST_NAME,lName);
        ab.put(Contract.CopEntry.COLUMN_RANK,rank);
        ab.put(Contract.CopEntry.COLUMN_POST,post);
        //Toast.makeText(AddScheduleActivity.this,"successfully added",Toast.LENGTH_SHORT).show();
        //mAdapter.swapCursor(getAllGuests());
        return sdb.insert(Contract.CopEntry.TABLE_NAME,null,ab);
    }
    private long addCredentials()
    {
        ContentValues ab = new ContentValues();
        ab.put(Contract.CredentialsEntry.COLUMN_ID,copid);
        ab.put(Contract.CredentialsEntry.COLUMN_PASSWORD,password);
        return sdb.insert(Contract.CredentialsEntry.TABLE_NAME,null,ab);
    }
}


