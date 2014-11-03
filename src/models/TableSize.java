package models;

import android.provider.BaseColumns;

public class TableSize {
	
	public TableSize()
	{
		
	}
	public static abstract class TblSize implements BaseColumns
	{
		public static final String DATABASE_NAME = "Set_size";
		public static final String TABLE_NAME = "Sizes";
		public static final String SIZES = "Size";
	}
}
