package com.ankit.mvc.service;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ankit.mvc.entity.AccountEntity;
import com.ankit.mvc.entity.CardEntity;
import com.ankit.mvc.model.CardType;
import com.ankit.mvc.repo.AccountRepo;
import com.ankit.mvc.repo.CardRepo;

@Service
public class AccountService {

	@Autowired
	AccountRepo accountRepo;
	@Autowired
	CardRepo cardRepo;

	public String createAccount(String name, String empId) {
		Random rand = new Random();
		String id = "999000" + rand.nextInt(1000);
		System.out.println("Account details saved as bellow - \n\n Name: " + name + ",\n Employee Id: " + empId
				+ "\n Account number: " + id);
		AccountEntity newAccount = new AccountEntity(Long.parseLong(id), empId, name);
		accountRepo.save(newAccount);
		return id;
	}

	public String createCard(String accntId, String crdTyp, String limit, Model model) throws Exception {
		// TODO Auto-generated method stub
		String cardNumber = null;
		AccountEntity account = null;
		CardEntity card = null;
		Random rand = new Random();
		if (isvalidAccount(accntId)) {
			account = accountRepo.findByAccountNumber(Long.parseLong(accntId));
			if (crdTyp.equalsIgnoreCase(CardType.Credit_Card.name())) {
				cardNumber = "84310005" + rand.nextInt(10000);
				card = new CardEntity("C", Long.parseLong(limit),
						accountRepo.findByAccountNumber(Long.parseLong(accntId)), Long.parseLong(cardNumber));
			} else if (crdTyp.equalsIgnoreCase(CardType.Debit_Card.name())) {
				cardNumber = "79200005" + rand.nextInt(10000);
				card = new CardEntity("D", Long.parseLong(limit),
						accountRepo.findByAccountNumber(Long.parseLong(accntId)), Long.parseLong(cardNumber));
			} else {
				throw new Exception("Enter valid card type, in the form of 'cardtype_card' ");
			}
		} else {
			throw new Exception("Account is not valid");
		}
		validateLimit(account, card);
		int defaultPin = generatePin(card);
		System.out.println(card.toString());
		cardRepo.save(card);
		model.addAttribute("pin", defaultPin);
		return cardNumber;
	}

	private int generatePin(CardEntity card) {
		// TODO Auto-generated method stub
		Random rand = new Random();
		int pin = rand.nextInt(10000);
		System.out.println("secret pin: " + pin);
		byte[] bytes = ByteBuffer.allocate(4).putInt(pin).array();
		String encoded = Base64.getEncoder().encodeToString(bytes);
		card.setPin(encoded);
		return pin;
	}

	private void validateLimit(AccountEntity account, CardEntity card) throws Exception {
		// TODO Auto-generated method stub
		double accBalance = account.getBalance();
		double newLimit = card.getLimit();
		if ((card.getLimit()) > (5 * accBalance)) {
			newLimit = 5 * (accBalance);
			card.setLimit(newLimit);
			throw new Exception("Maximum limit for your account is $" + newLimit);
		}

	}

	public boolean isvalidAccount(String accntId) {
		Optional<AccountEntity> acc = Optional.ofNullable(accountRepo.findByAccountNumber(Long.parseLong(accntId)));

		return acc.isPresent();
	}

}
