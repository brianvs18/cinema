package com.example.cinema.usecase;

import com.example.cinema.dto.UserDTO;
import com.example.cinema.entity.UserDocument;
import com.example.cinema.enums.UserErrorEnum;
import com.example.cinema.exceptions.UserException;
import com.example.cinema.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServices {
    private final UserRepository userRepository;

    public Flux<UserDTO> findAll() {
        return userRepository.findAll()
                .map(userDocument -> UserDTO.builder()
                        .id(userDocument.getId())
                        .name(userDocument.getName())
                        .lastname(userDocument.getLastname())
                        .build());
    }

    public Mono<UserDTO> findById(String userId) {
        return userRepository.findById(userId)
                .map(userDocument -> UserDTO.builder()
                        .id(userDocument.getId())
                        .name(userDocument.getName())
                        .lastname(userDocument.getLastname())
                        .build());
    }

    public Mono<UserDTO> saveUser(UserDTO userDTO) {
        return Mono.just(userDTO)
                .filter(user -> Objects.nonNull(user.getName()))
                .filter(user -> Objects.nonNull(user.getLastname()))
                .flatMap(user -> userRepository.save(UserDocument.builder()
                        .name(user.getName())
                        .lastname(user.getLastname())
                        .build()))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new UserException(UserErrorEnum.NON_EMPTY_FIELDS))))
                .thenReturn(userDTO);
    }

    public Mono<Void> deleteUser(String userId){
        return userRepository.deleteById(userId);
    }
}
