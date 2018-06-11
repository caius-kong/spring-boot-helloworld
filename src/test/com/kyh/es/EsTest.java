package com.kyh.es;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * @author kongyunhui on 2018/6/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class EsTest {

    @Test
    public void testTransportClient() throws Exception {
        // 初始化客户端，并绑定多个节点
        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch") //如果你的集群名不是"elasticsearch",则必须设置集群名
                .put("client.transport.sniff", true)  //允许你的客户端连接集群中的其他节点
                .build();

        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));

        // 查询
        SearchResponse searchResponse = client
                .prepareSearch("megacorp")
                .setQuery(
                        boolQuery()
                                .must(matchQuery("first_name", "Jane")).must(matchQuery("last_name", "Smith"))
                                .filter(rangeQuery("age").gte(30)))
                .execute().actionGet();
        System.out.println(searchResponse); // source部分是一个json字符串格式的输出

        // 关闭客户端
        client.close();
    }

    @Test
    public void testJavaRestHigh() throws Exception {
        // 初始化客户端，并绑定多个节点
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                ));

        // 查询
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("megacorp");
        searchRequest.source(new SearchSourceBuilder()
                .query(QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("first_name", "Jane"))
                        .must(QueryBuilders.matchQuery("last_name", "Smith"))
                        .filter(QueryBuilders.rangeQuery("age").gte(30)))
                .from(0)
                .size(1)
                .timeout(new TimeValue(60, TimeUnit.SECONDS))
        );

        SearchResponse searchResponse = client.search(searchRequest); // 同步方法。后缀+Async支持异步
        System.out.println(searchResponse); // source是一个对象的toString格式的输出

        // 关闭客户端
        client.close();
    }
}
