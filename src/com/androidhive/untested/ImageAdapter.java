package com.androidhive.untested;

import com.androidhive.androidlearn.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Context myContext;
	private int[] myImageIds = { R.drawable.ic_pages, R.drawable.ic_home,
			R.drawable.ic_launcher, R.drawable.ic_communities,
			R.drawable.ic_whats_hot, R.drawable.ic_drawer };

	public ImageAdapter(Context c) {
		this.myContext = c;
	}

	@Override
	public int getCount() {
		return this.myImageIds.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView i = new ImageView(this.myContext);
		i.setImageResource(this.myImageIds[position]);
		i.setScaleType(ImageView.ScaleType.FIT_XY);
		i.setLayoutParams(new Gallery.LayoutParams(120, 120));
		return i;
	}

	public float getScale(boolean focused, int offset) {
		/* Formula: 1 / (2 ^ offset) */
		return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
	}
}
