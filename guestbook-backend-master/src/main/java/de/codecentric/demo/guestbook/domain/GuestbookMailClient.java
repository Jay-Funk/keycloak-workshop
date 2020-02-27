package de.codecentric.demo.guestbook.domain;

public interface GuestbookMailClient {

	Boolean sendMail(GuestbookEntry entry);
}