package kr.codesquad.customException;

public class InvalidInputExeption extends NumberFormatException{

    public InvalidInputExeption(){}

    public InvalidInputExeption(String msg)
    {
        super(msg);
    }
}
