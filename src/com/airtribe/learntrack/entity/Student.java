package com.airtribe.learntrack.entity;

public class Student extends Person {
    private String batch;
    private boolean active;

    public Student() {
        this.active = true;
    }

    public Student(int id, String firstName, String lastName, String email, String batch, boolean active) {
        super(id, firstName, lastName, email);
        this.batch = batch;
        this.active = active;
    }

    public Student(int id, String firstName, String lastName, String email, String batch) {
        this(id, firstName, lastName, email, batch, true);
    }

    public Student(int id, String firstName, String lastName, String batch) {
        this(id, firstName, lastName, null, batch, true);
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String getDisplayName() {
        String safeBatch = batch == null || batch.isBlank() ? "-" : batch.trim();
        return super.getDisplayName() + " (Batch: " + safeBatch + ")";
    }

    @Override
    public String toString() {
        return "Student{"
            + "id=" + getId()
            + ", name='" + getDisplayName() + '\''
            + ", email='" + getEmail() + '\''
            + ", active=" + active
            + '}';
    }
}
