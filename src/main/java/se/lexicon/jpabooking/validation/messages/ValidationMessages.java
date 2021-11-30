package se.lexicon.jpabooking.validation.messages;

public class ValidationMessages {
    public static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    public static final String WEAK_PASSWORD = "Lösenordet är för svagt. 8 tecken med minst en siffra och en bokstav";
    public static final String PNR_REGEX = "^\\d{12}";
    public static final String MANDATORY_FIELD = "Detta fält är obligatoriskt";
    public static final String INVALID_PNR = "Ogiltigt format. Accepterat format är (ååååmmddxxxx)";
    public static final String USERNAME_SIZE = "Måste vara mellan 6 och 100 tecken";
    public static final String USERNAME_TAKEN = "Detta användarnamn är upptaget";
    public static final String MANDATORY_FORM = "Du måste fylla i detta formulär";
    public static final String UNIQUE_PNR = "Förekommer redan i databasen";
    public static final String UNIQUE_EMAIL = "Detta email finns redan registrerat";

}
