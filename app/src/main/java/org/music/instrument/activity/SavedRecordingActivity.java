package org.music.instrument.activity;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.music.instrument.DBHelper;
import org.music.instrument.R;
import org.music.instrument.RecordingItem;
import org.music.instrument.adapters.FileViewerAdapter;

import java.util.ArrayList;
/**
 * Created by GAGAN.
 */
public class SavedRecordingActivity extends AppCompatActivity {

    private FileViewerAdapter mFileViewerAdapter;
    private DBHelper mDatabase;
    TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_recording);
        mDatabase = new DBHelper(SavedRecordingActivity.this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SavedRecordingActivity.this);
        String username = sharedPreferences.getString("username", "default");
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        //newest to oldest order (database stores from oldest to newest)
        llm.setReverseLayout(true);
        llm.setStackFromEnd(true);
        emptyView = (TextView) findViewById(R.id.empty_view);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        FragmentManager fm = getSupportFragmentManager();
        mFileViewerAdapter = new FileViewerAdapter(SavedRecordingActivity.this, llm, fm, getItem(username));
        mRecyclerView.setAdapter(mFileViewerAdapter);
        int total = mDatabase.getCount(username);
        if (total==0) {
            mRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            mRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    public ArrayList<RecordingItem> getItem(String username) {
        return mDatabase.getUserRecording(username);
    }

}
