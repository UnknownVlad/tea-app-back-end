package org.example.teaappbackend.aspect;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoggedRepository extends JpaRepository<LoggedEntity, Long> {
}
