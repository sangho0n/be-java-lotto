package kr.codesquad.IO;
import kr.codesquad.Lotto.LottoStatus;
import kr.codesquad.User;
import kr.codesquad.customException.InvalidInputException;

import java.util.ArrayList;
import java.util.Scanner;

public class Console {
    static Scanner scanner = new Scanner(System.in);

    private enum ScanContext{ CASH, BONUS, MANUAL }

    public void printCashInstruction()
    {
        System.out.println("구입 금액을 입력하세요");
    }

    public int scanCashAmount()
    {
        int cash = 0;
        try {
            cash = toInt(scanner.nextLine(), ScanContext.CASH);
            return cash;
        }
        catch(InvalidInputException e) {
            System.out.println(e.getMessage());
        }
        catch(NumberFormatException e) {
            System.out.println("입력값이 숫자가 아니거나, 범위를 벗어납니다.");
            System.out.printf("범위 안에 들어가는 숫자를 입력하세요 : %d ~ %d\n", 1000, Integer.MAX_VALUE - (Integer.MAX_VALUE%1000));
        }

        cash = scanCashAmount();
        return cash;
    }

    private int toInt(String str, ScanContext context) throws NumberFormatException
    {
        int ret = Integer.parseInt(str);
        if(context == ScanContext.CASH  && (ret <= 0 || ret % 1000 != 0))
            throw new InvalidInputException("유효한 숫자 입력이 아닙니다. 1000원 단위의 금액을 입력하세요");
        return ret;
    }

    private int toInt(String str, ScanContext context, int userCash) throws NumberFormatException
    {
        int ret = Integer.parseInt(str);
        if(context == ScanContext.MANUAL&& (ret < 0 || ret > userCash/1000))
            throw new InvalidInputException("음수개를 구매하거나, 구매 가능 개수를 초과하여 구매할 수 없습니다. 올바른 값으로 다시 입력하세요.");
        return ret;
    }

    public int scanManualTicketCount(int userCash)
    {
        int count;
        try{
            count = toInt(scanner.nextLine(), ScanContext.MANUAL, userCash);
            return count;
        }
        catch(InvalidInputException e)
        {
            System.out.println(e.getMessage());
        }
        catch(NumberFormatException e)
        {
            System.out.println("입력값이 숫자가 아니거나, 구매가능개수를 초과합니다. 다시 입력하세요");
        }
        count = scanManualTicketCount(userCash);
        return count;
    }

    public int scanBonusBall()
    {
        int ret = Integer.parseInt(scanner.nextLine());
        return ret;
    }


    public String scanManualTicket()
    {
        return scanner.nextLine();
    }


    public String scanWinNums()
    {
        return scanner.nextLine();
    }

    public void printManualTicketCountInstruction()
    {
        System.out.println("수동으로 구매할 로또 수를 입력해 주세요.");
    }

    public void printManualTicketInstruction()
    {
        System.out.println("수동으로 구매할 번호를 입력해주세요.");
    }

    public void printManualAndAutoCount(int m, int a)
    {
        System.out.printf("수동으로 %d장, 자동으로 %d개를 구매했습니다.\n", m, a);
    }

    public void printAllTickets(User user)
    {
        printManualTickets(user);
        printAutoTickets(user);
    }
    private void printManualTickets(User user)
    {
        for(int i = 0; i < user.getManualCount(); i++)
        {
            System.out.println(user.getLottoTickets().get(i));
        }
    }
    private void printAutoTickets(User user)
    {
        int autoTicketStartIndex = user.getManualCount();
        for(int i = 0; i < user.getAutoCount(); i++)
        {
            System.out.println(user.getLottoTickets().get(autoTicketStartIndex + i));
        }
        System.out.println();
    }

    public void printLastWinnumInstruction()
    {
        System.out.println("지난 주 당첨 번호를 입력해주세요");
    }


    public void printBonusBallInstruction()
    {
        System.out.println("보너스 볼을 입력해주세요");
    }

    public void printResult(ArrayList<Integer> Result, int buyNum)
    {
        System.out.println();
        System.out.println("당첨 통계");
        System.out.println("-------------");

        for(LottoStatus stat : LottoStatus.values())
        {
            System.out.printf("%s, (%d)- %d개\n", stat.getStatusString(), stat.getWinningAmout(), Result.get(stat.ordinal()));
        }
//          System.out.printf("3개 일치 (5000원)- %d개\n", Result.get(0));
//          System.out.printf("4개 일치 (50000원)- %d개\n", Result.get(1));
//          System.out.printf("5개 일치 (1500000원)- %d개\n", Result.get(2));
//          System.out.printf("5개 일치, 보너스 볼 일치(30000000원)- %d개\n", Result.get(3));
//          System.out.printf("6개 일치 (2000000000원)- %d개\n", Result.get(4));

        float rate = (float)(
                LottoStatus.Fifth.getWinningAmout()*Result.get(0) +
                        LottoStatus.Fourth.getWinningAmout()*Result.get(1) +
                        LottoStatus.Third.getWinningAmout()*Result.get(2) +
                        LottoStatus.Second.getWinningAmout()*Result.get(3) +
                        LottoStatus.First.getWinningAmout()*Result.get(4) - buyNum*1000) /
                (float) (buyNum*1000)
                * 100;

        System.out.printf("총 수익률은 %.2f%% 입니다.\n", rate);
    }
}
