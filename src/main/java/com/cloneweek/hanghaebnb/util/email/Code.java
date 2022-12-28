package com.cloneweek.hanghaebnb.util.email;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String randomCode;

    public Code(String randomCode, String email) {
        this.randomCode = randomCode;
        this.email = email;
    }
}
