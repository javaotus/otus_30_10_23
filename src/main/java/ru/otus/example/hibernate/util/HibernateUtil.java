package ru.otus.example.hibernate.util;

import org.hibernate.SessionFactory;

import ru.otus.example.hibernate.enums.SessionFactoryType;

public interface HibernateUtil {
    SessionFactory buildSessionFactory(SessionFactoryType type);
}