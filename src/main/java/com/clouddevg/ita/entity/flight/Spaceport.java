package com.clouddevg.ita.entity.flight;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(
        name = "spaceport",
        indexes = @Index(
                name = "idx_spaceport_code",
                columnList = "code",
                unique = true
        )
)
public class Spaceport {

    @Id
    @Column(name = "spaceport_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String name;

    private String detail;
}
