package com.ghovhannisyan.takehometask.card.services;

import com.ghovhannisyan.takehometask.card.entities.CardEntity;

@FunctionalInterface
public interface CardSearchingService {
    CardEntity search();
}
