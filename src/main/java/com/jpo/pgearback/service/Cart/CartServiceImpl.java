package com.jpo.pgearback.service.Cart;

import com.jpo.pgearback.dto.AddToCartDTO;
import com.jpo.pgearback.entity.Cart;
import com.jpo.pgearback.entity.CartProducts;
import com.jpo.pgearback.entity.Product;
import com.jpo.pgearback.entity.User;
import com.jpo.pgearback.enums.CartStatus;
import com.jpo.pgearback.repository.CartProductsRepository;
import com.jpo.pgearback.repository.CartRepository;
import com.jpo.pgearback.repository.ProductRepository;
import com.jpo.pgearback.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    private final CartRepository r_cartRepository;
    private final UserRepository r_userRepository;
    private final CartProductsRepository r_cartProductsRepository;
    private final ProductRepository r_productRepository;

    public CartServiceImpl(CartRepository p_cartRepository, UserRepository p_userRepository,
                           CartProductsRepository p_cartProductsRepository, ProductRepository p_productRepository) {
        this.r_cartRepository = p_cartRepository;
        this.r_userRepository = p_userRepository;
        this.r_cartProductsRepository = p_cartProductsRepository;
        this.r_productRepository = p_productRepository;
    }

    public ResponseEntity<?> addProductsToCart(AddToCartDTO p_addToCartDTO) {
        Cart v_cartUser = r_cartRepository.findByUserIdAndCartStatus(p_addToCartDTO.getUserId(), CartStatus.WAITING);
        if(v_cartUser == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("cart not found");
        }
        Optional<CartProducts> v_cartProducts = r_cartProductsRepository.findByProductIdAndCartIdAndUserId
                (p_addToCartDTO.getProductId(), v_cartUser.getId(), p_addToCartDTO.getUserId());

        if(v_cartProducts.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {
            Optional<Product> v_product = r_productRepository.findById(p_addToCartDTO.getProductId());
            Optional<User> v_user = r_userRepository.findById(p_addToCartDTO.getUserId());

            if(v_product.isPresent() && v_user.isPresent()){
                CartProducts v_newCartProducts = new CartProducts();
                v_newCartProducts.setProduct(v_product.get());
                v_newCartProducts.setPriceProduct(v_product.get().getPrix());
                v_newCartProducts.setQuantityProduct(1L);
                v_newCartProducts.setUser(v_user.get());
                v_newCartProducts.setCart(v_cartUser);

                r_cartProductsRepository.save(v_newCartProducts);
                v_cartUser.setTotalPrice(v_cartUser.getTotalPrice() + v_newCartProducts.getPriceProduct());
                v_cartUser.setPriceAfterReduction(v_cartUser.getPriceAfterReduction() + v_newCartProducts.getPriceProduct());
                v_cartUser.getCartProducts().add(v_newCartProducts);

                r_cartRepository.save(v_cartUser);
                return ResponseEntity.status(HttpStatus.CREATED).body("created");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Product Not Found");
            }
        }
    }

}
