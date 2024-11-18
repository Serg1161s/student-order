package edu.student_orden.validator.register;

import edu.student_orden.exaption.TransportException;
import edu.student_orden.domain.register.CityRegisterResponse;
import edu.student_orden.domain.wedding.Person;
import edu.student_orden.exaption.CityRegisterException;

public class RealCityRegisterChecker implements CityRegisterChecker {
    public CityRegisterResponse checkPerson(Person person) throws CityRegisterException, TransportException {
        return null;
    }
}
