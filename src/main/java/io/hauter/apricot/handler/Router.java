package io.hauter.apricot.handler;

import io.hauter.apricot.annotation.Controller;
import io.hauter.apricot.annotation.RequestMapping;
import io.hauter.apricot.exception.RequestMethodNotSetException;
import io.hauter.apricot.model.RequestControllerMethod;
import io.hauter.apricot.model.RequestMethod;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by sun on 16/5/30.
 * 处理请求和 controller 方法的映射关系
 */
public class Router {
    private static final Logger logger = LoggerFactory.getLogger(Router.class);

    private String basePackage;
    private List<RequestControllerMethod> requestControllerMethodList = new ArrayList<>();

    public Router(String basePackage) throws RequestMethodNotSetException {
        this.basePackage = basePackage;
        scan();

        requestControllerMethodList.forEach(r -> {
            logger.info(" Request mapping | =====> {}:{} => {}.{}", r.getUrlPattern(), r.getRequestMethods(),
                    r.getControllerClass().getName(), r.getControllerMethod().getName());
        });
    }


    private void scan() throws RequestMethodNotSetException {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);

        for (Class<?> controller : controllers) {
            RequestMapping classRequestMapping = controller.getAnnotation(RequestMapping.class);
            String classUrlPatten = classRequestMapping == null ? "" : classRequestMapping.urlPatterns();



            Method[] methods = controller.getMethods();
            for (Method method : methods) {
                RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
                if (methodRequestMapping == null) {
                    continue;
                }


                String urlPatten = classUrlPatten + methodRequestMapping.urlPatterns();

                RequestMethod[] requestMethods = methodRequestMapping.method();
                if (requestMethods.length == 0) {
                    throw new RequestMethodNotSetException(controller.getName() + "." + method.getName() + "() request method not set");
                }

                requestControllerMethodList.add(new RequestControllerMethod(urlPatten, requestMethods, controller, method));
            }
        }
    }


    /**
     * 查找对应的处理器
     * @param requestUri
     * @return
     */
    public RequestControllerMethod getRequestControllerMethod(String requestUri, RequestMethod method) {
        //查找对应的
        for (RequestControllerMethod m : requestControllerMethodList) {
            if (m.isMatchPattern(requestUri, method)) {
                return m;
            }
        }

        return null;
    }


}
