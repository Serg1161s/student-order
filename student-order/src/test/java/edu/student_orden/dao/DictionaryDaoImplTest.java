package edu.student_orden.dao;

import edu.student_orden.domain.wedding.CountryArea;
import edu.student_orden.domain.wedding.PassportOffice;
import edu.student_orden.domain.wedding.RegisterOffice;
import edu.student_orden.domain.wedding.Street;
import edu.student_orden.exaption.DaoException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;



public class DictionaryDaoImplTest {
    private static final Logger logger= LoggerFactory.getLogger(DictionaryDaoImplTest.class);

  @BeforeClass
  public static void startUp() throws SQLException, URISyntaxException, IOException {
     DBStartUp.startUp();
  }
  @Before
  public void startTest() {
      System.out.println("________________________");
      System.out.println("Test started");
  }

  @Test
  public void testStreets() throws DaoException {
    logger.info("Test");
      System.out.println("Test Street started");
      List<Street> streets = new DictionaryDaoImpl().findStreet("пе");
      Assert.assertTrue(streets.size()==2);
      System.out.println("Test Street finished");
    }
    @Test
    public void testOffice() throws DaoException {

      logger.debug("ddd = {}" + 3);
        System.out.println("Office test started");
        List<RegisterOffice> registerOffices = new DictionaryDaoImpl().findRegisterOffice("020020010000");
       Assert.assertTrue(registerOffices.size()==1);
        List<PassportOffice> passportOffices = new DictionaryDaoImpl().findPassportOffice("010010000000");
        Assert.assertTrue(passportOffices.size() == 2);
        System.out.println("Office test finished");
    }
    @Test
//    @Ignore
    public void testArea() throws DaoException {
        System.out.println("Area test started");
        List<CountryArea> countryArea1 = new DictionaryDaoImpl().findArea("");
        Assert.assertTrue(countryArea1.size() == 2);

        List<CountryArea> countryArea2 = new DictionaryDaoImpl().findArea("020000000000");
        Assert.assertTrue(countryArea2.size() == 2);

        List<CountryArea> countryArea3 = new DictionaryDaoImpl().findArea("020010000000");
        Assert.assertTrue(countryArea3.size() == 2);

        List<CountryArea> countryArea4 = new DictionaryDaoImpl().findArea("020010010000");
        Assert.assertTrue(countryArea4.size()==2);
        System.out.println("Area test finished");
    }
  
}