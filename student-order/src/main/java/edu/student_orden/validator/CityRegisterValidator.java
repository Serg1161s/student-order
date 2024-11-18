package edu.student_orden.validator;

import edu.student_orden.exaption.TransportException;
import edu.student_orden.domain.register.AnswerCityRegister;
import edu.student_orden.domain.register.AnswerCityRegisterItem;
import edu.student_orden.validator.register.CityRegisterChecker;
import edu.student_orden.validator.register.FakeCityRegisterChecker;
import edu.student_orden.domain.wedding.Child;
import edu.student_orden.domain.register.CityRegisterResponse;
import edu.student_orden.domain.wedding.Person;
import edu.student_orden.domain.wedding.StudentOrder;
import edu.student_orden.exaption.CityRegisterException;

public class CityRegisterValidator {
    public static final String IN_CODE = "NO_NGR";

   private CityRegisterChecker personChecker;

    public CityRegisterValidator() {
        personChecker = new FakeCityRegisterChecker();
    }

    public AnswerCityRegister checkCityRegister (StudentOrder so) {
        AnswerCityRegister ans = new AnswerCityRegister();
        ans.addItem(checkPerson(so.getHusband()));
        ans.addItem(checkPerson(so.getWife()));
        for (Child child: so.getChildren()){
            ans.addItem(checkPerson(child));
        }
        return ans;
    }
    
    private AnswerCityRegisterItem checkPerson (Person person){
       AnswerCityRegisterItem.CityError error = null;
       AnswerCityRegisterItem.CityStatus status  = null;
                try {
                  CityRegisterResponse tmp =   personChecker.checkPerson(person);
                    status = tmp.isExisting() ?
                            AnswerCityRegisterItem.CityStatus.YES:
                            AnswerCityRegisterItem.CityStatus.NO;
                } catch (CityRegisterException ex){
                    ex.printStackTrace(System.out);
                    status = AnswerCityRegisterItem.CityStatus.ERROR;
                    error = new AnswerCityRegisterItem.CityError(ex.getCode(), ex.getMessage());
                }catch (TransportException ex){
                    ex.printStackTrace(System.out);
                    status = AnswerCityRegisterItem.CityStatus.ERROR;
                    error = new AnswerCityRegisterItem.CityError(IN_CODE, ex.getMessage() );
                }catch (Exception ex){
                    ex.printStackTrace(System.out);
                    status = AnswerCityRegisterItem.CityStatus.ERROR;
                    error = new AnswerCityRegisterItem.CityError(IN_CODE, ex.getMessage() );
                }

       AnswerCityRegisterItem ans = new AnswerCityRegisterItem(error,person,status);
                return ans;
    }

}
