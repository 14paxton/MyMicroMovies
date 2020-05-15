package movies

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
import io.reactivex.subscribers.TestSubscriber
import org.bson.Document
import org.reactivestreams.Subscriber

import static com.mongodb.client.model.Filters.*
import io.reactivex.*

import static tour.SubscriberHelpers.ObservableSubscriber
import static tour.SubscriberHelpers.PrintSubscriber


@Controller("/movies")
public class MoviesController {

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
    @Get("/find/{id}")
    Movie movie(String id) {
        ObservableSubscriber<Movie> subscriber = new ObservableSubscriber<Movie>()
        getMovieCollection().find(eq('_id', id)).subscribe(subscriber)
        subscriber.await().received[0]

    }



    @Produces(MediaType.TEXT_JSON)
    @Get("/delete/{id}")
    HttpResponse<String> delete(String id) {
//        ObservableSubscriber<DeleteResult> subscriber = new ObservableSubscriber<DeleteResult>()
//        getMovieCollection().deleteOne(eq('_id', id))
//                .subscribe(subscriber)
//        def x = subscriber.await()
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

    @Post(value = "/save", consumes = MediaType.APPLICATION_JSON)
    //{title: "adsfadsfasd", genreId: "", numberInStock: "4", dailyRentalRate: "4"}
     HttpResponse saveMovie(@Body String data) {
        return HttpResponse.ok();
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

