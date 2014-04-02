package com.androidhive.utils;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;

public class DisplayUtil {
	Context _context;
	public DisplayUtil(Context context){
		this._context = context;
	}
	
	/**
	 * px��pixels���Ǿ������أ��ж��ٸ����ص���Ƕ��ٸ����ص㡣
	 * dip��device independent pixel���豸�������أ��������޹ء�
	 * ����ת����ʽ���£�
	 * */
	public int dip2px(float dipValue){              
        final float scale = _context.getResources().getDisplayMetrics().density;                   
        return (int)(dipValue * scale + 0.5f);           
    }              
    public int px2dip(float pxValue){                  
        final float scale = _context.getResources().getDisplayMetrics().density;                   
        return (int)(pxValue / scale + 0.5f);           
    } 
    /**
     * ת��ʱǣ�浽һ���ܶȵĸ���ܶ�Խ�󣬵�λ����ߴ��ϵ����ص��Խ�࣬dipҲ��Խ��
     * ��ת����ʽ��Ҳ���Կ�����px���Ƶ���dip*density��
     * dip��dpһ����˼����android�������Ƽ�����ʹ��dip���
     * 
     * �ٸ��򵥵����ӣ����ɨ����ο�ĳ���Ϊ300px����densityΪ320��480���ֻ���Ļ����ʾЧ������ȫ��һ���ģ���˵�λҪʹ��dip��
     * ������ʹ��canvas���ƶ���ʱ�������յ�����ϵ��Rect�ȵ�λ����px��������ߴ�Ҫ��dipΪ��λ��������Ҫ��pxΪ��λ��
     * ����Ҫ����Ļ������Ϊ�����ε����ģ���һ���߳�ΪD�������Σ���D = 240dip. 
     * ���ȵõ�dipΪ��λ����Ļ���ģ�
     * Ȼ����D(��λdip)�õ���λΪdip�����������꣬
     * �ٽ���ת���ɵ�λΪpx�����꣬�����ڲ�ͬ���ֻ�����ʾЧ������һ���ġ�
     * */
    int D = 240;
    public void testConvert(Context context){
    	DisplayMetrics dm = context.getResources().getDisplayMetrics();
    	Point center_dip = new Point(px2dip(dm.widthPixels)/2, px2dip( dm.heightPixels)/2); //��dipΪ��λ������  
    	Rect square_screen = new Rect(dip2px(center_dip.x - D/2),dip2px(center_dip.y - D/2),
    			dip2px(center_dip.x + D/2), dip2px(center_dip.y + D/2));
    	
    	square_screen.centerX();
    	
    	
    	
    	
    	float density = dm.density; // ��Ļ�ܶȣ����ر�����0.75/1.0/1.5/2.0��  
    	int densityDPI = dm.densityDpi; // ��Ļ�ܶȣ�ÿ�����أ�120/160/240/320��  
    	float xdpi = dm.xdpi;  
    	float ydpi = dm.ydpi;  
    	
    	int screenWidthDip = dm.widthPixels;
    	int screenHeightDip = dm.heightPixels;
    	
    	int screenWidthPx = (int)(dm.widthPixels * density + 0.5f);
    	int screenHeightPx = (int)(dm.heightPixels * density + 0.5f);
    	
    	
    }
    
    /**
     * dip = px/density
     * px = dip*density 
     * 
     * densityDpi ÿӢ����ٸ����ص�,
     * density =  densityDpi /160
     * 
     * ��ʵ������ô����dip��dip�е������ֻ�������ߴ磬����ʵ���ϲ��ǡ�
     * geek 5.0������G700���õ��Ľ����һ���ģ�1080/3 = 720/2
     * 
     * 
     * APP����densityDpi�Ĵ�С���������ĸ��ļ����µ�ͼƬ����ϵ���£�
     * drawable-ldpi ���ܶȣ�ͨ����ָ120
     * drawable-mdpi �е��ܶȣ�ͨ����ָ160
     * drawable-xhdpi �����ܶȣ�ͨ����ָ320
     * 
     * 
     * �ڻ��ƾ���ʱ���������Ȼ����Ļ��ȵ�dip������dip�����߳�D��Ȼ�������еķ���ȷ����px���ꡣ
     * 
     * �ֱ��ʵĸ���
     * �������Ǿ�����320*240��Ϊ �ֱ��ʣ���ʵ�ǲ�׼ȷ�ģ��������ͼƬ�����ص��������ˣ�
     * �ֱ��ʵĸ�����ÿӢ���ϵ����ص�������λΪdpi.һ��ͼƬ��dpiΪ96DPI��
     * */
    
}
