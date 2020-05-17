package movies

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import com.sun.xml.internal.ws.developer.Serialization
import grails.gorm.annotation.Entity
import org.bson.types.ObjectId

import javax.persistence.Id

@Entity
 class Movie implements  Serializable{

    @Id
    @JsonSerialize(using = ToStringSerializer)
    ObjectId id

    String title
    String genre
    Long numberInStock
    Long dailyRentalRate


    @Override
     String toString(){
         return "{id: $id, title: $title, genre: $genre,numberInStock: $numberInStock,dailyRentalRate: $dailyRentalRate}"
     }

}
