package com.project.charusat_ams;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ReportActivity extends Activity {
	
	public static final String CLASS_EXTRA = "class";
	public static final String DATE_EXTRA = "date";
	public static final String SUBJECT_EXTRA = "subject";
	
	String CLASS;
	String DATE;
	String SUBJECT;
	
	SQLiteOpenHelper databaseHelper;
	SQLiteDatabase db;
	
	Cursor cursorATTENDANCE;
	Cursor cursorATTENDANCEandSTUDENT;
	
	ListView lreport;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);
		
		Intent intent = getIntent();
		CLASS = intent.getStringExtra(CLASS_EXTRA);
		DATE = intent.getStringExtra(DATE_EXTRA);
		SUBJECT = intent.getStringExtra(SUBJECT_EXTRA);
		
		TextView tclass = (TextView) findViewById(R.id.tclass);
		TextView tdate = (TextView) findViewById(R.id.tdate);
		TextView tsubject = (TextView) findViewById(R.id.tsubject);
		tclass.setText(CLASS);
		tdate.setText(DATE);
		tsubject.setText(SUBJECT);
		
		int flag = 1;
		try
		{
			databaseHelper = new DatabaseHelper(this);
			db = databaseHelper.getReadableDatabase();
			
			cursorATTENDANCE = db.rawQuery("SELECT ATTENDANCE._id, ATTENDANCE.ID_NO, SUBJECT, DATE, STUDENT.CLASS FROM ATTENDANCE, STUDENT WHERE ATTENDANCE.ID_NO = STUDENT.ID_NO", null);
			
			
			cursorATTENDANCE.moveToFirst();
			
			do{
			if( CLASS.equals(cursorATTENDANCE.getString(cursorATTENDANCE.getColumnIndex("CLASS"))) && SUBJECT.equals(cursorATTENDANCE.getString(cursorATTENDANCE.getColumnIndex("SUBJECT"))) && DATE.equals(cursorATTENDANCE.getString(cursorATTENDANCE.getColumnIndex("DATE"))) )
			{
				flag = 1;
				break;
			}
			else
			{	
				flag = 0;
			}
			}while(cursorATTENDANCE.moveToNext());
			
			if(flag == 1)
			{
				cursorATTENDANCEandSTUDENT = db.rawQuery("SELECT ATTENDANCE._id, ATTENDANCE.ID_NO, FIRST_NAME, LAST_NAME, CLASS, P_A, SUBJECT, DATE FROM STUDENT, ATTENDANCE WHERE ATTENDANCE.ID_NO = STUDENT.ID_NO AND SUBJECT = ? AND DATE = ? AND CLASS = ?", new String[] {SUBJECT, DATE, CLASS});
			
				SimpleCursorAdapter cursorATTENDANCEandSTUDENTadapter = new SimpleCursorAdapter(this,
																						R.layout.report_item_layout,
																						cursorATTENDANCEandSTUDENT,
																						new String[] {"ID_NO", "FIRST_NAME", "LAST_NAME", "P_A"},
																						new int[] {R.id.idno, R.id.fname, R.id.lname, R.id.presentabsent},
																						0);
				lreport = (ListView) findViewById(R.id.lreport);
				lreport.setAdapter(cursorATTENDANCEandSTUDENTadapter);
				
				/*for(int i = 0; i < lreport.getCount(); i++)
				{
					View v = lreport.getChildAt(i);
					TextView t = (TextView) v.findViewById(R.id.presentabsent);
					
					if(t.getText().toString().equals("1"))
					{
						t.setText("Present");
					}
					else
					{
						t.setText("Absent");
					}
				}*/
			}
			else
			{
				Toast toast = Toast.makeText(this, "Data Unavaiable", Toast.LENGTH_SHORT);
				toast.show();
			}
		}
		catch(SQLiteException e)
		{
			Toast toast = Toast.makeText(this, "Database Unavaiable", Toast.LENGTH_SHORT);
			toast.show();
		}	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_report, menu);
		return true;
	}
}
