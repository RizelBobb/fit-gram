package com.parse.starter;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FitnessListActivity extends AppCompatActivity {
    private SQLiteDatabase mDatabase;
    private FitnessAdapter mAdapter;
    private EditText mEditTextName;
    private TextView mTextViewAmount;
    private int mAmount =0;
    private Button buttonSeeExample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_list);

        FitnessDBHelper dbHelper = new FitnessDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new FitnessAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long) viewHolder.itemView.getTag());

            }
        }).attachToRecyclerView(recyclerView);

        mEditTextName = (EditText) findViewById(R.id.edittext_name);
        mTextViewAmount = (TextView) findViewById(R.id.textview_amount);

        Button buttonIncrease = (Button) findViewById(R.id.button_increase);
        Button buttonDecrease = (Button) findViewById(R.id.button_decrease);
        Button buttonAdd = (Button) findViewById(R.id.button_add);

        buttonIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increase();
            }
        });

        buttonDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrease();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        buttonSeeExample = (Button) findViewById(R.id.example_button);
        buttonSeeExample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStartWorkout();
            }
        });

    }



    private void increase(){
        mAmount++;
        mTextViewAmount.setText(String.valueOf(mAmount));
    }
    private void decrease(){
        if (mAmount > 0){
            mAmount--;
            mTextViewAmount.setText(String.valueOf(mAmount));
        }
    }
    private void addItem(){
        if(mEditTextName.getText().toString().trim().length() == 0 || mAmount ==0){
            return;
        }
        String name = mEditTextName.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put(FitnessContract.FitnessEntry.COLUMN_NAME, name);
        cv.put(FitnessContract.FitnessEntry.COLUMN_AMOUNT, mAmount);

        mDatabase.insert(FitnessContract.FitnessEntry.TABLE_NAME, null, cv);

        mAdapter.swapCursor(getAllItems());

        mEditTextName.getText().clear();
    }

    private void removeItem(long id){
        mDatabase.delete(FitnessContract.FitnessEntry.TABLE_NAME,
                FitnessContract.FitnessEntry._ID + "=" + id, null);
        mAdapter.swapCursor(getAllItems());
    }

    private Cursor getAllItems(){
        return mDatabase.query(
                FitnessContract.FitnessEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                FitnessContract.FitnessEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }
    public void openStartWorkout(){
        Intent intent = new Intent(this, WorkoutMainActivity.class);
        startActivity(intent);
    }
}
