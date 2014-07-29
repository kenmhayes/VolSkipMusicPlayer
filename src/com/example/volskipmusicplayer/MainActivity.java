package com.example.volskipmusicplayer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.example.volskipmusicplayer.MediaPlayerService.MediaPlayerBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends Activity {

	private MediaPlayerService mediaPlayerService;
	private Intent playIntent;
	private boolean musicBound = false;
	private ArrayList<MP3File> songList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		File musicFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
		ArrayList<MP3File> fileList = new ArrayList<MP3File>();
		collectMP3(musicFolder, fileList, this);
		
		songList = fileList;
		
		SongAdapter adapter = new SongAdapter(this, songList);
		
		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(adapter);
		
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		if (playIntent == null) {
			playIntent = new Intent(this, MediaPlayerService.class);
			bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
			startService(playIntent);
		}
	}
	
	public void songPicked(View view) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException{
		  mediaPlayerService.setPosition(Integer.parseInt(view.getTag().toString()));
		  mediaPlayerService.playSong();
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

