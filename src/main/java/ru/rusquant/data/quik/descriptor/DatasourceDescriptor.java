package ru.rusquant.data.quik.descriptor;

import ru.rusquant.data.quik.types.DSParameterType;
import ru.rusquant.data.quik.types.TimeScale;

/**
 * Class-descriptor for quik datasource for OHLC prices.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class DatasourceDescriptor extends Descriptor {

    private TimeScale interval;
    private DSParameterType parameter;

    public DatasourceDescriptor() {

    }

    public DatasourceDescriptor(Long id,
                                String classCode,
                                String securityCode,
                                TimeScale interval,
                                DSParameterType parameter) {
        super(id, classCode, securityCode);
        this.interval = interval;
        this.parameter = parameter;
    }

    @Override
    public String toString() {
        return "DatasourceDescriptor{" +
                "id=" + id +
                "classCode='" + classCode + '\'' +
                ", securityCode='" + securityCode + '\'' +
                ", interval=" + interval +
                ", parameter=" + parameter +
                '}';
    }

    public TimeScale getInterval() {
        return interval;
    }

    public void setInterval(TimeScale interval) {
        this.interval = interval;
    }

    public DSParameterType getParameter() {
        return parameter;
    }

    public void setParameter(DSParameterType parameter) {
        this.parameter = parameter;
    }
}
