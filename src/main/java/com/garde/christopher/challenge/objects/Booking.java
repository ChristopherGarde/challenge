package com.garde.christopher.challenge.objects;

import java.time.LocalDate;

public class Booking {
    private LocalDate day;
    private ClassDetails classDetails;
    private Member member;

    public Booking(LocalDate day, ClassDetails classDetails, Member member) {
        this.day = day;
        this.classDetails = classDetails;
        this.member = member;
    }

    public boolean isValid() {
        return day != null && member != null && member.isValid() && classDetails != null && classDetails.isValid();
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public ClassDetails getClassDetails() {
        return classDetails;
    }

    public void setClassDetails(ClassDetails classDetails) {
        this.classDetails = classDetails;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
