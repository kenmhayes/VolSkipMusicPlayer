package com.example.volskipmusicplayer;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.media.AudioManager;
import android.media.MediaPlayer;

public class MediaPlayerService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
MediaPlayer.OnCompletionListener {

	private MediaPlayer mediaPlayer;
	private ArrayList<MP3File> songList;
	private int position;
	private final IBinder mediaPlayerBind = new MediaPlayerBinder();
	
	public void onCreate() {
		super.onCreate();
		position = 0;
		mediaPlayer = new MediaPlayer();
		initializeMediaPlayer();
	}
	
	public void initializeMediaPlayer() {
		//mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.setOnCompletionListener(this);
		mediaPlayer.setOnErrorListener(this);
	}
	
	public void onPrepared(MediaPlayer player) {
        player.start();
    }

	
	public void setSongList(ArrayList<MP3File> list) {
		songList = list;
	}
	
	public class MediaPlayerBinder extends Binder {
		  MediaPlayerService getService() {
		    return MediaPlayerService.this;
		  }
		}
	
	@Override
	public IBinder onBind(Intent intent) {
		return mediaPlayerBind;
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		mediaPlayer.stop();
		mediaPlayer.release();
		return false;
	}
	
	@Override
	  public int onStartCommand(Intent intent, int flags, int startId) {
	    //TODO do something useful
	    return Service.START_NOT_STICKY;
	  }

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		return false;
	}

	public void playSong() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
		mediaPlayer.reset();
		MP3File song = songList.get(position);
		mediaPlayer.setDataSource(song.getPath());
		mediaPlayer.prepareAsync();
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
}
