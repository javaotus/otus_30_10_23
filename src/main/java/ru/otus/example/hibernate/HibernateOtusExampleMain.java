package ru.otus.example.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import ru.otus.example.hibernate.enums.SessionFactoryType;
import ru.otus.example.hibernate.utils.HibernateUtilImpl;

import java.lang.invoke.MethodHandles;

public class HibernateOtusExampleMain {

	private static final Log LOGGER = LogFactory.getLog(MethodHandles.lookup().lookupClass());

	public static void main(String[] args) {

		SessionFactory sessionFactory = new HibernateUtilImpl()
				.buildSessionFactory(SessionFactoryType.JAVA_BASED);
		sessionFactory.close();

	}

}