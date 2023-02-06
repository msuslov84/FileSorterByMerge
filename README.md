## Программа сортировки слиянием нескольких файлов
### Описание:
* Входные файлы должны содержать данные одного из двух видов: целые числа или строки. Данные 
должны быть записаны в столбик (каждая строка файла – новый элемент). Строки могут содержать 
любые не пробельные символы, строки с пробелами считаются ошибочными. Также считается, что 
файлы предварительно отсортированы (в противном случае выполняется частичная обработка данных).
* Результатом работы программы является новый файл с объединенным содержимым входных файлов, 
отсортированным по возрастанию или убыванию путем сортировки слиянием. Если содержимое исходных 
файлов не позволяет произвести сортировку слиянием (например, нарушен порядок сортировки), 
производится частичная сортировка (насколько это возможно).
* Выходной файл содержит отсортированные данные даже в случае ошибок, при условии потери 
ошибочных данных.
* В программе реализовано простое логгирование происходящих ошибок, старта и завершения выполнения 
как в консоль, так и во внешний файл (log.log).
* Алгоритм устойчив к большим файлам, не помещающимся целиком в оперативную память, т.к. 
использует построчное получение данных из каждого файла, их сравнение и запись в результирующий
файл.

### Параметры (при запуске через аргументы командной строки, по порядку):
* Режим сортировки (-a или -d), необязательный, по умолчанию сортируем по возрастанию.
* Тип данных (-s или -i), обязательный.
* Имя выходного файла, обязательное.
* остальные параметры – имена входных файлов, не менее одного.

### Примеры запуска из командной строки для Windows:
* java -jar TestTask.jar -i out.txt in1.txt in2.txt (для целых чисел по возрастанию)
* java -jar TestTask.jar -s -a out.txt st1.txt st2.txt (для строк по возрастанию)
* java -jar TestTask.jar -d -s out.txt st1.txt st2.txt st3.txt (для строк по по убыванию)

### Техническая информация:
* Версия Java 8 (Oracle OpenJDK version 1.8.0_311)
