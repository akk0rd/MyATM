/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pti.myatm;

import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.InOrder;
import static org.mockito.Mockito.*;

/**
 *
 * @author andrii
 */
public class ATMTest {

    @Test
    public void testGetMoneyInATM() {
        System.out.println("getMoneyInATM");
        ATM instance = new ATM(1000.0);
        double expResult = 1000.0;
        double result = instance.getMoneyInATM();
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testValidateCard() {
        System.out.println("validateCard");
        Card card = mock(Card.class);
        int pinCode = 1234;
        when(card.checkPin(pinCode)).thenReturn(true);
        when(card.isBlocked()).thenReturn(false);
        ATM instance = new ATM(1000.0);
        boolean expResult = true;
        boolean result = instance.validateCard(card, pinCode);
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckBalance() throws NoCardInsertedException{
        System.out.println("checkBalance");
        ATM instance = new ATM(1000.0);
        Card mockCard = mock(Card.class);
        Account mockAccount = mock(Account.class);
        double expResult = 700.0;
        when(mockCard.getAccount()).thenReturn(mockAccount);
        when(mockCard.getAccount().getBalance()).thenReturn(700.0);
        instance.setCurrentCard(mockCard);
        double result = instance.checkBalance();
        assertEquals(expResult, result, 0.0);   
    }

        @Test
    public void testGetCash()throws NotEnoughMoneyInATMException,NotEnoughMoneyInAccountException,NoCardInsertedException{
        System.out.println("test getCash");
        double amount = 1000;
        double accountBalance = 500;
        double getAmount = 400;
        int pin = 1234;
        ATM instance = new ATM(amount);
        Card mockCard = mock(Card.class);
        Account mockAccount = mock(Account.class);
        when(mockCard.checkPin(pin)).thenReturn(true);
        when(mockCard.isBlocked()).thenReturn(false);
        when(mockCard.getAccount()).thenReturn(mockAccount);
        when(mockAccount.getBalance()).thenReturn(accountBalance).thenReturn(accountBalance-getAmount);
        when(mockCard.getAccount().withdrow(getAmount)).thenReturn(accountBalance - getAmount);
        instance.validateCard(mockCard, pin);
        instance.getCash(getAmount);
        InOrder inOrder = inOrder(mockCard,mockAccount);
        inOrder.verify(mockCard).checkPin(pin);
        inOrder.verify(mockCard).getAccount();
        inOrder.verify(mockAccount).getBalance();
        assertEquals(instance.getCurrentCard().getAccount().getBalance(),accountBalance-getAmount,0.0);
        assertEquals(instance.getMoneyInATM(),amount-getAmount,0.0);
    }
    @Test(expected = IllegalArgumentException.class)
    public void ATMwithNegativeSum()throws UnsupportedOperationException{
        System.out.println("test create ATM with negative sum");
        ATM instance = new ATM(-500.0);
    }
    @Test(expected = IllegalArgumentException.class)
    public void ATMoverflow()throws UnsupportedOperationException{
        System.out.println("test create ATM with negative sum");
        ATM instance = new ATM(5000000.0);
    }
    @Test(expected = NoCardInsertedException.class)
    public void NoCard() throws NotEnoughMoneyInATMException,NotEnoughMoneyInAccountException, NoCardInsertedException{
        System.out.println("Exeption no card");
        ATM instance = new ATM(5000.0);
        instance.getCurrentCard();
    }
    @Test(expected = UnsupportedOperationException.class)
    public void GetNegativeMoney()throws NotEnoughMoneyInATMException,NotEnoughMoneyInAccountException, NoCardInsertedException{
        System.out.println("Get negative sum of money");
        ATM instance = new ATM(1000);
        Card mockCard = mock(Card.class);
        Account mockAccount = mock(Account.class);
        instance.getCash(-400);
    }
    @Test(expected = NotEnoughMoneyInATMException.class)
    public void GetMoneyMoreThenATM()throws NotEnoughMoneyInATMException,NotEnoughMoneyInAccountException, NoCardInsertedException{
        System.out.println("Get sum of money more than ATM");
        ATM instance = new ATM(500);
        instance.getCash(800);
    }
    @Test(expected = NotEnoughMoneyInAccountException.class)
    public void GetMoneyMoreThanAccount()throws NotEnoughMoneyInATMException,NotEnoughMoneyInAccountException, NoCardInsertedException{
        System.out.println("Get Money More Than Account");
        double amount = 1000;
        double accountBalance = 500;
        double getAmount = 400;
        int pin = 1111;
        ATM instance = new ATM(1000.0);
        Card mockCard = mock(Card.class);
        Account mockAccount = mock(Account.class);
        when(mockCard.getAccount()).thenReturn(mockAccount);
        when(mockAccount.getBalance()).thenReturn(500.0);
        when(mockCard.checkPin(pin)).thenReturn(true);
        instance.validateCard(mockCard, pin);
        instance.getCash(800);
    }
}
