package com.example.cinema.usecase;

import com.example.cinema.dto.UserDTO;
import com.example.cinema.enums.GenericErrorEnum;
import com.example.cinema.enums.UserErrorEnum;
import com.example.cinema.exceptions.GenericException;
import com.example.cinema.exceptions.UserException;
import com.example.cinema.factory.UserFactory;
import com.example.cinema.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServices implements UserFactory {
    private final UserRepository userRepository;
    private final BookingServices bookingServices;

    public Flux<UserDTO> findAll() {
        return userRepository.findAll()
                .map(this::buildUser);
    }

    public Mono<UserDTO> findById(String userId) {
        return userRepository.findById(userId)
                .map(this::buildUser);
    }

    public Mono<UserDTO> saveUser(UserDTO userDTO) {
        return Mono.just(userDTO)
                .filter(user -> StringUtils.isNotBlank(userDTO.getName()) && StringUtils.isNotBlank(userDTO.getLastname()))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new GenericException(GenericErrorEnum.NON_EMPTY_FIELDS))))
                .filter(userData -> Objects.nonNull(userData.getId()))
                .flatMap(userData -> findById(userDTO.getId())
                        .map(userDB -> editBuildUser(userDTO, userDB))
                        .switchIfEmpty(Mono.defer(() -> Mono.error(new UserException(UserErrorEnum.USER_IS_NOT_EXISTS)))))
                .switchIfEmpty(Mono.defer(() -> Mono.just(userDTO)
                        .map(this::saveBuildUser)
                ))
                .flatMap(userRepository::save)
                .thenReturn(userDTO);
    }

    public Mono<Void> deleteUser(String userId) {
        return bookingServices.findAllByUserId(userId)
                .switchIfEmpty(userRepository.deleteById(userId).then(Mono.empty()))
                .then();
    }
}