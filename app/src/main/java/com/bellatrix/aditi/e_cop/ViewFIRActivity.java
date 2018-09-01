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

public class ViewFIRActivity extends AppCompatActivity  implements FIRAdapter.onListItemClickLister{

    private SQLiteDatabase sdb;
    private FIRAdapter firAdapter;
    private RecyclerView recyclerView;

    private String USER;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fir);
        USER = getIntent().getStringExtra("userId");

        ECopDbHelper dbHelper = new ECopDbHelper(this);
        sdb = dbHelper.getReadableDatabase();

        recyclerView = (RecyclerView)findViewById(R.id.rv_fir);

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

        firAdapter = new FIRAdapter(this, cursor);
        recyclerView.setAdapter(firAdapter);

    }

    private Cursor getData()
    {
        return sdb.query(Contract.FIREntry.TABLE_NAME,
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
        return sdb.query(Contract.FIREntry.TABLE_NAME,
                null,
                Contract.FIREntry.COLUMN_USER+"=?",
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
        if(USER==null)
        {
            //final int x =
            //final String user = mCursor.getString((mCursor.getColumnIndex(Contract.ComplainEntry.COLUMN_USER)));

            AlertDialog.Builder builder = new AlertDialog.Builder(ViewFIRActivity.this);
            //builder.setTitle("")
            builder.setMessage("Do you want to open a case?");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(ViewFIRActivity.this, OpenCaseActivity.class);
                    //Bundle bundle = intent.getExtras();
                    intent.putExtra("FIRNo",mCursor.getInt(mCursor.getColumnIndex(Contract.FIREntry.COLUMN_FIR_NUMBER)));

                    startActivity(intent);
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

        firAdapter.swapCursor(cursor);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
