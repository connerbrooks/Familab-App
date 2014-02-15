package org.familab.app.models.Status;

/**
 * Created by conner on 10/18/13.
 */
public class Ticket {
    private String body;
    private String created_at;
    private String fuid;
    private String id;
    private String status;
    private String ticket_type;
    private String updated_at;
    private String user_id;

    public String getBody() {
        return body;
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

    public String getStatus() {
        return status;
    }

    public String getTicket_type() {
        return ticket_type;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getUser_id() {
        return user_id;
    }
}
