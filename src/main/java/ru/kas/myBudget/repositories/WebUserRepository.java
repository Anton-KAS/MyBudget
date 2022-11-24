package ru.kas.myBudget.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kas.myBudget.models.WebUser;

import java.util.Optional;

/**
 * @since 0.1
 * @author Anton Komrachkov
 */

@Repository
public interface WebUserRepository extends JpaRepository<WebUser, Integer> {
    Optional<WebUser> findByUsername (String username);
}
