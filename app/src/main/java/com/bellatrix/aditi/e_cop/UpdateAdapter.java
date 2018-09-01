package com.bellatrix.aditi.e_cop;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bellatrix.aditi.e_cop.Database.Contract;

/**
 * Created by Aditi on 16-04-2018.
 */

public class UpdateAdapter extends RecyclerView.Adapter<UpdateAdapter.UpdateViewHolder> {

    private Cursor mCursor;
    private onListItemClickLister clickLister;

    public interface onListItemClickLister
    {
        void onListItemClick(int index);
    }

    UpdateAdapter(onListItemClickLister listItemClickLister, Cursor cursor)
    {
        this.mCursor = cursor;
        //this.mNumberOfItems = n;
        this.clickLister = listItemClickLister;
    }


    @Override
    public UpdateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.update_holder,parent,false);

        UpdateViewHolder updateViewHolder = new UpdateViewHolder(view);
        return updateViewHolder;
    }

    @Override
    public void onBindViewHolder(UpdateViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class UpdateViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        TextView title,description,time;
        UpdateViewHolder(View view)
        {
            super(view);
            title = (TextView)view.findViewById(R.id.Caseno);
            description = (TextView)view.findViewById(R.id.Description);
            time = (TextView)view.findViewById(R.id.time);

            view.setOnClickListener(this);
        }
        void bind(int position)
        {
            if (!mCursor.moveToPosition(position))
                return; // bail if returned null*/

            // Update the view holder with the information needed to display
            String name = mCursor.getString(mCursor.getColumnIndex(Contract.UpdatesEntry.COLUMN_UPDATE_NUMBER));
            String des = mCursor.getString(mCursor.getColumnIndex(Contract.UpdatesEntry.COLUMN_DESCRIPTION));

            this.title.setText(name);
            this.description.setText(des);
            this.time.setText(mCursor.getString(mCursor.getColumnIndex(Contract.UpdatesEntry.COLUMN_DATE)));
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

