package de.codecentric.demo.guestbook.domain;

public interface GuestbookMailClient {

	boolean sendMail(GuestbookEntry entry);
}
