# Лабораторная работа №1

Разработать имитационную модель функционирования СМО.

СМО представляет собой одноканальную разомкнутую систему (один генератор заявок и один обслуживающий аппарат). Буфер имеет бесконечную емкость.
Закон поступления (генерации заявок) и закон распределения времени обслуживания заявок задается в таблице и выбирается в соответствии с номером в списке группы.

В качестве исходных данных пользователь задает интенсивность поступления заявок и интенсивность обслуживания заявок. Программа должна выводить расчетную загрузку системы и фактическую, полученную по результатам моделирования. Пользователь должен иметь возможность задавать время моделирования.

Если параметры законов распределения отличны от интенсивности, то предусмотреть ввод интенсивностей с дальнейшим пересчетом в программе этих величин в параметры закона. В случае двухпараметрических законов пользователь задает интенсивность и ее разброс (среднеквадратическое отклонение).

Построить график зависимости выходного параметра (ср. время ожидания (пребывания) в зависимости от загрузки системы).

Предусмотреть наращивание системы путем добавления новых генераторов и обслуживающих аппаратов.

Подготовить отчет по лабораторной работе.

- Первый закон $-$ закон распределения интервалов времени между приходом сообщений (заявок)
- Второй закон $-$ закон распределения времени обслуживания заявок

№ варианта | Первый закон | Второй закон
--- | --- | ---
<u>1</u> | <u>Экспоненциальный</u> | <u>Экспоненциальный</u>
2 | Экспоненциальный | Вейбулла с параметром 2
3 | Равномерный | Равномерный
4 | Равномерный | Вейбулла с параметром 2
5 | Нормальный | Нормальный
6 | Нормальный | Вейбулла с параметром 2
7 | Экспоненциальный | Равномерный
8 | Экспоненциальный | Нормальный
9 | Равномерный | Нормальный
10 | Равномерный | Экспоненциальный
11 | Нормальный | Экспоненциальный
12 | Нормальный | Равномерный
13 | Рэлея | Равномерный
14 | Рэлея | Экспоненциальный
15 | Рэлея | Нормальный
16 | Рэлея | Рэлея
17 | Рэлея | Вейбулла с параметром 2
18 | Экспоненциальный | Рэлея
19 | Равномерный | Рэлея
20 | Нормальный | Рэлея
21 | Вейбулла с параметром 2 | Экспоненциальный
22 | Вейбулла с параметром 2 | Равномерный
23 | Вейбулла с параметром 2 | Рэлея
24 | Вейбулла с параметром 2 | Вейбулла с параметром 2
25 | Вейбулла с параметром 2 | Нормальный

Учесть, что рассматриваемые случайные величины принимают положительные значения (интервалы между приходом требований и времена обслуживания).
