package ru.rusquant.api;

import ru.rusquant.data.mql.MqlDataObject;

/**
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public interface JavaToMqlAPI
{
    MqlDataObject getEcho(String message);
}
