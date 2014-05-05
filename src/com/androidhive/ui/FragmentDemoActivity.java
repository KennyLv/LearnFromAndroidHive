package com.androidhive.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.androidhive.androidlearn.R;

public class FragmentDemoActivity extends Activity {
	public static String[] array = { "text1", "text2", "text3", "text4", "text5", "text6", "text7", "text8" };
	public static String[] array_txt = { "text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1te" +
			"xt1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1tex" +
			"t1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1t" +
			"ext1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1t" +
			"ext1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text" +
			"1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1tex" +
			"t1text1text1text1text1text1text1text1text1tetext1text1text1text1text1text1text1text1text1text1text1text" +
			"1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text" +
			"1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1tex" +
			"t1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1tex" +
			"t1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1te" +
			"xt1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1te" +
			"xt1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1te" +
			"xt1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1text1",
		"text2", "text3", "text4", "text5", "text6", "text7", "text8" };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment_titles);
		
	}

    @SuppressLint("NewApi")
	public static class TitlesFragment extends ListFragment {
        boolean isDetailPaneReady;
        int curCheckedPosition = 0;
        
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            System.out.println("Fragment-->onCreate");
        }
        
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            System.out.println("Fragment-->onCreateView");
            return super.onCreateView(inflater, container, savedInstanceState);
        }
        
        @Override
        public void onPause() {
            super.onPause();
            System.out.println("Fragment-->onPause");
        }

        @Override
        public void onStop() {
            super.onStop();
            System.out.println("Fragment-->onStop");
        }
        
        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            System.out.println("Fragment-->onAttach");
        }
        
        @Override
        public void onStart() {
            super.onStart();
            System.out.println("Fragment-->onStart");
        }
        
        @Override
        public void onResume() {
            super.onResume();
            System.out.println("Fragment-->onResume");
        }
        
        @Override
        public void onDestroy() {
            super.onDestroy();
            System.out.println("Fragment-->onDestroy");
        }
        
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            System.out.println("Fragment-->onActivityCreted");
            
            setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, array));
            View detailsFrame = getActivity().findViewById(R.id.details);

            isDetailPaneReady = (detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE);

            if (savedInstanceState != null) {
            	curCheckedPosition = savedInstanceState.getInt("curChoice", 0); //从保存的状态中取出数据
            }

            if (isDetailPaneReady) {
                getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                showDetails(curCheckedPosition);
            }
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putInt("curChoice", curCheckedPosition);//保存当前的下标
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            super.onListItemClick(l, v, position, id);
            showDetails(position);
        }

        void showDetails(int index) {
        	curCheckedPosition = index; 
            if (isDetailPaneReady) {
                getListView().setItemChecked(index, true);
                DetailsFragment details = (DetailsFragment) getFragmentManager().findFragmentById(R.id.details); 
                if (details == null || details.getShownIndex() != index) {
                    details = DetailsFragment.newInstance(curCheckedPosition);
                    //得到一个fragment 事务（类似sqlite的操作）
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.details, details);//将得到的fragment 替换当前的viewGroup内容，add则不替换会依次累加
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);//设置动画效果
                    ft.commit();//提交
                }
            } else {
                new AlertDialog.Builder(getActivity())
	                .setTitle(android.R.string.dialog_alert_title)
	                .setMessage(array[index])
	                .setPositiveButton(android.R.string.ok, null)
	                .show();
            }
        }
    }

    /**
     * 作为界面的一部分，为fragment 提供一个layout
     * @author terry
     *
     */
    @SuppressLint("NewApi")
	public static class DetailsFragment extends Fragment {

        public static DetailsFragment newInstance(int index) { 
            DetailsFragment details = new DetailsFragment();
            Bundle args = new Bundle();
            args.putInt("index", index);
            details.setArguments(args);
            return details;
        }

        public int getShownIndex() {
            return getArguments().getInt("index", 0);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            if (container == null)
                return null;

            ScrollView scroller = new ScrollView(getActivity());
            TextView text = new TextView(getActivity());

            int padding = (int) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 4, getActivity().getResources().getDisplayMetrics());
            text.setPadding(padding, padding, padding, padding);
            scroller.addView(text);

            text.setText(array_txt[getShownIndex()]);
            return scroller;
        }
    }

}
