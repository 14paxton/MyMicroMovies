package movies
import grails.gorm.annotation.Entity
import groovy.json.*
import org.bson.types.ObjectId

@Entity
 class Movie {

// ObjectId _id
 String title
 String genre
 Integer numberInStock
 Integer dailyRentalRate


     @Override
     String toString(){
         return "{title: $title, genre: $genre,numberInStock: $numberInStock,dailyRentalRate: $dailyRentalRate}"
     }

}
