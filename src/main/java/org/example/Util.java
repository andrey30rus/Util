package org.example;
import java.util.List;
import java.util.Map;

/*
-o (output) Args() ex. -o /some/path
-p (prefix) -p result_

-a (append) bool
По умолчанию файлы результатов перезаписываются.
С помощью опции -a можно задать режим добавления в существующие файлы.

В процессе фильтрации данных необходимо собирать статистику по каждому типу данных.
Статистика должна поддерживаться двух видов: краткая и полная.
Выбор статистики производится опциями -s и -f соответственно.
Краткая статистика содержит только количество элементов записанных в исходящие файлы.
Полная статистика для чисел дополнительно содержит минимальное и максимальное значения, сумма и среднее.
Полная статистика для строк, помимо их количества,
содержит также размер самой короткой строки и самой длинной.
Статистику по каждому типу отфильтрованных данных утилита должна вывести в консоль.


Файлы с результатами должны создаваться по мере необходимости.
Если какого-то типа данных во входящих файлах нет, то и создавать исходящий файл,
который будет заведомо пустым, не нужно.


Все возможные виды ошибок должны быть обработаны. Программа не должна «падать».
Если после ошибки продолжить выполнение невозможно, программа должна сообщить об этом пользователю с указанием причины неудачи. Частичная обработка при наличии ошибок более предпочтительна чем останов программы. Код программы должен быть «чистым».


Для реализации необходимо использовать язык программирования Java,
допустимо использовать стандартные системы сборки проекта (Maven, Gradle)
 Решение принимается в виде исходного кода проекта.

К решению должна прилагаться инструкция по запуску.
В ней можно отображать особенности реализации, не уточненные в задании.
В частности, в инструкции необходимо указывать:
• версию Java;
• при использовании системы сборки – указать систему сборки и ее версию;
• при использовании сторонних библиотек указать их название и версию, а также приложить ссылки на такие библиотеки (можно в формате зависимостей системы сборки).  

  Пример запуска утилиты

java -jar util.jar -s -a -p sample- in1.txt in2.txt

sample-integers.txt
45
1234567890123456789
100500

sample-ﬂoats.txt
1.528535047E-25
3.1415
-0.001

sample-strings.txt
Lorem ipsum dolor sit amet
Нормальная форма числа с плавающей запятой
Пример
Long
consectetur adipiscing
тестовое задание
  */



public class Util {
    private static CLIParser parser = new CLIParser();
    public static void main(String[] args) {
        parser.parse(args);
        Map<String, String> options = parser.getOptionsMap();
        List<String> targetFilesList = parser.getTargetFilesList();
//        System.out.println(options);
//        System.out.println(targetFilesList);
        System.out.println("------");
        FileHandler fileHandler = new FileHandler(options, targetFilesList);
//        fileHandler.processTargetFiles();
        fileHandler.processFilesList();
    }
}
