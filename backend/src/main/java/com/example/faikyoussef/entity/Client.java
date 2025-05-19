package com.example.faikyoussef.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @Email
    @NotNull
    private String email;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Credit> credits;
      @OneToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;
}
