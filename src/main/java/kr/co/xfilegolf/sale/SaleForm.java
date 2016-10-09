package kr.co.xfilegolf.sale;

import kr.co.xfilegolf.product.Product;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author jason, Moon
 * @since 2016-10-09
 */
@Data
public class SaleForm {

    private Long id;

    @NotNull(message = "제품코드는 반드시 선택해야 합니다.")
    private String productCode;

    @NotNull
    @Size(min = 1, message = "시리얼번호는 반드시 입력해야 합니다.")
    private String serialNumber;

    @NotNull
    @Size(min = 1, message = "판매일자는 반드시 입력해야 합니다.")
    private String salesOn;

    private List<Product> products;
}
