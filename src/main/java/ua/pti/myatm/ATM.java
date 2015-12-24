package ua.pti.myatm;
import ua.pti.myatm.NoCardInsertedException;
import ua.pti.myatm.NotEnoughMoneyInATMException;
import ua.pti.myatm.NotEnoughMoneyInAccountException;
public class ATM {
        private double moneyInATM;
        private Card card;
    //Можно задавать количество денег в банкомате 
    ATM(double moneyInATM)throws UnsupportedOperationException{
        double maxMoney = 1000000;
        if(moneyInATM < 0){
         throw new IllegalArgumentException("ATM can`t have negative sum of money");
        }else{
        if(moneyInATM > maxMoney){
         throw new IllegalArgumentException("ATM can`t have money, match for the maxMoney");
        }else{
        this.moneyInATM = moneyInATM;
        this.card = null;
        }
        }
    }
     public void setCurrentCard(Card card) {
        this.card = card;
    }
     public Card getCurrentCard() throws NoCardInsertedException {
        if (this.card!=null) return this.card;
        else  throw new NoCardInsertedException("Couldn't find some card in the ATM");
    }

    // Возвращает каоличестов денег в банкомате
    public double getMoneyInATM() {
          return moneyInATM;

    }
        
    //С вызова данного метода начинается работа с картой
    //Метод принимает карту и пин-код, проверяет пин-код карты и не заблокирована ли она
    //Если неправильный пин-код или карточка заблокирована, возвращаем false. При этом, вызов всех последующих методов у ATM с данной картой должен генерировать исключение NoCardInserted
    public boolean validateCard(Card card, int pinCode) {
         if (!card.checkPin(pinCode) || card.isBlocked())  return false;
        else {
            this.card = card;
            return true;
        }

    }
    
    //Возвращает сколько денег есть на счету
    public double checkBalance()throws NoCardInsertedException{
        if (this.card==null||this.card.isBlocked()) throw new NoCardInsertedException("your card is missing or blocked");
        return  this.card.getAccount().getBalance();
    }
    
    //Метод для снятия указанной суммы
    //Метод возвращает сумму, которая у клиента осталась на счету после снятия
    //Кроме проверки счета, метод так же должен проверять достаточно ли денег в самом банкомате
    //Если недостаточно денег на счете, то должно генерироваться исключение NotEnoughMoneyInAccount 
    //Если недостаточно денег в банкомате, то должно генерироваться исключение NotEnoughMoneyInATM 
    //При успешном снятии денег, указанная сумма должна списываться со счета, и в банкомате должно уменьшаться количество денег
    public double getCash(double amount)throws  NotEnoughMoneyInATMException,NotEnoughMoneyInAccountException,NoCardInsertedException {
        if(amount<0)
            throw new UnsupportedOperationException("you cannot take off negative value off money");
        if(getMoneyInATM()<amount)
            throw new NotEnoughMoneyInATMException("Not enough money in ATM");
        if(checkBalance()<amount)
            throw  new NotEnoughMoneyInAccountException("Not enough money in your account");
        Card curCard = getCurrentCard();
        curCard.getAccount().withdrow(amount);
        setCurrentCard(curCard);
        this.moneyInATM=getMoneyInATM()-amount;
        return  curCard.getAccount().getBalance();
    }
}
