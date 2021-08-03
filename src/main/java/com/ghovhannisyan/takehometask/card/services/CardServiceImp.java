package com.ghovhannisyan.takehometask.card.services;

import com.ghovhannisyan.takehometask.card.entities.CardEntity;
import com.ghovhannisyan.takehometask.card.entities.UserEntity;
import com.ghovhannisyan.takehometask.card.entities.UserStatus;
import com.ghovhannisyan.takehometask.card.exceptions.CardApiException;
import com.ghovhannisyan.takehometask.card.exceptions.ErrorMessage;
import com.ghovhannisyan.takehometask.card.model.CardResponse;
import com.ghovhannisyan.takehometask.card.model.CardSubType;
import com.ghovhannisyan.takehometask.card.model.CardType;
import com.ghovhannisyan.takehometask.card.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardServiceImp implements CardService {

    @Autowired
    private CardSearchingServiceFactory cardSearchingServiceFactory;

    @Autowired
    private UserRepository userRepository;

    public CardResponse getCard(String username, CardType cardType, CardSubType cardSubtype) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        UserStatus userStatus = user.orElseThrow(() -> new CardApiException(ErrorMessage.NOT_FOUND,
                "User is not found", "username"))
                .getStatus();

        CardSearchingService cardSearchingService =
                cardSearchingServiceFactory.getCardSearchingService(userStatus, cardType, cardSubtype);

        CardEntity cardEntity = cardSearchingService.search();

        return convertCardToCardResponse(cardEntity);
    }

    private CardResponse convertCardToCardResponse(CardEntity cardEntity) {
        CardResponse cardResponse = new CardResponse();
        return cardResponse
                .author(cardEntity.getAuthor())
                .button(cardEntity.getButton())
                .icon(cardEntity.getIcon())
                .message(cardEntity.getMessage())
                .title(cardEntity.getTitle());
    }
}
