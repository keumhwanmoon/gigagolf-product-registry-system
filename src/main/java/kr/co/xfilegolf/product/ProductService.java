package kr.co.xfilegolf.product;

import kr.co.xfilegolf.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author jason, Moon
 * @since 2016-10-07
 */
@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {

        return productRepository.findAll();
    }

    public void save(ProductForm productForm) {

        Product product = null;

        if (null == productForm.getId()) {
            product = new Product();
        } else {

            product = productRepository.findOne(productForm.getId());

            product.setLastModifiedOn(LocalDateTime.now());

            product.setLastModifiedBy(SecurityUtils.currentUserName());
        }

        mapProduct(productForm, product);

        productRepository.save(product);
    }

    public void remove(Long id) {
        productRepository.delete(id);
    }

    public Product findOne(Long id) {
        return productRepository.findOne(id);
    }

    private void mapProduct(ProductForm productForm, Product product) {

        product.setCode(productForm.getCode());
        product.setName(productForm.getName());
    }

    public boolean isExists(String code) {

        if ("NONE".equals(code)) { // 화면에서 코드없을을 체크 했을때는 "NONE" 값이 전달 되는데, 이때 중복 체크는 하지 않는다.
            return false;
        } else {
            return productRepository.findByCode(code).isPresent();
        }
    }
}
