package io.hauter.apricot;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Created by sun on 16/5/30.
 */
public class JettyServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8090);

        ServletContextHandler context = new ServletContextHandler();

        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.setBasePackage("io.hauter.apricot.test");

        context.addServlet(new ServletHolder(dispatcherServlet),"/*");
        server.setHandler(context);

        server.start();
        server.join();


    }
}
