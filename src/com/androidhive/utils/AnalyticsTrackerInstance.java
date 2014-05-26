package com.androidhive.utils;

import android.content.Context;
import android.os.Debug;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;

public class AnalyticsTrackerInstance {
	private GoogleAnalyticsTracker tracker;
	public AnalyticsTrackerInstance(Context context) {
	
		tracker = GoogleAnalyticsTracker.getInstance();
		
		tracker.start("UA-21334020-1",context);
		Debug.startMethodTracing("myTraceview");
		
        tracker.trackEvent(
                "Clicks",  // Category
                "Button",  // Action
                "clicked", // Label
                77);       // Value
        
        
        
     // Add a Custom Variable to this pageview, with name of "Medium" and value "MobileApp" and
        // scope of session-level.
        tracker.setCustomVar(1, "Navigation Type", "Button click", 2);
        // Track a page view. This is probably the best way to track which parts of your application
        // are being used.
        // E.g.
        // tracker.trackPageView("/help"); to track someone looking at the help screen.
        // tracker.trackPageView("/level2"); to track someone reaching level 2 in a game.
        // tracker.trackPageView("/uploadScreen"); to track someone using an upload screen.
        tracker.trackPageView("/testApplicationHomeScreen");
        
        
     // Manually start a dispatch, not needed if the tracker was started with a dispatch interval.
        tracker.dispatch();
        
     // Stop the tracker when it is no longer needed.
        tracker.stop();
        
        
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
