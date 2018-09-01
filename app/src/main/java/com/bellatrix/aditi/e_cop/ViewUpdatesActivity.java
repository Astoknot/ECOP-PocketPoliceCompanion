package com.bellatrix.aditi.e_cop;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.bellatrix.aditi.e_cop.Database.Contract;
import com.bellatrix.aditi.e_cop.Database.ECopDbHelper;

public class ViewUpdatesActivity extends AppCompatActivity implements UpdateAdapter.onListItemClickLister{

    private SQLiteDatabase sdb;
    private UpdateAdapter updateAdapter;
    private RecyclerView recyclerView;

    private String caseNumber;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_updates);

        caseNumber = getIntent().getStringExtra("case");

        ECopDbHelper dbHelper = new ECopDbHelper(this);
        sdb = dbHelper.getReadableDatabase();

        recyclerView = (RecyclerView)findViewById(R.id.rv_updates);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(true);

        if(caseNumber!=null)
        {
            cursor = getData(caseNumber);
        }
        else
        {
            cursor = getData();
        }

        updateAdapter = new UpdateAdapter(this, cursor);
        recyclerView.setAdapter(updateAdapter);

        ((TextView)findViewById(R.id.Update)).setText("Update for Case: "+caseNumber);

    }

    private Cursor getData()
    {
        return sdb.query(Contract.UpdatesEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    private Cursor getData(String caseNumber)
    {
        String[] where={caseNumber};
        return sdb.query(Contract.UpdatesEntry.TABLE_NAME,
                null,
                Contract.UpdatesEntry.COLUMN_CASE_NUMBER+"=?",
                where,
                null,
                null,
                null,
                null);
    }

    public void onListItemClick(int position) {

        Cursor mCursor = getData();
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null
        // Update the view holder with the information needed to display
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(caseNumber!=null)
        {
            cursor = getData(caseNumber);
        }
        else
        {
            cursor = getData();
        }

        updateAdapter.swapCursor(cursor);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
