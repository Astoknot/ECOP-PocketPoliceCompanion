package com.bellatrix.aditi.e_cop;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bellatrix.aditi.e_cop.Database.Contract;

/**
 * Created by Aditi on 17-04-2018.
 */

public class FIRAdapter extends RecyclerView.Adapter<FIRAdapter.FIRViewHolder> {

private Cursor mCursor;
private onListItemClickLister clickLister;

public interface onListItemClickLister
{
    void onListItemClick(int index);
}

    FIRAdapter(onListItemClickLister listItemClickLister, Cursor cursor)
    {
        this.mCursor = cursor;
        //this.mNumberOfItems = n;
        this.clickLister = listItemClickLister;
    }


    @Override
    public FIRAdapter.FIRViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fir_holder,parent,false);

        FIRViewHolder firViewHolder = new FIRViewHolder(view);
        return firViewHolder;
    }

    @Override
    public void onBindViewHolder(FIRViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

public class FIRViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener
{
    TextView title,description,time,type;
    FIRViewHolder(View view)
    {
        super(view);
        title = (TextView)view.findViewById(R.id.firNo);
        description = (TextView)view.findViewById(R.id.Information);
        time = (TextView)view.findViewById(R.id.time_n_date);
        type =  (TextView)view.findViewById(R.id.type);
        view.setOnClickListener(this);
    }
    void bind(int position)
    {
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null*/

        // Update the view holder with the information needed to display
        String name = "FIR "+mCursor.getString(mCursor.getColumnIndex(Contract.FIREntry.COLUMN_FIR_NUMBER));
        String victim = mCursor.getString(mCursor.getColumnIndex(Contract.FIREntry.COLUMN_VICTIM));
        String accussed = mCursor.getString(mCursor.getColumnIndex(Contract.FIREntry.COLUMN_ACCUSED));
        String des="";
        if(victim!=null)
            des = des + "Victim: "+victim+"\n";
        if(accussed!=null)
            des = des + "Accussed: "+accussed+"\n";

        des = des+mCursor.getString(mCursor.getColumnIndex(Contract.ComplainEntry.COLUMN_DESCRIPTION));

        this.title.setText(name);
        this.description.setText(des);
        this.type.setText(mCursor.getString(mCursor.getColumnIndex(Contract.FIREntry.COLUMN_TYPE)));
        this.time.setText(mCursor.getString(mCursor.getColumnIndex(Contract.FIREntry.COLUMN_LODGED_DATE)));
    }

    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();
        clickLister.onListItemClick(position);
    }
}
    public void swapCursor(Cursor cursor1)
    {
        if(mCursor!=null)
            mCursor.close();

        mCursor=cursor1;
        if(mCursor!=null)
        {
            this.notifyDataSetChanged();
        }
    }
}

