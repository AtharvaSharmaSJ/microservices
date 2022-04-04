package iojavabrains.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import iojavabrains.moviecatalogservice.models.Rating;
import iojavabrains.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
@Service
public class UserRatingInfo {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackUserRating")
    public UserRating getUserRating(String userId) {
        UserRating userRating = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId, UserRating.class);
        return userRating;
    }

    public UserRating getFallbackUserRating(String userId){
        UserRating userRating = new UserRating();
        userRating.setUserId(userId);
        userRating.setUserRating(
                Arrays.asList(
                        new Rating("0",0)
                )
        );
        return userRating;
    }
}
