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
package com.productservice.productdemo.product.api;


import com.productservice.productdemo.product.dto.ProductCommentDto;
import com.productservice.productdemo.product.dto.ProductDto;
import com.productservice.productdemo.product.entity.Product;
import com.productservice.productdemo.product.entity.ProductComment;
import com.productservice.productdemo.product.service.ProductService;
import com.productservice.productdemo.product.service.UserService;
import com.userservice.userdemo.user.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 产品管理的Endpoint
 *
 * @author CD826
 * @since 1.0.0
 */
@RestController
@RequestMapping("/products")
@Api(value = "ProductEndpoint", description = "商品管理相关Api")
public class ProductEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductEndpoint.class);

    @Autowired
    private ProductService productService;

    @Autowired
    @Qualifier(value="restTemplate")
    /**
     * 注入RestTemplate，调用用户微服务
     */
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier(value = "lbcRestTemplate")
    private RestTemplate lbcRestTemplate;

    @Autowired
    /**
     * 织入Ribbon的客户端负载
     */
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private UserService userService;

    /**
     * 获取产品信息列表
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "获取商品分页数据", notes = "获取商品分页数据", httpMethod = "GET", tags = "商品管理相关Api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页，从0开始，默认为第0页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每一页记录数的大小，默认为20", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序，格式为:property,property(,ASC|DESC)的方式组织，如sort=firstname&sort=lastname,desc", dataType = "String", paramType = "query")
    })
    public List<ProductDto> list(Pageable pageable) {
        Page<Product> page = this.productService.getPage(pageable);
        if (null != page) {
            return page.getContent().stream().map((product) -> {
                return new ProductDto(product);
            }).collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "获取商品详情数据", notes = "获取商品详情数据", httpMethod = "GET", tags = "商品管理相关Api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品的主键", dataType = "int", paramType = "path")
    })
    public ProductDto detail(@PathVariable Long id){
        Product product = this.productService.load(id);
        if (null == product) {
            return null;
        }
        return new ProductDto(product);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "根据productId删除商品数据", notes = "根据productId删除商品数据", httpMethod = "DELETE", tags = "商品管理相关Api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品的主键", dataType = "int", paramType = "path")
    })
    public boolean delete(@PathVariable Long id){
        try {
            this.productService.delete(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping(value = "/{id}/comments", method = RequestMethod.GET)
    @ApiOperation(value = "获取商品的评论列表", notes = "获取商品的评论列表", httpMethod = "GET", tags = "商品管理相关Api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品的主键", dataType = "int", paramType = "path")
    })
    public List<ProductCommentDto> comments(@PathVariable Long id){
        List<ProductComment> commentList = this.productService.findAllByProduct(id);
        if (null == commentList || commentList.isEmpty()) {
            return Collections.emptyList();
        }

        ProductDto productDto = new ProductDto(this.productService.load(id));
        return commentList.stream().map((comment) -> {
            ProductCommentDto dto = new ProductCommentDto(comment);
            dto.setProduct(productDto);
            //dto.setAuthor(this.loadUser(comment.getAuthorId()));
            dto.setAuthor(this.userService.load(comment.getAuthorId()));
            //dto.setAuthor(this.loadUserEx(comment.getAuthorId()));
            return dto;
        }).collect(Collectors.toList());
    }

    //RestTemplate方式
    protected UserDto loadUser(Long userId){
        UserDto userDto = this.restTemplate.getForEntity("http://USER-SERVICE/users/{id}"
                ,UserDto.class,userId).getBody();
        if (userDto != null) {
            LOGGER.info("I came from server:{}",userDto.getUserServicePort());
        }
        return userDto;
    }

    //loadBalancerClient方式
    public UserDto loadUserEx(Long userId) {
        //首先通过loadBalancerClient获取一个用户微服务实例
        ServiceInstance instance = this.loadBalancerClient.choose("USER-SERVICE");
        //URI userUri = URI.create(String.format("http://%s:%s/users/{id}", instance.getHost(), instance.getPort()));
        //LOGGER.info("Target service uri = {}", userUri.toString());
        //然后再通过restTemplate进行请求
        UserDto userDto = this.lbcRestTemplate.getForEntity(String.format("http://%s:%s/users/{id}", instance.getHost(), instance.getPort())
                , UserDto.class, userId).getBody();
        if (userDto != null) {
            LOGGER.debug("I came from server : {}", userDto.getUserServicePort());
        }
        return userDto;
    }

}
