package io.hauter.apricot.model;

import java.lang.reflect.Method;

/**
 * Created by sun on 16/5/30.
 */
public class RequestControllerMethod {
    private String urlPattern;
    private RequestMethod [] requestMethods;

    private Class controllerClass;
    private Method controllerMethod;


    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    public Class getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Method getControllerMethod() {
        return controllerMethod;
    }

    public void setControllerMethod(Method controllerMethod) {
        this.controllerMethod = controllerMethod;
    }

    public RequestMethod[] getRequestMethods() {
        return requestMethods;
    }

    public void setRequestMethods(RequestMethod[] requestMethods) {
        this.requestMethods = requestMethods;
    }

    public RequestControllerMethod(String urlPattern, RequestMethod [] requestMethods, Class controllerClass, Method controllerMethod) {
        this.urlPattern = urlPattern;
        this.requestMethods = requestMethods;
        this.controllerClass = controllerClass;
        this.controllerMethod = controllerMethod;
    }


    /**
     * 判断当前 urlPattern 是否符合
     * @param uri
     * @param method
     * @return
     */
    public boolean isMatchPattern(String uri, RequestMethod method) {

        boolean isMethodMatch = false;

        for (RequestMethod m : requestMethods) {
            if (m == method) {
                isMethodMatch = true;
                break;
            }
        }

        return isMethodMatch && urlPattern.equals(uri);
    }
}
