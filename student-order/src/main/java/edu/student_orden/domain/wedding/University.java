package edu.student_orden.domain.wedding;

public class University {
    private Long universityId;
    private String universityName;

    public University() {

    }

    public University(Long id, String name) {
        this.universityId = id;
        this.universityName = name;
    }

    public Long getUniversityId() {
        return universityId;
    }

    public void setUniversityId(Long universityId) {
        this.universityId = universityId;
    }

    public String getName() {
        return universityName;
    }

    public void setName(String name) {
        this.universityName = name;
    }
}
