package ru.otus.example.hibernate.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.UuidGenerator;
import ru.otus.example.hibernate.enums.Currency;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false, unique = true, columnDefinition = "date default now()")
    private LocalDate created;

    @Column(name = "balance", nullable = false, columnDefinition = "double precision default 0")
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean closed;

    @ManyToOne
    @JoinColumn(name = "bank")
    private Bank bank;

}