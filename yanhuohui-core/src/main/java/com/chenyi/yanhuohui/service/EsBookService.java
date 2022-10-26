package com.chenyi.yanhuohui.service;

import com.chenyi.yanhuohui.bean.BookDTO;
import com.chenyi.yanhuohui.esbook.Book;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * ElasticsearchTemplate一些常用API的使用demo
 */
@Service
@Slf4j
public class EsBookService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public void save(BookDTO bookDTO){
        Book book = new Book();
        BeanUtils.copyProperties(bookDTO,book);
        IndexQuery indexQuery = new IndexQueryBuilder().withId(book.getId()).withType("book").withObject(book).build();
        String documentId = elasticsearchTemplate.index(indexQuery);
        log.info("文档插入成功，新生成的文档ID为{}",documentId);
//        IndexQueryBuilder builder = new IndexQueryBuilder();
//        for (int i = 0; i < 10; i++) {
//            String id = "00"+i;
//            elasticsearchTemplate.index(builder.withObject(new Book(id,i+"号变形金刚","这是讲述汽车人与狂派之间的故事第"+i+"回",10*i)).build());
//        }
    }

    public void update(BookDTO bookDTO){
        Map<String, Object> params = new HashMap<>();
        // 其中某个需要更新的属性
        params.put("bookName", bookDTO.getBookName());
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.doc(params);
        UpdateQueryBuilder updateQueryBuilder = new UpdateQueryBuilder();
        UpdateQuery updateQuery = updateQueryBuilder.withId(bookDTO.getId()).withUpdateRequest(updateRequest).withClass(Book.class).build();
        UpdateResponse update = elasticsearchTemplate.update(updateQuery);
    }

    //CriteriaQuery对象，适合添加精确的数据，来封装成查询对象，然后进行查询
    public List<Book> findAll(Pageable pageable){
        CriteriaQuery criteriaQuery = new CriteriaQuery(new Criteria()).setPageable(pageable);
        criteriaQuery.addSort(Sort.by(Sort.Direction.DESC, "id"));
        return elasticsearchTemplate.queryForList(criteriaQuery, Book.class);
    }

    public List<Book> queryByName(BookDTO bookDTO){
        CriteriaQuery criteriaQuery = new CriteriaQuery(new Criteria());
        if(Objects.nonNull(bookDTO.getBookName())){
            criteriaQuery.addCriteria(new Criteria("bookName").startsWith(bookDTO.getBookName()));
        }
        criteriaQuery.addCriteria(new Criteria("pagecount").between(30,70));

        return elasticsearchTemplate.queryForList(criteriaQuery, Book.class);
    }

    //使用SearchQuery构建查询对象
    public List<Book> queryBuilder(BookDTO bookDTO){
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if(Objects.nonNull(bookDTO.getBookName())){
            //只能withQuery一次，会覆盖，所以最好不要这样写
            //builder.withQuery(QueryBuilders.queryStringQuery(bookDTO.getBookName()).defaultField("bookName"));
            boolQuery.must(QueryBuilders.queryStringQuery(bookDTO.getBookName()).defaultField("bookName"));
            //查询title字段中，包含 ”开发”、“开放" 这个字符串的document；相当于把"浦东开发开放"分词了，再查询；
            //QueryBuilders.queryStringQuery("开发开放").defaultField("title");
            //不指定feild，查询范围为所有feild
            //QueryBuilders.queryStringQuery("青春");
            //指定多个feild
            //QueryBuilders.queryStringQuery("青春").field("title").field("content");
        }
        //这是错误写法，差点上了当
//        if(Objects.nonNull(bookDTO.getId())){
//            builder.withQuery(QueryBuilders.termQuery("id", bookDTO.getId()));
//        }
        if(Objects.nonNull(bookDTO.getId())){
            boolQuery.must(QueryBuilders.termQuery("id", bookDTO.getId()));
        }
        builder.withQuery(boolQuery);

        //match是针对单个字段的，上面的queryString是针对文档所有字段内容的，当然queryString可以指定单个字段，反过来match也能指定多个字段
        //QueryBuilders.matchQuery("title", "开发开放")
        //QueryBuilders.multiMatchQuery("fieldlValue", "fieldName1", "fieldName2", "fieldName3")

        builder.withPageable(PageRequest.of(0, 5))
                //.withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC))
                .withHighlightFields(new HighlightBuilder.Field("bookName"));

        return elasticsearchTemplate.queryForList(builder.build(),Book.class);
    }

}
