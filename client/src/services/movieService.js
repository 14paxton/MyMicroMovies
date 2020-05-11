import http from "./httpService";
import { apiUrl, apiMicronaut } from "../config.json";

const apiEndpoint = apiUrl + "/movies";

function movieUrl(id) {
    return `${apiEndpoint}/${id}`;
}

export function getMovies() {
    // var x = http.get("http://localhost:8080/movies/hi");
    console.log("getsome");
    // console.log(x);
    // return http.get(apiEndpoint);
}

export function getMovie(movieId) {
    return http.get(movieUrl(movieId));
}

export function saveMovie(movie) {
    if (movie._id) {
        const body = { ...movie };
        delete body._id;
        return http.put(movieUrl(movie._id), body);
    }

    return http.post(apiEndpoint, movie);
}

export function deleteMovie(movieId) {
    return http.delete(movieUrl(movieId));
}
