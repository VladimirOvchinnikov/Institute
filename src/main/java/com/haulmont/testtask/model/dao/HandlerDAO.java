package com.haulmont.testtask.model.dao;


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

    public static DAO getDAO(Class clazz) throws DAOException {
        return check(clazz);
    }

    public static List select(List list, Class clazz) throws DAOException {
        return check(clazz).select(list);
    }

    public static int delete(List entities, Class clazz) throws DAOException {
        return check(clazz).delete(entities);
    }

    public static boolean update(Entity entity) throws DAOException {
        return check(entity.getClass()).update(entity);
    }

    public static Long insert(Entity entity) throws DAOException {
        return check(entity.getClass()).insert(entity);
    }

    private static DAO check(Class clazz) throws DAOException {
        if (clazz.equals(Student.class)) {
            return studentDAO;
        } else if (clazz.equals(Group.class)) {
            return groupDAO;
        } else {
            throw new DAOException("Unknown DAO " + clazz.getName());
        }
    }

}
