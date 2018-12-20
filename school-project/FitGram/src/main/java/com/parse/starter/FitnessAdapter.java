package com.parse.starter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FitnessAdapter extends RecyclerView.Adapter<FitnessAdapter.FitnessViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    public FitnessAdapter(Context context, Cursor cursor){
        mContext = context;
        mCursor = cursor;
    }

    public class FitnessViewHolder extends RecyclerView.ViewHolder{
        public TextView nameText;
        public TextView countText;

        public FitnessViewHolder(View itemView) {
            super(itemView);
            nameText = (TextView) itemView.findViewById(R.id.textview_name_item);
            countText = (TextView) itemView.findViewById(R.id.text_view_amount);
        }
    }

    @Override
    public FitnessViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.fitness_item, parent, false);
        return new FitnessViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FitnessViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)){
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(FitnessContract.FitnessEntry.COLUMN_NAME));
        int amount = mCursor.getInt(mCursor.getColumnIndex(FitnessContract.FitnessEntry.COLUMN_AMOUNT));
        long id = mCursor.getLong(mCursor.getColumnIndex(FitnessContract.FitnessEntry._ID));

        holder.nameText.setText(name);
        holder.countText.setText(String.valueOf(amount));
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
    public void swapCursor(Cursor newCursor){
        if (mCursor != null){
            mCursor.close();
        }
        mCursor = newCursor;
        if(newCursor != null){
            notifyDataSetChanged();
        }
    }
}
