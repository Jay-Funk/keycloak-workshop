package de.codecentric.demo.guestbook.api;

import java.security.Principal;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.codecentric.demo.guestbook.domain.GuestbookEntry;
import de.codecentric.demo.guestbook.domain.GuestbookMailService;
import de.codecentric.demo.guestbook.domain.GuestbookRepository;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.PUT})
public class GuestbookController {

	private final GuestbookRepository repository;
	
	private final GuestbookMailService mailService;

    public GuestbookController(GuestbookRepository gbRepo, GuestbookMailService mailService) {
        this.repository = gbRepo;
        this.mailService = mailService;
    }

    @GetMapping(value="/guestbook",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getEntries(Principal p) {
		List<GuestbookEntry> entries = repository.findAllOrderedByIdDesc();
		
		return ResponseEntity.ok(entries);
	}
	
	@PostMapping(value="/guestbook",
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestBody GuestbookEntry entry) throws InterruptedException, ExecutionException {
		entry = repository.save(entry);
        Future<Boolean> sendSuccessful = mailService.sendMail(entry);
        
        System.out.println("Mail send successfully? >" + sendSuccessful + "<");
		while (!sendSuccessful.isDone() && !sendSuccessful.isCancelled()) {
		}
		if (sendSuccessful.isDone() && sendSuccessful.get().equals(true)) {
			return ResponseEntity.ok(entry);
		}
		return new ResponseEntity(HttpStatus.FORBIDDEN);

	}
}
