package com.example.sampleschool.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sampleschool.Adapter.HomeFragmentAdapter;
import com.example.sampleschool.Common.Common;
import com.example.sampleschool.Common.SpacesItemDecoration;
import com.example.sampleschool.Model.AnnouncementModel;
import com.example.sampleschool.Model.AssignmentModel;
import com.example.sampleschool.Model.ClassModel;
import com.example.sampleschool.Model.ExaminationModel;
import com.example.sampleschool.Model.QuizListModel;
import com.example.sampleschool.Model.ResultModel;
import com.example.sampleschool.R;
import com.example.sampleschool.databinding.FragmentHomeBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    HomeFragmentAdapter adapter;

    List<String> days = new ArrayList<>();

    RecyclerView recycler_home;
    TextView txt_home_timetable,txt_aggregate_attendance,txt_announcement,
            txt_quiz,txt_assignments,txt_exams;
    ImageButton btn_view_time_table,btn_view_courses,btn_view_assignments,btn_view_attendance,
            btn_view_announcements,btn_quiz,btn_results,btn_exams;

    View root;

    LayoutAnimationController layoutAnimationController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        recycler_home = binding.recyclerHome;
        btn_view_courses = binding.btnViewCourses;
        btn_view_time_table = binding.btnViewTimeTable;
        btn_view_assignments = binding.btnViewAssignments;
        btn_view_attendance = binding.btnViewAttendance;
        txt_aggregate_attendance= binding.txtAggregateAttendance;
        txt_announcement = binding.txtAnnouncement;
        btn_view_announcements = binding.btnViewAnnouncements;
        txt_quiz = binding.txtQuiz;
        btn_quiz = binding.btnQuiz;
        txt_assignments = binding.txtAssignments;
        btn_results = binding.btnResults;
        btn_exams = binding.btnExams;
        txt_exams = binding.txtExams;

        btn_view_time_table.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_gallery));
        btn_view_courses.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_my_courses));

        btn_view_announcements.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_announcements));



        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());

        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        days.add("Saturday");


        homeViewModel.getErrorMessage().observe(getViewLifecycleOwner(), s -> Toast.makeText(getContext(), "" + s, Toast.LENGTH_SHORT).show());


        homeViewModel.getClassModelMutableLiveData().observe(getViewLifecycleOwner(), classModels -> {
            if (classModels != null)
            {

                homeViewModel.getQuizListMutableLiveData().observe(getViewLifecycleOwner(), quizListModelList -> {
                    if (quizListModelList != null && quizListModelList.size()>0)
                    {
                        txt_quiz.setText(new StringBuilder(""+quizListModelList.size()));
                    }
                });

                homeViewModel.getAssignmentListMutable().observe(getViewLifecycleOwner(), assignmentModels -> {
                    if (assignmentModels != null && assignmentModels.size()>0)
                    {
                        txt_assignments.setText(new StringBuilder(""+assignmentModels.size()));
                    }
                });

                homeViewModel.getExamMutableLiveData().observe(getViewLifecycleOwner(), examinationModels -> {
                    if (examinationModels != null && examinationModels.size()>0)
                    {
                        txt_exams.setText(new StringBuilder(""+examinationModels.size()));
                    }
                });

                if (days.contains(day))
                {
                    adapter = new HomeFragmentAdapter(getContext(),classModels.getTimetable().get(days.indexOf(day)).getTime(),days.indexOf(day));
                    recycler_home.setAdapter(adapter);
                    recycler_home.setLayoutAnimation(layoutAnimationController);
                }
            } else {
                txt_home_timetable.setText(new StringBuilder("No Timetable Available !"));
            }
        });

        layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.item_from_left);
        recycler_home.setHasFixedSize(true);
        recycler_home.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        recycler_home.addItemDecoration(new SpacesItemDecoration(8));


        homeViewModel.getAttendanceErrorMessage().observe(getViewLifecycleOwner(), s -> Snackbar.make(root,""+s,Snackbar.LENGTH_SHORT).show());

        homeViewModel.getAttendanceMutableLiveData().observe(getViewLifecycleOwner(), attendanceModelList -> {
            if (attendanceModelList != null && attendanceModelList.size()>0)
            {
                for (int i=0;i<attendanceModelList.size();i++)
                {
                    List<String>attendance = new ArrayList<>();
                    for (int j=0;j<attendanceModelList.get(i).getAttendance().size();j++)
                    {
                        attendance.add(attendanceModelList.get(i).getAttendance().get(j).getStatus());
                    }
                    Common.attendanceData.put(attendanceModelList.get(i).getSubjectName(),attendance);
                }
                float totalClass=0,totalAttended=0;
                if (Common.attendanceData.size()>0)
                {
                    for (int i=0;i<Common.attendanceData.size();i++)
                    {
                        totalClass += Common.attendanceData.get(attendanceModelList.get(i).getSubjectName()).size();
                        totalAttended += Collections.frequency(Common.attendanceData.get(attendanceModelList.get(i).getSubjectName()),"Present");
                    }
                }

                txt_aggregate_attendance.setText(new StringBuilder(""+Math.round(100*(totalAttended/totalClass))+"%"));
                btn_view_attendance.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_attendance_list));
            }
        });

        homeViewModel.getQuizErrorMessage().observe(getViewLifecycleOwner(), s -> Snackbar.make(root,"Quiz Error: "+s,Snackbar.LENGTH_SHORT).show());

        homeViewModel.getAssignmentError().observe(getViewLifecycleOwner(), s -> Toast.makeText(getContext(), ""+s, Toast.LENGTH_SHORT).show());

        homeViewModel.getAnnouncementError().observe(getViewLifecycleOwner(), s -> Snackbar.make(root,""+s,Snackbar.LENGTH_SHORT).show());

        homeViewModel.getAnnouncementListMutable().observe(getViewLifecycleOwner(), announcementModelList -> {
            if (announcementModelList != null && announcementModelList.size()>0)
            {
                txt_announcement.setText(new StringBuilder(""+announcementModelList.size()));
            }
        });

        homeViewModel.getResultErrorMutable().observe(getViewLifecycleOwner(), s -> Snackbar.make(root,""+s,Snackbar.LENGTH_LONG).show());

        homeViewModel.getResultMutableLiveData().observe(getViewLifecycleOwner(), resultModelList -> {
            if (resultModelList != null && resultModelList.size()>0)
            {

            }
        });

        btn_quiz.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_quiz));

        btn_view_assignments.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_assignments));

        btn_exams.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_examination));

        btn_results.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_results));

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.home,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_messages) {
            Navigation.findNavController(root).navigate(R.id.nav_messages);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}