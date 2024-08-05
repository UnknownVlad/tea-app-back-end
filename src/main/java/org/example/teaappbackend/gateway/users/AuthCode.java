package org.example.teaappbackend.gateway.users;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.teaappbackend.gateway.BaseEntity;
@Entity
@Table(name = "auth_code")
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthCode extends BaseEntity {
    @Column(name = "code", nullable = false, unique = true)
    String code;

    @Column(name = "is_sent", nullable = false)
    Boolean isSent;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    User user;
}
