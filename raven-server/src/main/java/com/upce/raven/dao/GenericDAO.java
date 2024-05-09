package com.upce.raven.dao;

import java.util.*;

public interface GenericDAO<T, Long> {

    /** Persist the newInstance object into database */
    Long create(T newInstance);

    /**
     * Retrieve an object that was previously persisted to the database using
     * the indicated id as primary key
     */
    T read(Long id);
    List<T> read();

    /** Save changes made to a persistent object. */
    void update(T transientObject);

    /** Remove an object from persistent storage in the database */
    void delete(Long id) throws Exception;

}
