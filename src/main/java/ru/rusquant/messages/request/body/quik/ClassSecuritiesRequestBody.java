package ru.rusquant.messages.request.body.quik;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.rusquant.messages.request.body.RequestBody;

/**
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
@JsonAutoDetect
public class ClassSecuritiesRequestBody extends RequestBody {

    private String classCode;
    private Integer firstIndex;
    private Integer lastIndex;

    public ClassSecuritiesRequestBody() {

    }

    public ClassSecuritiesRequestBody(String classCode, Integer firstIndex, Integer lastIndex) {
        this.classCode = classCode;
        this.firstIndex = firstIndex;
        this.lastIndex = lastIndex;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public Integer getFirstIndex() {
        return firstIndex;
    }

    public void setFirstIndex(Integer firstIndex) {
        this.firstIndex = firstIndex;
    }

    public Integer getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(Integer lastIndex) {
        this.lastIndex = lastIndex;
    }
}
