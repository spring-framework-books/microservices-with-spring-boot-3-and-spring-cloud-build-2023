package se.magnus.microservices.core.product.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import se.magnus.api.core.product.Product;
import se.magnus.api.core.product.ProductService;
import se.magnus.api.exceptions.InvalidInputException;
import se.magnus.api.exceptions.NotFoundException;
import se.magnus.util.http.ServiceUtil;

@RestController
public class ProductServiceImpl implements ProductService {

  private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

  private final ServiceUtil serviceUtil;

  @Autowired
  public ProductServiceImpl(ServiceUtil serviceUtil) {
    this.serviceUtil = serviceUtil;
  }

  @Override
  public Product getProduct(int productId) {
    LOG.debug("/product return the found product for productId={}", productId);
    /*
     * API implementations use the exceptions in the util project to signal errors.
     * They will be reported
     * back to the REST client as HTTPS status codes indicating what went wrong. For
     * example, the Product
     * microservice implementation class, ProductServiceImpl.java, uses the
     * InvalidInputException
     * exception to return an error that indicates invalid input, as well as the
     * NotFoundException exception
     * to tell us that the product that was asked for does not exist.
     */
    if (productId < 1) {
      throw new InvalidInputException("Invalid productId: " + productId);
    }
    // Since we currently aren’t using a database, we have to simulate when to throw
    // NotFoundException.
    if (productId == 13) {
      throw new NotFoundException("No product found for productId: " + productId);
    }

    return new Product(productId, "name-" + productId, 123, serviceUtil.getServiceAddress());
  }
}