package kr.co.xfilegolf.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
