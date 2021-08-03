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
public class NewUserDailyQuoteCardSearchingServiceTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private NewUserDailyQuoteCardSearchingService newUserDailyQuoteCardSearchingService;

    @Test
    public void positiveSearch() {

        CardEntity cardEntityNew = CardEntity.builder()
                .id(1L)
                .cardType(CardType.DAILY_NEW)
                .author("Author1")
                .title("Title1")
                .message("message1")
                .cardShowDate(LocalDate.now())
                .build();

        when(cardRepository.findByCardTypeAndCardShowDate(eq(CardType.DAILY_NEW), eq(LocalDate.now())))
                .thenReturn(Optional.of(cardEntityNew));

        CardEntity cardEntity = newUserDailyQuoteCardSearchingService.search();
        assertEquals(cardEntityNew, cardEntity);
        verify(cardRepository).findByCardTypeAndCardShowDate(eq(CardType.DAILY_NEW), eq(LocalDate.now()));
    }

    @Test
    public void negativeSearch() {

        when(cardRepository.findByCardTypeAndCardShowDate(eq(CardType.DAILY_NEW), eq(LocalDate.now())))
                .thenReturn(Optional.empty());

        CardApiException cardApiException = assertThrows(CardApiException.class,
                () -> {newUserDailyQuoteCardSearchingService.search();});
        assertEquals(ErrorMessage.NOT_FOUND, cardApiException.getErrorMessage());
        assertEquals("Daily quote card is not found for the new user", cardApiException.getMessage());
        assertEquals("internal", cardApiException.getSource());
        verify(cardRepository).findByCardTypeAndCardShowDate(eq(CardType.DAILY_NEW), eq(LocalDate.now()));
    }

}
