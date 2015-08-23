package com.abidroid.dailyexpense;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ExpenseAdapter extends ArrayAdapter<Expense> {

	ArrayList<Expense> objects;
	
	public ExpenseAdapter(Context context, int resource, ArrayList<Expense> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		
		this.objects = objects;
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		View v = convertView;
				
		if( v == null)
		{
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			//v = inflater.inflate(R.layout.list_item, null);
			v = inflater.inflate(R.layout.list_item, null);
			
			Expense expense = objects.get(position);
			
			if( expense != null)
			{
				TextView txtDate = (TextView)v.findViewById(R.id.txtRdate);
				TextView txtItem = (TextView)v.findViewById(R.id.txtRItem);
				TextView txtPrice = (TextView)v.findViewById(R.id.txtRprice);
								
				if( txtDate != null)
				{
					
					long dt = expense.getExpenseDate();
					Date d = new Date( dt );
					
					SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd");
					String date = formatter.format(dt);
					
					txtDate.setText( date );
					
					txtItem.setText(expense.getExpenseItemName());
					txtPrice.setText(Integer.toString(expense.getItemPrice()));
					
				}
				
			}
		}
		return v;
		
	}
	
	@Override
	public void remove(Expense object) {
		// TODO Auto-generated method stub
		super.remove(object);
	}
}
