package com.haulmont.testtask.model.dao;


import com.haulmont.testtask.model.dao.exception.DAOCriticalException;
import com.haulmont.testtask.model.dao.exception.DAOException;
import com.haulmont.testtask.model.entity.Entity;
import com.haulmont.testtask.model.entity.Group;
import com.haulmont.testtask.model.entity.Student;

import java.util.List;

/**
 * Created by ovchinnikov on 14.06.2016.
 */
public class HandlerDAO {

    private static final GroupDAO groupDAO = new GroupDAO();
    private static final StudentDAO studentDAO = new StudentDAO();

    public static DAO getDAO(Class clazz) throws DAOCriticalException {
        return check(clazz);
    }

    public static StudentDAO getStudentDAO(){
        return studentDAO;
    }

    public static List select(List list, Class clazz) throws DAOCriticalException, DAOException {
        return check(clazz).select(list);
    }

    public static int delete(List entities, Class clazz) throws DAOCriticalException, DAOException {
        return check(clazz).delete(entities);
    }

    public static boolean update(Entity entity) throws DAOCriticalException, DAOException {
        return check(entity.getClass()).update(entity);
    }

    public static Long insert(Entity entity) throws DAOCriticalException, DAOException {
        return check(entity.getClass()).insert(entity);
    }

    private static DAO check(Class clazz) throws DAOCriticalException {
        if (clazz.equals(Student.class)) {
            return studentDAO;
        } else if (clazz.equals(Group.class)) {
            return groupDAO;
        } else {
            //return null;//throw
            throw new DAOCriticalException("Unkonw DAO");
        }
    }

}
