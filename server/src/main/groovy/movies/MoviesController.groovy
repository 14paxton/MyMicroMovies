package movies

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces
import grails.gorm.transactions.Transactional
import com.mongodb.client.FindIterable
import static com.mongodb.client.model.Filters.*
import io.reactivex.*

@Controller("/movies")
public class MoviesController {

    private final MongoClient mongoClient


    public MoviesController(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Produces(MediaType.TEXT_PLAIN)
    @Get("/{name}")
    @Transactional
    HttpResponse<String> hello(String name) {

//        mongoClient.getDatabase('vidly').getCollection('movies')

//        new Movie(title: "Robocop", genre: "scifi", numberInStock: 6, dailyRentalRate: "1").save(flush: true)

        return HttpResponse.ok("hello" + name);
    }

}

