package com.ghovhannisyan.takehometask.card.services;

import com.ghovhannisyan.takehometask.card.entities.CardEntity;
import com.ghovhannisyan.takehometask.card.entities.UserEntity;
import com.ghovhannisyan.takehometask.card.entities.UserStatus;
import com.ghovhannisyan.takehometask.card.exceptions.CardApiException;
import com.ghovhannisyan.takehometask.card.exceptions.ErrorMessage;
import com.ghovhannisyan.takehometask.card.model.CardResponse;
import com.ghovhannisyan.takehometask.card.model.CardType;
import com.ghovhannisyan.takehometask.card.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CardServiceImpTest {

    @Mock
    private CardSearchingServiceFactory cardSearchingServiceFactory;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CardServiceImp cardServiceImp;

    @Test
    public void positiveGetCard() {
        String user1 = "user1";
        UserEntity userEntityNew = UserEntity.builder()
				.id(1L)
                .username(user1)
                .status(UserStatus.NEW)
                .build();

        when(userRepository.findByUsername(eq(user1))).thenReturn(Optional.of(userEntityNew));

        CardSearchingService cardSearchingService = Mockito.mock(CardSearchingService.class);

        when(cardSearchingServiceFactory.getCardSearchingService(eq(UserStatus.NEW), eq(CardType.DAILY), isNull()))
                .thenReturn(cardSearchingService);

        CardEntity cardEntityNew = CardEntity.builder()
				.id(1L)
                .cardType(com.ghovhannisyan.takehometask.card.entities.CardType.DAILY_NEW)
                .author("Author1")
                .title("Title1")
                .message("message1")
                .cardShowDate(LocalDate.now())
                .build();

        when(cardSearchingService.search()).thenReturn(cardEntityNew);

        CardResponse cardResponse = cardServiceImp.getCard(user1, CardType.DAILY, null);
        assertNotNull(cardResponse);
        assertEquals("Author1", cardResponse.getAuthor());
        assertEquals("Title1", cardResponse.getTitle());
        assertEquals("message1", cardResponse.getMessage());
        assertNull(cardResponse.getButton());
        assertNull(cardResponse.getIcon());

        verify(userRepository).findByUsername(eq(user1));
        verify(cardSearchingServiceFactory).getCardSearchingService(eq(UserStatus.NEW), eq(CardType.DAILY), isNull());
        verify(cardSearchingService).search();
    }

    @Test
    public void negativeGetCardUserNotFound() {
        String user1 = "user1";
        when(userRepository.findByUsername(eq(user1))).thenReturn(Optional.empty());
        CardApiException cardApiException = assertThrows(CardApiException.class, () -> {
            cardServiceImp.getCard(user1, CardType.DAILY, null);
        });

        assertEquals(ErrorMessage.NOT_FOUND, cardApiException.getErrorMessage());
        assertEquals("User is not found", cardApiException.getMessage());
        assertEquals("username", cardApiException.getSource());

        verify(userRepository).findByUsername(eq(user1));
        verify(cardSearchingServiceFactory, times(0)).getCardSearchingService(any(), any(), any());
    }

    @Test
    public void negativeGetCardServiceFactoryFails() {
        String user1 = "user1";
        UserEntity userEntityNew = UserEntity.builder()
                .id(1L)
                .username(user1)
                .status(UserStatus.NEW)
                .build();

        when(userRepository.findByUsername(eq(user1))).thenReturn(Optional.of(userEntityNew));
        CardApiException cardApiExceptionInvalidCardType = new CardApiException(ErrorMessage.INVALID_CARD_TYPE,
                "Card type is not supported", "cardType");
        doThrow(cardApiExceptionInvalidCardType).when(cardSearchingServiceFactory)
                .getCardSearchingService(eq(UserStatus.NEW), eq(CardType.DAILY), isNull());
        CardApiException cardApiException = assertThrows(CardApiException.class, () -> {
            cardServiceImp.getCard(user1, CardType.DAILY, null);
        });

        assertEquals(ErrorMessage.INVALID_CARD_TYPE, cardApiException.getErrorMessage());
        assertEquals("Card type is not supported", cardApiException.getMessage());
        assertEquals("cardType", cardApiException.getSource());

        verify(userRepository).findByUsername(eq(user1));
        verify(cardSearchingServiceFactory).getCardSearchingService(eq(UserStatus.NEW), eq(CardType.DAILY), isNull());
    }

}
