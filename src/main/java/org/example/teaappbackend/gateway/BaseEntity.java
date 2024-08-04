package org.example.teaappbackend.gateway;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@Entity
@SuperBuilder
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @EqualsAndHashCode.Exclude
    @Column(name = "last_update_date")
    OffsetDateTime lastUpdateDate;

    @EqualsAndHashCode.Exclude
    @Column(name = "saving_time")
    OffsetDateTime savingTime;

    @PrePersist
    public void onUpdate(){
        lastUpdateDate = OffsetDateTime.now();
        savingTime = savingTime == null ? OffsetDateTime.now() : savingTime;
    }
}
