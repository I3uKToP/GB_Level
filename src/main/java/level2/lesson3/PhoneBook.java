package level2.lesson3;

import java.util.ArrayList;
import java.util.List;

public class PhoneBook {
    List<People> contacts = new ArrayList<>();

    public void add(String firsName, String lastName, String phone) {
        contacts.add(new People(firsName, lastName, phone));
    }

    public void get (String lastName) {
        for (People contact : contacts) {
            if(contact.getLastName().equals(lastName)) System.out.println(contact.toString());
        }
    }
}
