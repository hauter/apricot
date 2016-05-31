package io.hauter.apricot;

import com.google.gson.Gson;
import io.hauter.apricot.exception.RequestMethodNotSetException;
import io.hauter.apricot.handler.Router;
import io.hauter.apricot.model.HttpStatus;
import io.hauter.apricot.model.HttpStatusAndMessage;
import io.hauter.apricot.model.RequestControllerMethod;
import io.hauter.apricot.model.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sun on 16/5/30.
 *  负责所有 请求的分发
 */

public class DispatcherServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);


    private Router router;

    public void setBasePackage(String basePackage) throws RequestMethodNotSetException {
        router = new Router(basePackage);
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpStatusAndMessage httpStatusAndMessage = handleRequest(req, RequestMethod.POST);

        resp.setStatus(httpStatusAndMessage.status);
        resp.getOutputStream().print(httpStatusAndMessage.message);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpStatusAndMessage httpStatusAndMessage = handleRequest(req, RequestMethod.GET);

        resp.setStatus(httpStatusAndMessage.status);
        resp.getOutputStream().print(httpStatusAndMessage.message);
    }


    //处理 request
    private HttpStatusAndMessage handleRequest(HttpServletRequest req, RequestMethod requestMethod) {
        try {
            String requestUrl = req.getRequestURI();

            RequestControllerMethod controllerMethod = router.getRequestControllerMethod(requestUrl, requestMethod);


            if (controllerMethod == null) {
                return new HttpStatusAndMessage(HttpStatus.NOT_FOUND_404);
            }


            Object controller = controllerMethod.getControllerClass().newInstance();
            Object methodReturn = controllerMethod.getControllerMethod().invoke(controller);
            if (methodReturn instanceof String) {
                return new HttpStatusAndMessage(HttpStatus.OK_200, (String) methodReturn);
            } else {
                return new HttpStatusAndMessage(HttpStatus.OK_200, new Gson().toJson(methodReturn));
            }


        } catch (Exception e) {
            logger.error("Excepiton, response 500", e);
            return new HttpStatusAndMessage(HttpStatus.INTERNAL_SERVER_ERROR_500);
        }
    }

    public static void main(String[] args) {
        new Gson().toJson("sdf");
    }

}
