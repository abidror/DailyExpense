package com.abidroid.dailyexpense;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "homeExpense";
	private static final int DATABASE_VERSION = 1;
	
	private static final String TABLE_ITEMS = "tblitems";
	private static final String TABLE_EXPENSES = "tblexpenses";
	
	private static final String COLUMN_ITEM_ID = "item_id";
	private static final String COLUMN_ITEM_NAME = "item_name";
	
	private static final String COLUMN_EXPENSE_ID = "expense_id";
	private static final String COLUMN_EXPENSE_ITEM = "item_name";
	private static final String COLUMN_ITEM_PRICE = "item_price";
	private static final String COLUMN_EXPENSE_DATE = "expense_date";
	
	private static final String CREATE_TABLE_ITEMS = "CREATE TABLE "
			+ TABLE_ITEMS + "( " + COLUMN_ITEM_ID + " INTEGER PRIMARY KEY,"
			+ COLUMN_ITEM_NAME + " TEXT"
			+ ")";
	
	private static final String CREATE_TABLE_EXPENSES = "CREATE TABLE "
			+ TABLE_EXPENSES + "( " + COLUMN_EXPENSE_ID + " INTEGER PRIMARY KEY,"
			+ COLUMN_EXPENSE_ITEM + " TEXT,"
			+ COLUMN_ITEM_PRICE + " INTEGER,"
			+ COLUMN_EXPENSE_DATE + " INTEGER"
			+ ")";
	
	public DatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_ITEMS);
		db.execSQL(CREATE_TABLE_EXPENSES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
		
		onCreate(db);
	}

	public void addItem( Item item )
	{
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			
			values.put(COLUMN_ITEM_NAME, item.getItemName());
			
			db.insert(TABLE_ITEMS, null, values);
			System.out.println("<<<<<<<<<<<<< Item Added >>>>>>>>>>>");
			db.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("not inserted");
		}
	}
	
	public void addExpense( Expense expense )
	{
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			
			values.put(COLUMN_EXPENSE_ITEM, expense.getExpenseItemName());
			values.put(COLUMN_ITEM_PRICE, expense.getItemPrice());
			
			
			values.put(COLUMN_EXPENSE_DATE, expense.getExpenseDate());
			
			db.insert(TABLE_EXPENSES, null, values);
			db.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Item> getAllItems()
	{
		ArrayList<Item> items = new ArrayList<Item>();
		
		String selectQuery = "SELECT * from " + TABLE_ITEMS + " ORDER BY " + COLUMN_ITEM_NAME + " ASC";
		
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor c = db.rawQuery(selectQuery, null);
		
		if( c.moveToFirst())
		{
			do
			{
				Item item = new Item();
				
				item.setItemId(c.getInt(c.getColumnIndex(COLUMN_ITEM_ID)));
				item.setItemName(c.getString(c.getColumnIndex(COLUMN_ITEM_NAME)));
				
				items.add(item);
			}while(c.moveToNext());
		}
		
		c.close();
		db.close();
		return items;
	}
	
	
	// getting expenses on a specific date
	public List<Expense> getExpensesByDate( long date)
	{
		ArrayList<Expense> expenses = new ArrayList<Expense>();
		String selectQuery = "SELECT * from " + TABLE_EXPENSES + " where " + COLUMN_EXPENSE_DATE + "=" + date;
	
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if( c.moveToFirst())
		{
			do
			{
				Expense expense = new Expense();
				
				expense.setExpenseId(c.getInt(c.getColumnIndex(COLUMN_EXPENSE_ID)));
				expense.setExpenseItemName(c.getString(c.getColumnIndex(COLUMN_EXPENSE_ITEM)));
				expense.setItemPrice(c.getInt(c.getColumnIndex(COLUMN_ITEM_PRICE)));
				expense.setExpenseDate(c.getLong(c.getColumnIndex(COLUMN_EXPENSE_DATE)));
				
				expenses.add(expense);
			}while(c.moveToNext());
		}
		
		c.close();
		db.close();
		return expenses;
	}
	
	// getting total expense amount on a specific date
	public int getTotalExpenseByDate(long date)
	{
		int totalExpense = 0;
		
		String selectQuery = "SELECT SUM(" + COLUMN_ITEM_PRICE + ") from " + TABLE_EXPENSES + " where " + COLUMN_EXPENSE_DATE + "=" + date;
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if( c.moveToFirst())
		{
			totalExpense = c.getInt(0);
		}
		
		c.close();
		db.close();
		
		return totalExpense;
	}
	
	public List<Expense> getExpensesFromDateToDate( long date1, long date2 )
	{
		ArrayList<Expense> expenses = new ArrayList<Expense>();
		String selectQuery = "SELECT * from " + TABLE_EXPENSES + " where " + COLUMN_EXPENSE_DATE + " between " + date1 + " AND " + date2;
	
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if( c.moveToFirst())
		{
			do
			{
				Expense expense = new Expense();
				
				expense.setExpenseId(c.getInt(c.getColumnIndex(COLUMN_EXPENSE_ID)));
				expense.setExpenseItemName(c.getString(c.getColumnIndex(COLUMN_EXPENSE_ITEM)));
				expense.setItemPrice(c.getInt(c.getColumnIndex(COLUMN_ITEM_PRICE)));
				expense.setExpenseDate(c.getLong(c.getColumnIndex(COLUMN_EXPENSE_DATE)));
				
				expenses.add(expense);
			}while(c.moveToNext());
		}
		
		c.close();
		db.close();
		return expenses;
	}
	
	public int getTotalExpenseFromDateToDate(long date1, long date2)
	{
		int totalExpense = 0;
		
		String selectQuery = "SELECT SUM(" + COLUMN_ITEM_PRICE + ") from " + TABLE_EXPENSES + " where " + COLUMN_EXPENSE_DATE + " between " + date1 + " AND " + date2; 
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if( c.moveToFirst())
		{
			totalExpense = c.getInt(0);
		}
		
		c.close();
		db.close();
		
		return totalExpense;
	}

	
	public List<Expense> getExpensesByItemAndDtoD( String itemName, long date1, long date2 )
	{
		ArrayList<Expense> expenses = new ArrayList<Expense>();
		String selectQuery = "SELECT * from " + TABLE_EXPENSES + " where " + COLUMN_EXPENSE_ITEM + "='" + itemName + 
								"' AND " + COLUMN_EXPENSE_DATE + " between " + date1 + " AND " + date2;
	
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if( c.moveToFirst())
		{
			do
			{
				Expense expense = new Expense();
				
				expense.setExpenseId(c.getInt(c.getColumnIndex(COLUMN_EXPENSE_ID)));
				expense.setExpenseItemName(c.getString(c.getColumnIndex(COLUMN_EXPENSE_ITEM)));
				expense.setItemPrice(c.getInt(c.getColumnIndex(COLUMN_ITEM_PRICE)));
				expense.setExpenseDate(c.getLong(c.getColumnIndex(COLUMN_EXPENSE_DATE)));
				
				expenses.add(expense);
				
			}while(c.moveToNext());
		}
		
		c.close();
		db.close();
		return expenses;
	}
	
	public int getTotalExpenseByItemAndDtoD( String itemName, long date1, long date2 )
	{
		int totalExpense = 0;
		
		String selectQuery = "SELECT SUM(" + COLUMN_ITEM_PRICE + ") from " + TABLE_EXPENSES + " where " + COLUMN_EXPENSE_ITEM + "='" + itemName +
							"' AND "+ COLUMN_EXPENSE_DATE + " between " + date1 + " AND " + date2; 
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if( c.moveToFirst())
		{
			totalExpense = c.getInt(0);
		}
		
		c.close();
		db.close();
		
		return totalExpense;
	}
	
	public int deleteExpense( Expense expense )
	{
		int deleted = 0;
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		deleted = db.delete(TABLE_EXPENSES, COLUMN_EXPENSE_ID + "=?", new String[]{String.valueOf(expense.getExpenseId())});
		db.close();
		
		return deleted;
	}
}








