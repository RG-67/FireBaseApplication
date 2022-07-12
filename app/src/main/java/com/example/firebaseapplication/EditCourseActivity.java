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

import java.util.HashMap;
import java.util.Map;

public class EditCourseActivity extends AppCompatActivity {

    private EditText edtCourseName, edtDescription, edtPrice, edtCourseSuitFor, edtCourseImageLink, edtCourseLink;
    private Button edtCourseBtn, edtCourseDltBtn;
    private ProgressBar edtCourseBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private CourseRVModal courseRVModal;
    private String courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        edtCourseName = findViewById(R.id.edtCourseName);
        edtDescription = findViewById(R.id.edtDescription);
        edtPrice = findViewById(R.id.edtPrice);
        edtCourseSuitFor = findViewById(R.id.edtCourseSuitFor);
        edtCourseImageLink = findViewById(R.id.edtCourseImageLink);
        edtCourseLink = findViewById(R.id.edtCourseLink);
        edtCourseBtn = findViewById(R.id.edtCourseBtn);
        edtCourseDltBtn = findViewById(R.id.edtCourseDltBtn);
        edtCourseBar = findViewById(R.id.edtCourseBar);
        firebaseDatabase = FirebaseDatabase.getInstance();
        courseRVModal = getIntent().getParcelableExtra("course");

        if (courseRVModal != null){
            edtCourseName.setText(courseRVModal.getCourseName());
            edtDescription.setText(courseRVModal.getCourseDescription());
            edtPrice.setText(courseRVModal.getCoursePrice());
            edtCourseSuitFor.setText(courseRVModal.getCourseSuitFor());
            edtCourseImageLink.setText(courseRVModal.getCourseImage());
            edtCourseLink.setText(courseRVModal.getCourseLink());
            courseID = courseRVModal.getCourseId();
        }
        databaseReference = firebaseDatabase.getReference("Courses").child(courseID);
        edtCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtCourseBar.setVisibility(View.VISIBLE);

                String courseName = edtCourseName.getText().toString();
                String courseDesc = edtDescription.getText().toString();
                String coursePrice = edtPrice.getText().toString();
                String courseSuitFr = edtCourseSuitFor.getText().toString();
                String courseImgLnk = edtCourseImageLink.getText().toString();
                String courseLink = edtCourseLink.getText().toString();

                Map<String, Object> map = new HashMap<>();
                map.put("courseName", courseName);
                map.put("courseDescription", courseDesc);
                map.put("coursePrice", coursePrice);
                map.put("courseSuitFor", courseSuitFr);
                map.put("courseImage", courseImgLnk);
                map.put("courseLink", courseLink);
                map.put("courseId", courseID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        edtCourseBar.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditCourseActivity.this, "Course updated successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditCourseActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditCourseActivity.this, "Fail to update course", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        edtCourseDltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCourse();
            }
        });
    }
    private void deleteCourse(){
        databaseReference.removeValue();
        Toast.makeText(this, "Course deleted successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditCourseActivity.this, MainActivity.class));
    }
}