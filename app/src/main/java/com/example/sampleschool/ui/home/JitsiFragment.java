package com.example.sampleschool.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sampleschool.Common.Common;
import com.example.sampleschool.R;

import org.jitsi.meet.sdk.BroadcastEvent;
import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetUserInfo;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

public class JitsiFragment extends Fragment {

    Unbinder unbinder;

    @BindView(R.id.txt_jitsi_subject_name)
    TextView txt_jitsi_subject_name;
    @BindView(R.id.txt_jitsi_time)
    TextView txt_jitsi_time;
    @BindView(R.id.txt_jitsi_time_attended)
    TextView txt_jitsi_time_attended;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;


    private String subjectName, startTime, endTime, start, end;
    private int hiphen,weekPos,timePos;

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onBroadcastReceived(intent);
        }
    };

    private void onBroadcastReceived(Intent intent) {
        if (intent != null) {
            BroadcastEvent event = new BroadcastEvent(intent);

            switch (event.getType()) {
                case CONFERENCE_JOINED:
                    start = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                    Timber.i("Conference Joined with url%s", event.getData().get("url"));
                    break;
                case PARTICIPANT_JOINED:
                    Timber.i("Participant joined%s", event.getData().get("name"));
                    break;
                case CONFERENCE_TERMINATED:
                    end = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                    display();
                    break;
            }
        }
    }

    private void display() {
        txt_jitsi_subject_name.setText(subjectName);

        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");

            java.util.Date d1 = (java.util.Date) format.parse(startTime);
            java.util.Date d2 = (java.util.Date) format.parse(endTime);
            long totalDuration = TimeUnit.MILLISECONDS.toMinutes(Math.abs(d2.getTime() - d1.getTime()));

            java.util.Date ds = (java.util.Date) format.parse(start);
            java.util.Date de = (java.util.Date) format.parse(end);
            long attendedTime = TimeUnit.MILLISECONDS.toMinutes(Math.abs(de.getTime() - ds.getTime()));

            txt_jitsi_time_attended.setText(new StringBuilder("Attended Time: " + attendedTime + " Minutes"));

            progress_bar.setProgress((int) (100 * (attendedTime / totalDuration)));

        } catch (Exception e) {
            Timber.d("Time Conversion Error %s", e);
        }


    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_jitsi, container, false);
        unbinder = ButterKnife.bind(this, root);

        if (getArguments()!= null){

            subjectName = getArguments().getString("subjectName");

            weekPos = getArguments().getInt("weekPos");
            timePos = getArguments().getInt("timPos");

            startTime = Common.classModel.getValue().getTimetable().get(weekPos).getTime().get(timePos).getTimeFrom();
            endTime = Common.classModel.getValue().getTimetable().get(weekPos).getTime().get(timePos).getTimeTo();

            String time = startTime.substring(0,2)+"-"+endTime.substring(0,2);

            String l = "sampleSchool-"+Common.currentStudent.getStudentClass()+"-"+time+"/"+subjectName;

            String link = l.replaceAll("\\s","-");

            URL serverURL;
            try {
                serverURL = new URL("https://meet.jit.si");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new RuntimeException("Invalid server URL!");
            }
            JitsiMeetConferenceOptions defaultOptions
                    = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(serverURL)
                    .setFeatureFlag("kick-out.enabled", false)
                    .setFeatureFlag("live-streaming.enabled", false)
                    .setFeatureFlag("add-people.enabled", false)
                    .setFeatureFlag("invite.enabled", false)
                    .setFeatureFlag("recording.enabled", false)
                    .setFeatureFlag("help.enabled", false)
                    .setFeatureFlag("video-mute.enabled", false)
                    .setFeatureFlag("video-share.enabled", false)
                    .setFeatureFlag("meeting-password.enabled", false)
                    .setFeatureFlag("overflow-menu.enabled", false)
                    .setWelcomePageEnabled(false)
                    .build();
            JitsiMeet.setDefaultConferenceOptions(defaultOptions);
            registerForBroadcastMessages();

            JitsiMeetUserInfo userInfo = new JitsiMeetUserInfo();
            userInfo.setDisplayName(Common.currentStudent.getStudentName());
            if (!Common.currentStudent.getStudentImage().equalsIgnoreCase("Null")) {
                try {
                    if (!Common.currentStudent.getStudentImage().equals("NULL")){
                        URL url = new URL(Common.currentStudent.getStudentImage());
                        userInfo.setAvatar(url);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            // Build options object for joining the conference. The SDK will merge the default
            // one we set earlier and this one when joining.
            JitsiMeetConferenceOptions options
                    = new JitsiMeetConferenceOptions.Builder()
                    .setRoom(link)
                    .setUserInfo(userInfo)
                    .setVideoMuted(true)
                    .build();

            // Launch the new activity with the given options. The launch() method takes care
            // of creating the required Intent and passing the options.
            JitsiMeetActivity.launch(getContext(), options);
        }
        return root;
    }

    private void registerForBroadcastMessages() {
        IntentFilter intentFilter = new IntentFilter();

        /* This registers for every possible event sent from JitsiMeetSDK
           If only some of the events are needed, the for loop can be replaced
           with individual statements:
           ex:  intentFilter.addAction(BroadcastEvent.Type.AUDIO_MUTED_CHANGED.getAction());
                intentFilter.addAction(BroadcastEvent.Type.CONFERENCE_TERMINATED.getAction());
                ... other events
         */
        for (BroadcastEvent.Type type : BroadcastEvent.Type.values()) {
            intentFilter.addAction(type.getAction());
        }

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(broadcastReceiver, intentFilter);
    }
}