package com.project.cdio.controllers;

import com.project.cdio.entities.BookingEntity;
import com.project.cdio.exceptions.DataNotFoundException;
import com.project.cdio.models.request.BookingInforRequest;
import com.project.cdio.models.responses.BookingResponse;
import com.project.cdio.services.impl.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/{id}")
    public ResponseEntity<BookingResponse> createBooking(
             @PathVariable  Long id,
             @RequestBody BookingInforRequest bookingInforRequest
            ){
        bookingInforRequest.setRoomId(id);
        BookingResponse bookingResponse = new BookingResponse();

        try {
             bookingResponse = bookingService.createBooking(bookingInforRequest);
            bookingResponse.setMessage("create booking successfully");
            return ResponseEntity.ok(bookingResponse);
        } catch (DataNotFoundException e) {
            bookingResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(bookingResponse);
        }
    }

}
