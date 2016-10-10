package kr.co.xfilegolf.sale;

import org.codehaus.groovy.runtime.dgmimpl.arrays.IntegerArrayGetAtMetaMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author jason, Moon
 * @since 2016-10-09
 */
@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findByCreatedBy(String username);

    List<Sale> findTop10ByCreatedByOrderByCreatedOnDesc(String admin);

    List<Sale> findTop10ByOrderByCreatedOnDesc();

    long countByCreatedBy(String username);

    long countBySalesOnBetween(LocalDate yearAgo, LocalDate today);

    long countByCreatedByAndSalesOnBetween(String username, LocalDate yearAgo, LocalDate today);

    long countBySalesOnLessThan(LocalDate today);

    long countByCreatedByAndSalesOnLessThan(String username, LocalDate today);
}
