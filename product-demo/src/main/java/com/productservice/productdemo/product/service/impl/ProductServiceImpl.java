/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.productservice.productdemo.product.service.impl;


import com.productservice.productdemo.product.entity.Product;
import com.productservice.productdemo.product.entity.ProductComment;
import com.productservice.productdemo.product.repository.ProductCommentRepository;
import com.productservice.productdemo.product.repository.ProductRepository;
import com.productservice.productdemo.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 产品管理服务的默认实现
 *
 * @author CD826
 * @since 1.0.0
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    protected ProductRepository productRepository;
    @Autowired
    protected ProductCommentRepository productCommentRepository;

    @Override
    public Page<Product> getPage(Pageable pageable) {
        return this.productRepository.findAll(pageable);
    }

    @Override
    public Product load(Long id) {
        return this.productRepository.findOne(id);
    }

    @Override
    public List<ProductComment> findAllByProduct(Long productId) {
        return this.productCommentRepository.findByProductIdOrderByCreated(productId);
    }

    @Override
    public boolean delete(Long productId) {
        try {
            this.productRepository.delete(productId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
