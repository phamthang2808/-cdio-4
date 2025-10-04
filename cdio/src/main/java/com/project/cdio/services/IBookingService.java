package com.project.cdio.services;

import com.project.cdio.entities.BookingEntity;
import com.project.cdio.models.request.BookingInforRequest;
import com.project.cdio.models.responses.BookingResponse;

public interface IBookingService {

    BookingResponse createBooking(BookingInforRequest bookingInforRequest) throws Exception;

}
