package com.androidhive.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidhive.androidlearn.R;

public class ViewPagerActivity extends Activity {

	private View view0, view1, view2;//需要滑动的页卡  
	private List<View> viewList;//把需要滑动的页卡添加到这个list中  
	
	private ViewPager viewPager;//viewpager  
	//private PagerTitleStrip pagerTitleStrip;//viewpager的标题  
	//private PagerTabStrip pagerTabStrip;//一个viewpager的指示器，效果就是一个横的粗的下划线  
	//private List<String> titleList;//viewpager的标题  
	//private Button weibo_button;//button对象，一会用来进入第二个Viewpager的示例  
	//private Intent intent; 
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_pager);
		
		
		
		getLayoutInflater();
		LayoutInflater lf = LayoutInflater.from(this);  
        view0 = lf.inflate(R.layout.activity_view_pager_page_0, null);  
        view1 = lf.inflate(R.layout.activity_view_pager_page_1, null);  
        view2 = lf.inflate(R.layout.activity_view_pager_page_2, null);
        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中    
        viewList.add(view0);
        viewList.add(view1);  
        viewList.add(view2);
        /*
        titleList = new ArrayList<String>();// 每个页面的Title数据  
        titleList.add("Page0");  
        titleList.add("Page1");  
        titleList.add("Page2");  
		
        pagerTabStrip=(PagerTabStrip) findViewById(R.id.pagertab); 
        pagerTabStrip.setTabIndicatorColor(getResources().getColor(Color.rgb(0,122,0)));  
        pagerTabStrip.setDrawFullUnderline(false); 
        pagerTabStrip.setBackgroundColor(getResources().getColor(Color.rgb(222,122,10))); 
        pagerTabStrip.setTextSpacing(50);
       */
        
        PagerAdapter pagerAdapter = new MyViewPagerAdapter(viewList);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setCurrentItem(0);
        viewPager.setAdapter(pagerAdapter);  
		
	}
	
	
	class MyViewPagerAdapter extends PagerAdapter{  
        private List<View> mListViews; 
        //public ArrayList mListTitles;  
        
        public MyViewPagerAdapter(List<View> mListViews) {  
            this.mListViews = mListViews;//构造方法，参数是我们的页卡，这样比较方便。  
        }  
  
        @Override  
        public void destroyItem(ViewGroup container, int position, Object object)   {     
            container.removeView(mListViews.get(position));//删除页卡  
        }  
  
  
        @Override  
        public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡         
             container.addView(mListViews.get(position), 0);//添加页卡  
             return mListViews.get(position);  
        }  
        public CharSequence getPageTitle(int position) {
            return "Page " + (position + 1);
        }
        @Override  
        public int getCount() {           
            return  mListViews.size();//返回页卡的数量  
        }  
          
        @Override  
        public boolean isViewFromObject(View arg0, Object arg1) {             
            return arg0==arg1;//官方提示这样写  
        }  
    }
}
