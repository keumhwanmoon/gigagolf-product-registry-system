package kr.co.xfilegolf;

import kr.co.xfilegolf.sale.Sale;
import kr.co.xfilegolf.sale.SaleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

/**
 * @author jason, Moon
 * @since 2016-10-10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SaleFindTest {

    @Autowired
    SaleRepository saleRepository;

    @Test
    public void findTopTest() throws Exception {

        List<Sale> saleList = saleRepository.findTop10ByCreatedByOrderByCreatedOnDesc("admin");

        System.out.println("saleList = " + saleList);
    }

    @Test
    public void findCount() throws Exception {

        long count = saleRepository.count();

        System.out.println("count = " + count);
    }

    @Test
    public void findCountByUsername() throws Exception {

        long count = saleRepository.countByCreatedBy("admin");

        System.out.println("count = " + count);
    }

    @Test
    public void countBySaleOn() throws Exception {

        LocalDate today = LocalDate.now();
        LocalDate yearAgo = today.minusYears(1);

        long count = saleRepository.countBySalesOnBetween(yearAgo, today);

        System.out.println("count = " + count);
    }

    @Test
    public void countByUsernameAndSalesOnBetween() throws Exception {

        LocalDate today = LocalDate.now();
        LocalDate yearAgo = today.minusYears(1);

        long count = saleRepository.countByCreatedByAndSalesOnBetween("admin", yearAgo, today);

        System.out.println("count = " + count);
    }

    @Test
    public void countByPast() throws Exception {

        LocalDate today = LocalDate.now();

        long count = saleRepository.countBySalesOnLessThan(today);

        System.out.println("count = " + count);
    }
}
