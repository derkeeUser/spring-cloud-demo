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
package com.userservice2.userdemo2.user.service.impl;


import com.userservice2.userdemo2.user.dto.UserDto;
import com.userservice2.userdemo2.user.entity.User;
import com.userservice2.userdemo2.user.repository.UserRepository;
import com.userservice2.userdemo2.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 用户管理服务的默认实现
 *
 * @author CD826
 * @since 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Value("${server.port}")
    protected int serverPort = 0;

    @Autowired
    protected UserRepository userRepository;

    @Override
    public Page<User> getPage(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    @Override
    public User load(Long id) {
        return this.userRepository.findOne(id);
    }

    @Override
    @Transactional
    public User save(UserDto userDto) {
        User user = this.userRepository.findOne(userDto.getId());
        if (null == user) {
            user = new User();
        }
        user.setNickname(userDto.getNickname());
        user.setAvatar(userDto.getAvatar());
        return this.userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.userRepository.delete(id);
    }

    /**
     * 获取注册用户列表
     *
     * @return
     */
    @Override
    public List<UserDto> findAll() {
        List<User> users = this.userRepository.findAll();
        return users.stream().map(user -> {
            return new UserDto(user, serverPort);
        }).collect(Collectors.toList());
    }

    /**
     * 获取指定的注册用户信息
     *
     * @param id
     * @return
     */
    @Override
    public UserDto loadUserDto(Long id) {
        User user = this.userRepository.findOne(id);
        if (null != user) {
            return new UserDto(user, serverPort);
        }
        return null;
    }
}
