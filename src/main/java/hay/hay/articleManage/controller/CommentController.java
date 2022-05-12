package hay.hay.articleManage.controller;

import com.google.common.collect.Lists;
import hay.hay.articleManage.Dto.CommentOutDto;
import hay.hay.articleManage.Dto.ResponseBean;
import hay.hay.articleManage.entity.Article;
import hay.hay.articleManage.entity.Comment;
import hay.hay.articleManage.entity.User;
import hay.hay.articleManage.service.ArticleService;
import hay.hay.articleManage.service.CommentService;
import hay.hay.articleManage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("comment")
@CrossOrigin(value = "*", allowCredentials = "true")
public class CommentController {
    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    private ResponseBean create(@NotNull String comment, String articleId, String userId) {
        Article article = articleService.findById(articleId);
        if (article == null) {
            return new ResponseBean().code(ResponseBean.FAILED).success(false).data("已找不到文章");
        }
        User user = userService.findById(userId);
        if (user == null) {
            return new ResponseBean().code(ResponseBean.FAILED).success(false).data("已找不到文章");
        }
        Comment comment1 = new Comment(comment,article,user);
        commentService.save(comment1);
        return new ResponseBean().code(ResponseBean.OK).success(true).data("留言发布成功");
    }

    @PostMapping("/findAllByArticle")
    private ResponseBean findAllByArticle(@NotNull String id) {
        Article article = articleService.findById(id);
        if (article == null) {
            return new ResponseBean().code(ResponseBean.FAILED).success(false).data("已找不到文章");
        }
        List<CommentOutDto> outDto = Lists.newArrayList();
        List<Comment> comments = commentService.findAllByArticle(article);
        for (Comment comment:comments) {
            outDto.add(new CommentOutDto(comment));
        }
        return new ResponseBean().code(ResponseBean.OK).success(true).data(outDto);
    }

    @PostMapping("/delete")
    private ResponseBean deleteComment(@NotNull String id) {
        Comment comment = commentService.findById(id);
        if (comment == null) {
            return new ResponseBean().code(ResponseBean.FAILED).success(false).data("已找不到留言");
        }
        commentService.delete(comment);
        return new ResponseBean().code(ResponseBean.OK).success(true).data("留言删除成功");
    }
}
