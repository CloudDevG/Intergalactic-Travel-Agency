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
        name = "spacecraft",
        indexes = @Index(
                name = "idx_spacecraft_code",
                columnList = "code",
                unique = true
        )
)
public class Spacecraft {
    @Id
    @Column(name = "spacecraft_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private int capacity;

    private String make;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pilot_id")
    private Pilot pilot;
}
