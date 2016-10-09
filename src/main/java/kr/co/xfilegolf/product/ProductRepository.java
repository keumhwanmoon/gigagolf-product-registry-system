package kr.co.xfilegolf.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jason, Moon
 * @since 2016-10-07
 */
@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {
}
