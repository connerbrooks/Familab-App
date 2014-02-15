package org.familab.app.Models.Status;

/**
 * Created by conner on 12/23/13.
 */
public class Caveat {
    private String id;
    private String item_id;
    private String user_id;
    private String body;
    private String created_at;
    private String updated_at;

    public String getId() {
        return id;
    }

    public String getItem_id() {
        return item_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getBody() {
        return body;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
