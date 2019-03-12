package com.userservice.userdemo.user.entity;


/**
 * @BelongsProject: scdemo
 * @BelongsPackage: com.userservice.userdemo.user.entity
 * @Author: chenxiaoming
 * @CreateTime: 2019-03-11 17:09
 * @Description: 用户变更消息
 */
public class UserMsg {
    public static final String UP_DELETE = "delete";

    public static final String UP_UPDATE = "update";

    private String action;

    private Long userId;

    private String traceId;

    public UserMsg() {
    }

    public UserMsg(String action, Long userId, String traceId) {
        this.action = action;
        this.userId = userId;
        this.traceId = traceId;
    }

    @Override
    public String toString() {
        return "UserMsg{" +
                "action='" + action + '\'' +
                ", userId=" + userId +
                ", traceId='" + traceId + '\'' +
                '}';
    }
}
