package movies

import com.fasterxml.jackson.core.JsonGenerator
import com.mongodb.util.JSON
import io.micronaut.http.annotation.Delete
import org.bson.types.ObjectId
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.client.result.DeleteResult
import com.mongodb.reactivestreams.client.FindPublisher
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection
import com.mongodb.reactivestreams.client.Success;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces
import grails.gorm.transactions.Transactional
import com.mongodb.client.FindIterable
import io.micronaut.http.annotation.Put
import io.reactivex.subscribers.TestSubscriber
import org.bson.Document
import org.reactivestreams.Subscriber

import static com.mongodb.client.model.Filters.*
import io.reactivex.*

import static RxJavaHelpers.SubscriberHelpers.ObservableSubscriber
import static RxJavaHelpers.SubscriberHelpers.PrintSubscriber


@Controller("/movies")
public class MoviesController {
//    private static final Logger LOG = LoggerFactory.getLogger("movies")

    private final MongoClient mongoClient

     MoviesController(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Produces(MediaType.APPLICATION_JSON )
    @Get("/")
    List<Movie> allMovies() {
        ObservableSubscriber<Movie> subscriber = new ObservableSubscriber<Movie>()
        getMovieCollection().find().subscribe(subscriber)
        subscriber.await().received.collect {it as Movie}
    }

    @Produces(MediaType.APPLICATION_JSON )
    @Get("/{id}")
    Movie find(String id) {
        def objId = new ObjectId(id)
        ObservableSubscriber<Movie> subscriber = new ObservableSubscriber<Movie>()
        getMovieCollection().find(eq('_id', objId)).subscribe(subscriber)
        subscriber.await().received[0]

    }



    @Produces(MediaType.TEXT_JSON)
    @Delete("/{id}")
    HttpResponse<String> delete(String id) {
        def tempID = Movie.first().id
        ObservableSubscriber<DeleteResult> subscriber = new ObservableSubscriber<DeleteResult>()
        getMovieCollection().deleteOne(eq('_id', tempID))
                .subscribe(subscriber)
        def deleteResult = subscriber.await().getReceived().first()
        if(deleteResult.getDeletedCount() < 1)
        {
            return HttpResponse.notFound('{false}')
        }

        return HttpResponse.ok('{true}')

    }

    @Put(value = "/", consumes = MediaType.APPLICATION_JSON)
     HttpResponse update(@Body String data) {
        return HttpResponse.ok();
    }

    @Post(value = "/", consumes = MediaType.APPLICATION_JSON)
    @Transactional
    HttpResponse save(@Body Movie data) {

        def col =  mongoClient
                .getDatabase("vidly")
                .getCollection("movie")

        Document newMovie = new Document()
                .append("title", data.title)
                .append("genre", data.genre)
                .append("numberInStock", data.numberInStock)
                .append("dailyRentalRat", data.dailyRentalRate)


        ObservableSubscriber<Success> subscriber = new ObservableSubscriber<Success>()
        getMovieCollection().insertOne(newMovie ).subscribe(subscriber)
//        col.insertOne(doc).subscribe(subscriber)
        subscriber.await()

        return HttpResponse.ok('{true}')

    }



    private MongoCollection<Movie> getMovieCollection(){
        return mongoClient
                .getDatabase("vidly")
                .getCollection("movie", Movie.class)
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

}

