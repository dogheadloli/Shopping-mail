package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pojo.SearchResult;
import service.SearchService;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/9/5 0005 21:22
 * 4
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;
    @Value("${SEARCH_RESULT_ROWS}")
    private Integer SEARCH_RESULT_ROWS;

    @RequestMapping("/search")
    public String search(@RequestParam("q") String queryString, @RequestParam(defaultValue = "1") Integer page, Model model) {
        try {
            SearchResult searchResult = searchService.search(queryString, page, SEARCH_RESULT_ROWS);
            model.addAttribute("query", queryString);
            model.addAttribute("totalPages", searchResult.getTotalPages());
            model.addAttribute("itemList", searchResult.getItemList());
            model.addAttribute("page", page);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "search";
    }
}
