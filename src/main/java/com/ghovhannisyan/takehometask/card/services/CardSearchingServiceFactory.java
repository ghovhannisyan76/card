package com.ghovhannisyan.takehometask.card.services;

import com.ghovhannisyan.takehometask.card.entities.UserStatus;
import com.ghovhannisyan.takehometask.card.exceptions.CardApiException;
import com.ghovhannisyan.takehometask.card.exceptions.ErrorMessage;
import com.ghovhannisyan.takehometask.card.model.CardSubType;
import com.ghovhannisyan.takehometask.card.model.CardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardSearchingServiceFactory {
    @Autowired
    private ActiveUserDailyQuoteCardSearchingService activeUserDailyQuoteCardSearchingService;

    @Autowired
    private NewUserDailyQuoteCardSearchingService newUserDailyQuoteCardSearchingService;

    @Autowired
    private FullStatusUpdateCardSearchingService fullStatusUpdateCardSearchingService;

    @Autowired
    private IconStatusUpdateCardSearchingService iconStatusUpdateCardSearchingService;

    @Autowired
    private ButtonStatusUpdateCardSearchingService buttonStatusUpdateCardSearchingService;

    public CardSearchingService getCardSearchingService(UserStatus userStatus, CardType cardType, CardSubType cardSubtype) {
        CardSearchingService cardSearchingService;
        if(cardType == CardType.DAILY) {
            cardSearchingService = getDailyCardSearchingService(userStatus);
        } else if(cardType == CardType.STATUS) {
            cardSearchingService = getStatusCardSearchingService(cardSubtype);
        } else {
            // Preventing future card types
            throw new CardApiException(ErrorMessage.INVALID_CARD_TYPE, "Card type is not supported", "cardType");
        }
        return cardSearchingService;
    }

    private CardSearchingService getDailyCardSearchingService(UserStatus userStatus) {
        if(userStatus == UserStatus.NEW) {
            return  newUserDailyQuoteCardSearchingService;
        }

        return activeUserDailyQuoteCardSearchingService;
    }

    private CardSearchingService getStatusCardSearchingService(CardSubType cardSubtype) {
        CardSearchingService cardSearchingService;
        switch (cardSubtype) {
            case FULL:
                cardSearchingService = fullStatusUpdateCardSearchingService;
                break;
            case ICON:
                cardSearchingService = iconStatusUpdateCardSearchingService;
                break;
            case BUTTON:
                cardSearchingService = buttonStatusUpdateCardSearchingService;
                break;
            default:
                // preventing future card subtypes
                throw new CardApiException(ErrorMessage.INVALID_CARD_SUBTYPE, "Card subtype is not supported", "cardSubType");
        }
        return cardSearchingService;
    }
}
