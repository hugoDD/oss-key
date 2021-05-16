package org.maxkey.osskey.contorller;

import com.github.jsonzou.jmockdata.JMockData;
import com.github.jsonzou.jmockdata.TypeReference;
import org.maxkey.domain.param.PageSearchFilter;
import org.maxkey.domain.param.SearchFilter;
import org.maxkey.domain.result.PageResult;
import org.maxkey.osskey.vo.ArticleVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class ArticleContorller {

    public ArticleContorller() {
    }

    @GetMapping("/articles")
    public PageResult<ArticleVO> page(int page,int limit){
        List<ArticleVO> list = JMockData.mock(new TypeReference<List<ArticleVO>>() {
        });
        return PageResult.newInstance(list.size(),list);
    }
}
