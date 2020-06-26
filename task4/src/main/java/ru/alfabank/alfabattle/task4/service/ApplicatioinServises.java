package ru.alfabank.alfabattle.task4.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import ru.alfabank.alfabattle.task4.config.ElasticProperties;
import ru.alfabank.alfabattle.task4.entity.*;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.io.IOException;


@Service
@Slf4j
public class ApplicatioinServises {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    RestHighLevelClient client;

    @PostConstruct
    @SneakyThrows
    private void init() {
        try {
            var createIndexRequest =
                    new CreateIndexRequest("persons");

            var createIndexRequest =
                    new CreateIndexRequest("loans");

            client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        } catch (Exception er) {
            log.error("index exists");
        }

    }


    public ResponseEntity<?> setPeople() throws IOException {

        String personListJSON = readUsingScanner("/json/persons.json");

        PersonJsonList data = OBJECT_MAPPER.readValue(personListJSON, PersonJsonList.class);

        data.getPersons().forEach(person -> {

            try {
                XContentBuilder builder = XContentFactory.jsonBuilder()
                        .startObject()
                        .field("fio", person.getFio())
                        .field("docid", person.getDocId())
                        .timeField("birthday", person.getBirthday().toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate())
                        .field("salary", person.getSalary().multiply(new BigDecimal(100)))
                        .field("gender", person.getGender())
                        .endObject();
                IndexRequest request = new IndexRequest("persons", "id", person.getId());
                request.source(builder);
                IndexResponse response = client.index(request, RequestOptions.DEFAULT);

            } catch (IOException e) {
                e.printStackTrace();


            }
        });
        return ResponseEntity.ok(ResponseStatus.builder().status("OK").build());
    }

    public void setLoan() throws IOException {

        String loanListJSON = readUsingScanner("/json/loans.json");
        LoanJsonList data = OBJECT_MAPPER.readValue(loanListJSON, LoanJsonList.class);

        data.getLoans().forEach(loan -> {

                    try {

                        GetRequest getRequest = new GetRequest("persons");
                        getRequest.id(loan.getDocument());

                        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);

                        Person result = new Person();
                        result = OBJECT_MAPPER.readValue(getResponse.getSourceAsString(), Person.class);

                        XContentBuilder builder = XContentFactory.jsonBuilder()
                                .startObject()
                                .field("document", result.getDocid())
                                .field("loan", loan.getLoan())
                                .field("amount", (Integer) loan.getAmount() * 100)
                                .field("startdate", loan.getOpenDate().toInstant()
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate())
                                .field("period", loan.getPeriod() * 12)
                                .endObject();

                        IndexRequest request = new IndexRequest("loans", "loan", loan.getLoan());
                        request.source(builder);
                        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public ResponseEntity<?> getPerson(String docId) throws IOException {

        ResponseEntity result = null;

        SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(QueryBuilders.termQuery("docid", docId));

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.searchType(SearchType.DFS_QUERY_THEN_FETCH);
        searchRequest.source(builder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

        List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());

        Person person = new Person();
        if (searchHits.isEmpty())
            result = ResponseEntity.badRequest().body(ResponseStatus.builder().status("person not found").build());
        else
            result = ResponseEntity.ok(OBJECT_MAPPER.readValue(searchHits.get(0).getSourceAsString(), Person.class));

        return result;
    }

    public ResponseEntity<?> getLoan(String loanNum) throws IOException {

        ResponseEntity result = null;

        GetRequest getRequest = new GetRequest("loans");
        getRequest.id(loanNum);

        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);

        if (getResponse.isSourceEmpty())
            result = ResponseEntity.badRequest().body(ResponseStatus.builder().status("loan not found").build());
        else
            result = ResponseEntity.ok(OBJECT_MAPPER.readValue(getResponse.getSourceAsString(), Loan.class));

        return result;
    }


    public PersonLoans personLoans(String docId) throws IOException {

        PersonLoans result = new PersonLoans();

        SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(QueryBuilders.termQuery("document", docId));

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.searchType(SearchType.DFS_QUERY_THEN_FETCH);
        searchRequest.source(builder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

        List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());

        List<Loan> personLoans = new ArrayList<>();

        response.getHits().forEach(hit -> {
                    try {
                        personLoans.add(OBJECT_MAPPER.readValue(hit.getSourceAsString(), Loan.class));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );

        if (!personLoans.isEmpty()) {
            personLoans.forEach(loan -> {
                result.increaseCountLoan();
                result.increaseCountAmountLoan(loan.getAmount());
            });
            result.setLoans(personLoans);
        }
        return result;
    }

    public List<Loan> loansClosed() throws IOException {

        List<Loan> result = new ArrayList<>();

        SearchRequest searchRequest = new SearchRequest("loans");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.size(300);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());

        response.getHits().forEach(hit -> {
            try {
                Loan loan = OBJECT_MAPPER.readValue(hit.getSourceAsString(), Loan.class);

                if ((loan.getStartdate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate().plusMonths(loan.getPeriod()).compareTo(LocalDate.now().withDayOfMonth(1)) < 0))
                    result.add(loan);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return result;
    }

    public List<PersonWithLoans> loansSortByPersonBirthday() throws IOException {

        List<PersonWithLoans> result = new ArrayList<>();

        SearchRequest searchRequest = new SearchRequest("persons");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.size(300);
        searchSourceBuilder.sort("birthday", SortOrder.DESC);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());

        response.getHits().forEach(hit -> {
            try {
                Person person = OBJECT_MAPPER.readValue(hit.getSourceAsString(), Person.class);

                result.add(PersonWithLoans.builder()
                        .birthday(person.getBirthday())
                        .docid(person.getDocid())
                        .gender(person.getGender())
                        .fio(person.getFio())
                        .salary(person.getSalary())
                        .loans(personLoans(person.getDocid()).getLoans())
                        .build());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return result;
    }

    private static String readUsingScanner(String fileName) throws IOException {
        Scanner scanner = new Scanner(new ClassPathResource(fileName).getInputStream(), StandardCharsets.UTF_8.name());
        //здесь мы можем использовать разделитель, например: "\\A", "\\Z" или "\\z"
        String data = scanner.useDelimiter("\\A").next();
        scanner.close();
        return data;
    }
}
