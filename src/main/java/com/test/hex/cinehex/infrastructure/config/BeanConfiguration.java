package com.test.hex.cinehex.infrastructure.config;

import com.test.hex.cinehex.application.service.BookingService;
import com.test.hex.cinehex.application.service.MovieService;
import com.test.hex.cinehex.application.service.MovieShowService;
import com.test.hex.cinehex.application.service.RoomService;
import com.test.hex.cinehex.application.service.SeatService;
import com.test.hex.cinehex.application.service.UserService;
import com.test.hex.cinehex.domain.port.in.CreateMovieShowUseCase;
import com.test.hex.cinehex.domain.port.in.CreateMovieUseCase;
import com.test.hex.cinehex.domain.port.in.CreateRoomUseCase;
import com.test.hex.cinehex.domain.port.in.CreateSeatUseCase;
import com.test.hex.cinehex.domain.port.in.CreateUserUseCase;
import com.test.hex.cinehex.domain.port.out.BookingRepositoryPort;
import com.test.hex.cinehex.domain.port.out.MovieRepositoryPort;
import com.test.hex.cinehex.domain.port.out.MovieShowRepositoryPort;
import com.test.hex.cinehex.domain.port.out.RoomRepositoryPort;
import com.test.hex.cinehex.domain.port.out.SeatRepositoryPort;
import com.test.hex.cinehex.domain.port.out.UserRepositoryPort;
import com.test.hex.cinehex.infrastructure.adapter.in.transaction.TransactionalCreateMovieShowUseCase;
import com.test.hex.cinehex.infrastructure.adapter.in.transaction.TransactionalCreateMovieUseCase;

import com.test.hex.cinehex.infrastructure.adapter.in.transaction.TransactionalCreateRoomUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

  @Bean
  public CreateMovieUseCase createMovieUseCase(MovieRepositoryPort movieRepositoryPort) {
    return new MovieService(movieRepositoryPort);
  }

  @Bean
  public MovieService movieService(MovieRepositoryPort port) {
    return new MovieService(port);
  }

  @Bean
  TransactionalCreateMovieUseCase transactionalCreateMovieUseCase(MovieService movieService) {
    return new TransactionalCreateMovieUseCase(movieService);
  }

  @Bean
  public CreateRoomUseCase createRoomUseCase(RoomRepositoryPort roomRepositoryPort) {
    return new RoomService(roomRepositoryPort);
  }

  @Bean
  public RoomService roomService(RoomRepositoryPort port) {
    return new RoomService(port);
  }

  @Bean
  public TransactionalCreateRoomUseCase transactionalCreateRoomUseCase(RoomService roomService) {
    return new TransactionalCreateRoomUseCase(roomService);
  }

  @Bean
  public CreateSeatUseCase createSeatUseCase(
      SeatRepositoryPort seatRepositoryPort, RoomRepositoryPort roomRepositoryPort) {
    return new SeatService(seatRepositoryPort, roomRepositoryPort);
  }

  @Bean
  public SeatService seatService(SeatRepositoryPort port, RoomRepositoryPort roomRepositoryPort) {
    return new SeatService(port, roomRepositoryPort);
  }

  @Bean
  public CreateMovieShowUseCase createMovieShowUseCase(
      MovieShowRepositoryPort movieShowRepositoryPort,
      RoomRepositoryPort roomRepositoryPort,
      MovieRepositoryPort movieRepositoryPort) {
    return new MovieShowService(movieShowRepositoryPort, roomRepositoryPort, movieRepositoryPort);
  }

  @Bean
  public MovieShowService movieShowService(
      MovieShowRepositoryPort movieShowRepositoryPort,
      RoomRepositoryPort roomRepositoryPort,
      MovieRepositoryPort movieRepositoryPort) {
    return new MovieShowService(movieShowRepositoryPort, roomRepositoryPort, movieRepositoryPort);
  }

  @Bean
  public TransactionalCreateMovieShowUseCase transactionalCreateMovieShowUseCase(
      MovieShowService movieShowService) {
    return new TransactionalCreateMovieShowUseCase(movieShowService);
  }

  @Bean
  public BookingService bookingService(
      BookingRepositoryPort bookingRepositoryPort,
      UserRepositoryPort userRepositoryPort,
      MovieShowRepositoryPort movieShowRepositoryPort,
      SeatRepositoryPort seatRepositoryPort) {
    return new BookingService(
        bookingRepositoryPort, userRepositoryPort, movieShowRepositoryPort, seatRepositoryPort);
  }

  @Bean
  public UserService userService(UserRepositoryPort userRepositoryPort) {
    return new UserService(userRepositoryPort);
  }

  @Bean
  public CreateUserUseCase createUserUseCase(UserRepositoryPort userRepositoryPort) {
    return new UserService(userRepositoryPort);
  }
}
