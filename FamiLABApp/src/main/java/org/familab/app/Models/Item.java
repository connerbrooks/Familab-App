package org.familab.app.Models;

import java.util.List;

/**
 * Created by conner on 12/23/13.
 */
public class Item {
    public String id;
    public boolean loggable;
    public boolean ticketable;
    public String name;
    public String create_at;
    public String updated_at;
    public String photo_file_name;
    public String photo_content_type;
    public String photo_file_type;
    public String photo_updated_at;
    public String fuid;
    public String area_id;
    public List<Ticket> tickets;
    public List<Caveat> caveats;
    public List<Contact> contacts;
}
