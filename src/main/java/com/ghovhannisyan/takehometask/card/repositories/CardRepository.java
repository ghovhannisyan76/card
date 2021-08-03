package com.ghovhannisyan.takehometask.card.repositories;

import com.ghovhannisyan.takehometask.card.entities.CardEntity;
import com.ghovhannisyan.takehometask.card.entities.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {
    Optional<CardEntity> findByCardTypeAndCardShowDate(CardType cardType, LocalDate cardShowDate);
    Optional<CardEntity> findByCardTypeAndCardShowDateAndIconNotNullAndButtonNotNull(CardType cardType, LocalDate cardShowDate);
    Optional<CardEntity> findByCardTypeAndCardShowDateAndIconNotNullAndButtonNull(CardType cardType, LocalDate cardShowDate);
    Optional<CardEntity> findByCardTypeAndCardShowDateAndIconNullAndButtonNotNull(CardType cardType, LocalDate cardShowDate);
}
