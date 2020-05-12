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


//     Movie(){}
//
//     Movie(title, genre, numberInStock, dailyRentalRate){
//        this.title = title
//        this.genre = genre
//        this.numberInStock = numberInStock
//        this.dailyRentalRate = dailyRentalRate
//    }
//
//     @Override
//     String toString(){
//         return """Movie{
//                        |title: $title,
//                        |genre: $genre,
//                        |numberInStock: $numberInStock,
//                        |dailyRentalRate: $dailyRentalRate}""".stripMargin()
//     }


}
