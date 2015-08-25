package com.droxx.dev.travelbudget;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addNewTransaction(View view){
        Intent intent = new Intent(this, AddNewTransaction.class);

        startActivity(intent);
    }

    public void exportTransactions(View view)    {
        TransactionDatabaseHelper tDbHelper = new TransactionDatabaseHelper(getApplicationContext());
        SQLiteDatabase db = tDbHelper.getReadableDatabase();

        String[] projectsion = {
                TransactionDatabaseHelper.TransactionEntry.COLUMN_NAME_DATE_ID   ,
                TransactionDatabaseHelper.TransactionEntry.COLUMN_NAME_COUNTRY_ID,
                TransactionDatabaseHelper.TransactionEntry.COLUMN_NAME_NOTE_ID,
                TransactionDatabaseHelper.TransactionEntry.COLUMN_NAME_CATEGORY_ID,
                TransactionDatabaseHelper.TransactionEntry.COLUMN_NAME_LOCAL_ID,
                TransactionDatabaseHelper.TransactionEntry.COLUMN_NAME_VALUE_ID,
        };

        String sortOrder =
                TransactionDatabaseHelper.TransactionEntry.COLUMN_NAME_DATE_ID + " DESC";

        Cursor c = db.query(
                TransactionDatabaseHelper.TransactionEntry.TABLE_NAME,
                projectsion, null, null, null, null, sortOrder);


        if(c.getCount() > 0) {
            c.moveToFirst();

            for(Integer i = 0; i < c.getCount(); i++)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setMessage(c.getString(2)).setTitle("Entry " + i);

                AlertDialog dialog = builder.create();
            }
        }

    }

}
