package com.androidhive.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.androidhive.ui.options.ViewPagerPage_0Activity;
import com.androidhive.ui.options.ViewPagerPage_1Activity;
import com.androidhive.ui.options.ViewPagerPage_2Activity;
import com.androidhive.androidlearn.R;

@SuppressWarnings("deprecation")
public class TabHostActivity extends TabActivity {
	// TabSpec Names
    private static final String INBOX_SPEC = "Inbox";
    private static final String OUTBOX_SPEC = "Outbox";
    private static final String PROFILE_SPEC = "Profile";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_host);
		
		TabHost tabHost = getTabHost();
        
        TabSpec inboxSpec = tabHost.newTabSpec(INBOX_SPEC);
        inboxSpec.setIndicator(INBOX_SPEC, getResources().getDrawable(R.drawable.icon_inbox));
        Intent inboxIntent = new Intent(this, ViewPagerPage_0Activity.class);
        inboxSpec.setContent(inboxIntent);
        
        TabSpec outboxSpec = tabHost.newTabSpec(OUTBOX_SPEC);
        outboxSpec.setIndicator(OUTBOX_SPEC, getResources().getDrawable(R.drawable.icon_outbox));
        Intent outboxIntent = new Intent(this, ViewPagerPage_1Activity.class);
        outboxSpec.setContent(outboxIntent);
         
        TabSpec profileSpec = tabHost.newTabSpec(PROFILE_SPEC);
        profileSpec.setIndicator(PROFILE_SPEC, getResources().getDrawable(R.drawable.icon_profile));
        Intent profileIntent = new Intent(this, ViewPagerPage_2Activity.class);
        profileSpec.setContent(profileIntent);
         
        // Adding all TabSpec to TabHost
        tabHost.addTab(inboxSpec); // Adding Inbox tab
        tabHost.addTab(outboxSpec); // Adding Outbox tab
        tabHost.addTab(profileSpec); // Adding Profile tab
		
	}

}
