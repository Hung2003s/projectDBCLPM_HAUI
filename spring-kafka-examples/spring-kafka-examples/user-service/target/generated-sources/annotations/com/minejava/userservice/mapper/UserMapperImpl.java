package com.minejava.userservice.mapper;

import com.minejava.userservice.model.User;
import com.minejava.utilservice.payload.UserPayload;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-17T17:06:02+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.40.0.z20241023-1306, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserPayload userServiceToUserPayload(User user) {
        if ( user == null ) {
            return null;
        }

        UserPayload userPayload = new UserPayload();

        userPayload.setEmail( user.getEmail() );
        userPayload.setId( user.getId() );
        userPayload.setName( user.getName() );
        userPayload.setUsername( user.getUsername() );

        return userPayload;
    }

    @Override
    public User userPayloadToUserService(UserPayload user) {
        if ( user == null ) {
            return null;
        }

        User user1 = new User();

        user1.setId( user.getId() );
        user1.setUsername( user.getUsername() );
        user1.setEmail( user.getEmail() );
        user1.setName( user.getName() );

        return user1;
    }
}
