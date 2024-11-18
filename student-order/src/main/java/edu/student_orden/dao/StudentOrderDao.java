package edu.student_orden.dao;

import edu.student_orden.domain.wedding.StudentOrder;
import edu.student_orden.exaption.DaoException;

import java.util.List;

public interface StudentOrderDao
{
    Long saveStudentOrder (StudentOrder so) throws DaoException;
    List <StudentOrder> getStudentOrder () throws DaoException;
}
