package com.msevent.ms_event_manager.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.assertj.core.api.Assertions;

import com.msevent.ms_event_manager.entities.Event;
import com.msevent.ms_event_manager.entities.dto.EventRequestDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void createProduct_WithValidData_Returns201() {
        EventRequestDto requestDto = new EventRequestDto(
                "Event Name",
                "2021-10-10T10:00:00",
                "64260000");

        Event sut = webTestClient
                .post()
                .uri("/api/events/create-event")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Event.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(sut).isNotNull();
        Assertions.assertThat(sut.getEventName()).isNotNull();
        Assertions.assertThat(sut.getEventDateTime()).isNotNull();
        Assertions.assertThat(sut.getCep()).isNotNull();
    }

    @Test
    public void createProduct_WithInvalidData_Returns422() {
        EventRequestDto requestDto = new EventRequestDto(
                "",
                "2021-10-10T10:00:00",
                "64260000");

        webTestClient
                .post()
                .uri("/api/events/create-event")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        ;
    }

    @Test
    public void createProduct_WithInvalidCep_Returns422() {
        EventRequestDto requestDto = new EventRequestDto(
                "Event Name",
                "2021-10-10T10:00:00",
                "6426000");

        webTestClient
                .post()
                .uri("/api/events/create-event")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void createProduct_WithInvalidCep_Returns400() {
        EventRequestDto requestDto = new EventRequestDto(
                "Event Name",
                "2021-10-10T10:00:00",
                "1234567890");

        webTestClient
                .post()
                .uri("/api/events/create-event")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void getAllEvents_Returns200() {
        webTestClient
                .get()
                .uri("/api/events/get-all-events")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void getAllEventsSorted_Returns200() {
        webTestClient
                .get()
                .uri("/api/events/get-all-events/sorted")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void getEventById_Returns200() {
        EventRequestDto requestDto = new EventRequestDto(
                "Event Name",
                "2021-10-10T10:00:00",
                "64260000");

        String eventId = webTestClient
                .post()
                .uri("/api/events/create-event")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Event.class)
                .returnResult()
                .getResponseBody()
                .getId();

        webTestClient
                .get()
                .uri("/api/events/get-event/{id}", eventId)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void getEventById_Returns404() {
        webTestClient
                .get()
                .uri("/api/events/get-event/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void updateEvent_Returns200() {
        EventRequestDto requestDto = new EventRequestDto(
                "Event UPDATE Name",
                "2021-10-10T10:00:00",
                "01020-000");

        String eventId = webTestClient
                .post()
                .uri("/api/events/create-event")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Event.class)
                .returnResult()
                .getResponseBody()
                .getId();

        webTestClient
                .put()
                .uri("/api/events/update-event/{id}", eventId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void updateEvent_Returns404() {
        EventRequestDto requestDto = new EventRequestDto(
                "Event UPDATE Name",
                "2021-10-10T10:00:00",
                "01020-000");

        webTestClient
                .put()
                .uri("/api/events/update-event/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void deleteEvent_Returns204() {
        EventRequestDto requestDto = new EventRequestDto(
                "Event Name",
                "2021-10-10T10:00:00",
                "64260000");

        String eventId = webTestClient
                .post()
                .uri("/api/events/create-event")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Event.class)
                .returnResult()
                .getResponseBody()
                .getId();

        webTestClient
                .delete()
                .uri("/api/events/delete-event/{id}", eventId)
                .exchange()
                .expectStatus().isNoContent();
    }
}
