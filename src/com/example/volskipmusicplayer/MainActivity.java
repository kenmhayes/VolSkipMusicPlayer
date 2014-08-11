package com.example.volskipmusicplayer;

import java.io.IOException;
import java.util.ArrayList;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;


public class MainActivity extends Activity {
	
	
	private ArrayList<MP3File> songList;
	private MediaPlayerService mediaPlayerService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		VolSkipApplication  app = (VolSkipApplication) getApplication();
		songList = app.songList();
		mediaPlayerService = app.mediaPlayerService();
		
		SongAdapter adapter = new SongAdapter(this, songList);
		
		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(adapter);
		
	}
	
	public void songPicked(View view) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException{
		  mediaPlayerService.setPosition(Integer.parseInt(view.getTag().toString()));
		  mediaPlayerService.playSong();
		}
	
	
	
	
}

