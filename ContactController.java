import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/identify")
    public ResponseEntity<Contact> identifyContact(@RequestBody ContactPayload payload) {
        Contact processedContact = contactService.processContact(payload.getEmail(), payload.getPhoneNumber());
        return ResponseEntity.ok(processedContact);
    }
}
