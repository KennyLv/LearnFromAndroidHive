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

	private View view0, view1, view2;//��Ҫ������ҳ��  
	private List<View> viewList;//����Ҫ������ҳ����ӵ����list��  
	
	private ViewPager viewPager;//viewpager  
	//private PagerTitleStrip pagerTitleStrip;//viewpager�ı���  
	//private PagerTabStrip pagerTabStrip;//һ��viewpager��ָʾ����Ч������һ����Ĵֵ��»���  
	//private List<String> titleList;//viewpager�ı���  
	//private Button weibo_button;//button����һ����������ڶ���Viewpager��ʾ��  
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
        viewList = new ArrayList<View>();// ��Ҫ��ҳ��ʾ��Viewװ��������    
        viewList.add(view0);
        viewList.add(view1);  
        viewList.add(view2);
        /*
        titleList = new ArrayList<String>();// ÿ��ҳ���Title����  
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
            this.mListViews = mListViews;//���췽�������������ǵ�ҳ���������ȽϷ��㡣  
        }  
  
        @Override  
        public void destroyItem(ViewGroup container, int position, Object object)   {     
            container.removeView(mListViews.get(position));//ɾ��ҳ��  
        }  
  
  
        @Override  
        public Object instantiateItem(ViewGroup container, int position) {  //�����������ʵ����ҳ��         
             container.addView(mListViews.get(position), 0);//���ҳ��  
             return mListViews.get(position);  
        }  
        public CharSequence getPageTitle(int position) {
            return "Page " + (position + 1);
        }
        @Override  
        public int getCount() {           
            return  mListViews.size();//����ҳ��������  
        }  
          
        @Override  
        public boolean isViewFromObject(View arg0, Object arg1) {             
            return arg0==arg1;//�ٷ���ʾ����д  
        }  
    }
}
