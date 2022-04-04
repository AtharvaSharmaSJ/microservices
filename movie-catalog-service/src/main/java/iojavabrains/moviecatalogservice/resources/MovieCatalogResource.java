package iojavabrains.moviecatalogservice.resources;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import iojavabrains.moviecatalogservice.models.CatalogItem;
import iojavabrains.moviecatalogservice.models.Movie;
import iojavabrains.moviecatalogservice.models.Rating;
import iojavabrains.moviecatalogservice.models.UserRating;
import iojavabrains.moviecatalogservice.services.MovieInfo;
import iojavabrains.moviecatalogservice.services.UserRatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserRatingInfo userRatingInfo;

    @Autowired
    private MovieInfo movieInfo;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
        // get all rated movies id - hardcoded for now
        UserRating userRating = userRatingInfo.getUserRating(userId);
        List<Rating> ratings = userRating.getUserRating();

        return ratings.stream().map(rating->{
            return movieInfo.getCatalog(rating);
        }).collect(Collectors.toList());
    }






}
