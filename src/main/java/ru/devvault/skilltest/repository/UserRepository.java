package ru.devvault.skilltest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.devvault.skilltest.entity.User;

/**
 * Интерфейс репозитория пользователя
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    User findByEmail(String email);
    User findByUsername(String username);

    @Modifying
    @Query("update User u set u.isActive = ?1 where u.id = ?2") // Query to set user active flag
    void setActive(Boolean active, Long id);
}
