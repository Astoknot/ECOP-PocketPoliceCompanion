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

public class CaseAdapter extends RecyclerView.Adapter<CaseAdapter.CaseViewHolder> {

    private Cursor mCursorCase;
    //private int mNumberOfItems;
    private onListItemClickLister clickLister;

    public interface onListItemClickLister
    {
        void onListItemClick(int index);
    }

    CaseAdapter(onListItemClickLister listItemClickLister, Cursor cursor1)
    {
        this.mCursorCase = cursor1;
        //this.mNumberOfItems = n;
        this.clickLister = listItemClickLister;
    }

    @Override
    public CaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.case_holder, parent, false);

        CaseViewHolder caseViewHolder = new CaseViewHolder(view);
        return caseViewHolder;
    }

    @Override
    public void onBindViewHolder(CaseViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mCursorCase.getCount();
    }

    public class CaseViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        TextView title,description;
        CaseViewHolder(View view)
        {
            super(view);
            title = (TextView)view.findViewById(R.id.Caseno);
            description = (TextView)view.findViewById(R.id.Description);
            view.setOnClickListener(this);
        }
        void bind(int position)
        {
            if (!mCursorCase.moveToPosition(position))
                return; // bail if returned null*/

            // Update the view holder with the information needed to display
            String name = mCursorCase.getString(mCursorCase.getColumnIndex(Contract.CasesEntry.COLUMN_CASE_NUMBER));
            String des = mCursorCase.getString(mCursorCase.getColumnIndex(Contract.CasesEntry.COLUMN_DESCRIPTION));

            this.title.setText(name);
            this.description.setText(des);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            clickLister.onListItemClick(position);
        }
    }
    public void swapCursor(Cursor cursor1)
    {
        if(mCursorCase!=null)
            mCursorCase.close();

        mCursorCase=cursor1;
        if(mCursorCase!=null)
        {
            this.notifyDataSetChanged();
        }
    }
}
