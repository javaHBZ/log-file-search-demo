package com.beite.log.search.logfilesearchdome.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author beite_he[beite_he@insightfo.cn]
 * @author <a href="mailto:beite_he@insightfo.cn">Beite</a>
 * @date 2023年04月09日 23:10
 * @since 1.0
 */
@Controller
public class IndexController {

    @GetMapping("/index")
    public String indexPage() {
        return "index";
    }
}
