package movies


import com.mongodb.reactivestreams.client.FindPublisher
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection
import com.mongodb.reactivestreams.client.Success;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces
import grails.gorm.transactions.Transactional
import com.mongodb.client.FindIterable
import io.reactivex.subscribers.*

import org.reactivestreams.Publisher
//import org.reactivestreams.Subscriber

import org.bson.Document
import org.reactivestreams.Subscriber;

import static com.mongodb.client.model.Filters.*
import io.reactivex.*

import static tour.SubscriberHelpers.ObservableSubscriber
import static tour.SubscriberHelpers.PrintDocumentSubscriber


@Controller("/movies")
public class MoviesController {

    private final MongoClient mongoClient


     MoviesController(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

//    @Produces(MediaType.TEXT_PLAIN)
//    @Get("/{name}")
//    @Transactional
//    HttpResponse<String> hello(String name) {
//
//        def col = mongoClient.getDatabase('vidly').getCollection('movie')
//        TestSubscriber sub = new TestSubscriber()
//        col.find(eq("title", "Robocop")).subscribe(sub)
//
////        TestSubscriber subscriber = new TestSubscriber();
////        col.drop().subscribe(subscriber);
////        subscriber.awaitTerminalEvent();
//
//        return HttpResponse.ok("hello" + sub.await().values().first().toString());
//    }


    @Produces(MediaType.TEXT_JSON)
    @Get("/{name}")
    HttpResponse<String> movie(String name) {

        ObservableSubscriber<Movie> subscrib2 = new ObservableSubscriber<Movie>()
        def col2 = mongoClient.getDatabase('vidly').getCollection('movie', Movie.class)
        col2.find().subscribe(subscrib2 )
        def x = subscrib2.await()

        return HttpResponse.ok(x);
    }


    TestSubscriber<Movie> getMovie() {
        TestSubscriber sub = new TestSubscriber()
        findMovie('Robocop').subscribe(sub)
        return sub
    }




    private FindPublisher findMovie(movie) {
        return mongoClient
                .getDatabase("vidly")
                .getCollection("movie", Movie.class)
                .find(eq("title", movie))
    }



}

