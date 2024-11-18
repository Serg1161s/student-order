package edu.student_orden.dao;

import edu.student_orden.domain.wedding.CountryArea;
import edu.student_orden.domain.wedding.PassportOffice;
import edu.student_orden.domain.wedding.RegisterOffice;
import edu.student_orden.domain.wedding.Street;
import edu.student_orden.exaption.DaoException;

import java.util.List;

public interface DictionaryDao {
   List<Street> findStreet(String areaId)throws DaoException;
   List<PassportOffice> findPassportOffice(String areaID)throws DaoException;
   List<RegisterOffice> findRegisterOffice(String areaID)throws DaoException;
   List<CountryArea> findArea(String areaID)throws DaoException;
}
