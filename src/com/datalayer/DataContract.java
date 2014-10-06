package com.datalayer;
import android.provider.BaseColumns;

public final class DataContract {
	public DataContract()
	{
		
	}
	private static final String TEXT_TYPE = " TEXT";
	private static final String INTEGER_TYPE=" INTEGER";
	private static final String COMMA_SEP = ",";
	
	//AppSetting
	public static abstract class MedicAppSetting implements BaseColumns {
	        public static final String TABLE_NAME = "appsetting";
	        public static final String COLUMN_NAME_LOGINCODE = "logincode";
	        public static final String COLUMN_NAME_PASSWORD = "password";
	        public static final String COLUMN_NAME_ISACTIVED = "isactived";
	        public static final String COLUMN_NAME_USERNAME = "username";
	        public static final String COLUMN_NAME_PHONENUMBER = "phonenumber";
	        public static final String COLUMN_NAME_PASSCODE = "passcode";
	        public static final String COLUMN_NAME_REMINDTIME = "remindtime";
	}
		
	public static final String SQL_CREATE_APPSETTING =
			    "CREATE TABLE " + MedicAppSetting.TABLE_NAME + " (" +
			    		MedicAppSetting._ID + " INTEGER PRIMARY KEY," +
			    		MedicAppSetting.COLUMN_NAME_LOGINCODE + TEXT_TYPE + COMMA_SEP +
			    		MedicAppSetting.COLUMN_NAME_PASSWORD + TEXT_TYPE + COMMA_SEP +
			    		MedicAppSetting.COLUMN_NAME_ISACTIVED + INTEGER_TYPE + COMMA_SEP +
			    		MedicAppSetting.COLUMN_NAME_USERNAME + TEXT_TYPE + COMMA_SEP +
			    		MedicAppSetting.COLUMN_NAME_PHONENUMBER + TEXT_TYPE + COMMA_SEP +
			    		MedicAppSetting.COLUMN_NAME_PASSCODE + TEXT_TYPE + COMMA_SEP +
			    		MedicAppSetting.COLUMN_NAME_REMINDTIME + INTEGER_TYPE +
			    " )";

	public static final String SQL_DELETE_APPSETTING ="DROP TABLE IF EXISTS " + MedicAppSetting.TABLE_NAME;
	
	
	//Pouche
	public static abstract class MedicPouche implements BaseColumns {
		        public static final String TABLE_NAME = "pouche";
		        public static final String COLUMN_NAME_POUCHENAME = "pouchename";
		        public static final String COLUMN_NAME_POUCHTIME = "pouchetime";
		        public static final String COLUMN_NAME_MEDICINENUMBER = "medicinenumber";
		        public static final String COLUMN_NAME_MEDICINEAMOUNT = "medicineamount";
		        public static final String COLUMN_NAME_MEDICINEART = "medicineart";
		        public static final String COLUMN_NAME_MEDICINENAME = "medicinename";
		        public static final String COLUMN_NAME_MEDICINEDISCRIPTION = "medicinedescription";
		        public static final String COLUMN_NAME_HAVINGPDF = "havingpdf";
		        public static final String COLUMN_NAME_HAVINGPHOTO = "havingphoto";
		        public static final String COLUMN_NAME_ISFINISHED= "isfinished";
		        public static final String COLUMN_NAME_TIME= "time";
		        public static final String COLUMN_NAME_HITNUMBER= "hitnumber";
		}
			
	public static final String SQL_CREATE_POUCHE =
				    "CREATE TABLE " + MedicPouche.TABLE_NAME + " (" +
				    		MedicPouche._ID + " INTEGER PRIMARY KEY," +
				    		MedicPouche.COLUMN_NAME_POUCHENAME + TEXT_TYPE + COMMA_SEP +
				    		MedicPouche.COLUMN_NAME_POUCHTIME + TEXT_TYPE + COMMA_SEP +
				    		MedicPouche.COLUMN_NAME_MEDICINENUMBER + INTEGER_TYPE + COMMA_SEP +
				    		MedicPouche.COLUMN_NAME_MEDICINEAMOUNT + INTEGER_TYPE + COMMA_SEP +
				    		MedicPouche.COLUMN_NAME_MEDICINEART + INTEGER_TYPE + COMMA_SEP +
				    		MedicPouche.COLUMN_NAME_MEDICINENAME + TEXT_TYPE + COMMA_SEP +
				    		MedicPouche.COLUMN_NAME_MEDICINEDISCRIPTION + TEXT_TYPE + COMMA_SEP +
				    		MedicPouche.COLUMN_NAME_HAVINGPDF + INTEGER_TYPE + COMMA_SEP +
				    		MedicPouche.COLUMN_NAME_HAVINGPHOTO + INTEGER_TYPE + COMMA_SEP +
				    		MedicPouche.COLUMN_NAME_TIME + INTEGER_TYPE + COMMA_SEP +
				    		MedicPouche.COLUMN_NAME_HITNUMBER + INTEGER_TYPE + COMMA_SEP +
				    		MedicPouche.COLUMN_NAME_ISFINISHED + INTEGER_TYPE +
				    " )";

		public static final String SQL_DELETE_POUCHE ="DROP TABLE IF EXISTS " + MedicPouche.TABLE_NAME;
		
		//Category
		public static abstract class MedicCategory implements BaseColumns {
			        public static final String TABLE_NAME = "category";
			        public static final String COLUMN_NAME_NAME = "categoryname";
			        public static final String COLUMN_NAME_ID = "categoryid";
			        public static final String COLUMN_NAME_UPDATEDDATE = "categoryupdateddate";
			}
		public static final String SQL_CREATE_CATEGORY =
			    "CREATE TABLE " + MedicCategory.TABLE_NAME + " (" +
			    		MedicCategory._ID + " INTEGER PRIMARY KEY," +
			    		MedicCategory.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
			    		MedicCategory.COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
			    		MedicCategory.COLUMN_NAME_UPDATEDDATE + TEXT_TYPE +
			    " )";
		public static final String SQL_DELETE_CATEGORY ="DROP TABLE IF EXISTS " + MedicCategory.TABLE_NAME;
		
		//Document
		public static abstract class MedicDocument implements BaseColumns {
					        public static final String TABLE_NAME = "document";
					        public static final String COLUMN_NAME_TITLE = "documentname";
					        public static final String COLUMN_NAME_ID = "documentid";
					        public static final String COLUMN_NAME_UPDATEDDATE = "documentupdateddate";
					        public static final String COLUMN_NAME_CONTENT = "documentcontent";
					        public static final String COLUMN_NAME_YOUTUBEURL = "documentyoutubeurl";
					        public static final String COLUMN_NAME_CATEGORYID = "categoryid";
					}
		public static final String SQL_CREATE_DOCUMENT =
					    "CREATE TABLE " + MedicDocument.TABLE_NAME + " (" +
					    		MedicDocument._ID + " INTEGER PRIMARY KEY," +
					    		MedicDocument.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
					    		MedicDocument.COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
					    		MedicDocument.COLUMN_NAME_CONTENT + TEXT_TYPE + COMMA_SEP +
					    		MedicDocument.COLUMN_NAME_YOUTUBEURL + TEXT_TYPE + COMMA_SEP +
					    		MedicDocument.COLUMN_NAME_CATEGORYID + INTEGER_TYPE + COMMA_SEP +
					    		MedicDocument.COLUMN_NAME_UPDATEDDATE + TEXT_TYPE +
					    " )";
		public static final String SQL_DELETE_DOCUMENT ="DROP TABLE IF EXISTS " + MedicDocument.TABLE_NAME;
}
