package soa.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchController {

    private final ProducerTemplate producerTemplate;

    @Autowired
    public SearchController(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/search")
    @ResponseBody
    public Object search(@RequestParam("q") String q,
            @RequestParam(name = "max", defaultValue = "10", required = false) int maxSize) {
        if (maxSize > 50)
            maxSize = 50;

        Map<String, Object> header = new HashMap<>(2);
        header.put("CamelTwitterCount", maxSize);
        header.put("CamelTwitterKeywords", q);

        return producerTemplate.requestBodyAndHeaders("direct:search", "", header);
    }
}
