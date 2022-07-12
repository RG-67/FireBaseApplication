package com.example.firebaseapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class CourseRVModal implements Parcelable {

    private String courseName;
    private String courseDescription;
    private String coursePrice;
    private String courseSuitFor;
    private String courseImage;
    private String courseLink;
    private String courseId;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public CourseRVModal() {
    }

    protected CourseRVModal(Parcel in) {
        courseName = in.readString();
        courseDescription = in.readString();
        coursePrice = in.readString();
        courseSuitFor = in.readString();
        courseImage = in.readString();
        courseLink = in.readString();
        courseId = in.readString();
    }

    public static final Creator<CourseRVModal> CREATOR = new Creator<CourseRVModal>() {
        @Override
        public CourseRVModal createFromParcel(Parcel in) {
            return new CourseRVModal(in);
        }

        @Override
        public CourseRVModal[] newArray(int size) {
            return new CourseRVModal[size];
        }
    };

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(String coursePrice) {
        this.coursePrice = coursePrice;
    }

    public String getCourseSuitFor() {
        return courseSuitFor;
    }

    public void setCourseSuitFor(String courseSuitFor) {
        this.courseSuitFor = courseSuitFor;
    }

    public String getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
    }

    public String getCourseLink() {
        return courseLink;
    }

    public void setCourseLink(String courseLink) {
        this.courseLink = courseLink;
    }

    public CourseRVModal(String courseName, String courseDescription, String coursePrice, String courseSuitFor, String courseImage, String courseLink, String courseId){
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.coursePrice = coursePrice;
        this.courseSuitFor = courseSuitFor;
        this.courseImage = courseImage;
        this.courseLink = courseLink;
        this.courseId = courseId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(courseName);
        parcel.writeString(courseDescription);
        parcel.writeString(coursePrice);
        parcel.writeString(courseSuitFor);
        parcel.writeString(courseImage);
        parcel.writeString(courseLink);
        parcel.writeString(courseId);
    }
}
