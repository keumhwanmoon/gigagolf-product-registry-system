package kr.co.xfilegolf.product;

import kr.co.xfilegolf.sale.Sale;
import kr.co.xfilegolf.sale.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

/**
 * @author jason, Moon
 * @since 2016. 9. 29.
 */
@Controller
public class ProductController {

    private final ProductService productService;
    private final SaleService saleService;

    @Autowired
    public ProductController(ProductService productService, SaleService saleService) {
        this.productService = productService;
        this.saleService = saleService;
    }

    @GetMapping(value = "/product")
    public ModelAndView product() {

        ModelAndView mv = new ModelAndView("/product/product");

        List<Product> products = productService.findAll();

        mv.addObject("products", products);

        return mv;
    }

    @DeleteMapping(value = "/product")
    public String productDelete(@RequestParam(name = "id") Long id) throws Exception {

        List<Sale> sales = saleService.findByProductId(id);

        if (!sales.isEmpty()) {
            throw new Exception("등록된 판매/구매 내역이 존재하여 상품을 삭제할 수 없습니다.");
        } else {
            productService.remove(id);
        }

        return "/product/product";
    }

    @GetMapping(value = "/product-register")
    public ModelAndView productRegister(@RequestParam(name = "id", required = false, defaultValue = "0") Long id, ProductForm productForm) {

        ModelAndView mv = new ModelAndView("/product/product-register");

        if (0L != id) {

            Product product = productService.findOne(id);

            productForm.setId(product.getId());
            productForm.setCode(product.getCode());
            productForm.setName(product.getName());

            mv.addObject("productForm", productForm);
        }

        return mv;
    }

    @PostMapping(value = "/product-register")
    public String productRegister(@Valid ProductForm productForm, BindingResult result) {

        boolean isExists ;

        if (productForm.getId() != null) {
            isExists = productService.isExists(productForm.getId(), productForm.getCode());
        } else {
            isExists = productService.isExists(productForm.getCode());
        }

        if (isExists) {

            FieldError fieldError = new FieldError("productForm", "code", "이미 등록된 제품코드입니다. : " + productForm.getCode());

            result.addError(fieldError);
        }

        if (result.hasErrors()) {
            return "/product/product-register";
        }

        productService.save(productForm);

        return "redirect:/product";
    }
}
