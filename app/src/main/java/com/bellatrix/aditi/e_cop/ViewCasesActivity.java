package com.bellatrix.aditi.e_cop;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bellatrix.aditi.e_cop.Cop.OpenCaseActivity;
import com.bellatrix.aditi.e_cop.Database.Contract;
import com.bellatrix.aditi.e_cop.Database.ECopDbHelper;

public class ViewCasesActivity extends AppCompatActivity  implements CaseAdapter.onListItemClickLister {

    private SQLiteDatabase sdb;
    private CaseAdapter caseAdapter;
    private RecyclerView recyclerView;

    private String USER;
    private boolean isCourt;
    private Cursor cursor1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cases);

        USER = getIntent().getStringExtra("userId");
        isCourt = getIntent().getBooleanExtra("copOrCourt",false);

        ECopDbHelper dbHelper = new ECopDbHelper(this);
        sdb = dbHelper.getReadableDatabase();

        recyclerView = (RecyclerView)findViewById(R.id.rv_cases);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(true);

        if(USER!=null)
        {
            cursor1 = getData(USER);
        }
        else
        {
            cursor1 = getData();
        }

        caseAdapter = new CaseAdapter(this, cursor1);
        recyclerView.setAdapter(caseAdapter);

    }

    private Cursor getData()
    {
        return sdb.query(Contract.CasesEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    private Cursor getData(String USER)
    {
        String[] where={USER};
        String q = "SELECT * FROM " + Contract.CasesEntry.TABLE_NAME + " INNER JOIN " + Contract.FIREntry.TABLE_NAME
                + " ON " + Contract.CasesEntry.TABLE_NAME+"."+Contract.CasesEntry.COLUMN_FIR_NUMBER + " = " +
                Contract.FIREntry.TABLE_NAME+"."+Contract.FIREntry.COLUMN_FIR_NUMBER
                + " WHERE " + Contract.FIREntry.COLUMN_USER + " = ?";// +  USER + "'";
        Cursor c = sdb.rawQuery(
                q,
                where
        );
        return c;
    }

    //TODO has to be solved
    /*private Cursor getData(String USER)
    {
        String[] where=getFIRNumbers(USER);
        return sdb.query(Contract.CasesEntry.TABLE_NAME,
                null,
                Contract.CasesEntry.COLUMN_FIR_NUMBER+"=?",
                where,
                null,
                null,
                null,
                null);
    }

    private String[] getFIRNumbers(String USER)
    {
        String[] firNos = new String[getData().getCount()];
        String[] where={USER};
        String[] projection = {Contract.FIREntry.COLUMN_FIR_NUMBER};
        Cursor c = sdb.query(Contract.FIREntry.TABLE_NAME,
                projection,
                Contract.FIREntry.COLUMN_USER+"=?",
                where,
                null,
                null,
                null,
                null);

    }*/

    public void onListItemClick(int position) {

        if(USER!=null) {
            Cursor mCursor = getData(USER);
            if (!mCursor.moveToPosition(position))
                return; // bail if returned null
            // Update the view holder with the information needed to display
            String caseNumber = mCursor.getString(mCursor.getColumnIndex(Contract.CasesEntry.COLUMN_CASE_NUMBER));

            Intent intent = new Intent(ViewCasesActivity.this, CaseDescriptionActivity.class);
            intent.putExtra("case", caseNumber);
            startActivity(intent);
        }
        else {
            final Cursor mCursor = getData();
            if (!mCursor.moveToPosition(position))
                return;
            String caseNumber = mCursor.getString(mCursor.getColumnIndex(Contract.CasesEntry.COLUMN_CASE_NUMBER));

            Intent intent = new Intent(ViewCasesActivity.this, CaseDescriptionActivity.class);
            intent.putExtra("case", caseNumber);
            if(isCourt)
                intent.putExtra("permissionToDelete",true);
            intent.putExtra("permissionToUpdate",true);
            startActivity(intent);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(USER!=null)
        {
            cursor1 = getData(USER);
        }
        else
        {
            cursor1 = getData();
        }

        caseAdapter.swapCursor(cursor1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}