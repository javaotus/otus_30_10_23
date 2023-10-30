package ru.otus.example.hibernate.utils;

import org.hibernate.SessionFactory;

import ru.otus.example.hibernate.enums.SessionFactoryType;

public interface HibernateUtil {
    SessionFactory buildSessionFactory(SessionFactoryType type);
}