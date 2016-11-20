package kr.co.xfilegolf.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author jason, Moon
 * @since 2016-10-07
 */
@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByCode(String code);

    Optional<Product> findByIdNotAndCode(Long id, String code);
}
