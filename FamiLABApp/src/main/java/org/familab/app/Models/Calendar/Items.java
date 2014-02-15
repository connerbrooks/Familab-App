package org.familab.app.Models.Calendar;

import org.familab.app.Models.Calendar.End;
import org.familab.app.Models.Calendar.Start;

/**
 * Created by conner on 9/28/13.
 */
public class Items {
    private String kind;
    private String etag;
    private String id;
    private String htmlLink;
    private String created;
    private String updated;
    private String summary;
    private String description;
    private String location;
    private Start start;
    private End end;

    public String getKind() {
        return kind;
    }

    public String getEtag() {
        return etag;
    }

    public String getId() {
        return id;
    }

    public String getHtmlLink() {
        return htmlLink;
    }

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public Start getStart() {
        return start;
    }

    public End getEnd() {
        return end;
    }
}





