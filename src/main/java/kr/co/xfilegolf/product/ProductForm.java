package kr.co.xfilegolf.product;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author jason, Moon
 * @since 2016-10-07
 */
@Data
public class ProductForm {

    private Long id;

    @NotNull
    @Size(min = 3, max = 15)
    private String code = "NONE"; // 기본값 설정..

    @NotNull
    @Size(min = 3, max = 15)
    private String name;
}
