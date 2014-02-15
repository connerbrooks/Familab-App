package org.familab.app.models.Calendar;

import java.util.List;

/**
 * Created by conner on 9/28/13.
 */
public class Calendar {
    private String kind;
    private String etag;
    private String summary;
    private String description;
    private String updated;
    private String timeZone;
    private String accessRole;
    private List<Items> items;

    public String getKind() {
        return kind;
    }

    public String getEtag() {
        return etag;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    public String getUpdated() {
        return updated;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getAccessRole() {
        return accessRole;
    }

    public List<Items> getItems() {
        return items;
    }
}
