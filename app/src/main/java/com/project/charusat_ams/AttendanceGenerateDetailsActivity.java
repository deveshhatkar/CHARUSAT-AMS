package com.project.charusat_ams;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.InputType;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.DatePicker;


public class AttendanceGenerateDetailsActivity extends Activity {
	
	private EditText date;
	DatePickerDialog datePicker;
	SimpleDateFormat dateFormat;
	
	SQLiteDatabase db;
	
	Cursor cursorDEPARTMENT;
	Cursor cursorCLASS;
	Cursor cursorSUBJECT;
	
	String CLASS;
	
	Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attendance_details);
		
		Spinner sdepartment = (Spinner) findViewById(R.id.sdepartment);
		
		try
		{
			SQLiteOpenHelper databaseHelper = new DatabaseHelper(this);
			db = databaseHelper.getReadableDatabase();
			
			cursorDEPARTMENT = db.query("DEPARTMENT",
										new String[] {"_id", "NAME"},
										null, null, null, null, null);
			
			SimpleCursorAdapter cursorDEPARTMENTadapter = new SimpleCursorAdapter(context,
																			android.R.layout.simple_spinner_item,
																			cursorDEPARTMENT,
																			new String[] {"NAME"},
																			new int[] {android.R.id.text1},
																			0);
			cursorDEPARTMENTadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			sdepartment.setAdapter(cursorDEPARTMENTadapter);
		}
		catch(SQLiteException e)
		{
			Toast toast = Toast.makeText(this, "Databse Unavaiable", Toast.LENGTH_SHORT);
			toast.show();
		}
		
		sdepartment.setOnItemSelectedListener(new OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
				{
					Spinner sclass = (Spinner) findViewById(R.id.sclass);
					Spinner ssubject = (Spinner) findViewById(R.id.ssubject);
					
					if(position == 0)
					{
						cursorCLASS = db.query("CLASS",
											   new String[] {"_id", "NAME", "DEPARTMENT"},
											   "DEPARTMENT = ?",
											   new String[] {"Computer Engineering"},
											   null, null, null);
						
						SimpleCursorAdapter cursorCLASSadapter = new SimpleCursorAdapter(context, 
																						 android.R.layout.simple_spinner_item, 
																						 cursorCLASS, 
																						 new String[] {"NAME"}, 
																						 new int[] {android.R.id.text1},
																						 0);
						cursorCLASSadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						sclass.setAdapter(cursorCLASSadapter);
						
						cursorSUBJECT = db.rawQuery("SELECT _id, (CODE || ' ' || NAME) AS FNAME, DEPARTMENT FROM SUBJECT WHERE DEPARTMENT = ?", new String[] {"Computer Engineering"});
						
						SimpleCursorAdapter cursorSUBJECTadapter = new SimpleCursorAdapter(context,
																						   android.R.layout.simple_spinner_item,
																						   cursorSUBJECT,
																						   new String[] {"FNAME"},
																						   new int[] {android.R.id.text1},
																						   0);
						cursorSUBJECTadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						ssubject.setAdapter(cursorSUBJECTadapter);
						
					}
					else if(position == 1)
					{
						cursorCLASS = db.query("CLASS",
								   new String[] {"_id", "NAME", "DEPARTMENT"},
								   "DEPARTMENT = ?",
								   new String[] {"Electrical Engineering"},
								   null, null, null);
						
						SimpleCursorAdapter cursorCLASSadapter = new SimpleCursorAdapter(context, 
																						 android.R.layout.simple_spinner_item, 
																						 cursorCLASS, 
																						 new String[] {"NAME"}, 
																						 new int[] {android.R.id.text1},
																						 0);
						cursorCLASSadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						sclass.setAdapter(cursorCLASSadapter);
						
						cursorSUBJECT = db.rawQuery("SELECT _id, (CODE || ' ' || NAME) AS FNAME, DEPARTMENT FROM SUBJECT WHERE DEPARTMENT = ?", new String[] {"Electrical Engineering"});
		
						SimpleCursorAdapter cursorSUBJECTadapter = new SimpleCursorAdapter(context,
																						   android.R.layout.simple_spinner_item,
																						   cursorSUBJECT,
																						   new String[] {"FNAME"},
																						   new int[] {android.R.id.text1},
																						   0);
						cursorSUBJECTadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						ssubject.setAdapter(cursorSUBJECTadapter);
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
		});
		
		date = (EditText) findViewById(R.id.edate);
		date.setInputType(InputType.TYPE_NULL);
		dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
		
		date.setOnClickListener(new OnClickListener() {
			public void onClick(View view)
			{
				datePicker.show();
			}
		});
		
		Calendar dateCalendar = Calendar.getInstance();
		datePicker = new DatePickerDialog(context, new OnDateSetListener(){
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
			{
				Calendar newDate = Calendar.getInstance();
				newDate.set(year, monthOfYear, dayOfMonth);
				date.setText(dateFormat.format(newDate.getTime()));
			}
		}, dateCalendar.get(Calendar.YEAR), dateCalendar.get(Calendar.MONTH), dateCalendar.get(Calendar.DAY_OF_MONTH));
					
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_attendance_details, menu);
		return true;
	}
	
	public void onSubmitClick(View view)
	{		
		Spinner ssubject = (Spinner) findViewById(R.id.ssubject);
		Intent intent = new Intent(AttendanceGenerateDetailsActivity.this, ReportActivity.class);
		intent.putExtra(ReportActivity.CLASS_EXTRA, cursorCLASS.getString(cursorCLASS.getColumnIndex("NAME")));
		intent.putExtra(ReportActivity.DATE_EXTRA, date.getText().toString());
		intent.putExtra(ReportActivity.SUBJECT_EXTRA, cursorSUBJECT.getString(cursorSUBJECT.getColumnIndex("FNAME")));
		startActivity(intent);
	}
}
