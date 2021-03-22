package com.example.demo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "picByte", length = 1000)
    private byte[] picByte;

    @OneToOne(mappedBy = "profilePicture", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private User user;

    public Image(String type, byte[] picByte) {
        this.type = type;
        this.picByte = picByte;
    }
}
