package com.example.Qatar2022.Services;

import com.example.Qatar2022.entities.UserEntity;
import com.example.Qatar2022.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<UserEntity> getUsers(){
        return userRepository.findAll();
    }


    public void addNewUser(UserEntity user) {
        Optional<UserEntity> userOptional = userRepository.findUserEntityByEmail(user.getEmail());
        if (userOptional.isPresent()){
            throw new IllegalStateException("email taken");
        }
        userRepository.save(user);
    }
    @Transactional
    public void updateUser(Long userId, String firstName, String lastName, String email, String password, String country, String tel, LocalDate dob) {
        UserEntity user = userRepository.findById(userId).orElseThrow(()-> new IllegalStateException(
                "user with id " + userId + " does not exist"));
        if (firstName!=null &&
                firstName.length() > 0 &&
                !Objects.equals(user.getFirstName(), firstName)){
            user.setFirstName(firstName);
        }
        if (lastName!=null &&
                lastName.length() > 0 &&
                !Objects.equals(user.getLastName(), lastName)){
            user.setLastName(lastName);
        }
        if (email!=null &&
                email.length()>0 &&
                !Objects.equals(user.getEmail(), email)){
            Optional<UserEntity> userOptional = userRepository.findUserEntityByEmail(email);
            if (userOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }
            user.setEmail(email);

        }

        if (password!=null &&
                password.length() > 0 &&
                !Objects.equals(user.getPassword(), password)){
            user.setPassword(password);
        }

        if (country!=null &&
                country.length() > 0 &&
                !Objects.equals(user.getCountry(), country)){
            user.setCountry(country);
        }

        if (tel!=null &&
                tel.length() > 0 &&
                !Objects.equals(user.getTel(), tel)){
            user.setTel(tel);
        }
        if (dob!=null &&
                dob.toString().length() > 0 &&
                !Objects.equals(user.getDob(), dob)){
            user.setDob(dob);
        }

    }
}
