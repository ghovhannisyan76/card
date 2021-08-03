package com.ghovhannisyan.takehometask.card.services;

import com.ghovhannisyan.takehometask.card.entities.CardEntity;
import com.ghovhannisyan.takehometask.card.entities.CardType;
import com.ghovhannisyan.takehometask.card.exceptions.CardApiException;
import com.ghovhannisyan.takehometask.card.exceptions.ErrorMessage;
import com.ghovhannisyan.takehometask.card.repositories.CardRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ActiveUserDailyQuoteCardSearchingServiceTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private ActiveUserDailyQuoteCardSearchingService activeUserDailyQuoteCardSearchingService;

    @Test
    public void positiveSearch() {

        CardEntity cardEntityActive = CardEntity.builder()
                .id(1L)
                .cardType(CardType.DAILY_ACTIVE)
                .author("Author1")
                .title("Title1")
                .message("message1")
                .cardShowDate(LocalDate.now())
                .build();

        when(cardRepository.findByCardTypeAndCardShowDate(eq(CardType.DAILY_ACTIVE), eq(LocalDate.now())))
                .thenReturn(Optional.of(cardEntityActive));

        CardEntity cardEntity = activeUserDailyQuoteCardSearchingService.search();
        assertEquals(cardEntityActive, cardEntity);
        verify(cardRepository).findByCardTypeAndCardShowDate(eq(CardType.DAILY_ACTIVE), eq(LocalDate.now()));
    }

    @Test
    public void negativeSearch() {

        when(cardRepository.findByCardTypeAndCardShowDate(eq(CardType.DAILY_ACTIVE), eq(LocalDate.now())))
                .thenReturn(Optional.empty());

        CardApiException cardApiException = assertThrows(CardApiException.class,
                () -> {activeUserDailyQuoteCardSearchingService.search();});
        assertEquals(ErrorMessage.NOT_FOUND, cardApiException.getErrorMessage());
        assertEquals("Daily quote card is not found for the active user", cardApiException.getMessage());
        assertEquals("internal", cardApiException.getSource());
        verify(cardRepository).findByCardTypeAndCardShowDate(eq(CardType.DAILY_ACTIVE), eq(LocalDate.now()));
    }

}
