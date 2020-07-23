package com.garde.christopher.challenge;

import com.garde.christopher.challenge.controller.Controller;
import com.garde.christopher.challenge.objects.Booking;
import com.garde.christopher.challenge.objects.ClassDetails;
import com.garde.christopher.challenge.objects.Member;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.is;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ControllerTests {
    @LocalServerPort
    private int port;

    private String uri;

    @AfterEach
    void cleanup() {
        Controller.reset();
    }

    @PostConstruct
    public void init() {
        uri = "http://localhost:" + port;
    }

    @Test
    void createClassSucceedsWith201() {
        String ENDPOINT = uri + "/classes";
        LocalDate startDate = LocalDate.of(2020, 7, 22);
        LocalDate endDate = LocalDate.of(2020, 7, 29);
        ClassDetails input = new ClassDetails("testName", startDate, endDate, 20);
        with()
                .contentType(ContentType.JSON)
                .body(input)
                .when()
                .post(ENDPOINT)
                .then()
                .statusCode(201);
    }

    @Test
    void createMultipleClassesSucceedsWith201() {
        String ENDPOINT = uri + "/classes";
        LocalDate startDate = LocalDate.of(2020, 7, 22);
        LocalDate endDate = LocalDate.of(2020, 7, 29);
        ClassDetails input = new ClassDetails("testName", startDate, endDate, 20);
        with()
                .contentType(ContentType.JSON)
                .body(input)
                .when()
                .post(ENDPOINT)
                .then()
                .statusCode(201);
        LocalDate startDate2 = LocalDate.of(2020, 7, 22);
        LocalDate endDate2 = LocalDate.of(2020, 7, 29);
        ClassDetails input2 = new ClassDetails("Other Class", startDate2, endDate2, 20);
        with()
                .contentType(ContentType.JSON)
                .body(input2)
                .when()
                .post(ENDPOINT)
                .then()
                .statusCode(201);
    }

    @Test
    void createDuplicateClassFailsWith409() {
        String ENDPOINT = uri + "/classes";
        LocalDate startDate = LocalDate.of(2020, 7, 22);
        LocalDate endDate = LocalDate.of(2020, 7, 29);
        ClassDetails input = new ClassDetails("testName", startDate, endDate, 20);
        with()
                .contentType(ContentType.JSON)
                .body(input)
                .when()
                .post(ENDPOINT)
                .then()
                .statusCode(201);
        with()
                .contentType(ContentType.JSON)
                .body(input)
                .when()
                .post(ENDPOINT)
                .then()
                .body("message", is("Class already exists"))
                .statusCode(409);
    }

    @Test
    void createClassFailsWhenDetailsAreIncompleteWith400() {
        String ENDPOINT = uri + "/classes";
        LocalDate startDate = LocalDate.of(2020, 7, 22);
        ClassDetails input = new ClassDetails("testName", startDate, null, 20);
        with()
                .contentType(ContentType.JSON)
                .body(input)
                .when()
                .post(ENDPOINT)
                .then()
                .statusCode(400);
    }

    @Test
    void createBookingSucceedsWith201() {
        String ENDPOINT = uri + "/bookings";

        LocalDate startDate = LocalDate.of(2020, 7, 22);
        LocalDate endDate = LocalDate.of(2020, 7, 29);
        ClassDetails classDetails = new ClassDetails("testName", startDate, endDate, 20);

        LocalDate bookingDay = LocalDate.of(2020, 7, 26);

        Member member = new Member("FirstName LastName", "name@email.com");

        Booking input = new Booking(bookingDay, classDetails, member);
        with()
                .contentType(ContentType.JSON)
                .body(input)
                .when()
                .post(ENDPOINT)
                .then()
                .statusCode(201);
    }

    @Test
    void createBookingSucceedsWith201WithMultipleMembers() {
        String ENDPOINT = uri + "/bookings";

        LocalDate startDate = LocalDate.of(2020, 7, 24);
        LocalDate endDate = LocalDate.of(2020, 7, 29);
        ClassDetails classDetails = new ClassDetails("testName", startDate, endDate, 20);

        LocalDate bookingDay = LocalDate.of(2020, 7, 26);

        Member member1 = new Member("FirstName LastName", "name@email.com");
        Member member2 = new Member("FirstName LastName2", "name2@email2.com");

        Booking input = new Booking(bookingDay, classDetails, member1);
        with()
                .contentType(ContentType.JSON)
                .body(input)
                .when()
                .post(ENDPOINT)
                .then()
                .statusCode(201);
        Booking input2 = new Booking(bookingDay, classDetails, member2);
        with()
                .contentType(ContentType.JSON)
                .body(input2)
                .when()
                .post(ENDPOINT)
                .then()
                .statusCode(201);
    }

    @Test
    void createBookingFailsWith409WhenAMemberTriesToDoubleBook() {
        String ENDPOINT = uri + "/bookings";

        LocalDate startDate = LocalDate.of(2020, 7, 22);
        LocalDate endDate = LocalDate.of(2020, 7, 29);
        ClassDetails classDetails = new ClassDetails("testName", startDate, endDate, 20);

        LocalDate bookingDay = LocalDate.of(2020, 7, 26);

        Member member = new Member("FirstName LastName", "name@email.com");

        Booking input = new Booking(bookingDay, classDetails, member);
        with()
                .contentType(ContentType.JSON)
                .body(input)
                .when()
                .post(ENDPOINT)
                .then()
                .statusCode(201);
        with()
                .contentType(ContentType.JSON)
                .body(input)
                .when()
                .post(ENDPOINT)
                .then()
                .body("message", is("Member is already booked for this session"))
                .statusCode(409);
    }

    @Test
    void createBookingFailsWith400WhenBookingDateIsNotInClassRange() {
        String ENDPOINT = uri + "/bookings";

        LocalDate startDate = LocalDate.of(2020, 7, 22);
        LocalDate endDate = LocalDate.of(2020, 7, 29);
        ClassDetails classDetails = new ClassDetails("testName", startDate, endDate, 20);

        LocalDate bookingDay = LocalDate.of(2020, 7, 30);

        Member member = new Member("FirstName LastName", "name@email.com");

        Booking input = new Booking(bookingDay, classDetails, member);
        with()
                .contentType(ContentType.JSON)
                .body(input)
                .when()
                .post(ENDPOINT)
                .then()
                .body("message", is("Day of booking is outside of class availability"))
                .statusCode(400);
    }

}
