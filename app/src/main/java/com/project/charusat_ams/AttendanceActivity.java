package com.project.charusat_ams;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AttendanceActivity extends Activity {
	
	public static final String CLASS_EXTRA = "class";
	public static final String DATE_EXTRA = "date";
	public static final String SUBJECT_EXTRA = "subject";
	
	SQLiteOpenHelper databaseHelper;
	SQLiteDatabase db;
	
	Cursor cursorSTUDENT;
	Cursor cursorATTENDANCE;
	
	ListView lstudent;
	
	String CLASS;
	String DATE;
	String SUBJECT;
	
	Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attendance);
		
		Intent intent = getIntent();
		CLASS = intent.getStringExtra(CLASS_EXTRA);
		DATE = intent.getStringExtra(DATE_EXTRA);
		SUBJECT = intent.getStringExtra(SUBJECT_EXTRA);
		lstudent = (ListView) findViewById(R.id.lstudent);
		
		try
		{
			databaseHelper = new DatabaseHelper(context);
			db = databaseHelper.getReadableDatabase();
			
			cursorSTUDENT = db.rawQuery("SELECT _id, ID_NO, FIRST_NAME, LAST_NAME, CLASS, DEPARTMENT FROM STUDENT WHERE CLASS = ?", new String[] {CLASS});
			
			SimpleCursorAdapter cursorSTUDENTadapter = new SimpleCursorAdapter(context,
																			   R.layout.list_item_layout,
																			   cursorSTUDENT,
																			   new String[] {"ID_NO", "FIRST_NAME", "LAST_NAME"},
																			   new int[] {R.id.tidno, R.id.tfirstname, R.id.tlastname},
																			   0);
			lstudent.setAdapter(cursorSTUDENTadapter);
			lstudent.setItemsCanFocus(false);
			lstudent.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			
			
		}
		catch(SQLiteException e)
		{
			Toast toast = Toast.makeText(this, "Databse Unavaiable", Toast.LENGTH_SHORT);
			toast.show();
		}		
	}
	
	public void onSaveClicked(View view)
	{
		cursorATTENDANCE = db.query("ATTENDANCE",
									new String[] {"_id", "ID_NO", "SUBJECT", "DATE"},
									null, null, null, null, null);
		
		
		int flag = 1;
		for(int i = 0; i < lstudent.getCount(); i++)
		{
			View viewList = lstudent.getChildAt(i);
			CheckBox c = (CheckBox) viewList.findViewById(R.id.check);
			TextView tidno = (TextView) viewList.findViewById(R.id.tidno);
			
			String idno = tidno.getText().toString();
			
			if(cursorATTENDANCE.moveToFirst())
			{
				do
				{
					if(idno.equals(cursorATTENDANCE.getString(cursorATTENDANCE.getColumnIndex("ID_NO"))) && SUBJECT.equals(cursorATTENDANCE.getString(cursorATTENDANCE.getColumnIndex("SUBJECT"))) && DATE.equals(cursorATTENDANCE.getString(cursorATTENDANCE.getColumnIndex("DATE"))))
					{
						flag = 0;					
						break;
					}
					
				}while(cursorATTENDANCE.moveToNext());
			}
			
			
			
			if(flag == 1)
			{
				ContentValues ATTENDANCEvalues = new ContentValues();
				ATTENDANCEvalues.put("ID_NO", idno);
				ATTENDANCEvalues.put("SUBJECT", SUBJECT);
				ATTENDANCEvalues.put("DATE", DATE);
				ATTENDANCEvalues.put("P_A", c.isChecked());
				db.insert("ATTENDANCE", null, ATTENDANCEvalues);
			}
			
			
		}
		
		if(flag == 1)
		{
			Toast toast = Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT);
			toast.show();
		}
		else
		{
			Toast toast = Toast.makeText(this, "Attendance Already Taken!!", Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_attendance, menu);
		return true;
	}
}
