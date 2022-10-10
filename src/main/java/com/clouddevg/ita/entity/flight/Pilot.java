package com.clouddevg.ita.entity.flight;

import com.clouddevg.ita.entity.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "pilot",
        indexes = @Index(
                name = "idx_pilot_code",
                columnList = "code",
                unique = true
        ))
public class Pilot {
    @Id
    @Column(name = "pilot_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String name;

    private String details;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_user_id")
    private User owner;

    @OneToMany(mappedBy = "pilot", cascade = CascadeType.ALL)
    private Set<Spacecraft> spacecrafts;
}
