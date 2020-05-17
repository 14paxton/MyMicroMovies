import http from "./httpService";
import { apiUrl, apiMicronaut } from "../config.json";

const apiEndpoint = apiUrl + "/movies";
const microEndpoint = apiMicronaut + "/movies";

function movieUrl(id) {
    return `${microEndpoint}/${id}`;
}

export function getMovies() {
    return http.get(microEndpoint);
}

export function getMovie(movieId) {
    console.log(movieId)
    return http.get(movieUrl(movieId));
}

export function saveMovie(movie) {
    if (movie.id) {
        const body = { ...movie };
        delete body._id;
        return http.put(movieUrl(movie.id), body);
    }

    return http.post(microEndpoint, movie);
}

export function deleteMovie(movieId) {
    return http.delete(movieUrl(movieId));
}
