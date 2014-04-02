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
	 * px即pixels，是绝对像素，有多少个像素点就是多少个像素点。
	 * dip即device independent pixel，设备独立像素，与像素无关。
	 * 二者转换公式如下：
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
     * 转换时牵涉到一个密度的概念，密度越大，单位物理尺寸上的像素点就越多，dip也就越大。
     * 从转换公式上也可以看到，px近似等于dip*density。
     * dip跟dp一个意思，在android开发中推荐像素使用dip概念。
     * 
     * 举个简单的例子，如果扫描矩形框的长度为300px，在density为320和480的手机屏幕上显示效果是完全不一样的，因此单位要使用dip。
     * 但是在使用canvas绘制东西时，所依照的坐标系、Rect等单位都是px，所以其尺寸要以dip为单位，而坐标要以px为单位。
     * 假设要以屏幕的中心为正方形的中心，画一个边长为D的正方形，设D = 240dip. 
     * 首先得到dip为单位的屏幕中心，
     * 然后以D(单位dip)得到单位为dip的正常性坐标，
     * 再将其转换成单位为px的坐标，这样在不同的手机上显示效果就是一样的。
     * */
    int D = 240;
    public void testConvert(Context context){
    	DisplayMetrics dm = context.getResources().getDisplayMetrics();
    	Point center_dip = new Point(px2dip(dm.widthPixels)/2, px2dip( dm.heightPixels)/2); //以dip为单位的中心  
    	Rect square_screen = new Rect(dip2px(center_dip.x - D/2),dip2px(center_dip.y - D/2),
    			dip2px(center_dip.x + D/2), dip2px(center_dip.y + D/2));
    	
    	square_screen.centerX();
    	
    	
    	
    	
    	float density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）  
    	int densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）  
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
     * densityDpi 每英寸多少个像素点,
     * density =  densityDpi /160
     * 
     * 其实可以这么看待dip，dip有点类似手机的物理尺寸，尽管实际上不是。
     * geek 5.0寸屏跟G700，得到的结果是一样的：1080/3 = 720/2
     * 
     * 
     * APP根据densityDpi的大小决定调用哪个文件夹下的图片，关系如下：
     * drawable-ldpi 底密度，通常是指120
     * drawable-mdpi 中等密度，通常是指160
     * drawable-xhdpi 超高密度，通常是指320
     * 
     * 
     * 在绘制矩形时，可以首先获得屏幕宽度的dip，根据dip决定边长D，然后用文中的方法确定其px坐标。
     * 
     * 分辨率的概念
     * 现在人们经常把320*240成为 分辨率，其实是不准确的，这仅仅是图片的像素点总数罢了，
     * 分辨率的概念是每英寸上的像素点数，单位为dpi.一般图片的dpi为96DPI。
     * */
    
}
