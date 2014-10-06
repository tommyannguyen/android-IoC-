package com.datalayer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.datalayer.DataContract.*;

import models.*;

public class CategoryRepository implements IBaseRepository<Category> {

	@Inject AppReaderDbHelper mDbHelper;
	@Inject DocumentRepository documentRepository;
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");

	@Override
	public long insert(Category entity) {
		
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(MedicCategory.COLUMN_NAME_ID,entity.Id);
		values.put(MedicCategory.COLUMN_NAME_NAME, entity.Name);
		values.put(MedicCategory.COLUMN_NAME_UPDATEDDATE, df.format(entity.UpdatedDate));

		return db.insert(
				MedicCategory.TABLE_NAME,
				"",
		         values);
	}

	@Override
	public void update(Category entity) {
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(MedicCategory.COLUMN_NAME_NAME, entity.Name);
		values.put(MedicCategory.COLUMN_NAME_UPDATEDDATE,df.format(entity.UpdatedDate));
		
		String selection = MedicCategory.COLUMN_NAME_ID+" = ?";
		String[] selectionArgs = {Integer.toString(entity.Id)};
	
		int count = db.update(
				MedicCategory.TABLE_NAME,
		    values,
		    selection,
		    selectionArgs);
	}

	@Override
	public void delete(Object id) {
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		String selection = MedicCategory.COLUMN_NAME_ID+" = ?";
		String[] selectionArgs = {id.toString()};
		db.delete(MedicCategory.TABLE_NAME, selection, selectionArgs);
	}

	@Override
	public Category get(Object id) {
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select _ID,"+
				MedicCategory.COLUMN_NAME_ID+","+
				MedicCategory.COLUMN_NAME_NAME+","+
				MedicCategory.COLUMN_NAME_UPDATEDDATE+" " +
				" from "+MedicCategory.TABLE_NAME+" where "+ MedicCategory.COLUMN_NAME_ID+" = ?",new String[] {id.toString()}); 
		if(cursor.getCount()>0)
		{
			cursor.moveToNext();
			Category category= new Category();
			category.Id = cursor.getInt(1);
			category.Name = cursor.getString(2);
			try {
				category.UpdatedDate = df.parse(cursor.getString(3));
			} catch (ParseException e) {
			}
			cursor.close();
			return category;
		}
		cursor.close();
		return null;
	}

	public List<Category> getAll() {
	
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		
		Cursor cursor = db.rawQuery("select _ID,"+
				MedicCategory.COLUMN_NAME_ID+","+
				MedicCategory.COLUMN_NAME_NAME+","+
				MedicCategory.COLUMN_NAME_UPDATEDDATE+" " +
				" from "+ MedicCategory.TABLE_NAME ,null) ;
		List<Category> result=  new ArrayList<Category>();
		while(cursor.moveToNext())
        {
			Category category= new Category();
			category.Id = cursor.getInt(1);
			category.Name = cursor.getString(2);
			category.CountDocuments = documentRepository.CountDocuments(category.Id);
			try {
				category.UpdatedDate = df.parse(cursor.getString(3));
			} catch (ParseException e) {
			}
			result.add(category);
		}
		cursor.close();
		return result;
	}
}
