package org.familab.app.Models.Status;

import java.util.List;

/**
 * Created by conner on 12/23/13.
 */
public class Item {
    private String id;
    private boolean loggable;
    private boolean ticketable;
    private String name;
    private String create_at;
    private String updated_at;
    private String photo_file_name;
    private String photo_content_type;
    private String photo_file_type;
    private String photo_updated_at;
    private String fuid;
    private String area_id;
    private List<Ticket> tickets;
    private List<Caveat> caveats;
    private List<Contact> contacts;

    public String getId() {
        return id;
    }

    public boolean isLoggable() {
        return loggable;
    }

    public boolean isTicketable() {
        return ticketable;
    }

    public String getName() {
        return name;
    }

    public String getCreate_at() {
        return create_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getPhoto_file_name() {
        return photo_file_name;
    }

    public String getPhoto_content_type() {
        return photo_content_type;
    }

    public String getPhoto_file_type() {
        return photo_file_type;
    }

    public String getPhoto_updated_at() {
        return photo_updated_at;
    }

    public String getFuid() {
        return fuid;
    }

    public String getArea_id() {
        return area_id;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public List<Caveat> getCaveats() {
        return caveats;
    }

    public List<Contact> getContacts() {
        return contacts;
    }
}
