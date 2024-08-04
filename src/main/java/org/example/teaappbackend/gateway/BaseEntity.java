package org.example.teaappbackend.gateway;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Data
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @EqualsAndHashCode.Exclude
    @Column(name = "last_update_date", nullable = false)
    OffsetDateTime lastUpdateDate;

    @EqualsAndHashCode.Exclude
    @Column(name = "saving_time", nullable = false)
    OffsetDateTime savingTime;

    @PrePersist
    public void onUpdate(){
        lastUpdateDate = OffsetDateTime.now();
        savingTime = savingTime == null ? OffsetDateTime.now() : savingTime;
    }
}
