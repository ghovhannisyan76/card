package com.ghovhannisyan.takehometask.card.api;

import com.ghovhannisyan.takehometask.card.model.CardResponse;
import com.ghovhannisyan.takehometask.card.model.CardSubType;
import com.ghovhannisyan.takehometask.card.model.CardType;
import com.ghovhannisyan.takehometask.card.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardApiDelegateImpl implements CardApiDelegate {

    @Autowired
    private CardService cardService;

    @Override
    public ResponseEntity<CardResponse> getCard(String username, CardType cardtype, CardSubType cardsubtype) {
        CardSubType inputCardSubType = cardsubtype;
        if(cardtype == CardType.STATUS && inputCardSubType == null){
            inputCardSubType = CardSubType.FULL;
        }
        CardResponse cardResponse = cardService.getCard(username, cardtype, inputCardSubType);
        return new ResponseEntity<>(cardResponse, HttpStatus.OK);
    }
}
