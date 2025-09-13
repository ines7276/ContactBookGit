package contactBook;

import java.util.*;

public class ContactBook {
    static final int DEFAULT_SIZE = 100;

    private int counter;
    private Contact[] contacts;
    private int currentContact;
    private final Map<Integer, List<Contact>> contactsByNumber;

    public ContactBook() {
        counter = 0;
        contacts = new Contact[DEFAULT_SIZE];
        currentContact = -1;
        contactsByNumber = new HashMap<>();
    }

    //Pre: name != null
    public boolean hasContact(String name) {
        return searchIndex(name) >= 0;
    }

    public boolean hasContact(int phone) { return getName(phone) != null; }

    public int getNumberOfContacts() {
        return counter;
    }

    //Pre: name!= null && !hasContact(name)
    public void addContact(String name, int phone, String email) {
        if (counter == contacts.length)
            resize();

        Contact newContact = new Contact(name, phone, email);
        contacts[counter] = newContact;
        counter++;

        if(contactsByNumber.containsKey(phone))
            contactsByNumber.get(phone).add(newContact);
        else {
            List<Contact> list = new ArrayList<>();
            list.add(newContact);
            contactsByNumber.put(phone, list);
        }
    }

    //Pre: name != null && hasContact(name)
    public void deleteContact(String name) {
        int index = searchIndex(name);
        Contact oldContact = contacts[index]; //save old contact
        for(int i=index; i<counter; i++)
            contacts[i] = contacts[i+1];
        counter--;

        contactsByNumber.get(oldContact.getPhone()).remove(oldContact);
    }

    //Pre: name != null && hasContact(name)
    public int getPhone(String name) {
        return contacts[searchIndex(name)].getPhone();
    }

    //Pre: name != null && hasContact(name)
    public String getEmail(String name) {
        return contacts[searchIndex(name)].getEmail();
    }

    //Pre: name != null && hasContact(name)
    public void setPhone(String name, int phone) {
        contacts[searchIndex(name)].setPhone(phone);
    }

    //Pre: name != null && hasContact(name)
    public void setEmail(String name, String email) {
        contacts[searchIndex(name)].setEmail(email);
    }

    public String getName(int phone){
        int index = 0;
        while(index<counter && contacts[index].getPhone() != phone){
            index++;
        }
        if(index == counter){
            return null;
        }
        return contacts[index].getName();
    }

    private int searchIndex(String name) {
        int i = 0;
        int result = -1;
        boolean found = false;
        while (i<counter && !found)
            if (contacts[i].getName().equals(name))
                found = true;
            else
                i++;
        if (found) result = i;
        return result;
    }

    private void resize() {
        Contact tmp[] = new Contact[2*contacts.length];
        for (int i=0;i<counter; i++)
            tmp[i] = contacts[i];
        contacts = tmp;
    }

    public void initializeIterator() {
        currentContact = 0;
    }

    public boolean hasNext() {
        return (currentContact >= 0 ) && (currentContact < counter);
    }

    //Pre: hasNext()
    public Contact next() {
        return contacts[currentContact++];
    }

    public boolean severalPhones (){
        Map<Integer, Contact> allConctacts = new HashMap<>();
        Contact tmp;
        boolean repeated = false;
        int i = 0;
        while(i < counter && !repeated){
            tmp = contacts[i];
            if(allConctacts.put(tmp.getPhone(), tmp) != null) {
                repeated = true;
            }
            i++;
        }
        return repeated;
    }
}
