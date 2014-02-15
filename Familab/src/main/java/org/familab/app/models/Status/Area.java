package org.familab.app.models.Status;

import java.util.List;

/**
 * Created by conner on 10/18/13.
 */
public class Area {
    private String created_at;
    private String desc;
    private String id;
    private String name;
    private String sequence;
    private String updated_at;
    private String photo_url;
    private List<Item> items;

    public String getCreated_at() {
        return created_at;
    }

    public String getDesc() {
        return desc;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSequence() {
        return sequence;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public List<Item> getItems() {
        return items;
    }
}
