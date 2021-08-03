package com.ghovhannisyan.takehometask.card;

import com.ghovhannisyan.takehometask.card.entities.CardEntity;
import com.ghovhannisyan.takehometask.card.entities.CardType;
import com.ghovhannisyan.takehometask.card.entities.UserEntity;
import com.ghovhannisyan.takehometask.card.entities.UserStatus;
import com.ghovhannisyan.takehometask.card.repositories.CardRepository;
import com.ghovhannisyan.takehometask.card.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CardApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CardRepository cardRepository;

	/**
	 * This test is a functional test. Ideally, it should be broken into multiple tests (1 test per scenario).
	 * It can be added more assertions on the returned object.
	 */
	@Test
	public void functionalTests() throws Exception {

		mockMvc.perform(get("/card"))
				.andExpect(status().isBadRequest());

		UserEntity userEntityNew = UserEntity.builder()
				.username("user1")
				.status(UserStatus.NEW)
				.build();
		UserEntity userEntityActive = UserEntity.builder()
				.username("user2")
				.status(UserStatus.ACTIVE)
				.build();
		List<UserEntity> userEntities = new ArrayList<>();
		userEntities.add(userEntityNew);
		userEntities.add(userEntityActive);
		userRepository.saveAllAndFlush(userEntities);

		CardEntity cardEntityNew = CardEntity.builder()
				.cardType(CardType.DAILY_NEW)
				.author("Author1")
				.title("Title1")
				.message("message1")
				.cardShowDate(LocalDate.now())
				.build();

		CardEntity cardEntityActive = CardEntity.builder()
				.cardType(CardType.DAILY_ACTIVE)
				.author("Author2")
				.title("Title2")
				.message("message2")
				.cardShowDate(LocalDate.now())
				.build();

		List<CardEntity> cardEntities = new ArrayList<>();
		cardEntities.add(cardEntityNew);
		cardEntities.add(cardEntityActive);
		cardRepository.saveAllAndFlush(cardEntities);

		// new user, Daily
		mockMvc.perform(get("/card?username=user1&cardtype=DAILY"))
				.andExpect(status().isOk());

		// active user, Daily
		mockMvc.perform(get("/card?username=user2&cardtype=DAILY"))
				.andExpect(status().isOk());

		//user not found
		mockMvc.perform(get("/card?username=user3&cardtype=DAILY"))
				.andExpect(status().isNotFound());

		CardEntity cardEntityStatusFull = CardEntity.builder()
				.cardType(CardType.STATUS_UPDATE)
				.title("Title3")
				.icon("URL1")
				.message("message3")
				.button("button1")
				.cardShowDate(LocalDate.now())
				.build();

		cardRepository.saveAndFlush(cardEntityStatusFull);


		// user status does not matter, Status, subtype is missing (default to Full)
		mockMvc.perform(get("/card?username=user1&cardtype=STATUS"))
				.andExpect(status().isOk());

		// user status does not matter, Status, Full
		mockMvc.perform(get("/card?username=user1&cardtype=STATUS&cardsubtype=FULL"))
				.andExpect(status().isOk());

		// user status does not matter, Status, Icon (record does not exist icon not null, button null)
		mockMvc.perform(get("/card?username=user1&cardtype=STATUS&cardsubtype=ICON"))
				.andExpect(status().isOk());

		// user status does not matter, Status, Icon (record does not exist icon null, button not null)
		mockMvc.perform(get("/card?username=user1&cardtype=STATUS&cardsubtype=BUTTON"))
				.andExpect(status().isOk());

		CardEntity cardEntityStatusIcon = CardEntity.builder()
				.cardType(CardType.STATUS_UPDATE)
				.title("Title4")
				.icon("URL2")
				.message("message4")
				.cardShowDate(LocalDate.now())
				.build();
		CardEntity cardEntityStatusButton = CardEntity.builder()
				.cardType(CardType.STATUS_UPDATE)
				.title("Title5")
				.message("message5")
				.button("button2")
				.cardShowDate(LocalDate.now())
				.build();

		cardEntities = new ArrayList<>();
		cardEntities.add(cardEntityStatusIcon);
		cardEntities.add(cardEntityStatusButton);
		cardRepository.saveAllAndFlush(cardEntities);

		// user status does not matter, Status, Icon (record exist icon not null, button null)
		mockMvc.perform(get("/card?username=user1&cardtype=STATUS&cardsubtype=ICON"))
				.andExpect(status().isOk());

		// user status does not matter, Status, Icon (record exist icon null, button not null)
		mockMvc.perform(get("/card?username=user1&cardtype=STATUS&cardsubtype=BUTTON"))
				.andExpect(status().isOk());
	}

}
