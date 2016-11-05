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

    public static BindingResult validation(@Valid UserRegisterForm userRegisterForm, BindingResult result) {

        // ROLE_USER Error Check
        if (userRegisterForm.getRole().equals("ROLE_USER")) {

            if (StringUtils.isEmpty(userRegisterForm.getAddress())) {

                FieldError fieldError = new FieldError("userRegisterForm", "address", "주소는 반드시 입력해야 합니다.");

                result.addError(fieldError);
            }

            if (StringUtils.isEmpty(userRegisterForm.getPersonInCharge())) {

                FieldError fieldError = new FieldError("userRegisterForm", "personInCharge", "이름은 반드시 입력해야 합니다.");

                result.addError(fieldError);
            }
        } else {
            if (StringUtils.isEmpty(userRegisterForm.getPresidentName())) {

                FieldError fieldError = new FieldError("userRegisterForm", "presidentName", "대표자명은 반드시 입력해야합니다.");

                result.addError(fieldError);
            }

            if (StringUtils.isEmpty(userRegisterForm.getAgencyName())) {

                FieldError fieldError = new FieldError("userRegisterForm", "agencyName", "대리점명은 반드시 입력해야합니다.");

                result.addError(fieldError);
            }

            if (StringUtils.isEmpty(userRegisterForm.getAgencyAddress())) {

                FieldError fieldError = new FieldError("userRegisterForm", "agencyAddress", "대리점 주소는 반드시 입력해야합니다.");

                result.addError(fieldError);
            }

            if (StringUtils.isEmpty(userRegisterForm.getBusinessNumber())) {

                FieldError fieldError = new FieldError("userRegisterForm", "businessNumber", "사업자등록번호는 반드시 입력해야합니다.");

                result.addError(fieldError);
            }

            if (StringUtils.isEmpty(userRegisterForm.getPersonInCharge())) {

                FieldError fieldError = new FieldError("userRegisterForm", "personInCharge", "담당자명은 반드시 입력해야 합니다.");

                result.addError(fieldError);
            }

            if (StringUtils.isEmpty(userRegisterForm.getPhoneNumber())) {

                FieldError fieldError = new FieldError("userRegisterForm", "phoneNumber", "대표전화는 반드시 입력해야합니다.");

                result.addError(fieldError);
            }
        }

        return result;
    }
}
