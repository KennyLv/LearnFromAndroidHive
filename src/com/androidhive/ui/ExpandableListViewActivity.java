package com.androidhive.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;

import com.fromandroidhive.androidlearn.R;

public class ExpandableListViewActivity extends Activity {
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expandable_list_view);

		// get the listview
		expListView = (ExpandableListView) findViewById(R.id.exp_list_view);

		// preparing list data
		prepareListData();

		listAdapter = new ExpandableListAdapter(ExpandableListViewActivity.this, listDataHeader, listDataChild);

		// setting list adapter
		expListView.setAdapter(listAdapter);
		
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() { 
            @Override  
            public void onGroupExpand(int groupPosition) {  
                //Only expand one at once
                for (int i = 0; i < listAdapter.getGroupCount(); i++) {  
                    if (groupPosition != i) {  
                    	expListView.collapseGroup(i);  
                    }
                }
                //set selected one to the top
                expListView.setSelectedGroup(groupPosition);
                //
            	//if(listAdapter.getChildrenCount(groupPosition) == 0){
            	//	return;
            	//}
            }  
        });
		
		/*
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
		    @Override
		    public void onGroupCollapse(int groupPosition) {
		    }
		});
		
		expListView.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, 
				int groupPosition, long id) {  
				return false;
			}  
		});
		
		expListView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                    int groupPosition, int childPosition, long id) {
                return false;
            }
        });
		*/
	}

	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		// Adding child data
		listDataHeader.add("Top 250");
		listDataHeader.add("Now Showing");
		listDataHeader.add("Coming Soon..");

		// Adding child data
		List<String> list_top250 = new ArrayList<String>();
		list_top250.add("The Shawshank Redemption");
		list_top250.add("The Godfather");
		list_top250.add("The Godfather: Part II");
		list_top250.add("Pulp Fiction");
		list_top250.add("The Good, the Bad and the Ugly");
		list_top250.add("The Dark Knight");
		list_top250.add("12 Angry Men");

		List<String> list_nowShowing = new ArrayList<String>();
		list_nowShowing.add("The Conjuring");
		list_nowShowing.add("Despicable Me 2");
		list_nowShowing.add("Turbo");
		list_nowShowing.add("Grown Ups 2");
		list_nowShowing.add("Red 2");
		list_nowShowing.add("The Wolverine");

		List<String> list_comingSoon = new ArrayList<String>();
		list_comingSoon.add("2 Guns");
		list_comingSoon.add("The Smurfs 2");
		list_comingSoon.add("The Spectacular Now");
		list_comingSoon.add("The Canyons");
		list_comingSoon.add("Europa Report");

		listDataChild.put(listDataHeader.get(0), list_top250); // Header, Child data
		listDataChild.put(listDataHeader.get(1), list_nowShowing);
		listDataChild.put(listDataHeader.get(2), list_comingSoon);
	}

	class ExpandableListAdapter extends BaseExpandableListAdapter {
		private Context _context;
		private List<String> _listDataHeader; // header titles
		// child data in format of header title, child title
		private HashMap<String, List<String>> _listDataChild;

		public ExpandableListAdapter(Context context,
				List<String> listDataHeader,
				HashMap<String, List<String>> listChildData) {
			this._context = context;
			this._listDataHeader = listDataHeader;
			this._listDataChild = listChildData;
		}

		@Override
		public Object getChild(int groupPosition, int childPosititon) {
			return this._listDataChild.get(
					this._listDataHeader.get(groupPosition))
					.get(childPosititon);
		}

		@Override
		public long getChildId(int groupPosition, int childPosititon) {
			return childPosititon;
		}

		@Override
		public View getChildView(int groupPosition, int childPosititon,
				boolean isLastChild, View convertView, ViewGroup parent) {

			final String childText = (String) getChild(groupPosition,
					childPosititon);
			if (convertView == null) {
				LayoutInflater infalInflater = (LayoutInflater) this._context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(
						R.layout.activity_expandable_list_view_item, null);
			}
			TextView txtListChild = (TextView) convertView
					.findViewById(R.id.exp_list_view_item);
			txtListChild.setText(childText);
			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return this._listDataChild.get(
					this._listDataHeader.get(groupPosition)).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return this._listDataHeader.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			return this._listDataHeader.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {

			String headerTitle = (String) getGroup(groupPosition);
			if (convertView == null) {
				LayoutInflater infalInflater = (LayoutInflater) this._context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(
						R.layout.activity_expandable_list_view_header, null);
			}

			TextView lblListHeader = (TextView) convertView
					.findViewById(R.id.exp_list_view_header);
			lblListHeader.setTypeface(null, Typeface.BOLD);
			lblListHeader.setText(headerTitle);

			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int arg1) {
			return true;
		}

	}

}
