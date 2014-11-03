package com.datalayer;

import models.TableSize.TblSize;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOperation extends SQLiteOpenHelper {

	
	public static final int DATABASE_VERSION = 1;
	public String CREATE_QUERY = "CREATE TABLE " + TblSize.TABLE_NAME+ "(" + TblSize.SIZES + " INTEGER );";
	
	public DatabaseOperation(Context context)
	{
		super(context,TblSize.DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		try{
			db.execSQL(CREATE_QUERY);
			final String Insert = "INSERT or replace INTO " + TblSize.TABLE_NAME + "(" + TblSize.SIZES + ")" + " VALUES(14);";
	        db.execSQL(Insert);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void saveInfo(DatabaseOperation dbh, int new_size)
	{
		SQLiteDatabase sq = dbh.getWritableDatabase();
		String rawQuery = "update " + TblSize.TABLE_NAME + " set " + TblSize.SIZES + " = "+new_size+";";
		sq.execSQL(rawQuery);
	}
	
//	public void saveInfo(DatabaseOperation dbh, int size, int new_size)
//	{
//		SQLiteDatabase sq = dbh.getWritableDatabase();
//		String [] args = {size};
//		ContentValues cv = new ContentValues();
//		cv.put(TblSize.SIZES, new_size);
//		sq.update(TblSize.TABLE_NAME, cv, TblSize.SIZES+" LIKE ?", args);
//	}

	public Cursor getInfo(DatabaseOperation db)
	{
		SQLiteDatabase sq = db.getReadableDatabase();
		String[] col = {TblSize.SIZES}; 
		Cursor cr = sq.query(TblSize.TABLE_NAME, col, null, null, null, null, null);
		return cr;
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
	}

}
