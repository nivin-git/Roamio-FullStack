package com.roamio.config;

import com.roamio.destination.*;
import com.roamio.provider.*;
import com.roamio.user.Role;
import com.roamio.user.User;
import com.roamio.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {
    @Bean
    CommandLineRunner seed(DestinationRepository destinations,UserRepository users,
                           ProviderProfileRepository providers,PasswordEncoder encoder){
        return args -> {
            if(destinations.count()==0){
                destinations.save(new Destination("Kerala","India","Backwaters, beaches, food and hill stations.",
                        "https://images.unsplash.com/photo-1602216056096-3b40cc0c9944"));
                destinations.save(new Destination("Kyoto","Japan","Temples, traditional neighborhoods and seasonal culture.",
                        "https://images.unsplash.com/photo-1493976040374-85c8e12f0c0e"));
                destinations.save(new Destination("Uganda","Uganda","Wildlife, gorilla trekking and lakes.",
                        "https://images.unsplash.com/photo-1547471080-7cc2caa01a7e"));
                destinations.save(new Destination("Rome","Italy","Ancient history, art and Italian cuisine.",
                        "https://images.unsplash.com/photo-1529260830199-42c24126f198"));
            }

            if(!users.existsByEmailIgnoreCase("provider@demo.com")){
                User u=new User();u.setEmail("provider@demo.com");u.setFullName("Safari Experts");
                u.setPasswordHash(encoder.encode("password"));u.setRole(Role.PROVIDER);users.save(u);
                ProviderProfile p=new ProviderProfile();p.setUser(u);p.setCompanyName("Safari Experts");
                p.setCountry("Uganda");p.setDestinations("Uganda,Kenya,Tanzania");
                p.setSpecialties("wildlife,photography,hiking");p.setRating(4.8);providers.save(p);
            }
        };
    }
}
