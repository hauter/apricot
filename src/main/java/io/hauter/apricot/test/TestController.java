package io.hauter.apricot.test;

import io.hauter.apricot.annotation.Controller;
import io.hauter.apricot.annotation.RequestMapping;
import io.hauter.apricot.model.RequestMethod;

import java.util.Arrays;
import java.util.List;

/**
 * Created by sun on 16/5/30.
 */
@Controller
@RequestMapping(urlPatterns = "/test")
public class TestController {

    @RequestMapping(urlPatterns = "/test1", method = RequestMethod.GET)
    public String test1() {
        return "sdfsdf";
    }


    @RequestMapping(urlPatterns = "/{id}", method = RequestMethod.GET)
    public List<String> test2(String id) {
        return Arrays.asList("111", id);
    }



}
