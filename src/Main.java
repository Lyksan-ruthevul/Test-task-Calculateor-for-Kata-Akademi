import java.util.Scanner;
import java.util.TreeMap;

    public class Main {
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            System.out.print("""
                    Введите матиматическое выражение в одну строку через пробел
                    Условия: два операнда и один оператор (+, -, /, *)
                    числа от 1 до 10 включительно
                    калькулятор умеет работать как с арабскими (1,2,3,4,5...), так и с римскими (I,II,III,IV,V...) числами.:""");
            String input = sc.nextLine();
            String res = calc(input);
            System.out.println(res);
        }
        public static String calc(String input) {
            Converter converter = new Converter();
            String[] arr = input.split(" ");
            if (arr.length != 3) {
                throw new IllegalArgumentException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *), введённые в одну строку через пробел");
            }
            String operator = arr[1];
            int result;
            if (converter.isRoman(arr[0]) == converter.isRoman(arr[2])) {
                int arabNum_1, arabNum_2;
                boolean isRoman = converter.isRoman(arr[0]);
                if (isRoman) {
                    arabNum_1 = converter.romanToInt(arr[0]);
                    arabNum_2 = converter.romanToInt(arr[2]);
                }
                else {
                    arabNum_1 = Integer.parseInt(arr[0]);
                    arabNum_2 = Integer.parseInt(arr[2]);
                }
                if(arabNum_1 < 1|| arabNum_1 > 10 || arabNum_2 < 1 || arabNum_2 > 10){
                    throw new IllegalArgumentException("формат математической операции не удовлетворяет заданию - входные числа должны быть от 1 до 10 уключительно");
                }
                switch (operator) {
                    case "+" -> result = arabNum_1 + arabNum_2;
                    case "-" -> result = arabNum_1 - arabNum_2;
                    case "*" -> result = arabNum_1 * arabNum_2;
                    case "/" -> result = arabNum_1 / arabNum_2;
                    default ->
                            throw new IllegalArgumentException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
                }
                if (isRoman) {
                    if(result < 1){
                        throw new IllegalArgumentException("формат математической операции не удовлетворяет заданию - результат операции римских чисел не может быть меньше 1");
                    }
                    return converter.intToRoman(result);
                } else {
                    return String.valueOf(result);
                }
            }
            else
            {
                throw new IllegalArgumentException("формат математической операции не удовлетворяет заданию используются одновременно разные системы счисления");
            }
        }
            static class Converter {
                TreeMap<Character, Integer> romanKeyMap = new TreeMap<>();
                TreeMap<Integer, String> arabianKeyMap = new TreeMap<>();
                public Converter() {
                    romanKeyMap.put('I', 1);
                    romanKeyMap.put('V', 5);
                    romanKeyMap.put('X', 10);
                    romanKeyMap.put('L', 50);
                    romanKeyMap.put('C', 100);
                    romanKeyMap.put('D', 500);
                    romanKeyMap.put('M', 1000);

                    arabianKeyMap.put(1000, "M");
                    arabianKeyMap.put(900, "CM");
                    arabianKeyMap.put(500, "D");
                    arabianKeyMap.put(400, "CD");
                    arabianKeyMap.put(100, "C");
                    arabianKeyMap.put(90, "XC");
                    arabianKeyMap.put(50, "L");
                    arabianKeyMap.put(40, "XL");
                    arabianKeyMap.put(10, "X");
                    arabianKeyMap.put(9, "IX");
                    arabianKeyMap.put(5, "V");
                    arabianKeyMap.put(4, "IV");
                    arabianKeyMap.put(1, "I");
                }
                public boolean isRoman(String number){
                    return romanKeyMap.containsKey(number.charAt(0));
                }
                public String intToRoman(int result) {
                    String roma_res = "";
                    int arab_num;
                    do {
                        arab_num = arabianKeyMap.floorKey(result);
                        roma_res += arabianKeyMap.get(arab_num);
                        result -= arab_num;
                    } while (result > 0);
                    return roma_res;
                }
                public int romanToInt(String roman_num) {
                    int end = roman_num.length() - 1;
                    char[] arr = roman_num.toCharArray();
                    int arab_num;
                    int result = romanKeyMap.get(arr[end]);
                    for (int i = end - 1; i >= 0; i--) {
                        arab_num = romanKeyMap.get(arr[i]);
                        if (arab_num < romanKeyMap.get(arr[i + 1])) {
                            result -= arab_num;
                        }
                        else {
                            result += arab_num;
                        }
                    }
                    return result;
                }
                }
            }