package w.whatevera.wiffleball.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by rich on 9/21/16.
 */
@Controller
public class WiffleballStaticController {

    @RequestMapping("/")
    public String index() {
        return "index.html";
    }
}