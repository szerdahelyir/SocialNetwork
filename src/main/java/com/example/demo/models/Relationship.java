package com.example.demo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="relationships",uniqueConstraints={@UniqueConstraint(columnNames = {"user_one_id", "user_two_id"})})
public class Relationship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_one_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_two_id", nullable = false)
    private User user2;


    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "action_user_id", nullable = false)
    private User actionUser;

    public Relationship(User user, User user2, Integer status, User actionUser) {
        this.user = user;
        this.user2 = user2;
        this.status = status;
        this.actionUser = actionUser;
    }
}
