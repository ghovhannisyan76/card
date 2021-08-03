package com.ghovhannisyan.takehometask.card.services;

import com.ghovhannisyan.takehometask.card.entities.CardEntity;
import com.ghovhannisyan.takehometask.card.entities.CardType;
import com.ghovhannisyan.takehometask.card.exceptions.CardApiException;
import com.ghovhannisyan.takehometask.card.exceptions.ErrorMessage;
import com.ghovhannisyan.takehometask.card.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class ActiveUserDailyQuoteCardSearchingService implements CardSearchingService {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public CardEntity search() {
        Optional<CardEntity> optionalCard = cardRepository.findByCardTypeAndCardShowDate(CardType.DAILY_ACTIVE, LocalDate.now());
        if(!optionalCard.isPresent()) {
            throw new CardApiException(ErrorMessage.NOT_FOUND, "Daily quote card is not found for the active user", "internal");
        }
        return optionalCard.get();
    }
}
