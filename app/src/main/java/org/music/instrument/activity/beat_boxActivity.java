package org.music.instrument.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import org.music.instrument.R;
import org.music.instrument.RecordingService;

import java.io.File;

/**
 * Created by GAGAN.
 */
public class beat_boxActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    private boolean mStartRecording = true;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_beatbox);
        floatingActionButton = findViewById(R.id.btnRecord);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecord(mStartRecording);
                mStartRecording = !mStartRecording;
            }
        });

        Button b1 = (Button) this.findViewById(R.id.beat1);
        Button b2 = (Button) this.findViewById(R.id.beat2);
        Button b3 = (Button) this.findViewById(R.id.beat3);
        Button b4 = (Button) this.findViewById(R.id.beat4);
        Button b5 = (Button) this.findViewById(R.id.beat5);
        Button b6 = (Button) this.findViewById(R.id.beat6);

        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                stopPlaying();
                mp = MediaPlayer.create(beat_boxActivity.this, R.raw.drumbeat1);
                mp.start();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                stopPlaying();
                mp = MediaPlayer.create(beat_boxActivity.this, R.raw.drumbeat2);
                mp.start();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                stopPlaying();
                mp = MediaPlayer.create(beat_boxActivity.this, R.raw.drumbeat3);
                mp.start();
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                stopPlaying();
                mp = MediaPlayer.create(beat_boxActivity.this, R.raw.drumbeat4);
                mp.start();
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                stopPlaying();
                mp = MediaPlayer.create(beat_boxActivity.this, R.raw.dubstep1);
                mp.start();
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                stopPlaying();
                mp = MediaPlayer.create(beat_boxActivity.this, R.raw.dubstep2);
                mp.start();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (!mStartRecording) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Stop Recording")
                    .setMessage("Do you want to stop the Recording?")
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    onRecord(mStartRecording);
                                    stopPlaying();
                                    finish();

                                }

                            }).setNegativeButton("No", null).show();
        } else {
            stopPlaying();
            this.finish();
        }
    }

    // Record the sound.
    private void onRecord(boolean start) {
        Intent intent = new Intent(this, RecordingService.class);
        if (start) {
            // start recording
            floatingActionButton.setImageResource(R.drawable.ic_media_stop);
            Toast.makeText(this, R.string.toast_recording_start, Toast.LENGTH_SHORT).show();
            File folder = new File(Environment.getExternalStorageDirectory() + "/SoundRecorder");
            if (!folder.exists()) {
                //folder /SoundRecorder doesn't exist, create the folder
                folder.mkdir();
            }

            //start RecordingService
            startService(intent);
            //keep screen on while recording
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            //stop recording
            floatingActionButton.setImageResource(R.drawable.ic_mic_white_36dp);

            stopService(intent);
            //allow the screen to turn off again once recording is finished
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    // Stop the beat.
    private void stopPlaying() {
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }
    }
}
