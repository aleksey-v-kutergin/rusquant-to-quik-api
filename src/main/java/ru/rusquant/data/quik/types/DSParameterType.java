package ru.rusquant.data.quik.types;


/**
 * List of available parameters of CreateDataSource() function from QLUA specification.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public enum DSParameterType {
    LOTSIZE,                // Размер лота
    BID,                    // Лучшая цена спроса
    BIDDEPTH,               // Спрос по лучшей цене
    BIDDEPTHT,              // Суммарный спрос
    NUMBIDS,                // Количество заявок на покупку
    OFFER,                  // Лучшая цена предложения
    OFFERDEPTH,             // Предложение по лучшей цене
    OFFERDEPTHT,            // Суммарное предложение
    NUMOFFERS,              // Количество заявок на продажу
    OPEN,                   // Цена открытия
    HIGH,                   // Максимальная цена сделки
    LOW,                    // Минимальная цена сделки
    LAST,                   // Цена последней сделки
    CHANGE,                 // Разница цены последней к предыдущей сессии
    QTY,                    // Количество бумаг в последней сделке
    VOLTODAY,               // Количество бумаг в обезличенных сделках
    VALTODAY,               // Оборот в деньгах
    VALUE,                  // Оборот в деньгах последней сделки
    WAPRICE,                // Средневзвешенная цена
    HIGHBID,                // Лучшая цена спроса сегодня
    LOWOFFER,               // Лучшая цена предложения сегодня
    NUMTRADES,              // Количество сделок за сегодня
    PREVPRICE,              // Цена закрытия
    PREVWAPRICE,            // Предыдущая оценка
    LASTCHANGE,             // % изменения от закрытия
    LASTTOPREVSTLPRC,       // Разница цены последней к предыдущей сессии
    MARKETPRICETODAY,       // Рыночная цена
    CLOSEPRICE,             // Цена периода закрытия
    PRICEMAX,               // Максимально возможная цена
    PRICEMIN,               // Минимально возможная цена
    BASEPRICE,              // Базовый курс
    PRICEMINUSPREVWAPRICE,  // Разница цены последней к предыдущей оценке
    PREVSETTLEPRICE,        // Предыдущая расчетная цена
    NUMCONTRACTS,           // Количество открытых позиций
    BUYDEPO,                // Гарантийное обеспечение продавца
    SELLDEPO,               // Гарантийное обеспечение покупателя
    BGOP,                   // БГО по покрытым позициям
    BGONP,                  // БГО по непокрытым позициям
    STRIKE,                 // Цена страйк
    STEPPRICET,             // Стоимость шага цены
    STEPPRICE,              // Стоимость шага цены (для новых контрактов FORTS и RTS Standard)
    VOLATILITY,             // Волатильность опциона
    THEORPRICE,             // Теоретическая цена
    CLPRICE,                // Котировка последнего клиринга
    REALVMPRICE,            // Текущая рыночная котировка
    STEPPRICECL,            // Стоимость шага цены для клиринга
    STEPPRICEPRCL,          // Стоимость шага цены для промклиринга
    SEC_FACE_VALUE,         // Номинал бумаги
    SEC_SCALE,              // Точность цены
    SETTLEPRICE,            // Расчетная цена
    PERCENTRATE;            // Агрегированная ставка

    public static boolean contains(String parameter) {
        for (DSParameterType type : DSParameterType.values()) {
            if (type.toString().equalsIgnoreCase(parameter)) {
                return true;
            }
        }
        return false;
    }

    public static String getAvailableDSParameters() {
        String availableDSParameters = "[ ";
        int counter = 1;
        for (DSParameterType type : DSParameterType.values()) {
            availableDSParameters += "\t\t" + type.toString().toUpperCase();
            if (counter < DSParameterType.values().length) {
                availableDSParameters += ", ";
            }
            counter++;
        }
        availableDSParameters += " ]";
        return availableDSParameters;
    }
}
