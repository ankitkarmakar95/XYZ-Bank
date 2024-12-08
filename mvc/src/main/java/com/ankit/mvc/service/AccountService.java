package com.ankit.mvc.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.ankit.mvc.model.CardType;

@Service
public class AccountService {

	public String createAccount(String name, String empId) {
		Random rand = new Random();
		String id = "999000" + rand.nextInt(1000);
		System.out.println("Account details saved as bellow - \n\n Name: " + name + ",\n Employee Id: " + empId
				+ "\n Account number: " + id);
		return id;
	}

	public String createCard(String accntId, String crdTyp, String limit) throws Exception {
		// TODO Auto-generated method stub
		String cardNumber = null;
		if (isvalidAccount(accntId)) {
			if (crdTyp.equalsIgnoreCase(CardType.Credit_Card.name())) {
				Random rand = new Random();
				cardNumber = "84310005" + rand.nextInt(10000);
			} else if (crdTyp.equalsIgnoreCase(CardType.Debit_Card.name())) {
				Random rand = new Random();
				cardNumber = "79200005" + rand.nextInt(10000);
			} else {
				throw new Exception("Enter valid card type, in the form of 'cardtype_card' ");
			}
		} else {
			throw new Exception("Account is not valid");
		}
		validateLimit(limit);
		return cardNumber;
	}

	private void validateLimit(String limit) throws Exception {
		// TODO Auto-generated method stub
		int accBalance = 1000;
		
		if (Integer.valueOf(limit) > (5 * accBalance)) {
			Integer newLimit = 5 * Integer.valueOf(accBalance);
			throw new Exception("Maximum limit for your account is $" + newLimit);
		}

	}

	public boolean isvalidAccount(String accntId) {
		return true;
	}

}
