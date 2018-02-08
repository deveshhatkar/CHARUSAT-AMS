package com.project.charusat_ams;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static String DB_NAME = "CHARUSAT_AMS";
	private static int DB_VERSION = 1;
	
	DatabaseHelper(Context context)
	{
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE STUDENT ( _id INTEGER AUTO INCREMENT, " +
                   						   "ID_NO TEXT, " +
                   						   "FIRST_NAME TEXT, " +
                   						   "LAST_NAME TEXT, " +
                   						   "CLASS TEXT, " +
                   						   "DEPARTMENT TEXT, " +
                   						   "PRIMARY KEY(_id, ID_NO), " +
                   						   "FOREIGN KEY(CLASS) REFERENCES CLASS(NAME), " +
                   						   "FOREIGN KEY(DEPARTMENT) REFERENCES DEPARTMENT(NAME));");
		
		insertSTUDENT(db, "15CE040", "Devesh", "Hatkar", "CE1", "Computer Engineering");
		insertSTUDENT(db, "15CE060", "Chintan", "Maniyar", "CE1", "ComputerEngineering");
		insertSTUDENT(db, "15CE047", "Jhanvi", "Jobanputra", "CE1", "Computer Engineering");
		insertSTUDENT(db, "15CE005", "Jeel", "Bhalodiya", "CE1", "Computer Engineering");
		insertSTUDENT(db, "15CE010", "Yash", "Chanchad", "CE1", "Computer Engineering");
		insertSTUDENT(db, "15CE111", "Vrajesh", "Rami", "CE2", "Computer Engineering");
		insertSTUDENT(db, "15CE127", "Manali", "Shah", "CE2", "Computer Engineering");
		insertSTUDENT(db, "15CE145", "Jaymin", "Trivedi", "CE2", "Computer Engineering");
		insertSTUDENT(db, "15CE148", "Dewanshi", "Yadav", "CE2", "Computer Engineering");
		insertSTUDENT(db, "15CE140", "Jaydeep", "Thik", "CE2", "Computer Engineering");
		insertSTUDENT(db, "15EE001", "Ayush", "Sinha", "EE1", "Electrical Engineering");
		insertSTUDENT(db, "15EE027", "Sunny", "Patel", "EE1", "Electrical Engineering");
		insertSTUDENT(db, "15EE045", "Payal", "Gajera", "EE1", "Electrical Engineering");
		insertSTUDENT(db, "15EE019", "Dhaval", "Keswani", "EE1", "Electrical Engineering");
		insertSTUDENT(db, "15EE010", "Ujjval", "Gothi", "EE1", "Electrical Engineering");
		insertSTUDENT(db, "15EE111", "Hemil", "Joshi", "EE2", "Electrical Engineering");
		insertSTUDENT(db, "15EE016", "Astik", "Desai", "EE2", "Electrical Engineering");
		insertSTUDENT(db, "15EE075", "Vidhi", "Kokel", "EE2", "Electrical Engineering");
		insertSTUDENT(db, "15EE028", "Runit", "Modi", "EE2", "Electrical Engineering");
		insertSTUDENT(db, "15EE002", "Hitesh", "Makwana", "EE2", "Electrical Engineering");
		
		
		db.execSQL("CREATE TABLE ATTENDANCE( _id INTEGER AUTO INCREMENT, " +
                							 "ID_NO TEXT, " +
                							 "SUBJECT TEXT, " +
                							 "DATE DATE, " +
                							 "P_A CHECKBOX, " +
                							 "PRIMARY KEY(_id), " +
                							 "FOREIGN KEY(ID_NO) REFERENCES STUDENT(ID_NO), " +
                							 "FOREIGN KEY(SUBJECT) REFERENCES SUBJECT(NAME));");
		
		//insertATTENDANCE(db, "15CE040", "Digital Electronics", "20-12-2016", 1);
		
		db.execSQL("CREATE TABLE CLASS( _id INTEGER AUTO INCREMENT, " +
                   						"NAME TEXT, " +
                   						"DEPARTMENT TEXT, " +
                   						"PRIMARY KEY(_id, NAME), " +
										"FOREIGN KEY(DEPARTMENT) REFERENCES DEPARTMENT(NAME));");
		
		insertCLASS(db, "CE1", "Computer Engineering");
		insertCLASS(db, "CE2", "Computer Engineering");
		insertCLASS(db, "EE1", "Electrical Engineering");
		insertCLASS(db, "EE2", "Electrical Engineering");
		
		db.execSQL("CREATE TABLE SUBJECT( _id INTEGER AUTO INCREMENT, " +
                						 "CODE TEXT, " +
                						 "NAME TEXT, " +
                						 "DEPARTMENT TEXT, " +
                						 "DESCRIPTION TEXT, " +
                						 "PRIMARY KEY(_id, CODE, NAME), " +
                						 "FOREIGN KEY(DEPARTMENT) REFERENCES DEPARTMENT(NAME));");
		
		insertSUBJECT(db, "MA201.01", "Discrete Mathematics", "Computer Engineering", "");
		insertSUBJECT(db, "CE201.02", "Data Structure and Algorithm", "Computer Engineering", "");
		insertSUBJECT(db, "CE217.01", "Database Management System", "Computer Engineering", "");
		insertSUBJECT(db, "EE207.02", "Network Analysis", "Electrical Engineering", "");
		insertSUBJECT(db, "MA202", "Engineering Mathematics 3", "Electrical Engineering", "");
		insertSUBJECT(db, "EE219", "Analog Electronics", "Electrical Engineering", "");
		
		insertSUBJECT(db, "DE101", "Digital Electronics", "Computer Engineering", "The logical implementation of computer circuitry using gates is studied.");
		
		db.execSQL("CREATE TABLE DEPARTMENT( _id INTEGER AUTO INCREMENT, " +
                							"NAME TEXT, " +
                							"DESCRIPTION TEXT, " +
                							"PRIMARY KEY(_id, NAME));");
		
		insertDEPARTMENT(db, "Computer Engineering", "");
		insertDEPARTMENT(db, "Electrical Engineering", "");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		// TODO Auto-generated method stub
		
	}
	
	public void insertSTUDENT(SQLiteDatabase db, String id_no, String fname, String lname, String CLASS, String department)
	{
		ContentValues STUDENTvalues = new ContentValues();
		STUDENTvalues.put("ID_NO", id_no);
		STUDENTvalues.put("FIRST_NAME", fname);
		STUDENTvalues.put("LAST_NAME", lname);
		STUDENTvalues.put("CLASS", CLASS);
		STUDENTvalues.put("DEPARTMENT", department);
		db.insert("STUDENT", null, STUDENTvalues);
	}
	
	public void insertATTENDANCE(SQLiteDatabase db, String id_no, String subject, String date, int p_a)
	{
		ContentValues ATTENDANCEvalues = new ContentValues();
		ATTENDANCEvalues.put("ID_NO", id_no);
		ATTENDANCEvalues.put("SUBJECT", subject);
		ATTENDANCEvalues.put("DATE", date);
		ATTENDANCEvalues.put("P_A", p_a);
		db.insert("ATTENDANCE", null, ATTENDANCEvalues);
	}
	
	public void insertCLASS(SQLiteDatabase db, String name, String department)
	{
		ContentValues CLASSvalues = new ContentValues();
		CLASSvalues.put("NAME", name);
		CLASSvalues.put("DEPARTMENT", department);
		db.insert("CLASS", null, CLASSvalues);
	}
	
	public void insertSUBJECT(SQLiteDatabase db, String code, String name, String department, String description)
	{
		ContentValues SUBJECTvalues = new ContentValues();
		SUBJECTvalues.put("CODE", code);
		SUBJECTvalues.put("NAME", name);
		SUBJECTvalues.put("DEPARTMENT", department);
		SUBJECTvalues.put("DESCRIPTION", description);
		db.insert("SUBJECT", null, SUBJECTvalues);
	}
	
	public void insertDEPARTMENT(SQLiteDatabase db, String name, String description)
	{
		ContentValues DEPARTMENTvalues = new ContentValues();
		DEPARTMENTvalues.put("NAME", name);
		DEPARTMENTvalues.put("DESCRIPTION", description);
		db.insert("DEPARTMENT", null, DEPARTMENTvalues);
	}
}
