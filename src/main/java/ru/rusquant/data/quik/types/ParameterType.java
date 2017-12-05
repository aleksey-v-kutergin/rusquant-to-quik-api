package ru.rusquant.data.quik.types;

/**
 *    Types of .
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public enum ParameterType
{
    STATUS,                  /** Статус **/
    LOTSIZE,                 /** Размер лота **/
    BID,                     /** Лучшая цена спроса **/
    BIDDEPTH,                /** Спрос по лучшей цене **/
    BIDDEPTHT,               /** Суммарный спрос **/
    NUMBIDS,                 /** Количество заявок на покупку **/
    OFFER,                   /** Лучшая цена предложения **/
    OFFERDEPTH,              /** Предложение по лучшей цене **/
    OFFERDEPTHT,             /** Суммарное предложение **/
    NUMOFFERS,               /** Количество заявок на продажу **/
    OPEN,                    /** Цена открытия **/
    HIGH,                    /** Максимальная цена сделки **/
    LOW,                     /** Минимальная цена сделки **/
    LAST,                    /** Цена последней сделки **/
    CHANGE,                  /** Разница цены последней к предыдущей сессии **/
    QTY,                     /** Количество бумаг в последней сделке **/
    TIME,                    /** Время последней сделки **/
    VOLTODAY,                /** Количество бумаг в обезличенных сделках **/
    VALTODAY,                /** Оборот в деньгах **/
    TRADINGSTATUS,           /** Состояние сессии **/
    VALUE,                   /** Оборот в деньгах последней сделки **/
    WAPRICE,                 /** Средневзвешенная цена **/
    HIGHBID,                 /** Лучшая цена спроса сегодня **/
    LOWOFFER,                /** Лучшая цена предложения сегодня **/
    NUMTRADES,               /** Количество сделок за сегодня **/
    PREVPRICE,               /** Цена закрытия **/
    PREVWAPRICE,             /** Предыдущая оценка **/
    CLOSEPRICE,              /** Цена периода закрытия **/
    LASTCHANGE,              /** % изменения от закрытия **/
    PRIMARYDIST,             /** Размещение **/
    ACCRUEDINT,              /** Накопленный купонный доход **/
    YIELD,                   /** Доходность последней сделки **/
    COUPONVALUE,             /** Размер купона **/
    YIELDATPREVWAPRICE,      /** Доходность по предыдущей оценке **/
    YIELDATWAPRICE,          /** Доходность по оценке **/
    PRICEMINUSPREVWAPRICE,   /** Разница цены последней к предыдущей оценке **/
    CLOSEYIELD,              /** Доходность закрытия **/
    CURRENTVALUE,            /** Текущее значение индексов Московской Биржи **/
    LASTVALUE,               /** Значение индексов Московской Биржи на закрытие предыдущего дня **/
    LASTTOPREVSTLPRC,        /** Разница цены последней к предыдущей сессии **/
    PREVSETTLEPRICE,         /** Предыдущая расчетная цена **/
    PRICEMVTLIMIT,           /** Лимит изменения цены **/
    PRICEMVTLIMITT1,         /** Лимит изменения цены T1 **/
    MAXOUTVOLUME,            /** Лимит объема активных заявок (в контрактах) **/
    PRICEMAX,                /** Максимально возможная цена **/
    PRICEMIN,                /** Минимально возможная цена **/
    NEGVALTODAY,             /** Оборот внесистемных в деньгах **/
    NEGNUMTRADES,            /** Количество внесистемных сделок за сегодня **/
    NUMCONTRACTS,            /** Количество открытых позиций **/
    CLOSETIME,               /** Время закрытия предыдущих торгов (для индексов РТС) **/
    OPENVAL,                 /** Значение индекса РТС на момент открытия торгов **/
    CHNGOPEN,                /** Изменение текущего индекса РТС по сравнению со значением открытия **/
    CHNGCLOSE,               /** Изменение текущего индекса РТС по сравнению со значением закрытия **/
    BUYDEPO,                 /** Гарантийное обеспечение продавца **/
    SELLDEPO,                /** Гарантийное обеспечение покупателя **/
    CHANGETIME,              /** Время последнего изменения **/
    SELLPROFIT,              /** Доходность продажи **/
    BUYPROFIT,               /** Доходность покупки **/
    TRADECHANGE,             /** Разница цены последней к предыдущей сделки (FORTS, ФБ СПБ, СПВБ) **/
    FACEVALUE,               /** Номинал (для бумаг СПВБ) **/
    MARKETPRICE,             /** Рыночная цена вчера **/
    MARKETPRICETODAY,        /** Рыночная цена **/
    NEXTCOUPON,              /** Дата выплаты купона **/
    BUYBACKPRICE,            /** Цена оферты **/
    BUYBACKDATE,             /** Дата оферты **/
    ISSUESIZE,               /** Объем обращения **/
    PREVDATE,                /** Дата предыдущего торгового дня **/
    DURATION,                /** Дюрация **/
    LOPENPRICE,              /** Официальная цена открытия **/
    LCURRENTPRICE,           /** Официальная текущая цена **/
    LCLOSEPRICE,             /** Официальная цена закрытия **/
    QUOTEBASIS,              /** Тип цены **/
    PREVADMITTEDQUOT,        /** Признаваемая котировка предыдущего дня **/
    LASTBID,                 /** Лучшая спрос на момент завершения периода торгов **/
    LASTOFFER,               /** Лучшее предложение на момент завершения торгов **/
    PREVLEGALCLOSEPR,        /** Цена закрытия предыдущего дня **/
    COUPONPERIOD,            /** Длительность купона **/
    MARKETPRICE2,            /** Рыночная цена 2 **/
    ADMITTEDQUOTE,           /** Признаваемая котировка **/
    BGOP,                    /** БГО по покрытым позициям **/
    BGONP,                   /** БГО по непокрытым позициям **/
    STRIKE,                  /** Цена страйк **/
    STEPPRICET,              /** Стоимость шага цены **/
    STEPPRICE,               /** Стоимость шага цены (для новых контрактов FORTS и RTS Standard) **/
    SETTLEPRICE,             /** Расчетная цена **/
    OPTIONTYPE,              /** Тип опциона **/
    OPTIONBASE,              /** Базовый актив **/
    VOLATILITY,              /** Волатильность опциона **/
    THEORPRICE,              /** Теоретическая цена **/
    PERCENTRATE,             /** Агрегированная ставка **/
    ISPERCENT,               /** Тип цены фьючерса **/
    CLSTATE,                 /** Статус клиринга **/
    CLPRICE,                 /** Котировка последнего клиринга **/
    STARTTIME,               /** Начало основной сессии **/
    ENDTIME,                 /** Окончание основной сессии **/
    EVNSTARTTIME,            /** Начало вечерней сессии **/
    EVNENDTIME,              /** Окончание вечерней сессии **/
    MONSTARTTIME,            /** Начало утренней сессии **/
    MONENDTIME,              /** Окончание утренней сессии **/
    CURSTEPPRICE,            /** Валюта шага цены **/
    REALVMPRICE,             /** Текущая рыночная котировка **/
    MARG,                    /** Маржируемый **/
    EXPDATE,                 /** Дата исполнения инструмента **/
    CROSSRATE,               /** Курс **/
    BASEPRICE,               /** Базовый курс **/
    HIGHVAL,                 /** Максимальное значение (RTSIND) **/
    LOWVAL,                  /** Минимальное значение (RTSIND) **/
    ICHANGE,                 /** Изменение (RTSIND) **/
    IOPEN,                   /** Значение на момент открытия (RTSIND) **/
    PCHANGE,                 /** Процент изменения (RTSIND) **/
    OPENPERIODPRICE,         /** Цена предторгового периода **/
    MIN_CURR_LAST,           /** Минимальная текущая цена **/
    SETTLECODE,              /** Код расчетов по умолчанию **/
    STEPPRICECL,             /** Стоимость шага цены для клиринга **/
    STEPPRICEPRCL,           /** Стоимость шага цены для промклиринга **/
    MIN_CURR_LAST_TI,        /** Время изменения минимальной текущей цены **/
    PREVLOTSIZE,             /** Предыдущее значение размера лота **/
    LOTSIZECHANGEDAT,        /** Дата последнего изменения размера лота **/
    CLOSING_AUCTION_PRICE,   /** Цена послеторгового аукциона **/
    CLOSING_AUCTION_VOLUME,  /** Количество в сделках послеторгового аукциона **/
    LONGNAME,                /** Полное название бумаги **/
    SHORTNAME,               /** Краткое название бумаги **/
    CODE,                    /** Код бумаги **/
    CLASSNAME,               /** Название класса **/
    CLASS_CODE,              /** Код класса **/
    TRADE_DATE_CODE,         /** Дата торгов **/
    MAT_DATE,                /** Дата погашения **/
    DAYS_TO_MAT_DATE,        /** Число дней до погашения **/
    SEC_FACE_VALUE,          /** Номинал бумаги **/
    SEC_FACE_UNIT,           /** Валюта номинала **/
    SEC_SCALE,               /** Точность цены **/
    SEC_PRICE_STEP,          /** Минимальный шаг цены **/
    SECTYPE;                 /** Тип инструмента **/


    public static boolean contains(String parameter)
    {
        for(ParameterType type : ParameterType.values())
        {
            if( type.toString().equalsIgnoreCase(parameter) )
            {
                return true;
            }
        }
        return false;
    }

    public static String getAvailableParameters()
    {
        String availableParameters = "[ ";
        int counter = 1;
        for(ParameterType type : ParameterType.values())
        {
            availableParameters += "\t\t" + type.toString().toUpperCase();
            if(counter < ParameterType.values().length) { availableParameters += ", "; }
            counter++;
        }
        availableParameters += " ]";
        return availableParameters;
    }
}
