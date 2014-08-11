package com.example.volskipmusicplayer;

import java.io.File;
import java.util.ArrayList;

import com.example.volskipmusicplayer.MediaPlayerService.MediaPlayerBinder;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Environment;
import android.os.IBinder;

public class VolSkipApplication extends Application {

	private MediaPlayerService mediaPlayerService;
	private boolean musicBound = false;
	private ArrayList<MP3File> songList;
	
	public MediaPlayerService mediaPlayerService() {
		return this.mediaPlayerService;
	}
	
	public ArrayList<MP3File> songList() {
		return this.songList;
	}
	
	public void setMusicBound(Boolean b) {
		this.musicBound = b;
	}
	
	public Boolean musicBound() {
		return this.musicBound;
	}
	
	
	
	@Override
	public void onCreate() {
		File musicFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
		ArrayList<MP3File> fileList = new ArrayList<MP3File>();
		collectMP3(musicFolder, fileList, this);
		
		songList = fileList;
		
		Intent playIntent = new Intent(this, MediaPlayerService.class);
		bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
		startService(playIntent);
		
		
	}
	
	private ServiceConnection musicConnection = new ServiceConnection(){
		 
		  @Override
		  public void onServiceConnected(ComponentName name, IBinder service) {
		    MediaPlayerBinder binder = (MediaPlayerBinder)service;
		    //get service
		    mediaPlayerService = binder.getService();
		    //pass list
		    mediaPlayerService.setSongList(songList);
		    musicBound = true;
		  }
		 
		  @Override
		  public void onServiceDisconnected(ComponentName name) {
		    musicBound = false;
		  }
		};
	
	// Recursive method to collect mp3 files from a folder
		private static void collectMP3(File root, ArrayList<MP3File> list, Context context) {
	        if (root.isDirectory()) {
	            File[] files = root.listFiles();
	            int numberOfFiles = files.length;
	            for (int i = 0; i < numberOfFiles; i++) {
	            	collectMP3(files[i], list, context);
	            }
	        }
	        
	        // Check if it is a .mp3 file
	        String fileName = root.toString();
	        int extensionLocation = fileName.length() - 4;
	        String fileExtension = fileName.substring(extensionLocation);
	        
	        if (fileExtension.equals(".mp3")) {
	        	list.add(new MP3File(root.toString(), context));
	        }
	    }
}
