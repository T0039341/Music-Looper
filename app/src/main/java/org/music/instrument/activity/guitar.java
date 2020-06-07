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
public class guitar extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    private boolean mStartRecording = true;
    MediaPlayer mp;
    Button s1, s2, s3, s4, s5, s6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guitar);

        floatingActionButton = findViewById(R.id.btnRecord);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecord(mStartRecording);
                mStartRecording = !mStartRecording;
            }
        });

        s1 = (Button) findViewById(R.id.s1);
        s2 = (Button) findViewById(R.id.s2);
        s3 = (Button) findViewById(R.id.s3);
        s4 = (Button) findViewById(R.id.s4);
        s5 = (Button) findViewById(R.id.s5);
        s6 = (Button) findViewById(R.id.s6);

        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPlaying();
                mp = MediaPlayer.create(guitar.this, R.raw.f1);
                mp.start();
            }
        });

        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPlaying();
                mp = MediaPlayer.create(guitar.this, R.raw.f2);
                mp.start();
            }
        });

        s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPlaying();
                mp = MediaPlayer.create(guitar.this, R.raw.f3);
                mp.start();
            }
        });
        s4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPlaying();
                mp = MediaPlayer.create(guitar.this, R.raw.f4);
                mp.start();
            }
        });

        s5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPlaying();
                mp = MediaPlayer.create(guitar.this, R.raw.f5);
                mp.start();
            }
        });

        s6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPlaying();
                mp = MediaPlayer.create(guitar.this, R.raw.f6);
                mp.start();
            }
        });

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

    // Stop the beat.
    private void stopPlaying() {
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }
    }
}
