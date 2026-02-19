package com.airtribe.learntrack.entity;

public class Trainer extends Person {
    private String expertiseArea;

    public Trainer() {
    }

    public Trainer(int id, String firstName, String lastName, String email, String expertiseArea) {
        super(id, firstName, lastName, email);
        this.expertiseArea = expertiseArea;
    }

    public String getExpertiseArea() {
        return expertiseArea;
    }

    public void setExpertiseArea(String expertiseArea) {
        this.expertiseArea = expertiseArea;
    }

    @Override
    public String getDisplayName() {
        String safeExpertise = expertiseArea == null || expertiseArea.isBlank() ? "General" : expertiseArea.trim();
        return super.getDisplayName() + " (Trainer - " + safeExpertise + ")";
    }
}
