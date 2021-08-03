package com.ghovhannisyan.takehometask.card.services;

import com.ghovhannisyan.takehometask.card.entities.UserStatus;
import com.ghovhannisyan.takehometask.card.exceptions.CardApiException;
import com.ghovhannisyan.takehometask.card.exceptions.ErrorMessage;
import com.ghovhannisyan.takehometask.card.model.CardSubType;
import com.ghovhannisyan.takehometask.card.model.CardType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class CardSearchingServiceFactoryTest {

    @Mock
    private ActiveUserDailyQuoteCardSearchingService activeUserDailyQuoteCardSearchingService;

    @Mock
    private NewUserDailyQuoteCardSearchingService newUserDailyQuoteCardSearchingService;

    @Mock
    private FullStatusUpdateCardSearchingService fullStatusUpdateCardSearchingService;

    @Mock
    private IconStatusUpdateCardSearchingService iconStatusUpdateCardSearchingService;

    @Mock
    private ButtonStatusUpdateCardSearchingService buttonStatusUpdateCardSearchingService;

    @InjectMocks
    private CardSearchingServiceFactory cardSearchingServiceFactory;

    @Test
    public void positiveGetCardSearchingServiceNewUserDaily() {
        CardSearchingService cardSearchingService =
                cardSearchingServiceFactory.getCardSearchingService(UserStatus.NEW, CardType.DAILY, null);
        assertEquals(newUserDailyQuoteCardSearchingService, cardSearchingService);
    }

    @Test
    public void positiveGetCardSearchingServiceActiveUserDaily() {
        CardSearchingService cardSearchingService =
                cardSearchingServiceFactory.getCardSearchingService(UserStatus.ACTIVE, CardType.DAILY, null);
        assertEquals(activeUserDailyQuoteCardSearchingService, cardSearchingService);
    }

    @Test
    public void positiveGetCardSearchingServiceFullStatus() {
        CardSearchingService cardSearchingService =
                cardSearchingServiceFactory.getCardSearchingService(UserStatus.NEW, CardType.STATUS, CardSubType.FULL);
        assertEquals(fullStatusUpdateCardSearchingService, cardSearchingService);
    }

    @Test
    public void positiveGetCardSearchingServiceIconStatus() {
        CardSearchingService cardSearchingService =
                cardSearchingServiceFactory.getCardSearchingService(UserStatus.NEW, CardType.STATUS, CardSubType.ICON);
        assertEquals(iconStatusUpdateCardSearchingService, cardSearchingService);
    }

    @Test
    public void positiveGetCardSearchingServiceButtonStatus() {
        CardSearchingService cardSearchingService =
                cardSearchingServiceFactory.getCardSearchingService(UserStatus.NEW, CardType.STATUS, CardSubType.BUTTON);
        assertEquals(buttonStatusUpdateCardSearchingService, cardSearchingService);
    }

    @Test
    public void negativeGetCardSearchingServiceUnknownCardType() {
        CardApiException cardApiException = assertThrows(CardApiException.class, () -> {
                cardSearchingServiceFactory.getCardSearchingService(UserStatus.NEW, null, null);});
        assertEquals(ErrorMessage.INVALID_CARD_TYPE, cardApiException.getErrorMessage());
        assertEquals("Card type is not supported", cardApiException.getMessage());
        assertEquals("cardType", cardApiException.getSource());
    }
}
