package kr.co.xfilegolf.user;

import org.jooq.tools.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.Valid;

/**
 * @author jason, Moon
 * @since 2016-10-22
 */
public class UserValidator {

    public static BindingResult validation(@Valid UserForm userForm, BindingResult result) {

        // ROLE_USER Error Check
        if (userForm.getRole().equals("ROLE_USER")) {

            if (StringUtils.isEmpty(userForm.getAddress())) {

                FieldError fieldError = new FieldError("userForm", "address", "주소는 반드시 입력해야 합니다.");

                result.addError(fieldError);
            }
        } else {
            if (StringUtils.isEmpty(userForm.getPresidentName())) {

                FieldError fieldError = new FieldError("userForm", "presidentName", "대표자명은 반드시 입력해야합니다.");

                result.addError(fieldError);
            }

            if (StringUtils.isEmpty(userForm.getAgencyName())) {

                FieldError fieldError = new FieldError("userForm", "agencyName", "대리점명은 반드시 입력해야합니다.");

                result.addError(fieldError);
            }

            if (StringUtils.isEmpty(userForm.getAgencyAddress())) {

                FieldError fieldError = new FieldError("userForm", "agencyAddress", "대리점 주소는 반드시 입력해야합니다.");

                result.addError(fieldError);
            }

            if (StringUtils.isEmpty(userForm.getBusinessNumber())) {

                FieldError fieldError = new FieldError("userForm", "businessNumber", "사업자등록번호는 반드시 입력해야합니다.");

                result.addError(fieldError);
            }

            if (StringUtils.isEmpty(userForm.getPhoneNumber())) {

                FieldError fieldError = new FieldError("userForm", "phoneNumber", "대표전화는 반드시 입력해야합니다.");

                result.addError(fieldError);
            }
        }

        return result;
    }
}
