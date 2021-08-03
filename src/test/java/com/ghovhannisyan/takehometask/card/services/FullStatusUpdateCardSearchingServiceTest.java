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
public class FullStatusUpdateCardSearchingServiceTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private FullStatusUpdateCardSearchingService fullStatusUpdateCardSearchingService;

    @Test
    public void positiveSearch() {

        CardEntity cardEntityStatusFull = CardEntity.builder()
				.id(1L)
                .cardType(CardType.STATUS_UPDATE)
                .title("Title3")
                .icon("URL1")
                .message("message3")
                .button("button1")
                .cardShowDate(LocalDate.now())
                .build();

        when(cardRepository.findByCardTypeAndCardShowDateAndIconNotNullAndButtonNotNull(eq(CardType.STATUS_UPDATE),
                eq(LocalDate.now())))
                .thenReturn(Optional.of(cardEntityStatusFull));

        CardEntity cardEntity = fullStatusUpdateCardSearchingService.search();
        assertEquals(cardEntityStatusFull, cardEntity);
        verify(cardRepository).findByCardTypeAndCardShowDateAndIconNotNullAndButtonNotNull(eq(CardType.STATUS_UPDATE),
                eq(LocalDate.now()));
    }

    @Test
    public void negativeSearch() {

        when(cardRepository.findByCardTypeAndCardShowDateAndIconNotNullAndButtonNotNull(eq(CardType.STATUS_UPDATE),
                eq(LocalDate.now())))
                .thenReturn(Optional.empty());

        CardApiException cardApiException = assertThrows(CardApiException.class,
                () -> {fullStatusUpdateCardSearchingService.search();});
        assertEquals(ErrorMessage.NOT_FOUND, cardApiException.getErrorMessage());
        assertEquals("Status card is not found, where all attributes are mandatory", cardApiException.getMessage());
        assertEquals("internal", cardApiException.getSource());
        verify(cardRepository).findByCardTypeAndCardShowDateAndIconNotNullAndButtonNotNull(eq(CardType.STATUS_UPDATE), eq(LocalDate.now()));
    }

}
