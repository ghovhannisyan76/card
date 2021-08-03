package com.ghovhannisyan.takehometask.card.api;

import org.springframework.beans.factory.annotation.Autowired;

public class CardApiImpl implements CardApi {

    @Autowired
    private CardApiDelegate cardApiDelegate;

    @Override
    public CardApiDelegate getDelegate() {
        return cardApiDelegate;
    }
}
