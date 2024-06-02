package com.techworld.notificationservice;

import brave.Tracer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import com.techworld.notificationservice.event.BookingCompletedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceApplication {


	private final Tracer tracer;

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

	@KafkaListener(topics = "notificationTopic")
	public void handleNotification(BookingCompletedEvent bookingCompletedEvent) {
		log.info("Got message <{}>", bookingCompletedEvent);

		log.info("TraceId- {}, Received Notification for Booking - {}",
				tracer.currentSpan().context().traceIdString(),
				bookingCompletedEvent.getBookingNumber());
		// send out an email notification
	}
}