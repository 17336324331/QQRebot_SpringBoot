package com.forte.demo.controller;

import com.forte.qqrobot.component.forhttpapi.HttpApplication;
import com.forte.qqrobot.sender.MsgSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * 此类用来验证robot内的东西是否都被正常整合进来了。
 * Controller如果存在依赖不存在的情况会直接启动报错的。
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Controller
public class TestController {

    /**
     * 通过Spring注入robot启动器
     */
    @Autowired
    private HttpApplication httpApplication;

    /**
     * 通过Spring注入送信器。
     * 这样就可以在robot以外的地方发送、获取消息了
     */
    @Autowired
    private MsgSender sender;

}
