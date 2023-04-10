package com.beite.log.search.logfilesearchdome.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;

/**
 * @author beite_he[beite_he@insightfo.cn]
 * @author <a href="mailto:beite_he@insightfo.cn">Beite</a>
 * @date 2023年04月09日 23:10
 * @since 1.0
 */
@Controller
public class IndexController {

    @Autowired
    private ServletContext servletContext;

    @GetMapping("/index")
    public String indexPage(Model model) {
        model.addAttribute("servletContext", servletContext);
        return "index";
    }
}
