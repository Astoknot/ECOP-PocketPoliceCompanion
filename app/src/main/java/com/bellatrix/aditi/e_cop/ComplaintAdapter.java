package com.bellatrix.aditi.e_cop;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bellatrix.aditi.e_cop.Database.Contract;

/**
 * Created by Aditi on 08-04-2018.
 */

public class ComplaintAdapter  extends RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder> {

    private Cursor mCursor;
    private onListItemClickLister clickLister;

    public interface onListItemClickLister
    {
        void onListItemClick(int index);
    }

    ComplaintAdapter(onListItemClickLister listItemClickLister, Cursor cursor)
    {
        this.mCursor = cursor;
        //this.mNumberOfItems = n;
        this.clickLister = listItemClickLister;
    }


    @Override
    public ComplaintViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.complaint_holder,parent,false);

        ComplaintViewHolder complaintViewHolder = new ComplaintViewHolder(view);
        return complaintViewHolder;
    }

    @Override
    public void onBindViewHolder(ComplaintViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class ComplaintViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        TextView title,description,time,victim;
        ComplaintViewHolder(View view)
        {
            super(view);
            title = (TextView)view.findViewById(R.id.complainNo);
            description = (TextView)view.findViewById(R.id.Description);
            time = (TextView)view.findViewById(R.id.time_n_date);
            victim = (TextView)view.findViewById(R.id.victim);

            view.setOnClickListener(this);
        }
        void bind(int position)
        {
            if (!mCursor.moveToPosition(position))
                return; // bail if returned null*/

            // Update the view holder with the information needed to display
            String name = "Complain "+mCursor.getString(mCursor.getColumnIndex(Contract.ComplainEntry.COLUMN_COMPLAIN_NUMBER));
            String des = mCursor.getString(mCursor.getColumnIndex(Contract.ComplainEntry.COLUMN_DESCRIPTION));
            String vict = mCursor.getString(mCursor.getColumnIndex(Contract.ComplainEntry.COLUMN_VICTIM));

            this.title.setText(name);
            this.description.setText(des);
            if(vict!=null)
                this.victim.setText(vict);
            else
                this.victim.setVisibility(View.GONE);
            this.time.setText(mCursor.getString(mCursor.getColumnIndex(Contract.ComplainEntry.COLUMN_FILED_DATE)));
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
