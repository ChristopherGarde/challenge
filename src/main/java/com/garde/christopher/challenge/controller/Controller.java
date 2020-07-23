package com.garde.christopher.challenge.controller;

import com.garde.christopher.challenge.error.HTTPBadRequestException;
import com.garde.christopher.challenge.error.HTTPConflictException;
import com.garde.christopher.challenge.objects.Booking;
import com.garde.christopher.challenge.objects.ClassDetails;
import com.garde.christopher.challenge.objects.Member;
import com.garde.christopher.challenge.objects.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    private static List<ClassDetails> classList = new ArrayList<>();
    private static List<Session> sessionList = new ArrayList<>();

    public Controller() {
    }

    //Debugging method for convenience
    @GetMapping(value = "/classes")
    public List<ClassDetails> getClassList() {
        LOGGER.info("GET to /classes");
        return classList;
    }

    @PostMapping(value = "/classes")
    public ResponseEntity<Void> createClass(@RequestBody ClassDetails classDetails) {
        LOGGER.info("POST to /classes");
        boolean isRequestBodyValid = classDetails.isValid();
        if (isRequestBodyValid) {
            boolean exists = false;
            for (ClassDetails cd : classList) {
                if (classDetails.isSameAs(cd)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                classList.add(classDetails);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                throw new HTTPConflictException("Class already exists");
            }
        } else {
            throw new HTTPBadRequestException("Request has incorrect class details");
        }
    }

    //Debugging method for convenience
    @GetMapping(value = "/bookings")
    public List<Session> getBookings() {
        LOGGER.info("GET to /bookings");
        return sessionList;
    }

    @PostMapping(value = "/bookings")
    public ResponseEntity<Void> createBooking(@RequestBody Booking booking) {
        LOGGER.info("POST to /bookings");
        ClassDetails classBeingBooked = booking.getClassDetails();
        Member memberForBooking = booking.getMember();
        if (!classBeingBooked.isDateInRange(booking.getDay())) {
            throw new HTTPBadRequestException("Day of booking is outside of class availability");
        }
        for (Session session : sessionList) {
            if (session.getClassDetails().isSameAs(classBeingBooked)) {
                if (!session.isMemberAttending(memberForBooking)) {
                    session.addAttendee(memberForBooking);
                    return new ResponseEntity<>(HttpStatus.CREATED);
                } else {
                    throw new HTTPConflictException("Member is already booked for this session");
                }
            }
        }
        sessionList.add(new Session(booking.getDay(), classBeingBooked, memberForBooking));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public static void reset() {
        classList = new ArrayList<>();
        sessionList = new ArrayList<>();
    }

}
