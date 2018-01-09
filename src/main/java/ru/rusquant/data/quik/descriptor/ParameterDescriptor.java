package ru.rusquant.data.quik.descriptor;


/**
 * Class-descriptor to order the parameters of the current trading table.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class ParameterDescriptor extends Descriptor {

    private String parameterName;

    public ParameterDescriptor() {

    }

    public ParameterDescriptor(Long id, String classCode, String securityCode, String parameterName) {
        super(id, classCode, securityCode);
        this.parameterName = parameterName;
    }

    @Override
    public String toString() {
        return "ParameterDescriptor: {" +
                "id=" + id +
                ", classCode='" + classCode + '\'' +
                ", securityCode='" + securityCode + '\'' +
                ", parameterName='" + parameterName + '\'' +
                '}';
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }
}
