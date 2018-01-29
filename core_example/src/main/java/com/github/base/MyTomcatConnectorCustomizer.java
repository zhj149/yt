package com.github.base;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;

/**
 * Created by limiao on 2018/1/26.
 */
class MyTomcatConnectorCustomizer implements TomcatConnectorCustomizer
{
    public void customize(Connector connector)
    {
        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
        //设置最大连接数
        protocol.setMaxConnections(500);
        //设置最大线程数
        protocol.setMaxThreads(500);
        protocol.setConnectionTimeout(30000);
    }
}