package com.example.firebaseapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CourseRVAdapter extends RecyclerView.Adapter<CourseRVAdapter.ViewHolder> {

    private ArrayList<CourseRVModal> courseRVModalArrayList;
    private Context context;
    private CourseClickInterface courseClickInterface;
    int lastPosition = -1;

    public CourseRVAdapter(ArrayList<CourseRVModal> courseRVModalArrayList, Context context, CourseClickInterface courseClickInterface) {
        this.courseRVModalArrayList = courseRVModalArrayList;
        this.context = context;
        this.courseClickInterface = courseClickInterface;
    }

    @NonNull
    @Override
    public CourseRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_rlview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseRVAdapter.ViewHolder holder, int position) {
        CourseRVModal courseRVModal = courseRVModalArrayList.get(position);
        holder.viewCourseName.setText(courseRVModal.getCourseName());
        holder.viewCoursePrice.setText("Rs: " +courseRVModal.getCoursePrice());
        Picasso.get().load(courseRVModal.getCourseImage()).into(holder.viewCourse);

        setAnimation(holder.itemView, position);
        holder.viewCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseClickInterface.onCourseClick(position);
            }
        });
    }
    private void setAnimation(View itemView, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return courseRVModalArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView viewCourse;
        private TextView viewCourseName, viewCoursePrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewCourse = itemView.findViewById(R.id.viewCourse);
            viewCourseName = itemView.findViewById(R.id.viewCourseName);
            viewCoursePrice = itemView.findViewById(R.id.viewCoursePrice);
        }
    }

    public interface CourseClickInterface {
        void onCourseClick(int position);
    }
}
