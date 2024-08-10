package org.example.teaappbackend.gateway.repositories;

import jakarta.persistence.LockModeType;
import org.example.teaappbackend.gateway.users.AuthCode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthCodeRepository extends JpaRepository<AuthCode, Long> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT a FROM AuthCode a WHERE a.isSent = false ORDER BY a.savingTime DESC")
    List<AuthCode> findNotSentCodes(Pageable pageable);

    Optional<AuthCode> findByCode(String code);

    int countAllByIsSentFalse();

}
