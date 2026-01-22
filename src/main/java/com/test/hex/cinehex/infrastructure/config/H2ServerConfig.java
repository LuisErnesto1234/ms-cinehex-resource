package com.test.hex.cinehex.infrastructure.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
@ConditionalOnProperty(name = "spring.h2.console.enabled", havingValue = "true")
public class H2ServerConfig {

    @Value("${h2.tcp.port:9092}")
    private String h2TcpPort;

    @Value("${h2.web.port:8082}")
    private String h2WebPort;

    private Server webServer;
    private Server tcpServer;

    @PostConstruct
    public void startH2Servers() throws SQLException {
        System.out.println("Iniciando servidores H2...");

        // Servidor TCP para conexiones externas
        this.tcpServer = Server.createTcpServer(
                "-tcp",
                "-tcpAllowOthers",
                "-tcpPort", h2TcpPort
        ).start();

        // Servidor Web para la consola H2
        this.webServer = Server.createWebServer(
                "-web",
                "-webAllowOthers",
                "-webPort", h2WebPort
        ).start();

        System.out.println("H2 TCP Server iniciado en puerto: " + h2TcpPort);
        System.out.println("H2 Web Console iniciada en: http://localhost:" + h2WebPort + "/h2-console");
        System.out.println("URL de conexión TCP: jdbc:h2:tcp://localhost:" + h2TcpPort + "/./data/cinehexdb");
    }

    @PreDestroy
    public void stopH2Servers() {
        if (this.webServer != null && this.webServer.isRunning(false)) {
            this.webServer.stop();
            System.out.println("H2 Web Server detenido");
        }
        if (this.tcpServer != null && this.tcpServer.isRunning(false)) {
            this.tcpServer.stop();
            System.out.println("H2 TCP Server detenido");
        }
    }

    @Bean
    public Server h2TcpServer() throws SQLException {
        return this.tcpServer;
    }

    @Bean
    public Server h2WebServer() throws SQLException {
        return this.webServer;
    }
}
