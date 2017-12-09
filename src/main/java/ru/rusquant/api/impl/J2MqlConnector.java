package ru.rusquant.api.impl;

import ru.rusquant.api.Connector;
import ru.rusquant.api.J2MqlAPI;
import ru.rusquant.data.mql.MqlDataObject;
import ru.rusquant.data.mql.MqlEcho;

/**
 *    Implementation of java to MQL% api.
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public class J2MqlConnector extends Connector implements J2MqlAPI
{

    @Override
    public MqlDataObject getEcho(String message)
    {
        return new MqlEcho("Hello world!");
    }
}
