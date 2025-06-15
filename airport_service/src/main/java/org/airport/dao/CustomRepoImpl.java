package org.airport.dao;

import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class CustomRepoImpl implements CustomRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void printStatistics() {
    	
            // Unwrap the EntityManager to a Hibernate Session
            Session session = entityManager.unwrap(Session.class);
            
            // Access the statistics
            Statistics statistics = session.getSessionFactory().getStatistics();

            // Print relevant statistics
            System.out.println("2 Entity Load Count: " + statistics.getEntityLoadCount());
            System.out.println("2 Entity Fetch Count: " + statistics.getEntityFetchCount());
            System.out.println("2 Entity Insert Count: " + statistics.getEntityInsertCount());
            System.out.println("2 Entity Update Count: " + statistics.getEntityUpdateCount());

    }
}