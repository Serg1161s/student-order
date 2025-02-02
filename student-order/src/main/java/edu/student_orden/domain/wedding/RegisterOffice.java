package edu.student_orden.domain.wedding;

public class RegisterOffice {
    private Long officeId;
    private String officeAreaId;
    private String officeName;

    public RegisterOffice() {
    }

    public RegisterOffice(Long officeId, String officeAreaId, String officeName) {
        this.officeId = officeId;
        this.officeAreaId = officeAreaId;
        this.officeName = officeName;
    }

    public RegisterOffice(Long officeId, String officeAreaId) {
        this.officeId = officeId;
        this.officeAreaId = officeAreaId;
    }


    public Long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }

    public String getOfficeAreaId() {
        return officeAreaId;
    }

    public void setOfficeAreaId(String officeAreaId) {
        this.officeAreaId = officeAreaId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }
}
