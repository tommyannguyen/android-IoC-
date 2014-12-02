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

public class DocumentRepository  implements IBaseRepository<Document> {

	@Inject AppReaderDbHelper mDbHelper;
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
	
	@Override
	public long insert(Document entity) {
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(MedicDocument.COLUMN_NAME_ID,entity.Id);
		values.put(MedicDocument.COLUMN_NAME_CONTENT, entity.Content);
		values.put(MedicDocument.COLUMN_NAME_UPDATEDDATE, df.format(entity.UpdatedDate));
		values.put(MedicDocument.COLUMN_NAME_TITLE, entity.Title);
		values.put(MedicDocument.COLUMN_NAME_YOUTUBEURL, entity.YouTubeUrl);
		values.put(MedicDocument.COLUMN_NAME_CATEGORYID, entity.CategoryId);
		return db.insert(
				MedicDocument.TABLE_NAME,
				"",
		         values);
	}

	@Override
	public void update(Document entity) {
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(MedicDocument.COLUMN_NAME_CATEGORYID, entity.CategoryId);
		values.put(MedicDocument.COLUMN_NAME_CONTENT, entity.Content);
		values.put(MedicDocument.COLUMN_NAME_TITLE, entity.Title);
		values.put(MedicDocument.COLUMN_NAME_YOUTUBEURL, entity.YouTubeUrl);
		values.put(MedicDocument.COLUMN_NAME_UPDATEDDATE,df.format(entity.UpdatedDate));
		
		String selection = MedicDocument.COLUMN_NAME_ID+" = ?";
		String[] selectionArgs = {Integer.toString(entity.Id)};
	
		int count = db.update(
				MedicDocument.TABLE_NAME,
		    values,
		    selection,
		    selectionArgs);
	}

	@Override
	public void delete(Object id) {
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		String selection = MedicDocument.COLUMN_NAME_ID+" = ?";
		String[] selectionArgs = {id.toString()};
		db.delete(MedicDocument.TABLE_NAME, selection, selectionArgs);
	}

	@Override
	public Document get(Object id) {
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select _ID,"+
				MedicDocument.COLUMN_NAME_ID+","+
				MedicDocument.COLUMN_NAME_CATEGORYID+","+
				MedicDocument.COLUMN_NAME_CONTENT+","+
				MedicDocument.COLUMN_NAME_TITLE+","+
				MedicDocument.COLUMN_NAME_YOUTUBEURL+","+
				MedicDocument.COLUMN_NAME_UPDATEDDATE+" " +
				" from "+MedicDocument.TABLE_NAME+" where "+ MedicDocument.COLUMN_NAME_ID+" = ?",new String[] {id.toString()}); 
		if(cursor.getCount()>0)
		{
			cursor.moveToNext();
			Document document= new Document();
			document.Id = cursor.getInt(1);
			document.CategoryId = cursor.getInt(2);
			document.Content = cursor.getString(3);
			document.Title= cursor.getString(4);
			document.YouTubeUrl = cursor.getString(5);
			try {
				document.UpdatedDate = df.parse(cursor.getString(6));
			} catch (ParseException e) {
			}
			cursor.close();
			return document;
		}
		cursor.close();
		return null;
	}
	public List<Document> getAll(int categoryId) {
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		
		Cursor cursor = db.rawQuery("select _ID,"+
				MedicDocument.COLUMN_NAME_ID+","+
				MedicDocument.COLUMN_NAME_CATEGORYID+","+
				MedicDocument.COLUMN_NAME_CONTENT+","+
				MedicDocument.COLUMN_NAME_TITLE+","+
				MedicDocument.COLUMN_NAME_YOUTUBEURL+","+
				MedicDocument.COLUMN_NAME_UPDATEDDATE+" " +
				" from "+ MedicDocument.TABLE_NAME +" where "+ MedicDocument.COLUMN_NAME_CATEGORYID+" = ?",new String[] {Integer.toString(categoryId)}) ;
		List<Document> result=  new ArrayList<Document>();
		while(cursor.moveToNext())
        {
			Document document= new Document();
			document.Id = cursor.getInt(1);
			document.CategoryId = cursor.getInt(2);
			document.Content = cursor.getString(3);
			document.Title= cursor.getString(4);
			document.YouTubeUrl = cursor.getString(5);
			try {
				document.UpdatedDate = df.parse(cursor.getString(6));
			} catch (ParseException e) {
			}
			result.add(document);
		}
		cursor.close();
		return result;
	}
	public int CountDocuments(int categoryId)
	{
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select COUNT(*) from "+ MedicDocument.TABLE_NAME+" where "+ MedicDocument.COLUMN_NAME_CATEGORYID+" = ?",new String[] {Integer.toString(categoryId)}); 
		if(cursor.getCount()>0)
		{
			cursor.moveToNext();
			Document document= new Document();
			int count = cursor.getInt(0);
			cursor.close();
			return count;
		}
		cursor.close();
		return 0;
	}
}
