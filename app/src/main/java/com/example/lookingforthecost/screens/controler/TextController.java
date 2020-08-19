package com.example.lookingforthecost.screens.controler;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.Currency;

public class TextController {
    private boolean thousands = true;//тысячи
    private boolean tensThousands = true;//десятки тысяч
    private boolean hundredsThousands = true;//сотни тысяч
    private boolean million = true;//миллион
    private boolean tensMillions = true;// десятки миллионов
    int position = 0;
    private static final String CURRENCY = "руб.";

    public void formatInput(final EditText input2) {//метод форматирует введенную сумму в удобный для пользователя формат путем добавления пробелов (1 000,10 000 и тд)

        input2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = String.valueOf(input2.getText()).replaceAll(" ", "");
                StringBuilder sb = new StringBuilder(str);
                if (str.length() == 4 && thousands) {
                    sb.insert(1, " ");
                    million = true;
                    thousands = false;
                    tensThousands = true;
                    hundredsThousands = true;
                    tensMillions = true;
                    position = input2.getSelectionEnd();
                    input2.setText(sb);
                    carriageMove(input2, String.valueOf(sb), position);
                }
                if (str.length() == 5 && tensThousands) {
                    sb.insert(2, " ");
                    million = true;
                    thousands = true;
                    tensThousands = false;
                    hundredsThousands = true;
                    tensMillions = true;
                    position = input2.getSelectionEnd();
                    input2.setText(sb);
                    carriageMove(input2, String.valueOf(sb), position);
                } else if (str.length() == 6 && hundredsThousands) {
                    sb.insert(3, " ");
                    million = true;
                    tensThousands = true;
                    thousands = true;
                    hundredsThousands = false;
                    tensMillions = true;
                    position = input2.getSelectionEnd();
                    input2.setText(sb);
                    carriageMove(input2, String.valueOf(sb), position);
                } else if (str.length() == 7 && million) {
                    sb.insert(1, " ");
                    sb.insert(5, " ");
                    million = false;
                    tensThousands = true;
                    thousands = true;
                    hundredsThousands = true;
                    tensMillions = true;
                    position = input2.getSelectionEnd();
                    input2.setText(sb);
                    carriageMove(input2, String.valueOf(sb), position);
                } else if (str.length() == 8 && tensMillions) {
                    sb.insert(2, " ");
                    sb.insert(6, " ");
                    tensThousands = true;
                    thousands = true;
                    hundredsThousands = true;
                    million = true;
                    tensMillions = false;
                    position = input2.getSelectionEnd();
                    input2.setText(sb);
                    carriageMove(input2, String.valueOf(sb), position);
                } else {
                    tensThousands = true;
                    thousands = true;
                    hundredsThousands = true;
                    million = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }


    public static String getFormatStr(String str, int sum) {//метод вернет строку с отформатированной суммой (1 000,10 000 и тд)
        String sumStr = String.valueOf(sum);
        StringBuilder stringBuffer = new StringBuilder(sumStr);
        switch (sumStr.length()) {
            case 0:
                return "";
            case 4:
                stringBuffer.insert(1, " ");
                break;
            case 5:
                stringBuffer.insert(2, " ");
                break;
            case 6:
                stringBuffer.insert(3, " ");
                break;
            case 7:
                stringBuffer.insert(1, " ");
                stringBuffer.insert(5, " ");
                break;
            case 8:
                stringBuffer.insert(2, " ");
                stringBuffer.insert(6, " ");
                break;
        }
        return str + " " + stringBuffer.toString() + " " + CURRENCY;
    }

    public static String getFormatStr(int sum) {
        String sumStr = String.valueOf(sum);
        StringBuilder stringBuffer = new StringBuilder(sumStr);
        switch (sumStr.length()) {
            case 0:
                return "";
            case 4:
                stringBuffer.insert(1, " ");
                break;
            case 5:
                stringBuffer.insert(2, " ");
                break;
            case 6:
                stringBuffer.insert(3, " ");
                break;
            case 7:
                stringBuffer.insert(1, " ");
                stringBuffer.insert(5, " ");
                break;
            case 8:
                stringBuffer.insert(2, " ");
                stringBuffer.insert(6, " ");
                break;
        }
        return stringBuffer.toString() + " " + CURRENCY;
    }


    private void carriageMove(EditText input2, String str, int move) {//метод отвечает за возвращение коретки в позицию,в котрой она находилась до вставки отформатированной строки
        if (move > str.length()) {
            input2.setSelection(move - 1);
        } else if (move == str.length() - 1) {
            input2.setSelection(move + 1);
        } else {
            input2.setSelection(move);
        }

    }


}



