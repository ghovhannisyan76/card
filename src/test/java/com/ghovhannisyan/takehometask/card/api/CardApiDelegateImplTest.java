package com.ghovhannisyan.takehometask.card.api;

import com.ghovhannisyan.takehometask.card.model.CardResponse;
import com.ghovhannisyan.takehometask.card.model.CardSubType;
import com.ghovhannisyan.takehometask.card.model.CardType;
import com.ghovhannisyan.takehometask.card.services.CardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CardApiDelegateImplTest {

    @Mock
    private CardService cardService;

    @InjectMocks
    private CardApiDelegateImpl cardApiDelegate;

    @Test
    public void positiveGetCardCardTypeDaily() {
        CardResponse cardResponse = new CardResponse();
        cardResponse.title("title");
        cardResponse.message("message");
        cardResponse.author("author");

        when(cardService.getCard(eq("user"), eq(CardType.DAILY), isNull())).thenReturn(cardResponse);

        ResponseEntity responseEntity = cardApiDelegate.getCard("user", CardType.DAILY, null);

        assertEquals(cardResponse, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(cardService).getCard(eq("user"), eq(CardType.DAILY), isNull());
    }

    @Test
    public void positiveGetCardCardTypeStatusSubtypeNull() {
        CardResponse cardResponse = new CardResponse();
        cardResponse.title("title");
        cardResponse.message("message");
        cardResponse.icon("icon");
        cardResponse.button("button");

        when(cardService.getCard(eq("user"), eq(CardType.STATUS), eq(CardSubType.FULL))).thenReturn(cardResponse);

        ResponseEntity responseEntity = cardApiDelegate.getCard("user", CardType.STATUS, null);

        assertEquals(cardResponse, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(cardService).getCard(eq("user"), eq(CardType.STATUS), eq(CardSubType.FULL));
    }

    @Test
    public void positiveGetCardCardTypeStatusSubtypeNotNull() {
        CardResponse cardResponse = new CardResponse();
        cardResponse.title("title");
        cardResponse.message("message");
        cardResponse.icon("icon");
        cardResponse.button("button");

        when(cardService.getCard(eq("user"), eq(CardType.STATUS), eq(CardSubType.ICON))).thenReturn(cardResponse);

        ResponseEntity responseEntity = cardApiDelegate.getCard("user", CardType.STATUS, CardSubType.ICON);

        assertEquals(cardResponse, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(cardService).getCard(eq("user"), eq(CardType.STATUS), eq(CardSubType.ICON));
    }
}
