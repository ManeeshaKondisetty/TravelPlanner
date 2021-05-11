package com.umkc.travelplanner.explore;

public class Activity {
    private String fullName;
    private String description;
    private String[] activities;
    private String directionsUri;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getActivities() {
        return activities;
    }

    public void setActivities(String[] activities) {
        this.activities = activities;
    }

    public String getDirectionsUri() {
        return directionsUri;
    }

    public void setDirectionsUri(String directionsUri) {
        this.directionsUri = directionsUri;
    }
}
