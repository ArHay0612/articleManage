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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("article")
@CrossOrigin(value = "*", allowCredentials = "true")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;

    /**上传地址*/
    @Value("${custom.fileStorePath}")
    private String path;

    /**
     * 上传图片
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadPicture")
    public ResponseBean uploadPicture(@RequestParam("file") MultipartFile file) throws IOException {

        //String base_picture_url = "http://localhost:8080/image/";

        //获取文件在服务器的储存位置
        File filePath = new File(path);
        System.out.println("文件的保存路径"+path);
        if(!filePath.exists() && !filePath.isDirectory()){
            System.out.println("目录不存在，创建目录" + filePath);
            filePath.mkdir();
        }

        //获取原始文件名称（包括格式）
        String originalFileName = file.getOriginalFilename();
        System.out.println("原始文件名称" + originalFileName);

        //获取文件类型，以最后一个‘.’为标识
        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        System.out.println("文件类型" + type);

        //获取文件名称（不包含格式）
        String name = originalFileName.substring(0,originalFileName.lastIndexOf("."));

        //设置文件新名称：当前事件+文件名称（不包含格式）
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String date = sdf.format(d);
        String fileName = date+ "." +type;
        System.out.println("新文件名称" +fileName);

        //在指定路径下创建文件
        File targetFile = new File(path,fileName);

        Map<String,Object> result = new HashMap<String,Object>();//定义结果
        //将文件保存到服务器指定位置
        try{
            file.transferTo(targetFile);
            System.out.println("上传成功");
            String totalPath ="http://localhost:8088/articleImageFiles/"+fileName;
            return new ResponseBean().code(200).success(true).data(totalPath);

        }catch (IOException e){
            System.out.println("上传失败");
            result.put("code",400);
            e.printStackTrace();
            return new ResponseBean().code(ResponseBean.FAILED).success(false).data("图片上传失败，请重试");
        }
    }

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
        if (inDto.getTitle().isEmpty()) {
            return new ResponseBean().code(ResponseBean.FAILED).success(false).data("标题不能为空");
        }
        if (inDto.getContent().isEmpty()) {
            return new ResponseBean().code(ResponseBean.FAILED).success(false).data("内容不能为空");
        }
        User user = userService.findById(inDto.getUserid());
        Article article = new Article(inDto,user);
        articleService.save(article);
        return new ResponseBean().code(ResponseBean.OK).success(true).data("发布成功");
    }

    @PostMapping("/delete")
    private ResponseBean delete(String id) {
        Article article = articleService.findById(id);
        articleService.delete(article);
        return new ResponseBean().code(ResponseBean.OK).success(true).data("删除成功");
    }

    @PostMapping("/findContent")
    private ResponseBean findContent(String id) {
        Article article = articleService.findById(id);
        article.setClickTime(article.getClickTime()+1);
        article = articleService.save(article);
        ArticleOutDto outDto = new ArticleOutDto(article);
        return new ResponseBean().code(ResponseBean.OK).success(true).data(outDto);
    }
}
