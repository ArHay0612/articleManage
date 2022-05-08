package hay.hay.articleManage.controller;

import com.google.common.collect.Lists;
import hay.hay.articleManage.Dto.ArticleInDto;
import hay.hay.articleManage.Dto.ArticleListOutDto;
import hay.hay.articleManage.Dto.ArticleOutDto;
import hay.hay.articleManage.Dto.ResponseBean;
import hay.hay.articleManage.entity.Article;
import hay.hay.articleManage.entity.User;
import hay.hay.articleManage.service.ArticleService;
import hay.hay.articleManage.service.UserService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("article")
@CrossOrigin(value = "*", allowCredentials = "true")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    private ResponseBean findAll(){
        List<Article> articleList = articleService.findAll();
        List<ArticleListOutDto> outDtos = Lists.newArrayList();
        for (Article article: articleList) {
            outDtos.add(new ArticleListOutDto(article));
        }
        return new ResponseBean().code(200).success(true).data(outDtos);
    }

    @PostMapping("/findMine")
    private ResponseBean findMine(String id) {
        User user = userService.findById(id);
        List<ArticleListOutDto> outDtos = Lists.newArrayList();
        List<Article> articleList = articleService.findMine(user);
        return new ResponseBean().code(200).success(true).data(outDtos);
    }

    @PostMapping("/create")
    private ResponseBean create(ArticleInDto inDto) {
        User user = userService.findById(inDto.getUserid());
        Article article = new Article(inDto,user);
        articleService.save(article);
        return new ResponseBean().code(200).success(true).data("发布成功");
    }

    @PostMapping("/delete")
    private ResponseBean delete(String id) {
        Article article = articleService.findById(id);
        articleService.delete(article);
        return new ResponseBean().code(200).success(true).data("删除成功");
    }

    @PostMapping("/findContent")
    private ResponseBean findContent(String id) {
        Article article = articleService.findById(id);
        article.setClickTime(article.getClickTime()+1);
        article = articleService.save(article);
        ArticleOutDto outDto = new ArticleOutDto(article);
        return new ResponseBean().code(200).success(true).data(outDto);
    }
}
