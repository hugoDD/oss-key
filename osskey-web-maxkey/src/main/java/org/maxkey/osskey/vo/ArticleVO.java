package org.maxkey.osskey.vo;

import lombok.Data;

import java.util.List;

@Data
public class ArticleVO {
    private Long id;
    private String status;
    private String title;
    private String abstractContent;
    private String fullContent;
    private String sourceURL;
    private String imageURL;
    private Long timestamp;
    private List<String> platforms;
    private boolean disableComment;
    private int importance;
    private String author;
    private String reviewer;
    private String type;
    private int pageviews;
}
