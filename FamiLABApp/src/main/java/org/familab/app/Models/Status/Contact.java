package org.familab.app.Models.Status;

/**
 * Created by conner on 12/23/13.
 */
public class Contact {
    private String id;
    private String item_id;
    private String phone;
    private String email;
    private String name;
    private String handle;
    private String created_at;
    private String updated_at;

    public String getId() {
        return id;
    }

    public String getItem_id() {
        return item_id;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getHandle() {
        return handle;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
