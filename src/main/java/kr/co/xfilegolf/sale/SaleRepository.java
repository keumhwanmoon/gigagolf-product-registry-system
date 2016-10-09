package kr.co.xfilegolf.sale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jason, Moon
 * @since 2016-10-09
 */
@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
}
