package io.hauter.apricot.model;


/**
 * Created by sun on 16/5/30.
 * http 请求 response 的状态和 消息
 */
public class HttpStatusAndMessage {

    /**
     * {@link io.hauter.apricot.model.HttpStatus}
     */
    public int status;
    public String message;

    public HttpStatusAndMessage(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatusAndMessage(int status) {
        this.status = status;
        this.message = "";
    }
}
