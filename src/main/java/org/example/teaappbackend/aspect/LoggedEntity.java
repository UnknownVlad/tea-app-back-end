package org.example.teaappbackend.aspect;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.teaappbackend.aspect.converters.ArrayJsonbConverter;
import org.example.teaappbackend.aspect.converters.SingleJsonbConverter;
import org.example.teaappbackend.gateway.BaseEntity;

import java.util.Collection;
import java.util.Map;

@Entity
@Table(name = "logged")
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoggedEntity extends BaseEntity {

    @Column(name = "method_name", nullable = false)
    String methodName;

    //todo: нужен jsonb
    @Convert(converter = SingleJsonbConverter.class)
    @Column(name = "potential_key", nullable = false, length = 1024)
    Map<String, Object> potentialKey;

    //todo: нужен jsonb
    @Convert(converter = ArrayJsonbConverter.class)
    @Column(name = "input_args", nullable = false, length = 1024)
    Collection<Map<String, Object>> inputArgs;

    //todo: нужен jsonb
    @Convert(converter = ArrayJsonbConverter.class)
    @Column(name = "output_args", nullable = false, length = 1024)
    Collection<Map<String, Object>> outputArgs;


}
