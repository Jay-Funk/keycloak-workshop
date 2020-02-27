package de.codecentric.demo.guestbook.domain;


import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

@Service
public class GuestbookMailService {

	private final GuestbookMailClient mailClient;

	public GuestbookMailService(GuestbookMailClient mailClient) {
		this.mailClient = mailClient;
	}

	@Async
	public Future<Boolean> sendMail(GuestbookEntry entry) {

		System.out.println("Sending Mail...");
		try {
			mailClient.sendMail(entry);
			System.out.println("Successfully sent Mail!");
			return new AsyncResult<>(true);
		} catch (final Exception e) {
			if (e.getLocalizedMessage().contains("403")) {
				System.out.println("ERROR sending mail: Status 403 - FORBIDDEN");
			} else {
				System.out.println("ERROR sending mail: " + e.getLocalizedMessage());
			}
			return new AsyncResult<>(false);
		}
	}
}
