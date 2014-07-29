package com.example.volskipmusicplayer;

import java.io.File;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.provider.MediaStore;

public class MP3File extends File {

	private String songTitle;
	private String artistName;
	
	public MP3File(String path, Context context) {
		super(path);
		String[] mProjection = { MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST };
		Cursor cur = context.getContentResolver().query(
		    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, 
		    mProjection, 
		    MediaStore.MediaColumns.DATA + "='" + path.replaceAll("'", "''") + "'", null, null);
			if(cur.getCount() > 0)	{
				cur.moveToFirst();
				songTitle = cur.getString(cur.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
				artistName = cur.getString(cur.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
			}
		cur.close();
		
	}
	
	public String songTitle() {
		return this.songTitle;
	}
	
	public String artistName() {
		return this.artistName;
	}
	
	@Override
	public String toString() {
		
		/*
		String pathName = super.toString();
		String fileName = pathName.substring(pathName.lastIndexOf('/') + 1);
		String songName = fileName.substring(0, fileName.length() - 4);
		return songName;
		*/
		
		return songTitle + "\n" + artistName;
	}
	

}
