package edu.kit.stateManager.infrastructure.dao;

import edu.kit.stateManager.logic.model.state.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RepositoryRestResource
public interface StateRepository extends JpaRepository<State, Long> {

    /* List<State> findAllByPublicationDateBetweenAndHistoryId
          (LocalDate from, LocalDate to, long historyId);*/

    List<State> findAllByPublicationDateBetweenAndHistoryIdOrderByPublicationDateDesc
            (LocalDate from, LocalDate to, long historyId);

    List<State> findAllByEnteredAtBetweenAndHistoryIdOrderByEnteredAtDesc
            (LocalDateTime from, LocalDateTime to, long historyId);

    List<State> findAllByHistoryIdOrderByEnteredAtDesc(long historyId);

    List<State> findAllByEnteredAtAfterAndHistoryIdOrderByEnteredAtDesc(LocalDateTime from, long historyId);

    List<State> findAllByEnteredAtBeforeAndHistoryIdOrderByEnteredAtDesc(LocalDateTime to, long historyId);
/*
   List<State> findAllByPublicationDate(LocalDate publicationDate);

   List<State> findAllByPublicationDateAfter(LocalDate publicationDate);

   List<State> findAllByPublicationDateBefore(LocalDate publicationDate);*/

}
