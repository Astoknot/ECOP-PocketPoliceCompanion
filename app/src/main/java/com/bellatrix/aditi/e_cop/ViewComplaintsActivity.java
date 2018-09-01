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

import com.bellatrix.aditi.e_cop.Cop.LodgeFIRActivity;
import com.bellatrix.aditi.e_cop.Cop.OpenCaseActivity;
import com.bellatrix.aditi.e_cop.Cop.RejectComplaintActivity;
import com.bellatrix.aditi.e_cop.Database.Contract;
import com.bellatrix.aditi.e_cop.Database.ECopDbHelper;

public class ViewComplaintsActivity extends AppCompatActivity implements ComplaintAdapter.onListItemClickLister{

    private SQLiteDatabase sdb;
    private ComplaintAdapter complaintAdapter;
    private RecyclerView recyclerView;

    private String USER;
    private boolean copOrCourt;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complaints);

        USER = getIntent().getStringExtra("userId");
        copOrCourt = getIntent().getBooleanExtra("copOrCourt",true);

        ECopDbHelper dbHelper = new ECopDbHelper(this);
        sdb = dbHelper.getReadableDatabase();

        recyclerView = (RecyclerView)findViewById(R.id.rv_complaints);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(true);

        if(USER!=null)
        {
            cursor = getData(USER);
        }
        else
        {
            cursor = getData();
        }

        complaintAdapter = new ComplaintAdapter(this, cursor);
        recyclerView.setAdapter(complaintAdapter);

    }

    private Cursor getData()
    {
        return sdb.query(Contract.ComplainEntry.TABLE_NAME,
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
        return sdb.query(Contract.ComplainEntry.TABLE_NAME,
                null,
                Contract.ComplainEntry.COLUMN_USER+"=?",
                where,
                null,
                null,
                null,
                null);
    }

    public void onListItemClick(int position) {

        final Cursor mCursor = getData();
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null
        // Update the view holder with the information needed to display
        if(USER==null&&copOrCourt)
        {
            final int x = mCursor.getInt(mCursor.getColumnIndex(Contract.ComplainEntry.COLUMN_COMPLAIN_NUMBER));
            final String user = mCursor.getString((mCursor.getColumnIndex(Contract.ComplainEntry.COLUMN_USER)));

            AlertDialog.Builder builder = new AlertDialog.Builder(ViewComplaintsActivity.this);
            //builder.setTitle("")
            builder.setMessage("Proceed to...");
            builder.setPositiveButton("Lodge FIR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(ViewComplaintsActivity.this, LodgeFIRActivity.class);
                    //Bundle bundle = intent.getExtras();

                    intent.putExtra("complaintNo",x);
                    intent.putExtra("userId",user);

                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = new Intent(ViewComplaintsActivity.this, RejectComplaintActivity.class);
                    //Bundle bundle = intent.getExtras();
                    //int x = mCursor.getInt(mCursor.getColumnIndex(Contract.ComplainEntry.COLUMN_COMPLAIN_NUMBER));
                    //Toast.makeText(this,String.valueOf(x),Toast.LENGTH_LONG).show();
                    intent.putExtra("complaintNo",x);
                    intent.putExtra("userId",user);
                    startActivity(intent);
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(USER!=null)
        {
            cursor = getData(USER);
        }
        else
        {
            cursor = getData();
        }

        complaintAdapter.swapCursor(cursor);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
