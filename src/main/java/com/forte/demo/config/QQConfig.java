package com.forte.demo.config;

import com.forte.demo.QQRunApplication;
import com.forte.qqrobot.component.forhttpapi.HttpApplication;
import com.forte.qqrobot.depend.DependGetter;
import com.forte.qqrobot.sender.MsgSender;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 *
 * SpringBoot 启动器, 整合 simple-robot 启动器
 * 通过SpringBoot的Configuration方式进行整合
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Component
@Configuration
public class QQConfig {

    /**
     * 通过注入的方式获取Spring的依赖工厂
     */
    @Autowired
    private BeanFactory factory;


    @Value("${QQConfig.ip}")
    private String strIp;

    @Value("${QQConfig.javaPort}")
    private int intJavaPort;

    @Value("${QQConfig.serverPath}")
    private String strServerPath;

    @Value("${QQConfig.serverPort}")
    private int intServerPort;


    /**
     *
     * 通过@Bean的方式启动并将HttpApi启动器注入SpringBoot容器
     * 通过此方法即可实现双向注入
     */
    @Bean
    public HttpApplication httpApplication(){
        HttpApplication httpApplication = new HttpApplication();

        // 通过SpringBoot的依赖工厂，构建一个提供给Http Api启动器的依赖工厂，以实现整合
        DependGetter dependGetter = new DependGetter() {
            @Override
            public <T> T get(Class<T> clazz) {
                try {
                    return factory.getBean(clazz);
                }catch (Exception e){
                    return null;
                }
            }

            @Override
            public <T> T get(String name, Class<T> type) {
                try {
                    return factory.getBean(name, type);
                }catch (Exception e){
                    return null;
                }
            }

            @Override
            public Object get(String name) {
                try {
                    return factory.getBean(name);
                }catch (Exception e){
                    return null;
                }
            }

            @Override
            public <T> T constant(String name, Class<T> type) {
                return null;
            }

            @Override
            public Object constant(String name) {
                return null;
            }
        };

        // 启动
        httpApplication.run(new QQRunApplication(dependGetter,strIp,intJavaPort,strServerPath,intServerPort));

        // 将启动器注入到Spring容器
        return httpApplication;
    }

    /**
     * 通过启动器获取到MsgSender对象
     */
    @Bean
    public MsgSender msgSender(HttpApplication httpApplication){
        return httpApplication.getMsgSender();
    }



}

