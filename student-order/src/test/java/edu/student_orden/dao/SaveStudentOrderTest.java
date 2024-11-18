package edu.student_orden.dao;

import edu.student_orden.domain.wedding.*;
import edu.student_orden.exaption.DaoException;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SaveStudentOrderTest {
 @BeforeClass
    public static void startUp() throws SQLException, URISyntaxException, IOException {
       DBStartUp.startUp();

    }
    @Test
    public void saveStudentOrder() throws DaoException {
        StudentOrder so = buildStudentOrder(10);
        Long id = new StudentOrderDaoImpl().saveStudentOrder(so);

    }
    @Test(expected = DaoException.class)
    public void saveStudentOrderError() throws DaoException {
            StudentOrder so = buildStudentOrder(10);
            so.getHusband().setSurName(null);
            Long id = new StudentOrderDaoImpl().saveStudentOrder(so);
    }

    @Test
    public void getStudentOrder() throws DaoException {
      // List<StudentOrder> list = new StudentOrderDaoImpl().getStudentOrder();
    }
    public StudentOrder buildStudentOrder(long id){
        StudentOrder so = new StudentOrder();
        so.setStudentOrderId(id);
        so.setMarriageCertificateID("" + (123456000 + id));
        so.setMarriageDate(LocalDate.of(2016, 7,4));
        RegisterOffice ro1 = new RegisterOffice(1L,"","" );
        so.setMarriageOffice(ro1);

        Street street = new Street(1L , "Заневский пр." );

        Address address = new Address("19500", street, "12","","142");

        Adult husband = new Adult("Петров", "Виктор", "Сергеевич", LocalDate.of(1997,8,24));
        husband.setPassportSeria("" + (1000+id));
        husband.setPassportNumber("" + (10000 + id));
        husband.setIssueDate(LocalDate.of(2017,9,15));
        PassportOffice po1 = new PassportOffice(1L,"12","12" );
        husband.setIssueDepartment(po1);
        husband.setStudentId("" + (100000 + id));
        husband.setAddress(address);
        husband.setUniversity(new University(2l,""));
        husband.setStudentId("HHH1234");

        Adult wife = new Adult("Петрова", "Вероника", "Алексевна", LocalDate.of(1998,3,12));
        wife.setPassportSeria("" + (2000+id));
        wife.setPassportNumber("" + (20000 + id));
        wife.setIssueDate(LocalDate.of(2018,4,5));
        PassportOffice po2 = new PassportOffice(1L,"","" );
        wife.setIssueDepartment(po2);
        wife.setStudentId("" + (200000 + id));
        wife.setAddress(address);
        wife.setUniversity(new University(1L, ""));
        wife.setStudentId("WW12345");

        Child child1 = new Child("Петрова", "Ирина", "Викторовна",LocalDate.of(2018,6,29));
        child1.setCertificateNumber("" + (300000 + id));
        child1.setIssueDate(LocalDate.of(2018,7,19));
        RegisterOffice ro2 = new RegisterOffice(1L,"","" );
        child1.setIssueDepartment(ro2);
        child1.setAddress(address);
        Child child2 = new Child("Петрова", "Света", "Викторовна",LocalDate.of(2018,6,29));
        child2.setCertificateNumber("" + (300000 + id));
        child2.setIssueDate(LocalDate.of(2018,7,19));
        RegisterOffice ro3 = new RegisterOffice(1L,"","" );
        child2.setIssueDepartment(ro3);
        child2.setAddress(address);

        so.setHusband(husband);
        so.setWife(wife);
        so.addChild(child1);
        so.addChild(child2);
        //   printStudentOrder(so);
        return so;
    }
}