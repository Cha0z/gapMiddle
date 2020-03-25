package com.education.nosql;

import com.education.nosql.domain.Domain;
import com.education.nosql.repository.DomainRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;


/**
 * Application was developed with spring boot, because the difference in general in configuration and starting
 * first version of jmx module was written without spring, but i wanted to try more than i`ve done in the first time
 */

@SpringBootApplication
@EnableScheduling
public class GapApplication {
    public static void main(String[] args) {
        SpringApplication.run(GapApplication.class, args);
    }


    @Bean
    CommandLineRunner init(DomainRepository domainRepository) {

        return args -> {

            MongoClient mongoClient = new MongoClient();
            MongoDatabase db = mongoClient.getDatabase("local");
            MongoCollection<Document> collection = db.getCollection("domain");


//            deleteAllWithFieldAndValue(collection, "domain", "Epam");
//            create100ObjectsForTest(domainRepository);


            List<Document> documents = new ArrayList<>();
            List<Domain> domains = new ArrayList<>();
            collection.find(gt("age", 80)).into(documents);
            ObjectMapper mapper = new ObjectMapper();

            JsonWriterSettings settings = JsonWriterSettings.builder()
                    .int64Converter((value, writer) -> writer.writeNumber(value.toString()))
                    .stringConverter((value, writer) -> writer.writeString(value))
                    .int32Converter((value, writer) -> writer.writeNumber(value.toString()))
                    .booleanConverter((value, writer) -> writer.writeBoolean(value.booleanValue()))

                    .build();

            for (Document doc : documents) {
                domains.add(mapper.readValue(doc.toJson(settings), Domain.class));
            }

            System.out.println(domains.size());


////
//            Domain obj = domainRepository.findById(5L).get();
//            System.out.println(obj);
            System.out.println(domainRepository.findAll().size());
//
//            Domain obj2 = domainRepository.findFirstByDomain("mkyong.com");
//            System.out.println(obj2);

//            int n = domainRepository.updateDomain("mkyong.com", true);
//            System.out.println("Number of records updated : " + n);

        };

    }

    private void create100ObjectsForTest(DomainRepository domainRepository) {
        for (int i = 0; i < 100; i++) {
            domainRepository.insert(new Domain(i, "Epam" + i, i, false));
        }
    }

    private void deleteAllWithFieldAndValue(MongoCollection<Document> collection, String field, String value) {
        collection.deleteMany(eq(field, value));
    }


}