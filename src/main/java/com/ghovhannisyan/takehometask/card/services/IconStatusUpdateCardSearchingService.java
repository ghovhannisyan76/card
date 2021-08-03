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
public class IconStatusUpdateCardSearchingService implements CardSearchingService {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public CardEntity search() {
        LocalDate localDate = LocalDate.now();
        Optional<CardEntity> optionalCard =
                cardRepository.findByCardTypeAndCardShowDateAndIconNotNullAndButtonNull(CardType.STATUS_UPDATE, localDate);
        if(!optionalCard.isPresent()) {
            optionalCard = cardRepository.findByCardTypeAndCardShowDateAndIconNotNullAndButtonNotNull(CardType.STATUS_UPDATE, localDate);
            if(!optionalCard.isPresent()) {
                throw new CardApiException(ErrorMessage.NOT_FOUND, "Status card is not found, where button is optional", "internal");
            }
        }
        return optionalCard.get();
    }
}
