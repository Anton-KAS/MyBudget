package komrachkov.anton.mybudget.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import komrachkov.anton.mybudget.models.TelegramUser;

import java.util.List;

/**
 * @author Anton Komrachkov
 * @since 0.1
 */

@Repository
public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {
    List<TelegramUser> findAllByActiveTrue();
}
