package edu.student_orden.domain.wedding;

public class CountryArea
{
    private String areaID;
    private String areaName;

    public CountryArea(String areaID, String areaName) {
        this.areaID = areaID;
        this.areaName = areaName;
    }

    public CountryArea() {
    }

    public String getAreaID() {
        return areaID;
    }

    public void setAreaID(String areaID) {
        this.areaID = areaID;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
