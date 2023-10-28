package ru.otus.example.hibernate.entities;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ContactDetails {

    private String phone;

    private String address;

    private String siteUrl;

}