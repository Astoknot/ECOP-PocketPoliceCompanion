package com.bellatrix.aditi.e_cop.User;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bellatrix.aditi.e_cop.ChangePasswordActivity;
import com.bellatrix.aditi.e_cop.Database.Contract;
import com.bellatrix.aditi.e_cop.Database.ECopDbHelper;
import com.bellatrix.aditi.e_cop.LoginActivity;
import com.bellatrix.aditi.e_cop.R;
import com.bellatrix.aditi.e_cop.ViewCasesActivity;
import com.bellatrix.aditi.e_cop.ViewComplaintsActivity;
import com.bellatrix.aditi.e_cop.ViewFIRActivity;

import org.w3c.dom.Text;

public class UserActivity extends AppCompatActivity{

    ImageButton cases,newComplain;
    private SQLiteDatabase sdb;
    private String USER;
    private TextView user,email,aadhar,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        // Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

        // getSupportActionBar().setTitle("Hello ");

        USER = getIntent().getStringExtra("userId");

        ECopDbHelper dbHelper = new ECopDbHelper(this);
        sdb = dbHelper.getWritableDatabase();

        cases = (ImageButton)findViewById(R.id.cases);
        newComplain = (ImageButton)findViewById(R.id.new_complain);
        user = (TextView)findViewById(R.id.Username);
        aadhar = (TextView)findViewById(R.id.Adharno) ;
        email = (TextView)findViewById(R.id.Emailidis);
        phone = (TextView)findViewById(R.id.Phonenois);

        setInfo();

        cases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserActivity.this,ViewCasesActivity.class);
                intent.putExtra("userId",USER);
                startActivity(intent);
            }
        });

        newComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this,FileComplaintActivity.class);
                intent.putExtra("userId",USER);
                startActivity(intent);
            }
        });
    }

    private void setInfo()
    {
        String[] where={USER};
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
            user.setText(c.getString(c.getColumnIndex(Contract.UserEntry.COLUMN_FIRST_NAME))
                        + " " + c.getString(c.getColumnIndex(Contract.UserEntry.COLUMN_LAST_NAME)));
            phone.setText(c.getString(c.getColumnIndex(Contract.UserEntry.COLUMN_CONTACT_NUMBER)));
            email.setText(c.getString(c.getColumnIndex(Contract.UserEntry.COLUMN_EMAIL)));
            aadhar.setText(USER);
        }
        c.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId())
        {
            case R.id.view_complaints:
                Intent intentvc = new Intent(this, ViewComplaintsActivity.class);
                intentvc.putExtra("userId",USER);
                startActivity(intentvc);
                break;
            case R.id.view_fir:
                Intent intentvc1 = new Intent(this, ViewFIRActivity.class);
                intentvc1.putExtra("userId",USER);
                startActivity(intentvc1);
                break;
            case R.id.change_pswrd:
                Intent intent = new Intent(UserActivity.this,ChangePasswordActivity.class);
                intent.putExtra("viewBool",0);
                intent.putExtra("userId",USER);
                startActivity(intent);
                break;
            case R.id.log_out:
                startActivity(new Intent(UserActivity.this,LoginActivity.class));
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
