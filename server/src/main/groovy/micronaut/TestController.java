package micronaut;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

@Controller("/test")
public class TestController {


    @Produces(MediaType.TEXT_PLAIN)
    @Get("/{name}")
    HttpResponse<String> getgud(String name) {
        return HttpResponse.ok("Hello, TEST "+ name );
    }


}
