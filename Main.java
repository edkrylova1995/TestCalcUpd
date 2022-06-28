import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
    System.out.println("Vvedite dannye:");
    Scanner inp = new Scanner(System.in);
    String input = inp.nextLine();
    inp.close();
    System.out.println("Otvet:");
    System.out.println(calc(input));
}

    public static String calc(String input) throws IOException {

        String[] calc_input = input.split(" ");
        if (calc_input.length != 3) {
            System.out.println("ERROR: vvedite vyrazhenie s 2 operandami");
            System.exit(0);
        }

        Main.Vrbl a = new Vrbl(); // первый операнд
        a.input = calc_input[0]; // фрагмент строки, введенной пользователем
        a.type = a.type(); // 1- арабские, 2- римские, 3- ошибка
        a.value = a.value(); // значение int для расчета результата

        Vrbl operator = new Vrbl(); // оператор
        operator.input = calc_input[1]; // либо +,-,*,/, либо ошибка

        Main.Vrbl b = new Main.Vrbl(); // второй операнд
        b.input = calc_input[2]; // фрагмент строки, введенной пользователем
        b.type = b.type(); // 1- арабские, 2- римские, 3- ошибка
        b.value = b.value(); // значение int для расчета результата

        if (a.type != b.type) {
            throw new IOException("ERROR: operandy dolzhny imet odin tip");
            }
        if (a.value <0 || b.value <0) {
            throw new IOException("ERROR: operand ne mozhet byt menshe 0");
        }
        if (a.value >10 || b.value >10) {
            throw new IOException("ERROR: operand ne mozhet byt bolshe 10");
            }

        int result = 0; //результат вычислений в арабских цифрах
        String romresult = ""; //результат вычислений в римских цифрах, если применимо
        String arabresult = ""; //результат вычислений в арабских цифрах, строкой
        String endresult = ""; //итоговый результат для вывода на экран

        switch (operator.input) { //расчет результата в арабских цифрах
            case "+" -> result = a.value + b.value;
            case "-" -> result = a.value - b.value;
            case "*" -> result = a.value * b.value;
            case "/" -> {try {
                result = a.value / b.value;
            } catch (ArithmeticException | InputMismatchException e) {
                System.out.println("Exception");
                System.out.println("Only integer non-zero parameters allowed");
                System.exit(1);}}
            default -> {throw new IOException("ERROR: Vveden nekorrektny operator");
                }
        }

        if (a.type == 2 & b.type == 2) { // перевод результата в римские цифры
            if (result > 0) {
                while (result >= 100) {
                    romresult += "C";
                    result -= 100;
                }
                while (result >= 90) {
                    romresult += "XC";
                    result -= 90;
                }
                while (result >= 50) {
                    romresult += "L";
                    result -= 50;
                }
                while (result >= 40) {
                    romresult += "XL";
                    result -= 40;
                }
                while (result >= 10) {
                    romresult += "X";
                    result -= 10;
                }
                while (result >= 9) {
                    romresult += "IX";
                    result -= 9;
                }
                while (result >= 5) {
                    romresult += "V";
                    result -= 5;
                }
                while (result >= 4) {
                    romresult += "IV";
                    result -= 4;
                }
                while (result >= 1) {
                    romresult += "I";
                    result -= 1;
                }
            }
            else {throw new IOException("Nevozmozhno poluchit resultat <1 v rimskih tsifrakh");
                }
        }
        else {arabresult = Integer.toString(result);
            romresult="n/a";}

        if (a.type == 1 & b.type == 1) endresult = arabresult;
        if (a.type == 2 & b.type == 2) endresult = romresult;

        //костыли для проверки, где именно ломается код
        //System.out.println("a-input " + vrbla.input);
        //System.out.println("a-type " + vrbla.type);
        //System.out.println("a-value " + vrbla.value);
        //System.out.println("b-input " + vrblb.input);
        //System.out.println("b-type " + vrblb.type);
        //System.out.println("b-value " + vrblb.value);
        //System.out.println("operator " + operator.input);
        //System.out.println("rom " + romresult);
        //System.out.println("arab " + arabresult);

        return endresult;}

    static class Vrbl {
        String input; // выделение операндов и оператора в main
        int type; // 1- арабские, 2- римские, 3- ошибка
        int value; // прием к расчету значения int

        int type() { // определение, какими цифрами введено число - арабскими или римскими (или ошибка)
            Scanner arab_rom = new Scanner(input);
            if (arab_rom.hasNextInt()) {type=1;
                return type;}
            else
                switch (input) {
                    case "I" -> type = 2;
                    case "II" -> type = 2;
                    case "III" -> type = 2;
                    case "IV" -> type = 2;
                    case "V" -> type = 2;
                    case "VI" -> type = 2;
                    case "VII" -> type = 2;
                    case "VIII" -> type = 2;
                    case "IX" -> type = 2;
                    case "X" -> type = 2;
                    default -> type = 3;}
            return type;}

        int value() throws IOException {
            if (type == 1) {
                value = Integer.parseInt(input);}
            else if (type == 3) {
                throw new IOException("ERROR: Vveden nekorrektny operand");
                }
            else
                switch (input) {
                    case "I" -> value = 1;
                    case "II" -> value = 2;
                    case "III" -> value = 3;
                    case "IV" -> value = 4;
                    case "V" -> value = 5;
                    case "VI" -> value = 6;
                    case "VII" -> value = 7;
                    case "VIII" -> value = 8;
                    case "IX" -> value = 9;
                    case "X" -> value = 10;}
            return value;
        }
    }
}
