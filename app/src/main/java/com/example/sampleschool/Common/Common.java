package com.example.sampleschool.Common;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.MutableLiveData;

import com.example.sampleschool.Model.AddAttendanceModel;
import com.example.sampleschool.Model.AnnouncementModel;
import com.example.sampleschool.Model.AssignmentModel;
import com.example.sampleschool.Model.AttendanceModel;
import com.example.sampleschool.Model.ClassModel;
import com.example.sampleschool.Model.ExaminationModel;
import com.example.sampleschool.Model.QuizListModel;
import com.example.sampleschool.Model.ResultModel;
import com.example.sampleschool.Model.StudentModel;
import com.example.sampleschool.Model.WeekdayModel;
import com.example.sampleschool.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Common {
    public static StudentModel currentStudent;
    public static MutableLiveData<ClassModel> classModel;
    public static List<AttendanceModel> myAttendanceList;
    public static List<QuizListModel> myQuizList;
    public static List<AnnouncementModel> announcementList;
    public static List<AssignmentModel> myAssignments;
    public static List<ExaminationModel> myExamsList;
    public static List<ResultModel> myResult;
    public static Map<String, List<String>> attendanceData = new HashMap<>();
    public static NetworkInfo networkInfo;

    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = cm.getActiveNetworkInfo();
        return networkInfo;
    }



    public static void setSpanString(String welcome, String name, TextView textView) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(welcome);
        SpannableString spannableString = new SpannableString(name);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(boldSpan, 0, name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(spannableString);
        textView.setText(builder, TextView.BufferType.SPANNABLE);
    }

    public static void updateToken(Context context, String token) {
        if (Common.currentStudent != null) {
            FirebaseFirestore.getInstance()
                    .collection("Students")
                    .document(Common.currentStudent.getUid())
                    .update("studentToken", token)
                    .addOnFailureListener(e -> Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    public static void showNotification(Context context, int id, String title, String content, Intent intent) {
        PendingIntent pendingIntent = null;
        if (intent != null)
            pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        String NOTIFICATION_CHANNEL_ID = "sampleSchool";
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "SampleSchool", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("SampleSchool");
            notificationChannel.enableLights(true);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        builder.setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setFullScreenIntent(pendingIntent,true)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher_round))
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE);
        if (pendingIntent != null)
            builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();

        notificationManager.notify(id, notification);

    }


}
