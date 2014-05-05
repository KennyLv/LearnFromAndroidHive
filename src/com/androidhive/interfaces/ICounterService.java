package com.androidhive.interfaces;

public interface ICounterService {
	 public void startCounter(int initVal);
	 public void resumeCounter();
     public void stopCounter();
	boolean isStopped();
}
