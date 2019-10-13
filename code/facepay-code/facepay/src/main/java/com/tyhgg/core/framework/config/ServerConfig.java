package com.tyhgg.core.framework.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * tomcat-部分配置
 *
 */
@Configuration
public class ServerConfig {
	
	@Value("${spring.server.noCheckChars}")
    private String noCheckChars;
	@Value("${spring.server.maxConnections}")
    private int maxConnections;
	@Value("${spring.server.maxThreads}")
    private int maxThreads;
	@Value("${spring.server.connectionTimeout}")
    private int connectionTimeout;
	
	@Bean
	public ConfigurableServletWebServerFactory WebServerFactory() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
			@Override
			public void customize(Connector connector) {
				//tomcatURL特殊字符检查添加例外
				connector.setProperty("relaxedQueryChars", noCheckChars);
				//connector.setProperty("relaxedPathChars", "\\");
				Http11NioProtocol protocol = (Http11NioProtocol)connector.getProtocolHandler();
				//System.out.println("ppppppppppppppppp"+protocol);
				protocol.setMaxThreads(maxThreads);
				protocol.setMaxConnections(maxConnections);
				protocol.setConnectionTimeout(connectionTimeout);
				//protocol.setPort(port);
			}
		});
		return factory;

	}
	
}

