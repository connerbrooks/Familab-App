package org.familab.app.Models.Status;

/**
 * Created by conner on 10/18/13.
 */
public class UniqueItem {
    private String area_id;
    private String created_at;
    private String fuid;
    private String id;
    private String loggable;
    private String name;
    private boolean ticketable;
    private String updated_at;
    private String photo_url;

    public String getArea_id() {
        return area_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getFuid() {
        return fuid;
    }

    public String getId() {
        return id;
    }

    public String getLoggable() {
        return loggable;
    }

    public String getName() {
        return name;
    }

    public boolean isTicketable() {
        return ticketable;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getPhoto_url() {
        return photo_url;
    }
}
