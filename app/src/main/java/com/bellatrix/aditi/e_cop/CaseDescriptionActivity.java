package com.bellatrix.aditi.e_cop;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bellatrix.aditi.e_cop.Cop.UpdateCaseActivity;
import com.bellatrix.aditi.e_cop.Database.Contract;
import com.bellatrix.aditi.e_cop.Database.ECopDbHelper;

public class CaseDescriptionActivity extends AppCompatActivity {

    private String caseNO;
    private SQLiteDatabase sdb;

    private TextView caseId,fir,court,lawyer,prosecutor,victim,accused,des;
    private FloatingActionButton fab1,fab2;
    private Button checkUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_description);

        caseNO = getIntent().getStringExtra("case");

        fab1 = findViewById(R.id.addUpdate);
        checkUpdate = findViewById(R.id.getUpdates);
        if(!(getIntent().getBooleanExtra("permissionToUpdate",false)))
        {
            fab1.setVisibility(View.GONE);
        }
        else
        {
            fab1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CaseDescriptionActivity.this, UpdateCaseActivity.class);
                    intent.putExtra("case",caseNO);
                    startActivity(intent);
                }
            });
        }
        fab2 = findViewById(R.id.deleteCase);
        if(!(getIntent().getBooleanExtra("permissionToDelete",false)))
        {
            fab2.setVisibility(View.GONE);
        }
        else
        {
            fab2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    openDialog();
                }
            });
        }

        ECopDbHelper dbHelper = new ECopDbHelper(this);
        sdb = dbHelper.getReadableDatabase();

        caseId = findViewById(R.id.caseId);
        fir = findViewById(R.id.firField);
        victim = findViewById(R.id.victimField);
        accused = findViewById(R.id.accusedField);
        lawyer = findViewById(R.id.lawyerField);
        prosecutor = findViewById(R.id.prosecutorField);
        court = findViewById(R.id.courtField);
        des = findViewById(R.id.descField);

        setInfo();

        checkUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaseDescriptionActivity.this,ViewUpdatesActivity.class);
                intent.putExtra("case",caseNO);
                startActivity(intent);
            }
        });

    }

    private void setInfo()
    {
        String[] where={caseNO};
        Cursor c = sdb.query(Contract.CasesEntry.TABLE_NAME,
                null,
                Contract.CasesEntry.COLUMN_CASE_NUMBER+"=?",
                where,
                null,
                null,
                null,
                null);
        String query = "SELECT * FROM " + Contract.CasesEntry.TABLE_NAME + " INNER JOIN " + Contract.FIREntry.TABLE_NAME
                + " ON " + Contract.CasesEntry.TABLE_NAME+"."+Contract.CasesEntry.COLUMN_FIR_NUMBER + " = " +
                Contract.FIREntry.TABLE_NAME+"."+Contract.FIREntry.COLUMN_FIR_NUMBER
                + " WHERE " + Contract.CasesEntry.TABLE_NAME+"."+Contract.CasesEntry.COLUMN_CASE_NUMBER + " = ?";// +  caseNO + "'";
        Cursor c1 = sdb.rawQuery(
                query,
                where
        );

        if (c != null) {
            c.moveToFirst();
            caseId.setText(c.getString(c.getColumnIndex(Contract.CasesEntry.COLUMN_CASE_NUMBER)));
            fir.setText(c.getString(c.getColumnIndex(Contract.CasesEntry.COLUMN_FIR_NUMBER)));
            des.setText(c.getString(c.getColumnIndex(Contract.CasesEntry.COLUMN_COURT_ID)));
            court.setText(c.getString(c.getColumnIndex(Contract.CasesEntry.COLUMN_COURT_ID)));
            lawyer.setText(c.getString(c.getColumnIndex(Contract.CasesEntry.COLUMN_LAWYER)));
            prosecutor.setText(c.getString(c.getColumnIndex(Contract.CasesEntry.COLUMN_PROSECUTOR)));
        }
        c.close();
        if(c1!=null)
        {
            c1.moveToFirst();
            String victimS = c1.getString(c1.getColumnIndex(Contract.FIREntry.COLUMN_VICTIM));
            String accusedS = c1.getString(c1.getColumnIndex(Contract.FIREntry.COLUMN_ACCUSED));
            //String x = String.valueOf(c1.getInt(c1.getColumnIndex(Contract.FIREntry.COLUMN_FIR_NUMBER)));
            //Toast.makeText(this,victimS+" "+accusedS+" "+x,Toast.LENGTH_LONG).show();
            if(victimS!=null)
                victim.setText(victimS);
            else
                victim.setText("NA");
            if(accusedS!=null)
                accused.setText(accusedS);
            else
                accused.setText("NA");
        }
        c1.close();
    }

    private void openDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(CaseDescriptionActivity.this);
        //builder.setTitle("")
        builder.setMessage("Are you sure you want to delete the case?");
        //builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteUpdates();
                deleteCase();
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private int deleteCase()
    {
        String[] where={caseNO};
        return sdb.delete(Contract.CasesEntry.TABLE_NAME, Contract.CasesEntry.COLUMN_CASE_NUMBER+"=?",where);
    }

    private int deleteUpdates()
    {
        String[] where={caseNO};
        return sdb.delete(Contract.UpdatesEntry.TABLE_NAME, Contract.UpdatesEntry.COLUMN_CASE_NUMBER+"=?",where);
        /*String query = "DELETE FROM "+
                Contract.UpdatesEntry.TABLE_NAME +
                "WHERE "+ Contract.UpdatesEntry.COLUMN_CASE_NUMBER + " = " + caseNO + ";";
        sdb.execSQL(query);*/
    }

}
