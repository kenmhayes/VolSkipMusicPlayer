package com.example.volskipmusicplayer;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SongAdapter extends BaseAdapter{
	private ArrayList<MP3File> songList;
	private LayoutInflater songInf;
	
	public SongAdapter(Context c, ArrayList<MP3File> songs){
		  songList = songs;
		  songInf=LayoutInflater.from(c);
	}
	
	@Override
	public int getCount() {
		return songList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout songLay = (LinearLayout)songInf.inflate
			      (R.layout.song_list_item, parent, false);
		TextView songView = (TextView)songLay.findViewById(R.id.song_title);
		TextView artistView = (TextView)songLay.findViewById(R.id.song_artist);
		MP3File currSong = songList.get(position);
		songView.setText(currSong.songTitle());
		artistView.setText(currSong.artistName());
		
		songLay.setTag(position);
		return songLay;
	}

}
