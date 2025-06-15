package org.airport.dao;

import org.hibernate.Session;
import org.hibernate.stat.Statistics;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class CustomRepoImplementation implements CustomRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void printStatistics() {
    	
            // Unwrap the EntityManager to a Hibernate Session
            Session session = entityManager.unwrap(Session.class);
            
            // Access the statistics
            Statistics statistics = session.getSessionFactory().getStatistics();

            // Print relevant statistics
            System.out.println("Entity Load Count: " + statistics.getEntityLoadCount());
            System.out.println("Entity Fetch Count: " + statistics.getEntityFetchCount());
            System.out.println("Entity Insert Count: " + statistics.getEntityInsertCount());
            System.out.println("Entity Update Count: " + statistics.getEntityUpdateCount());

    }
}
