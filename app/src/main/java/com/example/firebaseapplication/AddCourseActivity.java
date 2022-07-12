package com.example.firebaseapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddCourseActivity extends AppCompatActivity {

    private EditText addCourseName, addDescription, addPrice, addCourseSuitFor, addCourseImageLink, addCourseLink;
    private Button addCourseBtn;
    private ProgressBar addCourseBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        addCourseName = findViewById(R.id.addCourseName);
        addDescription = findViewById(R.id.addDescription);
        addPrice = findViewById(R.id.addPrice);
        addCourseSuitFor = findViewById(R.id.addCourseSuitFor);
        addCourseImageLink = findViewById(R.id.addCourseImageLink);
        addCourseLink = findViewById(R.id.addCourseLink);
        addCourseBtn = findViewById(R.id.addCourseBtn);
        addCourseBar = findViewById(R.id.addCourseBar);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Courses");

        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCourseBar.setVisibility(View.VISIBLE);
                String courseNm = addCourseName.getText().toString();
                String courseDesc = addDescription.getText().toString();
                String coursePrc = addPrice.getText().toString();
                String courseSuitFr = addCourseSuitFor.getText().toString();
                String courseImgLink =addCourseImageLink.getText().toString();
                String courseLnk = addCourseLink.getText().toString();
                courseID = courseNm;
                CourseRVModal courseRVModal = new CourseRVModal(courseNm, courseDesc, coursePrc, courseSuitFr, courseImgLink, courseLnk, courseID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child(courseID).setValue(courseRVModal);
                        Toast.makeText(AddCourseActivity.this, "Course added successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddCourseActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddCourseActivity.this, "Fail to add course", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}