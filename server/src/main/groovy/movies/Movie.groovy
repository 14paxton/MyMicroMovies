package movies
import grails.gorm.annotation.Entity
import org.bson.types.ObjectId

@Entity
 class Movie {

 ObjectId id
 String title
 String genre
 Integer numberInStock
 Integer dailyRentalRate


     @Override
     String toString(){
         return "{id: $id, title: $title, genre: $genre,numberInStock: $numberInStock,dailyRentalRate: $dailyRentalRate}"
     }

}
