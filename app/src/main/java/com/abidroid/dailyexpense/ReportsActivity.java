package com.abidroid.dailyexpense;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ReportsActivity extends Activity {

	private Button btnByDate, btnByItem, btnFromDateToDate;
	private TextView txtReportType, txtReportExpense;
	private ListView lvExpenses;
	
	long reportDate, fromDate, toDate, date1, date2;
	String strFromDate, strToDate;
	
	DatabaseHelper dbHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reports);
		
		dbHelper = new DatabaseHelper(this);
		
		btnByDate = (Button)findViewById(R.id.btnByDate);
		btnByItem = (Button)findViewById(R.id.btnByItem);
		
		btnFromDateToDate = (Button)findViewById(R.id.btnFromDateToDate);		
		txtReportType = (TextView)findViewById(R.id.txtReportType);
		txtReportExpense = (TextView)findViewById(R.id.txtReportTotal);
		
		lvExpenses = (ListView)findViewById(R.id.lvExpenses);
		
		btnByItem.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				final Dialog dialog = new Dialog(ReportsActivity.this);
				dialog.setTitle("Report By Item");				
				dialog.setContentView(R.layout.dialog_report_item);				
				
				final Spinner spinnerItem = (Spinner)dialog.findViewById(R.id.spinnerItemReport);
				// populate spinner item with items
				ArrayList<Item> items;				
				items = (ArrayList<Item>) dbHelper.getAllItems();				
				ArrayAdapter<Item> myAdapter = new ArrayAdapter<Item>(ReportsActivity.this, android.R.layout.simple_spinner_item, items);
		        spinnerItem.setAdapter(myAdapter);       
		        
		        
				final Spinner spinnerMonth = (Spinner)dialog.findViewById(R.id.spinnerMonth);
				final Spinner spinnerYear = (Spinner)dialog.findViewById(R.id.spinnerYear);
				Button btnCancel = (Button)dialog.findViewById(R.id.btnCancelReport);
				Button btnReportByItem = (Button)dialog.findViewById(R.id.btnReportByItem);
				
				
				btnReportByItem.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
				
						try {
							Item item = (Item) spinnerItem.getSelectedItem();
							
							if( item == null )
							{							
								Toast.makeText(ReportsActivity.this, "insert item first", Toast.LENGTH_LONG).show();
								dialog.dismiss();
							}
							
							String itemName = item.getItemName();
							int month = spinnerMonth.getSelectedItemPosition() + 1;						
							String year = (String) spinnerYear.getSelectedItem();							
							
							String dt1 = year + "-" + month + "-01";
							
							String dt2;
							
							if( month == 1 )
								dt2 = year + "-" + month + "-31";
							else if( month == 2 )
							{
								if( Integer.parseInt(year) % 4 == 0)
									dt2 = year + "-" + month + "-29";
								else
									dt2 = year + "-" + month + "-28";
							}
							else if( month == 3 )
								dt2 = year + "-" + month + "-31";
							else if( month == 4 )
								dt2 = year + "-" + month + "-30";
							else if( month == 5 )
								dt2 = year + "-" + month + "-31";
							else if( month == 6 )
								dt2 = year + "-" + month + "-30";
							else if( month == 7 )
								dt2 = year + "-" + month + "-31";
							else if( month == 8 )
								dt2 = year + "-" + month + "-31";
							else if( month == 9 )
								dt2 = year + "-" + month + "-30";
							else if( month == 10 )
								dt2 = year + "-" + month + "-31";
							else if( month == 11)
								dt2 = year + "-" + month + "-30";
							else
								dt2 = year + "-" + month + "-31";
							
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
							Date d1 = format.parse(dt1);
							Date d2 = format.parse(dt2);
							
							
							date1 = d1.getTime();
							date2 = d2.getTime();
							System.out.println(date2);
							
							ArrayList<Expense> expenses = new ArrayList<Expense>();							
							expenses = (ArrayList<Expense>) dbHelper.getExpensesByItemAndDtoD(itemName, date1, date2);							
							ExpenseAdapter expenseAdapter = new ExpenseAdapter(ReportsActivity.this, R.layout.list_item, expenses);
							lvExpenses.setAdapter(expenseAdapter);
							
							int totalExpense = dbHelper.getTotalExpenseByItemAndDtoD(itemName, date1, date2);
							txtReportExpense.setText("Total Expense: " + totalExpense);
							
							String months[] = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "Novmeber", "December" };
							txtReportType.setText("Item " + itemName + " in Month " + months[month-1]);
							//Toast.makeText(ReportsActivity.this, itemName + " " + month + " " + year, Toast.LENGTH_LONG).show();
							
							dialog.dismiss();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						catch( Exception e)
						{
							e.printStackTrace();
						}
					}
				});
				
				
				btnCancel.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
					
						dialog.dismiss();
					}
				});
				dialog.show();
			}
		});
		btnByDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Calendar cal = Calendar.getInstance();
				
				int m = cal.get(Calendar.MONTH);
				int d = cal.get(Calendar.DAY_OF_MONTH);
				int y = cal.get(Calendar.YEAR);
				
				DatePickerDialog dpd = new DatePickerDialog(ReportsActivity.this, dateListener, y,m,d);
				dpd.show();				
				
			}
		});
		
		btnFromDateToDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar cal = Calendar.getInstance();
				int m = cal.get(Calendar.MONTH);
				int d = cal.get(Calendar.DAY_OF_MONTH);
				int y = cal.get(Calendar.YEAR);
				
				DatePickerDialog dpdFromDate = new DatePickerDialog(ReportsActivity.this, fromDateListener, y, m, d);
				dpdFromDate.show();
				
				
			}
		});
		
		
		// #################################################################################
		// deleting expense on long click of listview
		// #################################################################################
		lvExpenses.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				final Expense expense = (Expense)lvExpenses.getAdapter().getItem(position);
				
				final int exp_id = expense.getExpenseId();				
				final String item = expense.getExpenseItemName();
				final long dt = expense.getExpenseDate();
				final int cost = expense.getItemPrice();
				
				AlertDialog.Builder builder = new AlertDialog.Builder(ReportsActivity.this);
				builder.setTitle("Delete Expense");
				builder.setMessage("Are you sure to delete ?");
				builder.setIcon(android.R.drawable.ic_delete);
				
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
				
						int result = dbHelper.deleteExpense(expense);
					
						if( result > 0 )
						{
							ExpenseAdapter adp = (ExpenseAdapter) lvExpenses.getAdapter();
							adp.remove(expense);
							adp.notifyDataSetChanged();
							
							Toast.makeText(ReportsActivity.this, "Expense deleted", Toast.LENGTH_LONG).show();
						}
					}
				});
				
				builder.setNegativeButton("No", null);
				builder.show();				
								
				return true;
			}
		});
		
		
	}	
	
	DatePickerDialog.OnDateSetListener dateListener = new OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			try 
			{				
				String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;			
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
				Date dt = sdf.parse(date);
				reportDate = dt.getTime();				
				txtReportType.setText("Report Type: By Date: " + date);				
				ArrayList<Expense> expenses = new ArrayList<Expense>();
				
				expenses = (ArrayList<Expense>) dbHelper.getExpensesByDate(reportDate);
				
				ExpenseAdapter expenseAdapter = new ExpenseAdapter(ReportsActivity.this, R.layout.list_item, expenses);
				lvExpenses.setAdapter(expenseAdapter);
				
				// calcualte total expense :)
				int totalExpense = dbHelper.getTotalExpenseByDate(reportDate);
				txtReportExpense.setText("Total Expense = " + totalExpense);
				
				
				System.out.println("date: " + reportDate + ", total expense = " + totalExpense);
				
			} 
			catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
	};
	
	DatePickerDialog.OnDateSetListener fromDateListener = new OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			try 
			{				
				strFromDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;			
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
				Date dt = sdf.parse(strFromDate);
				fromDate = dt.getTime();		
				
				Calendar cal = Calendar.getInstance();
				int m = cal.get(Calendar.MONTH);
				int d = cal.get(Calendar.DAY_OF_MONTH);
				int y = cal.get(Calendar.YEAR);
				
				DatePickerDialog dpdToDate = new DatePickerDialog(ReportsActivity.this, toDateListener, y,  m, d );
				dpdToDate.show();
				
			} 
			catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
	};
	
	DatePickerDialog.OnDateSetListener toDateListener = new OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			try 
			{				
				strToDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;			
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
				Date dt = sdf.parse(strToDate);
				toDate = dt.getTime();				
				txtReportType.setText("From: " + strFromDate + " to: " + strToDate);				
				ArrayList<Expense> expenses = new ArrayList<Expense>();
				
				expenses = (ArrayList<Expense>) dbHelper.getExpensesFromDateToDate(fromDate, toDate);
				
				ExpenseAdapter expenseAdapter = new ExpenseAdapter(ReportsActivity.this, R.layout.list_item, expenses);
				lvExpenses.setAdapter(expenseAdapter);
				
				// calcualte total expense :)
				int totalExpense = dbHelper.getTotalExpenseFromDateToDate(fromDate, toDate);
				txtReportExpense.setText("Total Expense = " + totalExpense);
				
				System.out.println("total expense from date to date = " + totalExpense);
				
			} 
			catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}			
		}
	};
	
	
}
