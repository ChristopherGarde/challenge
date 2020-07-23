package com.garde.christopher.challenge.objects;

import java.time.LocalDate;

public class ClassDetails {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private int maxCapacity = 0;

    public ClassDetails(String name, LocalDate startDate, LocalDate endDate, int maxCapacity) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxCapacity = maxCapacity;
    }

    public boolean isDateInRange(LocalDate date) {
        return date.isAfter(getStartDate().minusDays(1)) && date.isBefore(getEndDate().plusDays(1));
    }

    public boolean isValid() {
        return getName() != null && getStartDate() != null && getEndDate() != null && getMaxCapacity() != 0 && getStartDate().isBefore(getEndDate().plusDays(1));
    }

    public boolean isSameAs(ClassDetails other) {
        return other.getMaxCapacity() == getMaxCapacity() && other.getStartDate().equals(getStartDate()) && other.getEndDate().equals(getEndDate()) && other.getName().equals(getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}
