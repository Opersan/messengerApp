package com.kiraz.messengerapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "accounts", uniqueConstraints = {@UniqueConstraint(name = "uniqueProvider",
        columnNames = { "provider", "providerAccountId" }) })
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column
    private String type;

    @Column
    private String provider;

    @Column
    private String providerAccountId;

    @Column
    private String refresh_token;

    @Column
    private String access_token;

    @Column
    private String scope;

    @Column
    private int expires_at;

    @Column(columnDefinition = "TEXT")
    private String id_token;

    @Column
    private String session_state;

    @OneToOne(mappedBy = "account")
    @JsonBackReference
    private User user;
}
