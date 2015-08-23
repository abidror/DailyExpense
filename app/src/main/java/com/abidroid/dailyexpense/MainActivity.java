package com.abidroid.dailyexpense;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Spinner spinnerItems;
	private EditText edPrice;
	private TextView txtDate;
	private Button btnChangeDate, btnInsert, btnReports;
	
	private String strDate;
	private DatabaseHelper dbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dbHelper =  new DatabaseHelper(this);
		spinnerItems = (Spinner)findViewById(R.id.spinnerItemReport);
		edPrice = (EditText)findViewById(R.id.edPrice);
		txtDate = (TextView)findViewById(R.id.txtDate);
		btnChangeDate = (Button)findViewById(R.id.btnChangeDate);
		btnInsert = (Button)findViewById(R.id.btnInsert);
		btnReports = (Button)findViewById(R.id.btnReports);
		
		btnReports.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Intent i = new Intent(MainActivity.this, ReportsActivity.class);
				startActivity(i);
			}
		});
		
		btnInsert.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				try {
					Item item = (Item) spinnerItems.getSelectedItem();
					String itemName = item.getItemName();
					
					int price = Integer.parseInt(edPrice.getText().toString());
					
					String strDate = txtDate.getText().toString();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					
					Date date = sdf.parse(strDate);
					long dt = date.getTime();
					
					Expense expense = new Expense(itemName, price, dt);
					
					dbHelper.addExpense(expense);
					System.out.println("<<<<<<<<<<<<<<<<<<<<<<<< Expense Added >>>>>>>>>>>>>>>>>>>>>>>");
					Toast.makeText(MainActivity.this, "Expense Added", Toast.LENGTH_LONG).show();
					edPrice.setText("");
					
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch(Exception e){
					e.printStackTrace();
				}
				
					// TODO Auto-generated catch block
				
				
			}
		});
		
		populateSpinnerItem();
		
		
		spinnerItems.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
			
				final Dialog dialog = new Dialog(MainActivity.this);
				dialog.setTitle("Add New Item");
				dialog.setContentView(R.layout.dialog_add_item);
				
				final EditText edItemName = (EditText)dialog.findViewById(R.id.edItemNameInDialog);
				Button btnAdd = (Button)dialog.findViewById(R.id.btnAdd);
				Button btnClose = ( Button)dialog.findViewById(R.id.btnClose);
				
				dialog.show();
				
				btnAdd.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						String itemName = edItemName.getText().toString();
						
						Item item = new Item(itemName);
						
						dbHelper.addItem(item);
						Toast.makeText(getApplicationContext(), "item added", Toast.LENGTH_LONG).show();
						edItemName.setText("");
						populateSpinnerItem();
						
					}
				});
				
				btnClose.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				
				return false;
			}
		});
		
		//-- Setting current date
		Calendar rightNow = Calendar.getInstance();
		Date dt = rightNow.getTime();		
		strDate = new SimpleDateFormat("yyyy-MM-dd").format(dt);		
		txtDate.setText(strDate);
		
		btnChangeDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar cal = Calendar.getInstance();
				
				int m = cal.get(Calendar.MONTH);
				int d = cal.get(Calendar.DAY_OF_MONTH);
				int y = cal.get(Calendar.YEAR);
				
				DatePickerDialog dpd = new DatePickerDialog(MainActivity.this, dateListener, y, m, d);
				dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "OK", dpd);
				dpd.show();	
			}
		});
	}
	
	DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
				
			txtDate.setText( year + "-" + (monthOfYear + 1) + "-" + dayOfMonth );
			
		}
	};
	
	public void populateSpinnerItem()
	{
		ArrayList<Item> items;
		
		items = (ArrayList<Item>) dbHelper.getAllItems();
		
		ArrayAdapter<Item> myAdapter = new ArrayAdapter<Item>(this, android.R.layout.simple_spinner_item, items);
        spinnerItems.setAdapter(myAdapter);
	}
}
