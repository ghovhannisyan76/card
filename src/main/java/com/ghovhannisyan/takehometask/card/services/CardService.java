package com.ghovhannisyan.takehometask.card.services;

import com.ghovhannisyan.takehometask.card.model.CardResponse;
import com.ghovhannisyan.takehometask.card.model.CardSubType;
import com.ghovhannisyan.takehometask.card.model.CardType;

public interface CardService {

    CardResponse getCard(String username, CardType cardType, CardSubType cardSubtype);
}
