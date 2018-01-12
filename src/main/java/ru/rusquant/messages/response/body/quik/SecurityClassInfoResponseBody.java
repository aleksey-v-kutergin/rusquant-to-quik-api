package ru.rusquant.messages.response.body.quik;

import ru.rusquant.data.quik.table.SecurityClass;
import ru.rusquant.messages.response.body.ResponseBody;

/**
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class SecurityClassInfoResponseBody extends ResponseBody {

    private SecurityClass securityClass;

    public SecurityClassInfoResponseBody() {

    }

    public SecurityClassInfoResponseBody(SecurityClass securityClass) {
        this.securityClass = securityClass;
    }

    public SecurityClass getSecurityClass() {
        return securityClass;
    }

    public void setSecurityClass(SecurityClass securityClass) {
        this.securityClass = securityClass;
    }
}
