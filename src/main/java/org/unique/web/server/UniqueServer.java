package org.unique.web.server;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

//import org.eclipse.jetty.server.Connector;
//import org.eclipse.jetty.server.Server;
//import org.eclipse.jetty.server.nio.SelectChannelConnector;
//import org.eclipse.jetty.servlet.DefaultServlet;
//import org.eclipse.jetty.servlet.ServletContextHandler;
//import org.eclipse.jetty.util.thread.QueuedThreadPool;
//import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * unique jetty server
 * 
 * @author rex
 */
public class UniqueServer {

//    private static final Logger logger = LoggerFactory.getLogger(UniqueServer.class);
//
//    private int port = 9090;
//    
//    private String contextPath = "/";
//
//    private String webPath = "./WebContent";
//
//    private int threadNum = 50;
//
//    private Server server;
//
//    public UniqueServer init(Integer port, String webContext) {
//
//        this.port = (null != port) ? port : this.port;
//        this.webPath = (null != webContext) ? webContext : this.webPath;
//
//        server = new Server();
//
//        // 连接池
//        SelectChannelConnector connector = new SelectChannelConnector();
//        connector.setPort(port);
//        connector.setMaxIdleTime(30000);
//        connector.setRequestHeaderSize(8192);
//        QueuedThreadPool threadPool = new QueuedThreadPool(threadNum);
//        threadPool.setName("unique-jetty-http");
//        connector.setThreadPool(threadPool);
//
//        server.setConnectors(new Connector[] { connector });
//        ServletContextHandler context = null;
//        // webapp 可以支持 jsp
//        if (webPath != null) {
//            logger.info("load webPath={}", webPath);
//            try {
//                URL warUrl = new File(webPath).toURI().toURL();
//                final String warUrlString = warUrl.toExternalForm();
//                context = new WebAppContext(warUrlString, contextPath);
//                server.setHandler(context);
//            } catch (MalformedURLException e) {
//                logger.error(e.getMessage());
//            }
//        } else {
//            context = new ServletContextHandler(server, contextPath);
//        }
//
//        if (webPath == null) {
//            context.addServlet(DefaultServlet.class, "/*");
//        }
//        return this;
//    }
//    
//    /**
//     * start server
//     */
//    public void start() {
//        Runtime.getRuntime().addShutdownHook(new Thread() {
//            @Override
//            public void run() {
//                try {
//                    server.stop();
//                } catch (Exception e) {
//                    logger.error("run main stop error!", e);
//                }
//            }
//
//        });
//        try {
//            server.start();
//        } catch (Exception e) {
//            logger.warn("start has exception:", e);
//            System.exit(-1);
//        }
//        logger.info("jetty unique server started, port={}", port);
//    }
//
//    /**
//     * stop server
//     */
//    public void stop() {
//        try {
//            server.stop();
//            server.destroy();
//        } catch (Exception e) {
//            logger.warn("stop has exception:", e);
//            System.exit(-1);
//        }
//    }
    
}
