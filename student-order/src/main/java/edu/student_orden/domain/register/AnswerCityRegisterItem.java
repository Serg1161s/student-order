package edu.student_orden.domain.register;

import edu.student_orden.domain.wedding.Person;

public class AnswerCityRegisterItem
{
    public  enum CityStatus{
        YES, NO, ERROR;
    }

    public static class CityError{
        private String code;
        private String text;

        public CityError(String code, String text) {
            this.code = code;
            this.text = text;
        }

        public String getCode() {
            return code;
        }

        public String getText() {
            return text;
        }
    }
    private CityError error;
    private CityStatus status;
    private Person person;

    public CityError getError() {
        return error;
    }

    public CityStatus getStatus() {
        return status;
    }

    public Person getPerson() {
        return person;
    }

    public AnswerCityRegisterItem(CityError error, Person person, CityStatus status) {
        this.error = error;
        this.status = status;
        this.person = person;
    }

    public AnswerCityRegisterItem(CityError error, CityStatus status) {
        this.error = error;
        this.status = status;
    }
}
