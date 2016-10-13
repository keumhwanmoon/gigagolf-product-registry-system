package kr.co.xfilegolf.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author jason, Moon
 * @since 2016-10-01
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLoginId(String loginId);
}
