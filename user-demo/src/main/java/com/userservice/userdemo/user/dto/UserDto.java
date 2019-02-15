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
package com.userservice.userdemo.user.dto;

import com.google.common.base.MoreObjects;
import com.userservice.userdemo.user.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * 用户信息定义
 *
 * @author CD826(CD826Dong@gmail.com)
 * @since 1.0.0
 */
@ApiModel(value = "用户信息包")
public class UserDto implements Serializable {
    private static final long serialVersionUID = 1L;

    // ========================================================================
    // fields =================================================================
    @ApiModelProperty(value="主键Id")
    private Long id;
    @ApiModelProperty(value="用户昵称")
    private String nickname;
    @ApiModelProperty(value="用户头像")
    private String avatar;
    @ApiModelProperty(value="服务端口")
    private int userServicePort;

    public UserDto() {

    }

    public UserDto(Long id, String nickname, String avatar) {
        this.id = id;
        this.nickname = nickname;
        this.avatar = avatar;
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.avatar = user.getAvatar();
    }

    public UserDto(User user, int userServicePort) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.avatar = user.getAvatar();
        this.userServicePort = userServicePort;
    }

    @Override
    public String toString() {
        return this.toStringHelper().toString();
    }

    protected MoreObjects.ToStringHelper toStringHelper() {
        return MoreObjects.toStringHelper(this)
                .add("id", getId())
                .add("nickname", getNickname());
    }

    // ========================================================================
    // setter/getter ==========================================================
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getUserServicePort() {
        return userServicePort;
    }
    public void setUserServicePort(int userServicePort) {
        this.userServicePort = userServicePort;
    }
}