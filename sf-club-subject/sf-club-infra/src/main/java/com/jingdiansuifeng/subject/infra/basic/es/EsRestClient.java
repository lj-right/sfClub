package com.jingdiansuifeng.subject.infra.basic.es;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.*;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@Component
@Slf4j
public class EsRestClient {

    public static Map<String, RestHighLevelClient> clientMap = new HashMap<>();

    @Resource
    private EsConfigProperties esConfigProperties;

    private static final RequestOptions COMMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        COMMON_OPTIONS = builder.build();
    }

    @PostConstruct
    public void initialize() {
        List<EsClusterConfig> esConfigs = esConfigProperties.getEsConfigs();
        for (EsClusterConfig esConfig : esConfigs) {
            log.info("initialize.config.name:{},node:{}", esConfig.getName(), esConfig.getNodes());
            RestHighLevelClient restHighLevelClient = initRestClient(esConfig);
            if (restHighLevelClient != null) {
                clientMap.put(esConfig.getName(), restHighLevelClient);
            } else {
                log.error("config.name:{},node:{},init.error", esConfig.getName(), esConfig.getNodes());
            }
        }
    }

    private RestHighLevelClient initRestClient(EsClusterConfig esClusterConfig) {
        String[] ipPortArr = esClusterConfig.getNodes().split(",");
        List<HttpHost> httpHostList = new ArrayList<>(ipPortArr.length);
        for (String ipPort : ipPortArr) {
            String[] ipPortInfo = ipPort.split(":");
            if (ipPortInfo.length == 2) {
                HttpHost httpHost = new HttpHost(ipPortInfo[0], Integer.parseInt(ipPortInfo[1]));
                httpHostList.add(httpHost);
            }
        }
        HttpHost[] httpHosts = new HttpHost[httpHostList.size()];
        httpHostList.toArray(httpHosts);

        RestClientBuilder builder = RestClient.builder(httpHosts);
        return new RestHighLevelClient(builder);
    }

    private static RestHighLevelClient getClient(String clusterName) {
        return clientMap.get(clusterName);
    }

    public static boolean insertDoc(EsIndexInfo esIndexInfo, EsSourceData esSourceData) {
        try {
            IndexRequest indexRequest = new IndexRequest(esIndexInfo.getIndexName());
            indexRequest.source(esSourceData.getData());
            indexRequest.id(esSourceData.getDocId());
            getClient(esIndexInfo.getClusterName()).index(indexRequest, COMMON_OPTIONS);
            return true;
        } catch (IOException e) {
            log.error("EsRestClient.insertDoc.error:{}", e.getMessage(), e);
        }
        return false;
    }

    public static boolean updateDoc(EsIndexInfo esIndexInfo, EsSourceData esSourceData) {
        try {
            UpdateRequest updateRequest = new UpdateRequest();
            updateRequest.index(esIndexInfo.getIndexName());
            updateRequest.id(esSourceData.getDocId());
            updateRequest.doc(esSourceData.getData());
            getClient(esIndexInfo.getClusterName()).update(updateRequest, COMMON_OPTIONS);
            return true;
        } catch (IOException e) {
            log.error("EsRestClient.updateDoc.error:{}", e.getMessage(), e);
        }
        return false;
    }
    public static boolean batchUpdateDoc(EsIndexInfo esIndexInfo, List<EsSourceData> esSourceDataList) {
        try {
            boolean flag = false;
            BulkRequest bulkRequest = new BulkRequest();
            for (EsSourceData esSourceData : esSourceDataList){
                String docId = esSourceData.getDocId();
                if (StringUtils.isNotBlank(docId)){
                    UpdateRequest updateRequest = new UpdateRequest();
                    updateRequest.index(esIndexInfo.getIndexName());
                    updateRequest.id(esSourceData.getDocId());
                    updateRequest.doc(esSourceData.getData());
                    bulkRequest.add(updateRequest);
                    flag = true;
                }
            }
            if (flag){
                BulkResponse bulk = getClient(esIndexInfo.getClusterName()).bulk(bulkRequest, COMMON_OPTIONS);
                if (bulk.hasFailures()){
                    return false;
                }
            }
            return true;
        } catch (IOException e) {
            log.error("EsRestClient.batchUpdateDoc.error:{}", e.getMessage(), e);
        }
        return false;
    }

    public static boolean delete(EsIndexInfo esIndexInfo) {
        try {
            DeleteByQueryRequest deleteByQueryRequest =
                    new DeleteByQueryRequest(esIndexInfo.getIndexName());
            deleteByQueryRequest.setQuery(QueryBuilders.matchAllQuery());
            BulkByScrollResponse response = getClient(esIndexInfo.getClusterName()).deleteByQuery(
                    deleteByQueryRequest, COMMON_OPTIONS
            );
            long deleted = response.getDeleted();
            log.info("delete es index:{} success,deleted:{}", esIndexInfo.getIndexName(), deleted);
            return true;
        } catch (IOException e) {
            log.error("EsRestClient.delete.error:{}", e.getMessage(), e);
        }
        return false;
    }

    public static boolean deleteDoc(EsIndexInfo esIndexInfo, String docId) {
        try {
            DeleteRequest deleteRequest = new DeleteRequest(esIndexInfo.getIndexName());
            deleteRequest.id(docId);
            DeleteResponse response = getClient(esIndexInfo.getClusterName()).delete(deleteRequest, COMMON_OPTIONS);
            log.info("EsRestClient.deleteDoc.response:{}", JSON.toJSONString(response));
            return true;
        } catch (IOException e) {
            log.error("EsRestClient.deleteDoc.error:{}", e.getMessage(), e);
        }
        return false;
    }

    public static boolean isExistDocById(EsIndexInfo esIndexInfo, String docId) {
        try {
            GetRequest getRequest = new GetRequest(esIndexInfo.getIndexName());
            getRequest.id(docId);
            return getClient(esIndexInfo.getClusterName()).exists(getRequest, COMMON_OPTIONS);
        } catch (IOException e) {
            log.error("EsRestClient.isExistDocById.error:{}", e.getMessage(), e);
        }
        return false;
    }

    public static Map<String, Object> getDocById(EsIndexInfo esIndexInfo, String docId) {
        try {
            GetRequest getRequest = new GetRequest(esIndexInfo.getIndexName());
            getRequest.id(docId);
            GetResponse documentFields = getClient(esIndexInfo.getClusterName()).get(getRequest, COMMON_OPTIONS);
            Map<String, Object> source = documentFields.getSource();
            return source;
        } catch (IOException e) {
            log.error("EsRestClient.isExistDocById.error:{}", e.getMessage(), e);
        }
        return null;
    }

    public static Map<String, Object> getDocById(EsIndexInfo esIndexInfo, String docId,
                                                 String[] fields) {
        try {
            GetRequest getRequest = new GetRequest(esIndexInfo.getIndexName());
            getRequest.id(docId);
            FetchSourceContext fetchSourceContext = new FetchSourceContext(true, fields, null);
            getRequest.fetchSourceContext(fetchSourceContext);
            GetResponse response = getClient(esIndexInfo.getClusterName()).get(getRequest, COMMON_OPTIONS);
            Map<String, Object> source = response.getSource();
            return source;
        } catch (IOException e) {
            log.error("EsRestClient.isExistDocById.error:{}", e.getMessage(), e);
        }
        return null;
    }

    public static SearchResponse searchWithTermQuery(EsIndexInfo esIndexInfo, EsSearchRequest esSearchRequest) {
        try {
            BoolQueryBuilder bq = esSearchRequest.getBq();
            String[] fields = esSearchRequest.getFields();
            int from = esSearchRequest.getFrom();
            int size = esSearchRequest.getSize();
            Long minustes = esSearchRequest.getMinutes();
            Boolean needScroll = esSearchRequest.getNeedScroll();
            String sortName = esSearchRequest.getSortName();
            SortOrder sortOrder = esSearchRequest.getSortOrder();

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(bq);
            searchSourceBuilder.fetchSource(fields, null).from(from).size(size);

            if (Objects.nonNull(esSearchRequest.getHighlightBuilder())) {
                searchSourceBuilder.highlighter(esSearchRequest.getHighlightBuilder());
            }

            if (StringUtils.isNotBlank(sortName)) {
                searchSourceBuilder.sort(sortName);
            }

            searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.searchType(SearchType.DEFAULT);
            searchRequest.indices(esIndexInfo.getIndexName());
            searchRequest.source(searchSourceBuilder);
            if (needScroll) {
                Scroll scroll = new Scroll(TimeValue.timeValueMinutes(minustes));
                searchRequest.scroll(scroll);
            }
            SearchResponse search = getClient(esIndexInfo.getClusterName()).search(searchRequest, COMMON_OPTIONS);
            return search;
        } catch (IOException e) {
            log.error("EsRestClient.isExistDocById.error:{}", e.getMessage(), e);
        }
        return null;
    }
    public static boolean batchInsertDoc(EsIndexInfo esIndexInfo, List<EsSourceData> esSourceDataList) {
        if (log.isInfoEnabled()) {
            log.info("批量新增ES:" + esSourceDataList.size());
            log.info("indexName:" + esIndexInfo.getIndexName());
        }
        try {
            boolean flag = false;
            BulkRequest bulkRequest = new BulkRequest();

            for (EsSourceData source : esSourceDataList) {
                String docId = source.getDocId();
                if (StringUtils.isNotBlank(docId)) {
                    IndexRequest indexRequest = new IndexRequest(esIndexInfo.getIndexName());
                    indexRequest.id(docId);
                    indexRequest.source(source.getData());
                    bulkRequest.add(indexRequest);
                    flag = true;
                }
            }


            if (flag) {
                BulkResponse response = getClient(esIndexInfo.getClusterName()).bulk(bulkRequest, COMMON_OPTIONS);
                if (response.hasFailures()) {
                    return false;
                }
            }
        } catch (Exception e) {
            log.error("batchInsertDoc.error", e);
        }

        return true;
    }

    public static boolean updateByQuery(EsIndexInfo esIndexInfo, QueryBuilder queryBuilder, Script script, int batchSize) {
        if (log.isInfoEnabled()) {
            log.info("updateByQuery.indexName:" + esIndexInfo.getIndexName());
        }
        try {
            UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest(esIndexInfo.getIndexName());
            updateByQueryRequest.setQuery(queryBuilder);
            updateByQueryRequest.setScript(script);
            updateByQueryRequest.setBatchSize(batchSize);
            updateByQueryRequest.setAbortOnVersionConflict(false);
            BulkByScrollResponse response = getClient(esIndexInfo.getClusterName()).updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
            List<BulkItemResponse.Failure> failures = response.getBulkFailures();
        } catch (Exception e) {
            log.error("updateByQuery.error", e);
        }
        return true;
    }

    /**
     * 分词方法
     */
    public static List<String> getAnalyze(EsIndexInfo esIndexInfo, String text) throws Exception {
        List<String> list = new ArrayList<String>();
        Request request = new Request("GET", "_analyze");
        JSONObject entity = new JSONObject();
        entity.put("analyzer", "ik_smart");
        entity.put("text", text);
        request.setJsonEntity(entity.toJSONString());
        Response response = getClient(esIndexInfo.getClusterName()).getLowLevelClient().performRequest(request);
        JSONObject tokens = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
        JSONArray arrays = tokens.getJSONArray("tokens");
        for (int i = 0; i < arrays.size(); i++) {
            JSONObject obj = JSON.parseObject(arrays.getString(i));
            list.add(obj.getString("token"));
        }
        return list;
    }

}
