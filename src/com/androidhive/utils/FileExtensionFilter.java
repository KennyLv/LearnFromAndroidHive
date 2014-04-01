package com.androidhive.utils;

import java.io.File;
import java.io.FilenameFilter;

public class FileExtensionFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {
        return (name.endsWith(".mp3") || name.endsWith(".MP3") || name.endsWith(".Mp3")|| name.endsWith(".mP3"));
	}

}
