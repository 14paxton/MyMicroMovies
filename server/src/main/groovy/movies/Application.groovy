package movies

import io.micronaut.runtime.Micronaut
import groovy.transform.CompileStatic

@CompileStatic
class Application {
    static void main(String[] args) {
        Micronaut.run(Application)


//        new Movie(title: "Robocop", genre: "SciFi", numberInStock: 4, dailyRentalRate: 3).save(flush: true)
//        new Movie(title: "Die Hard", genre: "Action", numberInStock: 4, dailyRentalRate: 3).save(flush: true)
//        new Movie(title: "Gladiator", genre: "Action", numberInStock: 4, dailyRentalRate: 3).save(flush: true)
//

    }


}
