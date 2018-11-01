package muttlab.exceptions;

public class LanguageNotSupported extends Exception {
    @Override
    public String getMessage() { return "Language not supported."; }
}
