import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Contact processContact(String email, String phoneNumber) {
        Optional<Contact> existingContact = contactRepository.findByEmailOrPhoneNumber(email, phoneNumber);

        if (existingContact.isPresent()) {
            Contact primaryContact = existingContact.get();

            // If the existing contact has different information, create a secondary contact
            if (!email.equals(primaryContact.getEmail()) || !phoneNumber.equals(primaryContact.getPhoneNumber())) {
                Contact secondaryContact = new Contact();
                secondaryContact.setEmail(email);
                secondaryContact.setPhoneNumber(phoneNumber);
                secondaryContact.setLinkPrecedence("secondary");
                secondaryContact.setLinkedId(primaryContact.getId());

                return contactRepository.save(secondaryContact);
            }

            // Return existing primary contact details
            return primaryContact;
        } else {
            // Create a new primary contact
            Contact newContact = new Contact();
            newContact.setEmail(email);
            newContact.setPhoneNumber(phoneNumber);
            newContact.setLinkPrecedence("primary");

            return contactRepository.save(newContact);
        }
    }
}
