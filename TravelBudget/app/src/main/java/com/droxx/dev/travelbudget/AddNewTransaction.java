package com.droxx.dev.travelbudget;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddNewTransaction extends AppCompatActivity {

    EditText dateText;
    EditText countryText;
    EditText categoryText;
    EditText noteText;
    RadioGroup currencyGroup;
    RadioButton localButton;
    RadioButton homeButton;
    EditText valueText;

    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_transaction);

        AutoCompleteTextView textView = (AutoCompleteTextView)findViewById(R.id.transaction_country);
        String[] countries = getResources().getStringArray(R.array.countries_array);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        textView.setAdapter(adapter);

        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth){
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        dateText = (EditText)findViewById(R.id.transaction_date);
        countryText = (EditText)findViewById(R.id.transaction_country);
        categoryText = (EditText)findViewById(R.id.transaction_category);
        noteText = (EditText)findViewById(R.id.transaction_note);
        currencyGroup = (RadioGroup)findViewById(R.id.transaction_currency_type_rg);
        localButton = (RadioButton)findViewById(R.id.transaction_currency_local);
        homeButton = (RadioButton)findViewById(R.id.transaction_currency_home);
        valueText = (EditText)findViewById(R.id.transaction_value);


        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddNewTransaction.this, date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        RadioGroup currencyRg = (RadioGroup)findViewById(R.id.transaction_currency_type_rg);

        currencyRg.check(R.id.transaction_currency_local);

    }

    private void updateLabel(){
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
        dateText.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_add_new_transaction, menu);
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

    public void commitTransaction(View view)
    {
        TransactionDatabaseHelper tDbHelper = new TransactionDatabaseHelper(getApplicationContext());
        SQLiteDatabase db = tDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TransactionDatabaseHelper.TransactionEntry.COLUMN_NAME_DATE_ID, dateText.getText().toString());
        values.put(TransactionDatabaseHelper.TransactionEntry.COLUMN_NAME_COUNTRY_ID, countryText.getText().toString());
        values.put(TransactionDatabaseHelper.TransactionEntry.COLUMN_NAME_NOTE_ID, noteText.getText().toString());
        values.put(TransactionDatabaseHelper.TransactionEntry.COLUMN_NAME_CATEGORY_ID, categoryText.getText().toString());
        values.put(TransactionDatabaseHelper.TransactionEntry.COLUMN_NAME_LOCAL_ID, localButton.isChecked() ? 1 : 0);
        values.put(TransactionDatabaseHelper.TransactionEntry.COLUMN_NAME_VALUE_ID, valueText.getText().toString());

        long newRowId;
        newRowId = db.insert(TransactionDatabaseHelper.TransactionEntry.TABLE_NAME, null, values);

        finish();
    }

}
