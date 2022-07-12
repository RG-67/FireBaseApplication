package com.example.firebaseapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.text.Transliterator;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout bottomSheet;
    private RecyclerView mainRlViewCourses;
    private ProgressBar mainCourseBar;
    private FloatingActionButton mainCourseFBtn;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private ArrayList<CourseRVModal> courseRVModalArrayList;
    private CourseRVAdapter courseRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomSheet = findViewById(R.id.bSheet);
        mainRlViewCourses = findViewById(R.id.mainRlViewCourses);
        mainCourseBar = findViewById(R.id.mainCourseBar);
        mainCourseFBtn = findViewById(R.id.mainCourseFBtn);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Courses");
        mAuth = FirebaseAuth.getInstance();
        courseRVModalArrayList = new ArrayList<>();

        mainCourseFBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCourseActivity.class);
                startActivity(intent);
            }
        });
        courseRVAdapter = new CourseRVAdapter(courseRVModalArrayList, this, this::onCourseClick);
        mainRlViewCourses.setLayoutManager(new LinearLayoutManager(this));
        mainRlViewCourses.setAdapter(courseRVAdapter);
        getAllCourses();
    }

    private void getAllCourses() {
        courseRVModalArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                mainCourseBar.setVisibility(View.GONE);
                courseRVModalArrayList.add(snapshot.getValue(CourseRVModal.class));
                courseRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                mainCourseBar.setVisibility(View.GONE);
                courseRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                courseRVAdapter.notifyDataSetChanged();
                mainCourseBar.setVisibility(View.GONE);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                courseRVAdapter.notifyDataSetChanged();
                mainCourseBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void onCourseClick(int position) {
        displayBottomSheet(courseRVModalArrayList.get(position));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menuLogOut:
                Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void displayBottomSheet(CourseRVModal courseRVModal) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet, bottomSheet);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView mainCourseName = layout.findViewById(R.id.bSheetCourseName);
        ImageView mainCourseImage = layout.findViewById(R.id.bSheetImage);
        TextView mainCourseDesc = layout.findViewById(R.id.bSheetCourseDesc);
        TextView mainCourseSuitFor = layout.findViewById(R.id.bSheetCourseSuitFor);
        TextView mainCoursePrice = layout.findViewById(R.id.bSheetPrice);
        Button mainCourseEdtBtn = layout.findViewById(R.id.bSheetEdtCourse);
        Button mainCourseViewDetailsBtn = layout.findViewById(R.id.bSheetViewDetails);

        mainCourseName.setText(courseRVModal.getCourseName());
        mainCourseDesc.setText(courseRVModal.getCourseDescription());
        mainCourseSuitFor.setText("Best suited for " + courseRVModal.getCourseSuitFor());
        mainCoursePrice.setText("Rs: " + courseRVModal.getCoursePrice());
        Picasso.get().load(courseRVModal.getCourseImage()).into(mainCourseImage);

        mainCourseEdtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditCourseActivity.class);
                intent.putExtra("course", courseRVModal);
                startActivity(intent);
            }
        });
        mainCourseViewDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(courseRVModal.getCourseLink()));
                startActivity(intent);
            }
        });
    }
}