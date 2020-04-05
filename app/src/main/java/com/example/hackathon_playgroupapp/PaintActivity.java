package com.example.hackathon_playgroupapp;

import android.os.Bundle;
import android.provider.ContactsContract;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.webrtc.AudioSource;
import org.webrtc.AudioTrack;
import org.webrtc.Camera1Enumerator;
import org.webrtc.CameraEnumerator;
import org.webrtc.EglBase;
import org.webrtc.MediaConstraints;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.SurfaceViewRenderer;
import org.webrtc.VideoCapturer;
import org.webrtc.VideoRenderer;
import org.webrtc.VideoSource;
import org.webrtc.VideoTrack;

public class PaintActivity extends AppCompatActivity {


    private String roomName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Receive roomName
        Bundle extras = getIntent().getExtras();
        roomName = extras.getString("roomName");


        PaintView paintView = new PaintView(this, roomName);
        setContentView(R.layout.paint_activity);
        ConstraintLayout rootLayout = (ConstraintLayout)findViewById(R.id.paintactivity);
        rootLayout.addView(paintView,3000,3000);

        //Initialize PeerConnectionFactory globals.
        //Params are context, initAudio,initVideo and videoCodecHwAcceleration
        PeerConnectionFactory.initialize(PeerConnectionFactory.InitializationOptions.builder(this).setEnableVideoHwAcceleration(true).createInitializationOptions());

        //Create a new PeerConnectionFactory instance.
        PeerConnectionFactory peerConnectionFactory = PeerConnectionFactory.builder().createPeerConnectionFactory();


        //Now create a VideoCapturer instance. Callback methods are there if you want to do something! Duh!
        VideoCapturer videoCapturerAndroid = createVideoCapturer();
        //Create MediaConstraints - Will be useful for specifying video and audio constraints. More on this later!
        MediaConstraints constraints = new MediaConstraints();

        //Create a VideoSource instance
        VideoSource videoSource = peerConnectionFactory.createVideoSource(videoCapturerAndroid);
        VideoTrack localVideoTrack = peerConnectionFactory.createVideoTrack("100", videoSource);

        //create an AudioSource instance
        AudioSource audioSource = peerConnectionFactory.createAudioSource(constraints);
        AudioTrack localAudioTrack = peerConnectionFactory.createAudioTrack("101", audioSource);

        //we will start capturing the video from the camera
        //params are width,height and fps
        videoCapturerAndroid.startCapture(200, 200, 30);

        //create surface renderer, init it and add the renderer to the track
        SurfaceViewRenderer videoView = findViewById(R.id.relativelayout);


        videoView.setMirror(true);

        EglBase rootEglBase = EglBase.create();
        videoView.init(rootEglBase.getEglBaseContext(), null);

        localVideoTrack.addRenderer(new VideoRenderer(videoView));

    }

    private VideoCapturer createVideoCapturer() {
        VideoCapturer videoCapturer;
        videoCapturer = createCameraCapturer(new Camera1Enumerator(false));
        return videoCapturer;
    }



    private VideoCapturer createCameraCapturer(CameraEnumerator enumerator) {
        final String[] deviceNames = enumerator.getDeviceNames();

        // Trying to find a front facing camera!
        for (String deviceName : deviceNames) {
            if (enumerator.isFrontFacing(deviceName)) {
                VideoCapturer videoCapturer = enumerator.createCapturer(deviceName, null);

                if (videoCapturer != null) {
                    return videoCapturer;
                }
            }
        }

        // We were not able to find a front cam. Look for other cameras
        for (String deviceName : deviceNames) {
            if (!enumerator.isFrontFacing(deviceName)) {
                VideoCapturer videoCapturer = enumerator.createCapturer(deviceName, null);
                if (videoCapturer != null) {
                    return videoCapturer;
                }
            }
        }

        return null;
    }
}
