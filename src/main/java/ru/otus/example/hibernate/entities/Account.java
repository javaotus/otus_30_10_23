package ru.otus.example.hibernate.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false, columnDefinition = "date default now()")
    private LocalDate created;

    @Column(nullable = false, columnDefinition = "double precision default 0")
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "currency")
    private Currency currency;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean closed;

    @Fetch(FetchMode.JOIN)
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name="account_bank_customer", joinColumns=@JoinColumn(name="account"), inverseJoinColumns=@JoinColumn(name="bank"))
    private Set<Bank> banks;

    @Fetch(FetchMode.JOIN)
    @JoinTable(name="account_bank_customer", joinColumns=@JoinColumn(name="account"), inverseJoinColumns=@JoinColumn(name="customer"))
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Set<Customer> customers;

}