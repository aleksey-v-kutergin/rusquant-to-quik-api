package ru.rusquant.data.quik;

import ru.rusquant.data.quik.types.QuikTableType;

/**
 *    Information about quik table.
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public class QuikTableInfo extends QuikDataObject
{
    private QuikTableType tableType;
    private Integer rowsCount;

    public QuikTableInfo()
    {

    }

    @Override
    public String toString()
    {
        return "QuikTableInfo: \n\t\t{" +
                "tableType=" + tableType +
                ", rowsCount=" + rowsCount +
                '}';
    }

    public QuikTableType getTableType()
    {
        return tableType;
    }

    public void setTableType(QuikTableType tableType)
    {
        this.tableType = tableType;
    }

    public Integer getRowsCount()
    {
        return rowsCount;
    }

    public void setRowsCount(Integer rowsCount)
    {
        this.rowsCount = rowsCount;
    }
}
