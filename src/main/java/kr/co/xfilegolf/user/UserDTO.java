package kr.co.xfilegolf.user;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author jason, Moon
 * @since 2016-10-08
 */
@Data
public class UserDTO {

    private Long id;
    private String role;
    private String agencyName;
    private String presidentName;
    private String agencyAddress;
    private String businessNumber;
    private String phoneNumber;
    private String mobilePhoneNumber;
    private LocalDateTime createdOn;
    private LocalDateTime lastModifiedOn;
    private boolean activation;
}
