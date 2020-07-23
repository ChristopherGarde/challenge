package com.garde.christopher.challenge.objects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Session {
    private LocalDate bookingDate;
    private ClassDetails classDetails;
    private List<Member> attendees = new ArrayList<>();

    public Session(LocalDate bookingDate, ClassDetails classDetails) {
        this.bookingDate = bookingDate;
        this.classDetails = classDetails;
    }

    public Session(LocalDate bookingDate, ClassDetails classDetails, Member member) {
        this.bookingDate = bookingDate;
        this.classDetails = classDetails;
        addAttendee(member);
    }

    public ClassDetails getClassDetails() {
        return classDetails;
    }

    public void setClassDetails(ClassDetails classDetails) {
        this.classDetails = classDetails;
    }

    public List<Member> getAttendees() {
        return attendees;
    }

    public boolean isMemberAttending(Member member) {
        boolean alreadyAttending = false;
        for (Member m : attendees) {
            if (m.getEmail().equals(member.getEmail())) {
                alreadyAttending = true;
                break;
            }
        }
        return alreadyAttending;
    }

    public void addAttendee(Member member) {
        this.attendees.add(member);
    }

    public void setAttendees(List<Member> attendees) {
        this.attendees = attendees;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }
}
