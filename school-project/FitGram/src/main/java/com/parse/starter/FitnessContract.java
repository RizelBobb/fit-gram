package com.parse.starter;
import android.provider.BaseColumns;

public class FitnessContract {

    private FitnessContract(){}

    public static final class FitnessEntry implements BaseColumns{
        public static final String TABLE_NAME = "fitnessList";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_TIMESTAMP = "timestamp";

    }


}
