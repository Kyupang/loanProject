package com.fastcampus.loan.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO 클래스는 클라이언트와 직접 연결된 가방이다.
// repository로 직접 가지 않는다.
// 서비스 단에 연결된다. 클라이언트 입력- > 서비스 를 연결해주는 클래스다.
public class CounselDTO implements Serializable {

  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @Getter
  @Setter
  public static class Request {

    private String name;

    private String cellPhone;

    private String email;

    private String memo;

    private String address;

    private String addressDetail;

    private String zipCode;
  }

  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @Getter
  @Setter
  public static class Response {

    private Long counselId;

    private String name;

    private String cellPhone;

    private String email;

    private String memo;

    private String address;

    private String addressDetail;

    private String zipCode;

    private LocalDateTime appliedAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
  }
}
