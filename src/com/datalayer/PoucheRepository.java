package com.datalayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import models.Pouche;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.datalayer.DataContract.MedicPouche;

public class PoucheRepository implements IBaseRepository<Pouche> {

	@Inject AppReaderDbHelper mDbHelper;
	
	@Override
	public long insert(Pouche entity) {
		
		Pouche ref=getByName(entity.PoucheName);
		
		if(ref!=null)
		{
			update(entity);
			return ref.PoucheNr;
		}
		else
		{
			SQLiteDatabase db2 = mDbHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(MedicPouche.COLUMN_NAME_HAVINGPDF, entity.HavingPDF?1:0);
			values.put(MedicPouche.COLUMN_NAME_HAVINGPHOTO, entity.HavingPhoto?1:0);
			values.put(MedicPouche.COLUMN_NAME_ISFINISHED, entity.IsFinished?1:0);
			values.put(MedicPouche.COLUMN_NAME_MEDICINEAMOUNT, entity.MedicineAmount);
			values.put(MedicPouche.COLUMN_NAME_MEDICINEART, entity.MedicineArt);
			values.put(MedicPouche.COLUMN_NAME_MEDICINEDISCRIPTION, entity.MedicineDescription);
			values.put(MedicPouche.COLUMN_NAME_MEDICINENAME, entity.MedicineName);
			values.put(MedicPouche.COLUMN_NAME_MEDICINENUMBER, entity.MedicineNumber);
			values.put(MedicPouche.COLUMN_NAME_POUCHENAME, entity.PoucheName);
			values.put(MedicPouche.COLUMN_NAME_TIME, entity.Time);
			values.put(MedicPouche.COLUMN_NAME_HITNUMBER, entity.HitNumber);
			values.put(MedicPouche.COLUMN_NAME_POUCHTIME, Long.toString(entity.IntakeDateTime));
			long newRowId = db2.insert(
					MedicPouche.TABLE_NAME,
					"",
			         values);
			
			return newRowId;
		}
	}

	@Override
	public void update(Pouche entity) {
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(MedicPouche.COLUMN_NAME_HAVINGPDF, entity.HavingPDF?1:0);
		values.put(MedicPouche.COLUMN_NAME_HAVINGPHOTO, entity.HavingPhoto?1:0);
		values.put(MedicPouche.COLUMN_NAME_ISFINISHED, entity.IsFinished?1:0);
		values.put(MedicPouche.COLUMN_NAME_MEDICINEAMOUNT, entity.MedicineAmount);
		values.put(MedicPouche.COLUMN_NAME_MEDICINEART, entity.MedicineArt);
		values.put(MedicPouche.COLUMN_NAME_MEDICINEDISCRIPTION, entity.MedicineDescription);
		values.put(MedicPouche.COLUMN_NAME_MEDICINENAME, entity.MedicineName);
		values.put(MedicPouche.COLUMN_NAME_MEDICINENUMBER, entity.MedicineNumber);
		values.put(MedicPouche.COLUMN_NAME_POUCHENAME, entity.PoucheName);
		values.put(MedicPouche.COLUMN_NAME_TIME, entity.Time);
		values.put(MedicPouche.COLUMN_NAME_HITNUMBER, entity.HitNumber);
		values.put(MedicPouche.COLUMN_NAME_POUCHTIME, Long.toString(entity.IntakeDateTime));
		
		String selection = MedicPouche.COLUMN_NAME_POUCHENAME+" = ?";
		String[] selectionArgs = {entity.PoucheName};
	
		int count = db.update(
			MedicPouche.TABLE_NAME,
		    values,
		    selection,
		    selectionArgs);

	}

	@Override
	public void delete(Object id) {
		SQLiteDatabase db=mDbHelper.getReadableDatabase();
		String selection = "_ID = ?";
		String[] selectionArgs = {id.toString()};
		db.delete(MedicPouche.TABLE_NAME, selection, selectionArgs);
		db.close();
		
	}
	public void deleteByName(String name) {
		SQLiteDatabase db=mDbHelper.getReadableDatabase();
		String selection = MedicPouche.COLUMN_NAME_POUCHENAME+" = ?";
		String[] selectionArgs = {name};
		db.delete(MedicPouche.TABLE_NAME, selection, selectionArgs);
		
	}
	@Override
	public Pouche get(Object id) {
		SQLiteDatabase db=mDbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select _ID,"+
				MedicPouche.COLUMN_NAME_HAVINGPDF+","+
				MedicPouche.COLUMN_NAME_HAVINGPHOTO+","+
				MedicPouche.COLUMN_NAME_ISFINISHED+","+
				MedicPouche.COLUMN_NAME_MEDICINEAMOUNT+","+
				MedicPouche.COLUMN_NAME_MEDICINEART+","+
				MedicPouche.COLUMN_NAME_MEDICINEDISCRIPTION+","+
				MedicPouche.COLUMN_NAME_MEDICINENAME+","+
				MedicPouche.COLUMN_NAME_MEDICINENUMBER+","+
				MedicPouche.COLUMN_NAME_POUCHENAME+","+
				MedicPouche.COLUMN_NAME_POUCHTIME+","+
				MedicPouche.COLUMN_NAME_TIME+", "+
				MedicPouche.COLUMN_NAME_HITNUMBER+" "+
				" from "+MedicPouche.TABLE_NAME+" where _ID = ?",new String[] {id.toString()}); 
		if(cursor.getCount()>0)
		{
			cursor.moveToNext();
			Pouche pouche= new Pouche();
			pouche.PoucheNr=cursor.getInt(0);
			pouche.HavingPDF=cursor.getInt(1)==1;
			pouche.HavingPhoto=cursor.getInt(2)==1;
			pouche.IsFinished=cursor.getInt(3)==1;
			pouche.MedicineAmount=cursor.getInt(4);
			pouche.MedicineArt=cursor.getInt(5);
			pouche.MedicineDescription=cursor.getString(6);
			pouche.MedicineName=cursor.getString(7);
			pouche.MedicineNumber=cursor.getInt(8);
			pouche.PoucheName=cursor.getString(9);
			pouche.IntakeDateTime=Long.parseLong(cursor.getString(10));
			pouche.Time=cursor.getInt(11);
			pouche.HitNumber=cursor.getInt(12);
			cursor.close();
			db.close();
			mDbHelper.close();
			return pouche;
		}
		cursor.close();
		db.close();
		mDbHelper.close();
		return null;
	}
	
	public Pouche getByName(String name)
	{
		SQLiteDatabase db=mDbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select _ID,"+
				MedicPouche.COLUMN_NAME_HAVINGPDF+","+
				MedicPouche.COLUMN_NAME_HAVINGPHOTO+","+
				MedicPouche.COLUMN_NAME_ISFINISHED+","+
				MedicPouche.COLUMN_NAME_MEDICINEAMOUNT+","+
				MedicPouche.COLUMN_NAME_MEDICINEART+","+
				MedicPouche.COLUMN_NAME_MEDICINEDISCRIPTION+","+
				MedicPouche.COLUMN_NAME_MEDICINENAME+","+
				MedicPouche.COLUMN_NAME_MEDICINENUMBER+","+
				MedicPouche.COLUMN_NAME_POUCHENAME+","+
				MedicPouche.COLUMN_NAME_POUCHTIME+","+
				MedicPouche.COLUMN_NAME_TIME+", "+
				MedicPouche.COLUMN_NAME_HITNUMBER+" "+
				" from "+MedicPouche.TABLE_NAME+" where "+MedicPouche.COLUMN_NAME_POUCHENAME+" = ?",new String[] {name}); 

		if(cursor.getCount()>0)
		{
			cursor.moveToNext();
			Pouche pouche= new Pouche();
			pouche.PoucheNr=cursor.getInt(0);
			pouche.HavingPDF=cursor.getInt(1)==1;
			pouche.HavingPhoto=cursor.getInt(2)==1;
			pouche.IsFinished=cursor.getInt(3)==1;
			pouche.MedicineAmount=cursor.getInt(4);
			pouche.MedicineArt=cursor.getInt(5);
			pouche.MedicineDescription=cursor.getString(6);
			pouche.MedicineName=cursor.getString(7);
			pouche.MedicineNumber=cursor.getInt(8);
			pouche.PoucheName=cursor.getString(9);
			pouche.IntakeDateTime=Long.parseLong(cursor.getString(10));
			pouche.Time=cursor.getInt(11);
			pouche.HitNumber=cursor.getInt(12);
			cursor.close();
			return pouche;
		}
		Log.d("iii","iii-name"+name);
		cursor.close();

		return null;
	}
	
	public List<Pouche> getAllPouches()
	{
		 List<Pouche> listresult= new ArrayList<Pouche>();
		 SQLiteDatabase db=mDbHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery("select _ID,"+
					MedicPouche.COLUMN_NAME_HAVINGPDF+","+
					MedicPouche.COLUMN_NAME_HAVINGPHOTO+","+
					MedicPouche.COLUMN_NAME_ISFINISHED+","+
					MedicPouche.COLUMN_NAME_MEDICINEAMOUNT+","+
					MedicPouche.COLUMN_NAME_MEDICINEART+","+
					MedicPouche.COLUMN_NAME_MEDICINEDISCRIPTION+","+
					MedicPouche.COLUMN_NAME_MEDICINENAME+","+
					MedicPouche.COLUMN_NAME_MEDICINENUMBER+","+
					MedicPouche.COLUMN_NAME_POUCHENAME+","+
					MedicPouche.COLUMN_NAME_POUCHTIME+","+
					MedicPouche.COLUMN_NAME_TIME+", "+
					MedicPouche.COLUMN_NAME_HITNUMBER+" "+
					" from "+MedicPouche.TABLE_NAME,null); 
			while(cursor.moveToNext())
	        {
				Pouche pouche= new Pouche();
				pouche.PoucheNr=cursor.getInt(0);
				pouche.HavingPDF=cursor.getInt(1)==1;
				pouche.HavingPhoto=cursor.getInt(2)==1;
				pouche.IsFinished=cursor.getInt(3)==1;
				pouche.MedicineAmount=cursor.getInt(4);
				pouche.MedicineArt=cursor.getInt(5);
				pouche.MedicineDescription=cursor.getString(6);
				pouche.MedicineName=cursor.getString(7);
				pouche.MedicineNumber=cursor.getInt(8);
				pouche.PoucheName=cursor.getString(9);
				pouche.IntakeDateTime=Long.parseLong(cursor.getString(10));
				pouche.Time=cursor.getInt(11);
				pouche.HitNumber=cursor.getInt(12);
				listresult.add(pouche);
			}
			cursor.close();
			
			
		Collections.sort(listresult, new Comparator<Pouche>() {
	            public int compare(Pouche one, Pouche other) {
	                 if (one.IntakeDateTime >= other.IntakeDateTime) {
	                     return -1;
	                 } else {
	                     return 1;
	                 } 
	               }
	        });
		 return listresult;
	}
	
	public void ClearData()
	{
		 SQLiteDatabase db=mDbHelper.getReadableDatabase();
		 db.execSQL(DataContract.SQL_DELETE_POUCHE);
		 db.execSQL(DataContract.SQL_CREATE_POUCHE);
		
	}
}
