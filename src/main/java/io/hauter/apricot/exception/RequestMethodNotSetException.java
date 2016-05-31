package io.hauter.apricot.exception;


/**
 * Created by sun on 16/5/30.
 */
public class RequestMethodNotSetException extends Exception {
    public RequestMethodNotSetException() {}

    public RequestMethodNotSetException(String msg) {
        super(msg);
    }
}
