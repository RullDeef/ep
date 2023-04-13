# ДФЭ $2^{4-1}_{IV}$

Генератор плана: $x_4 = x_1x_2x_3$

Определяющий контраст: $I = x_1x_2x_3x_4$

Разрешающая способность - 4.

Схема смешивания:

- $b_0 \longrightarrow \beta_0 + \beta_{1234}$
- $b_1 \longrightarrow \beta_1 + \beta_{234}$
- $b_2 \longrightarrow \beta_2 + \beta_{134}$
- $b_3 \longrightarrow \beta_3 + \beta_{124}$
- $b_4 \longrightarrow \beta_4 + \beta_{123}$
- $b_{12} \longrightarrow \beta_{12} + \beta_{34}$
- $b_{13} \longrightarrow \beta_{13} + \beta_{24}$
- $b_{14} \longrightarrow \beta_{14} + \beta_{23}$

Уравнение регрессии (полное):

$$
y = b_0x_0 + b_1x_1 + b_2x_2 + b_3x_3 + b_4x_4 + \\
+ b_{12}x_1x_2 + b_{13}x_1x_3 + b_{14}x_1x_4 + \\
+ b_{23}x_2x_3 + b_{24}x_2x_4 + b_{34}x_3x_4 + \\
+ b_{123}x_1x_2x_3 + b_{124}x_1x_2x_4 + \\
+ b_{134}x_1x_3x_4 + b_{234}x_2x_3x_4 + \\
+ b_{1234}x_1x_2x_3x_4
$$

Cчитаем, что взаимодействия второго и третьего порядка оказывают несущественное влияние на время ожидания.

