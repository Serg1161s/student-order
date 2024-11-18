package edu.student_orden.dao;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SimpleRunner {
    public static void main(String[] args) {
        SimpleRunner simpleRunner = new SimpleRunner();
        simpleRunner.startRunner();
    }

    private void startRunner() {
        try {
            Class cl = Class.forName("edu.student_orden.dao.DictionaryDaoImplTest");
            Constructor con = cl.getConstructor();
            Object entity = con.newInstance();
            Method[] methods = cl.getMethods();
            for (Method method: methods){
                Test ann = method.getAnnotation(Test.class);
                if (ann != null){
                    method.invoke(entity);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
