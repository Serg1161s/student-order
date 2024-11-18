package edu.student_orden.validator;

import edu.student_orden.domain.children.AnswerChildren;
import edu.student_orden.domain.wedding.StudentOrder;

public class ChildrenValidator {
     public AnswerChildren checkChildren (StudentOrder so){
        System.out.println("Children is running");
        return new AnswerChildren();
    }

}
