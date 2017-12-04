package ru.rusquant.api.impl;

import ru.rusquant.api.Connector;
import ru.rusquant.api.JavaToMqlAPI;
import ru.rusquant.data.mql.MqlDataObject;
import ru.rusquant.data.mql.MqlEcho;

/**
 *    Implementation of java to MQL% api.
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public class JavaToMqlConnector extends Connector implements JavaToMqlAPI
{

    @Override
    public MqlDataObject getEcho(String message)
    {
        return new MqlEcho("Hello world!");
    }
}
