package com.bellatrix.aditi.e_cop;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bellatrix.aditi.e_cop.Database.Contract;
import com.bellatrix.aditi.e_cop.Database.ECopDbHelper;
import java.util.Random;

public class ChangePasswordActivity extends AppCompatActivity {

    private static int viewbool;

    private Button button;
    private EditText oldpassword, passwordEmail, newconfirm;
    private TextView email, old, new1, new2, prompt;
    private SQLiteDatabase sdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        viewbool = getIntent().getIntExtra("viewBool",0);

        ECopDbHelper dbHelper = new ECopDbHelper(this);
        sdb = dbHelper.getWritableDatabase();

        button = findViewById(R.id.ok);
        oldpassword = findViewById(R.id.old);
        passwordEmail = findViewById(R.id.password);
        newconfirm = findViewById(R.id.passwordcnf);

        email = findViewById(R.id.emailview);
        old = findViewById(R.id.oldview);
        new1 = findViewById(R.id.newview);
        new2 = findViewById(R.id.newview2);
        prompt = findViewById(R.id.prompt);

        setVisibility();
    }

    private void setVisibility(){
        // 0 - from reset ;1 - from forgot
        if(viewbool == 0){
            oldpassword.setVisibility(View.VISIBLE);
            newconfirm.setVisibility(View.VISIBLE);
            old.setVisibility(View.VISIBLE);
            new1.setVisibility(View.VISIBLE);
            new2.setVisibility(View.VISIBLE);
            passwordEmail.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        else{
            email.setVisibility(View.VISIBLE);
            passwordEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }
    }

    private String getPassword(String USER)
    {
        String[] where={USER};
        String p="";
        Cursor c = sdb.query(Contract.CredentialsEntry.TABLE_NAME,
                null,
                Contract.CredentialsEntry.COLUMN_ID+"=?",
                where,
                null,
                null,
                null,
                null);

        if (c != null) {
            c.moveToFirst();
            p =  c.getString(c.getColumnIndex(Contract.CredentialsEntry.COLUMN_PASSWORD));
        }
        c.close();
        return p;
    }

    private void clickAction() {
        if(viewbool==0){
            //BACKEND STUFF?
            //Reset Pwd
            String USER[] = {getIntent().getStringExtra("userId")};
            String oldPass = getPassword(USER[0]);

            if(oldpassword.getText().toString().trim().equals(oldPass)&&passwordEmail.getText().toString().trim().length()>=8&&
                    passwordEmail.getText().toString().trim().equals(newconfirm.getText().toString().trim()))
            {
                ContentValues cv = new ContentValues();
                cv.put(Contract.CredentialsEntry.COLUMN_PASSWORD,passwordEmail.getText().toString().trim());
                sdb.update(Contract.CredentialsEntry.TABLE_NAME,cv,Contract.CredentialsEntry.COLUMN_ID+"=?",USER);
                finish();
            }
            else
            {
                prompt.setText("Unsuccessful");
            }

        }
        else{
            String email = passwordEmail.getText().toString().trim();
            //forgot password;
            String subject = "Initiated Password Reset";
            String pwd = passwordUtil();

            String USER[] = {getIntent().getStringExtra("userId")};
            ContentValues cv = new ContentValues();
            cv.put(Contract.CredentialsEntry.COLUMN_PASSWORD,pwd);
            sdb.update(Contract.CredentialsEntry.TABLE_NAME,cv,Contract.CredentialsEntry.COLUMN_ID+"=?",USER);

            String message = "Dear User,\n\n You have applied for a new password." +
                    " Kindly use the following as the new password to login.\n" + pwd +
                    "\nDue to safety concerns we urge you to change to a new password...";
            SendMail sm = new SendMail(this, email, subject, message);
            sm.execute();
        }
    }

    public void onClick(View v){
        if(v == button){
            if(viewbool == 0){
                if(oldpassword.equals("") || passwordEmail.equals("") || newconfirm.equals(""))
                    prompt.setText("Please Fill in Fields");
                else
                    clickAction();
            }
            else{
                if(passwordEmail.equals(""))
                    prompt.setText("Please Enter E-mail Id");
                else
                    clickAction();
            }
        }
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
}
