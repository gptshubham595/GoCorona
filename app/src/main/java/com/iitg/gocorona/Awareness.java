package com.iitg.gocorona;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class Awareness extends AppCompatActivity {
    ViewPager viewPager;
    FragmentPagerItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awareness);

        adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("SYMPTOMS", AwarenessFragment1.class)
                .add("PREVENTION", AwarenessFragment2.class)
                .add("TREATMENT", AwarenessFragment3.class)
                .create());

        try{VideoView videoView = (VideoView) findViewById(R.id.video);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video));
        videoView.start();}catch (Exception e){e.printStackTrace();
            Toast.makeText(this, "Cannot Play Video", Toast.LENGTH_SHORT).show();}
    }
}
